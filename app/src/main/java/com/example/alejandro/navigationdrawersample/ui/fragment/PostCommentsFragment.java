package com.example.alejandro.navigationdrawersample.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alejandro.navigationdrawersample.R;
import com.example.alejandro.navigationdrawersample.adapter.CommentsAdapter;
import com.example.alejandro.navigationdrawersample.http.WebServiceManager;
import com.example.alejandro.navigationdrawersample.model.Comment;
import com.example.alejandro.navigationdrawersample.model.Post;
import com.example.alejandro.navigationdrawersample.util.Constants;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alejandro on 7/11/2015.
 */
public class PostCommentsFragment extends Fragment {
    private static final String TAG = PostCommentsFragment.class.getSimpleName();

    /**
     * Constants
     */


    /**
     * UI VARIABLES
     */
    @Bind(value = R.id.post_container)
    LinearLayout postLL;

    @Bind(value = R.id.lst_post_title)
    TextView txtPostTitle;
    @Bind(value = R.id.lst_post_body)
    TextView txtPostBody;

    @Bind(value = R.id.lst_comments)
    RecyclerView lstComments;

    @Bind(value = R.id.pb_post_comments)
    ProgressBar progressBar;

    /**
     * NON_UI
     */
    Handler mHandler;

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_comments, container, false);
        ButterKnife.bind(this, view);
        mHandler = new Handler();
        String myTransitionName = getArguments().getString(Constants.KEY_TRANSITION_NAME_POST);
        int postId = getArguments().getInt(Constants.KEY_POST_ID);
        setPostData(getArguments());
        progressBar.setVisibility(View.VISIBLE);
        loadPostComments(postId);
        postLL.setTransitionName(myTransitionName);
        return view;
    }

    private void loadPostComments(int postId) {
        Callback callback = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PostCommentsFragment.this.getActivity(),
                                R.string.connection_error
                                ,Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String json = response.body().string();
                Gson gsonParser = new Gson();
                final Comment[] comments = gsonParser.fromJson(json, Comment[].class);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        setCommentsData(comments);
                    }
                });

            }
        };
        WebServiceManager.requestPostComments(postId, callback);

    }

    private void setCommentsData(Comment[] comments) {
        CommentsAdapter adapter = new CommentsAdapter(comments);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        lstComments.setLayoutManager(layoutManager);
        lstComments.setHasFixedSize(true);
        progressBar.setVisibility(View.GONE);
        lstComments.setAdapter(adapter);


    }

    private void setPostData(Bundle arguments){
        String title = arguments.getString(Constants.KEY_POST_TITLE);
        String body = arguments.getString(Constants.KEY_POST_BODY);
        txtPostBody.setText(body);
        txtPostTitle.setText(title);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public static PostCommentsFragment newInstance(String transitionName, int postId,String postTitle, String postBody) {
        PostCommentsFragment myFragment = new PostCommentsFragment();

        Bundle args = new Bundle();
        args.putString(Constants.KEY_TRANSITION_NAME_POST, transitionName);
        args.putString(Constants.KEY_POST_TITLE,postTitle);
        args.putString(Constants.KEY_POST_BODY,postBody);
        args.putInt(Constants.KEY_POST_ID, postId);
        myFragment.setArguments(args);

        return myFragment;
    }
}
