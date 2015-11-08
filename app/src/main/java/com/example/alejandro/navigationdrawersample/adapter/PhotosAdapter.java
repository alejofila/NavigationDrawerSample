package com.example.alejandro.navigationdrawersample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.alejandro.navigationdrawersample.R;
import com.example.alejandro.navigationdrawersample.model.Photo;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alejandro on 8/11/2015.
 */
public class PhotosAdapter  extends RecyclerView.Adapter<PhotosAdapter.PhotoHolder>{


    public PhotosAdapter(Photo[] photos, Context context){
        this.photos = photos;
        this.context = context;
    }
    private Photo photos[];
    private Context context;


    @Override
    public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item,parent,false);

        return new PhotoHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(PhotoHolder holder, int position) {
        Picasso.with(context).load(photos[position].getUrl())
                .placeholder(R.drawable.ic_loading)
                .fit()
                .into(holder.photo);

    }

    @Override
    public int getItemCount() {
        return photos.length;
    }

    public static class PhotoHolder extends RecyclerView.ViewHolder{
        @Bind(value = R.id.img_photo)
        protected ImageView photo;

        public PhotoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
