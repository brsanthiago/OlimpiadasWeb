package br.com.olimpiadas_web.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import br.com.olimpiadas_web.R;
import br.com.olimpiadas_web.model.Noticia;
import br.com.olimpiadas_web.util.DataUtil;

public class NoticiaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvConteudo;
    private TextView tvTitulo;
    private TextView tvData;
    private TextView tvCategoria;
    private ImageView imgArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia);

        tvConteudo = (TextView) findViewById(R.id.tvConteudo);
        tvTitulo = (TextView) findViewById(R.id.tvTitulo);
        tvData = (TextView) findViewById(R.id.tvData);
        tvCategoria = (TextView) findViewById(R.id.tvCategoria);
        imgArticle = (ImageView) findViewById(R.id.image_view_article);


        Noticia noticia = geNoticia();
        setToolbar(noticia);
        loadItem(noticia);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setToolbar(final Noticia noticia) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setSubtitle(noticia.getTitle());
        toolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void loadItem(final Noticia noticia) {

        tvTitulo.setText(noticia.getTitle());
        //tvData.setText(DataUtil.getDate(noticia.getPubDate()));
        tvConteudo.setText(noticia.getBody());
        loadImage(noticia.getImg(),imgArticle);
    }

    protected void loadImage(String imgUrl, ImageView ivImg) {
        if (imgUrl != null && !imgUrl.isEmpty()) {
            Glide.with(ivImg.getContext())
                    .load(imgUrl)
                    .crossFade()
                    .into(ivImg);
        } else {
            Glide.with(ivImg.getContext())
                    .load(R.drawable.md_transparent)
                    .crossFade()
                    .into(ivImg);
        }
    }

    private Noticia geNoticia() {
        Noticia noticia = (Noticia) getIntent().getSerializableExtra("noticia");
        return noticia;
    }
}
