package br.com.olimpiadas_web.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import br.com.olimpiadas_web.R;
import br.com.olimpiadas_web.adapter.NoticiaAdapter;
import br.com.olimpiadas_web.api.ApiClient;
import br.com.olimpiadas_web.api.INoticia;
import br.com.olimpiadas_web.model.Noticia;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bruno.santiago on 30/07/2016.
 */
public class MainActivity extends AppCompatActivity {

    private final String TAG = "NOTICIA";
    private INoticia iNoticia;

    private MaterialDialog dialog;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private NoticiaAdapter noticiaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        initNoticias();
    }

    protected void initCardView(final List<Noticia> noticias) {

        noticiaAdapter = new NoticiaAdapter(this, noticias);
        mRecyclerView.setAdapter(noticiaAdapter);
    }

    private void initNoticias() {
        iNoticia = ApiClient.getRetrofit().create(INoticia.class);

        Call<List<Noticia>> call = iNoticia.getNoticias();


        showAlertDialog(this, call);


        //closeDialog();
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
                        call.enqueue(new Callback<List<Noticia>>() {
                            @Override
                            public void onResponse(Call<List<Noticia>> call, Response<List<Noticia>> response) {
                                if (response.isSuccessful()) {
                                    initCardView(response.body());
                                    Log.i(TAG, response.body().toString());
                                }
                                closeDialog(dialog);
                            }

                            @Override
                            public void onFailure(Call<List<Noticia>> call, Throwable t) {
                                closeDialog(dialog);
                            }
                        });
                    }
                })
                .show();
    }


    protected void closeDialog(final DialogInterface dialog) {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
}
