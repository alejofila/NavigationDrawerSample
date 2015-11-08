package com.example.alejandro.navigationdrawersample.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alejandro.navigationdrawersample.R;
import com.example.alejandro.navigationdrawersample.dbUtils.TinyDB;
import com.example.alejandro.navigationdrawersample.http.WebServiceManager;
import com.example.alejandro.navigationdrawersample.model.User;
import com.example.alejandro.navigationdrawersample.util.Constants;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alejandro on 5/11/2015.
 */
public class UserFragment extends Fragment {
    /**
     * Constants
     */
    private static final String TAG = UserFragment.class.getSimpleName();
    /**
     * UI_BINDING
     */
    @Bind(R.id.txt_name)
    TextView txtName;
    @Bind(R.id.txt_user)
    TextView txtUser;
    @Bind(R.id.txt_email)
    TextView txtEmail;
    @Bind(R.id.txt_phone)
    TextView txtPhone;
    @Bind(R.id.txt_company)
    TextView txtCompany;
    /**
     * NON UI
     */
    private Handler mHandler;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        try {
            mOnFragmentChanged = (OnFragmentChanged) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);
        mHandler = new Handler();
        mOnFragmentChanged.onFragmentAttached(getActivity().getResources().getString(R.string.nav_user));
        loadUserData();

        return view;
    }

    private void loadUserData() {
        TinyDB tinyDB = new TinyDB(getActivity());
        User user = null;
        try {
            user = (User) tinyDB.getObject(Constants.KEY_USER_INFO, User.class);
            setUserInfo(user);
        }
        catch (NullPointerException e){
            // if hasn't been downloaded then download it
            requestUserData(tinyDB);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private OnFragmentChanged mOnFragmentChanged;

    private void requestUserData(final TinyDB tinyDB){

        int id = tinyDB.getInt(Constants.KEY_USER_ID);
        WebServiceManager.requestForUserInfo(id, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),getString(R.string.connection_error),Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String json = response.body().string();
                Gson gson = new Gson();
                final User user = gson.fromJson(json, User.class);
                tinyDB.putObject(Constants.KEY_USER_INFO,user);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        setUserInfo(user);
                    }
                });
            }
        });
    }

    private void setUserInfo(User user){
        txtName.setText(user.getName());
        txtCompany.setText(user.getCompany().getName());
        txtEmail.setText(user.getEmail());
        txtPhone.setText(user.getPhone());
        txtUser.setText(user.getUserName());
    }


}
