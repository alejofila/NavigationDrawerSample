package com.example.alejandro.navigationdrawersample.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.alejandro.navigationdrawersample.R;
import com.example.alejandro.navigationdrawersample.util.Constants;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alejandro on 9/11/2015.
 */
public class SinglePhotoDialogFragment extends DialogFragment {
    @Bind(value = R.id.full_image)
    ImageView fullImage;
    public SinglePhotoDialogFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.photo_layout,container,false);
        ButterKnife.bind(this, view);
        loadPhoto(getArguments());
        return view;


    }
    private void loadPhoto(Bundle args){
        String imageUrl = args.getString(Constants.KEY_PHOTO_URL);
        Picasso.with(getActivity()).load(imageUrl)
                .placeholder(R.drawable.ic_loading)
                .into(fullImage);

    }

    public static SinglePhotoDialogFragment newInstance(String imageUrl){
        SinglePhotoDialogFragment fragment = new SinglePhotoDialogFragment();
        Bundle args = new Bundle();
        args.putString(Constants.KEY_PHOTO_URL, imageUrl);
        fragment.setArguments(args);

        return fragment;
    }
}
