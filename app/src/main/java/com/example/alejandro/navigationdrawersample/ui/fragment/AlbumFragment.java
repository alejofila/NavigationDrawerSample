package com.example.alejandro.navigationdrawersample.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alejandro.navigationdrawersample.R;
import com.example.alejandro.navigationdrawersample.dbUtils.TinyDB;
import com.example.alejandro.navigationdrawersample.http.WebServiceManager;
import com.example.alejandro.navigationdrawersample.model.Album;
import com.example.alejandro.navigationdrawersample.model.Photo;
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
public class AlbumFragment extends Fragment {

    @Bind(value = R.id.lst_album)
    ListView listAlbum;

    @OnItemClick(R.id.lst_album)
    public void onAlbumClicked(int position) {
        PhotosFragment photosFragment = PhotosFragment.newInstance(position);
        FragmentManager fm = AlbumFragment.this.getFragmentManager();
        fm.beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,android.R.anim.fade_in,android.R.anim.fade_out)
                .replace(R.id.fragment_container, photosFragment)
                .addToBackStack(null)
                .commit();

    }

    /**
     * NON UI
     */
    Handler mHandler;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mOnFragmentChanged = (OnFragmentChanged) context;

        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_list, container, false);
        ButterKnife.bind(this, view);
        mHandler = new Handler();
        mOnFragmentChanged.onFragmentAttached(getString(R.string.nav_gallery));
        loadAlbums();
        return view;
    }

    private void loadAlbums() {
        TinyDB tinyDB = new TinyDB(getActivity());
        ArrayList auxAlbumArray = tinyDB.getListObject(Constants.KEY_ALBUM_LIST, Album.class);

        if(auxAlbumArray.size() == 0)
            requestAlbumData(tinyDB);

        else{
            Album[] albums = fromObjArrayToAlbumArray(auxAlbumArray);
            setAlbumData(albums);

        }

    }

    private Album[] fromObjArrayToAlbumArray(ArrayList albumArray){
        Album[] albums = new Album[albumArray.size()];
        for(int i=0;i<albumArray.size();i++){
            albums[i] = (Album)albumArray.get(i);
        }

        return albums;

    }

    private ArrayList fromArrayToAlbumArrayList(Album[] post){
        ArrayList albumArrayList = new ArrayList<>();
        for(int i =0;i<post.length;i++)
            albumArrayList.add(post[i]);
        return albumArrayList;
    }

    private void requestAlbumData(final TinyDB tinyDB) {
        int id = tinyDB.getInt(Constants.KEY_USER_ID);
        WebServiceManager.requestAlbumsUser(id, new Callback() {
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
                final Album[] albums = gson.fromJson(json, Album[].class);
                tinyDB.putListObject(Constants.KEY_ALBUM_LIST,fromArrayToAlbumArrayList(albums));
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        setAlbumData(albums);

                    }
                });


            }
        });
    }

    private void setAlbumData(Album[] albums){
        final String[] albumsTitle = new String[albums.length];
        for (int i = 0; i < albums.length; i++)
            albumsTitle[i] = albums[i].getTitle();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, albumsTitle);
        listAlbum.setAdapter(adapter);

    }

    OnFragmentChanged mOnFragmentChanged;
}
