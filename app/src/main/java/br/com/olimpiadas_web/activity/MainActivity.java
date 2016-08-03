package br.com.olimpiadas_web.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import br.com.olimpiadas_web.R;
import br.com.olimpiadas_web.adapter.NoticiaAdapter;
import br.com.olimpiadas_web.api.ApiClient;
import br.com.olimpiadas_web.api.INoticia;
import br.com.olimpiadas_web.model.Noticia;
import br.com.olimpiadas_web.parser.NoticiaParser;
import br.com.olimpiadas_web.util.ScrollListener;
import br.com.olimpiadas_web.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bruno.santiago on 30/07/2016.
 */
public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private final String TAG = "NOTICIA";
    private INoticia iNoticia;

    private MaterialDialog dialog;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private NoticiaAdapter noticiaAdapter;
    private NoticiaParser noticiaParser;
    private List<Noticia> noticias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);

        swipeRefreshLayout.setOnRefreshListener(this);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(MainActivity.this);
        mLayoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        noticias = new ArrayList<>();
        initNoticias();

        mRecyclerView.addOnScrollListener(new ScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                //TODO corrigir posicionamento
                nextOrPrevious(noticiaParser.getPrevious());
                mRecyclerView.scrollToPosition(noticias.size()-1);
            }
        });
    }

    protected void nextOrPrevious(final String nextPrevious) {
        if (nextPrevious != null && !nextPrevious.isEmpty()) {
            iNoticia = ApiClient.getRetrofit(Utils.BASE_URL).create(INoticia.class);
            Call<NoticiaParser> call = iNoticia.getNextPrevious(nextPrevious);
            parseNoticia(call);
        }

    }

    protected void initCardView(final List<Noticia> noticias) {
        noticiaAdapter = new NoticiaAdapter(this, noticias);
        mRecyclerView.setAdapter(noticiaAdapter);
    }

    private void initNoticias() {
        iNoticia = ApiClient.getRetrofit(Utils.BASE_URL).create(INoticia.class);

        Call<NoticiaParser> call = iNoticia.getNoticias(10);
        showAlertDialog(this, call);

    }

    protected void showAlertDialog(Context context, final Call call) {
        dialog = new MaterialDialog.Builder(context)
                .title("Olimpiadas Web")
                .content("Baixando conteúdo...")
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .cancelable(false)
                .showListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(final DialogInterface dialog) {
                        parseNoticia(call);
                    }
                })
                .show();
    }

    protected void parseNoticia(final Call call) {
        call.enqueue(new Callback<NoticiaParser>() {
            @Override
            public void onResponse(Call<NoticiaParser> call, Response<NoticiaParser> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        noticiaParser = response.body();
                        noticias.addAll(noticiaParser.getNoticias());
                        Set<Noticia> noticiaSet = new LinkedHashSet<>(noticias);
                        noticias.clear();
                        noticias.addAll(noticiaSet);
                        initCardView(noticias);
                        Log.i(TAG, response.body().toString());
                    }
                }
                closeDialog(dialog);
            }

            @Override
            public void onFailure(Call<NoticiaParser> call, Throwable t) {
                closeDialog(dialog);
            }
        });
    }

    protected void closeDialog(final DialogInterface dialog) {
        if (dialog != null) {
            dialog.dismiss();
        }
        if (swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
        nextOrPrevious(noticiaParser.getNext());
    }
}
