package com.example.alejandro.navigationdrawersample.ui.fragment;

import android.content.Context;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.PathMotion;
import android.transition.SidePropagation;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.alejandro.navigationdrawersample.R;
import com.example.alejandro.navigationdrawersample.adapter.PostAdapter;
import com.example.alejandro.navigationdrawersample.dbUtils.TinyDB;
import com.example.alejandro.navigationdrawersample.http.WebServiceManager;
import com.example.alejandro.navigationdrawersample.model.Post;
import com.example.alejandro.navigationdrawersample.util.Constants;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;


/**
 * Created by Alejandro on 5/11/2015.
 */
public class PostsListFragment extends Fragment {

    private static final String TAG = PostsListFragment.class.getSimpleName();
    /**
     * UI Binding
     */
    @Bind(value = R.id.list_posts)
    RecyclerView postList;


    /**
     * NON UI
     */
    private Handler mHandler;
    private PostAdapter.OnPostClickedCallback mOnPostClickedCallback = new PostAdapter.OnPostClickedCallback() {
        @Override
        public void onPostClicked(Post clickedPost, View sharedView) {
            Fragment postCommentsFragment = PostCommentsFragment.newInstance(clickedPost.getId(),clickedPost.getTitle(),clickedPost.getBody());
            FragmentManager fm = PostsListFragment.this.getFragmentManager();
            fm.beginTransaction()
            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,android.R.anim.fade_in,android.R.anim.fade_out)
                    .replace(R.id.fragment_container, postCommentsFragment)
                    .addToBackStack(null)
                    .commit();
        }
    };


    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnFragmentChanged = (OnFragmentChanged) context;
            //TODO  mOnPostClickedCallback = (PostAdapter.OnPostClickedCallback) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_post_list, container, false);
        ButterKnife.bind(this, view);
        loadPosts();
        mHandler = new Handler();

        mOnFragmentChanged.onFragmentAttached(getActivity().getResources().getString(R.string.nav_posts));
        return view;
    }

    private void loadPosts() {
        TinyDB tinyDB = new TinyDB(getActivity());
        ArrayList auxPostArray = tinyDB.getListObject(Constants.KEY_POST_LIST, Post.class);
        if (auxPostArray.size() == 0) {
            requestPostsData(tinyDB);
        } else {
            Post[] auxPosts = fromObjArrayToPostArray(auxPostArray);
            setPostsData(auxPosts);
        }


    }

    private Post[] fromObjArrayToPostArray(ArrayList auxPostArray) {
        Post[] posts = new Post[auxPostArray.size()];

        for (int i = 0; i < auxPostArray.size(); i++)
            posts[i] = (Post) auxPostArray.get(i);
        return posts;
    }

    private ArrayList fromArrayToPostArrayList(Post[] post) {
        ArrayList postArrayList = new ArrayList<>();
        for (int i = 0; i < post.length; i++)
            postArrayList.add(post[i]);
        return postArrayList;
    }

    private void requestPostsData(final TinyDB tinyDB) {
        int id = tinyDB.getInt(Constants.KEY_USER_ID);
        WebServiceManager.requestPostTitles(id, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Response response) throws IOException {
                String json = response.body().string();
                Gson gson = new Gson();
                final Post[] posts = gson.fromJson(json, Post[].class);
                tinyDB.putListObject(Constants.KEY_POST_LIST, fromArrayToPostArrayList(posts));

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        setPostsData(posts);
                    }
                });


            }
        });
    }

    private void setPostsData(Post[] posts) {
        PostAdapter adapter = new PostAdapter(posts, mOnPostClickedCallback);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        postList.setHasFixedSize(true);
        postList.setLayoutManager(layoutManager);
        postList.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private OnFragmentChanged mOnFragmentChanged;
}
