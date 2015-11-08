package com.example.alejandro.navigationdrawersample.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alejandro.navigationdrawersample.R;
import com.example.alejandro.navigationdrawersample.model.Post;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alejandro on 6/11/2015.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    private static final String TAG = PostAdapter.class.getSimpleName();

    public PostAdapter(Post[] posts, OnPostClickedCallback callback) {
        this.posts = posts;
        this.callback = callback;
    }

    private OnPostClickedCallback callback;
    protected Post[] posts;

    @Override
    public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_item_container, parent, false);
        return new PostHolder(item);

    }

    @Override
    public void onBindViewHolder(final PostHolder holder, final int position) {
        holder.tvTitle.setText(posts[position].getTitle());
        holder.tvBody.setText(posts[position].getBody());
        holder.mainContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mainContainer.setTransitionName("post_transition" + posts[position].getId());
                callback.onPostClicked(posts[position], holder.mainContainer.getTransitionName(), v);

            }
        });


    }

    @Override
    public int getItemCount() {
        return posts.length;
    }

    protected static class PostHolder extends RecyclerView.ViewHolder {
        @Bind(value = R.id.lst_post_title)
        protected TextView tvTitle;
        @Bind(value = R.id.lst_post_body)
        protected TextView tvBody;
        @Bind(value = R.id.card_post)
        protected CardView mainContainer;


        public PostHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public interface OnPostClickedCallback {
        public void onPostClicked(Post p, String transitionName, View v);
    }


}
