package br.com.olimpiadas_web.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import br.com.olimpiadas_web.R;
import br.com.olimpiadas_web.activity.NoticiaActivity;
import br.com.olimpiadas_web.model.Noticia;
import br.com.olimpiadas_web.util.DataUtil;

/**
 * Created by bruno.santiago on 30/07/2016.
 */
public class NoticiaAdapter extends RecyclerView.Adapter<NoticiaAdapter.NoticiaViewHolder> {

    private List<Noticia> itens = new ArrayList<>();
    private Context context;

    private LayoutInflater layoutInflater;

    public NoticiaAdapter(Context context, List<Noticia> itens) {
        this.itens = itens;
        this.context = context;
    }

    protected void loadImage(String imgUrl, ImageView ivImg) {
        if (imgUrl != null && !imgUrl.isEmpty()) {
            Glide.with(ivImg.getContext())
                    .load(imgUrl)
                    .crossFade()
                    .into(ivImg);
        } else {
            Glide.with(ivImg.getContext())
                    .load(R.mipmap.ic_launcher)
                    .crossFade()
                    .into(ivImg);
        }
    }

    @Override
    public NoticiaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noticia, parent, false);
        NoticiaViewHolder viewHolder = new NoticiaViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NoticiaViewHolder holder, int position) {
        Noticia item = itens.get(position);


        holder.tvTitulo.setText(item.getTitle());
        holder.tvDescription.setText(item.getBody());
        holder.tvData.setText(DataUtil.getDate(item.getPubDate()));
        loadImage(item.getImg(), holder.ivImg);
    }

    @Override
    public int getItemCount() {
        if (itens == null) {
            return 0;
        }
        return itens.size();
    }

    @Override
    public void onViewDetachedFromWindow(NoticiaViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.mItemView.clearAnimation();
    }


    public class NoticiaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTitulo,tvDescription,tvData;
        private ImageView ivImg;
        private View mItemView;

        public NoticiaViewHolder(View view) {

            super(view);
            tvTitulo = (TextView) view.findViewById(R.id.tvTitulo);
            tvDescription = (TextView) view.findViewById(R.id.tvDescription);
            tvData = (TextView) view.findViewById(R.id.tvData);
            ivImg = (ImageView) view.findViewById(R.id.ivImg);
            this.mItemView = view;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, NoticiaActivity.class);
            intent.putExtras(getItemBundle(getAdapterPosition()));
            context.startActivity(intent);
        }

        private Bundle getItemBundle(int position) {
            Noticia item = itens.get(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("noticia", item);
            return bundle;
        }
    }
}
