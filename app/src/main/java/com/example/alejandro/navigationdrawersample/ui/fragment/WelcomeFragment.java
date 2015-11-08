package com.example.alejandro.navigationdrawersample.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alejandro.navigationdrawersample.R;

/**
 * Created by Alejandro on 6/11/2015.
 */
public class WelcomeFragment  extends Fragment {


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnFragmentChanged = (OnFragmentChanged)context;
        }
        catch(ClassCastException e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome,container,false);
        mOnFragmentChanged.onFragmentAttached(getString(R.string.app_name));
        return view;

    }

    private OnFragmentChanged mOnFragmentChanged;
}
