package com.example.lenovo.onlinetrending;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Lenovo on 24-06-2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    ArrayList<Article> articlesarray;
    Context c;

    public NewsAdapter(ArrayList<Article> articlesarray, Context c) {
        this.articlesarray = articlesarray;
        this.c = c;
    }

    @Override
    public NewsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.MyViewHolder holder, int position) {
        if(!articlesarray.get(position).getUrlToImage().toString().isEmpty()) {
            Picasso.with(c).load(articlesarray.get(position).getUrlToImage().toString()).error(R.drawable.news).into(holder.newsImage);
        }
        /*
       holder.newsdescription.setText(articlesarray.get(position).description);
*/
        holder.title.setText(articlesarray.get(position).title);
    }

    @Override
    public int getItemCount() {
        return articlesarray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView newsdescription;
        TextView title;
        ImageView newsImage;
        public MyViewHolder(View itemView) {
            super(itemView);
            newsImage=(ImageView) itemView.findViewById(R.id.news_image);
/*
            newsdescription=(TextView) itemView.findViewById(R.id.news_tv);
*/
            title=(TextView) itemView.findViewById(R.id.news_title);
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i=getLayoutPosition();
                    Intent intent=new Intent(c,detailactivty.class);
                    intent.putExtra("layoutposition",getLayoutPosition());
                    intent.putParcelableArrayListExtra("position",articlesarray);
                    c.startActivity(intent);
                }
            });
        }
    }
}
