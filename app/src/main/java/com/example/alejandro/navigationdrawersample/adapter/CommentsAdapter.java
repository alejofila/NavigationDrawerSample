package com.example.alejandro.navigationdrawersample.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alejandro.navigationdrawersample.R;
import com.example.alejandro.navigationdrawersample.model.Comment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alejandro on 7/11/2015.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentHolder> {



    public CommentsAdapter(Comment[] comments){
        this.comments = comments;
    }

    private Comment[] comments;


    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent,false);
        return new CommentHolder(item);
    }


    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        holder.txtCommentBody.setText(comments[position].getBody());
        holder.txtCommentEmail.setText(comments[position].getEmail());

    }


    @Override
    public int getItemCount() {
        return comments.length;
    }

    public static class CommentHolder extends RecyclerView.ViewHolder{

        @Bind(value = R.id.comment_body)
        protected TextView txtCommentBody;
        @Bind(value = R.id.comment_email)
        protected TextView txtCommentEmail;
        @Bind(value = R.id.card_comment)
        protected CardView commentContainer;

        public CommentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
