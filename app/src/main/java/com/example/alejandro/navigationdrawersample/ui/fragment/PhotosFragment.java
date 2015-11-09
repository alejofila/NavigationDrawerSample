package com.example.alejandro.navigationdrawersample.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.alejandro.navigationdrawersample.R;
import com.example.alejandro.navigationdrawersample.adapter.PhotosAdapter;
import com.example.alejandro.navigationdrawersample.http.WebServiceManager;
import com.example.alejandro.navigationdrawersample.model.Photo;
import com.example.alejandro.navigationdrawersample.util.Constants;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alejandro on 8/11/2015.
 */
public class PhotosFragment extends Fragment{
    @Bind(value = R.id.lst_photos)
    RecyclerView lstPhotos;

    private Handler mHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_photos,container,false);
        ButterKnife.bind(this,view);

        mHandler  = new Handler();

        loadPhotosData(getArguments());
        return view;
    }

    private void loadPhotosData(Bundle arguments) {
        int albumId = arguments.getInt(Constants.KEY_ALBUM_ID);
        Callback callback = new Callback() {
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
               String json =  response.body().string();
                Gson gsonParser = new Gson();
                final Photo[] photos = gsonParser.fromJson(json,Photo[].class);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        setPhotos(photos);

                    }
                });


            }
        };
        WebServiceManager.requestAlbumPhotos(albumId, callback);
    }

    private void setPhotos(Photo[] photos) {
        PhotosAdapter adapter = new PhotosAdapter(photos,getActivity(),mOnPhotoClickedListener);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),4, GridLayoutManager.VERTICAL,false);
        lstPhotos.setLayoutManager(layoutManager);
        lstPhotos.setHasFixedSize(true);
        lstPhotos.setAdapter(adapter);

    }

    public static PhotosFragment newInstance(int albumId){
        PhotosFragment fragment = new PhotosFragment();
        Bundle b = new Bundle();
        b.putInt(Constants.KEY_ALBUM_ID, albumId);
        fragment.setArguments(b);
        return fragment;

    }
    PhotosAdapter.OnPhotoClickedListener mOnPhotoClickedListener = new PhotosAdapter.OnPhotoClickedListener() {
        @Override
        public void onPhotoClicked(Photo photo) {
            DialogFragment fragment = SinglePhotoDialogFragment.newInstance(photo.getUrl());
            fragment.setStyle(DialogFragment.STYLE_NO_FRAME,0);
            fragment.show(getFragmentManager(),"FullScreenPhoto");

        }
    };
}
