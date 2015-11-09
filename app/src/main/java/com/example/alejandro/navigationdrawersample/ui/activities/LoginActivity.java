package com.example.alejandro.navigationdrawersample.ui.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alejandro.navigationdrawersample.R;
import com.example.alejandro.navigationdrawersample.controller.Login;
import com.example.alejandro.navigationdrawersample.dbUtils.TinyDB;
import com.example.alejandro.navigationdrawersample.util.Constants;
import com.example.alejandro.navigationdrawersample.util.RandomHelper;
import com.kogitune.activity_transition.ActivityTransitionLauncher;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alejandro on 4/11/2015.
 */
public class LoginActivity extends AppCompatActivity{
    /**
     * UI BINDING
     */

    @Bind(R.id.txt_username)
    EditText txtUsername;
    @Bind(R.id.txt_password)
    EditText txtPassword;

    @Bind(R.id.btn_login)
    Button btnLogin;
    @OnClick(R.id.btn_login) void loginFunction(){
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        Login login = new Login(username,password);
        if(login.isAValidUser())
            succesfulLogin();
        else
            Toast.makeText(this, R.string.wrong_login, Toast.LENGTH_SHORT)
                    .show();

    }

    private void succesfulLogin(){
        Intent intent = new Intent(this,MainActivity.class);
        TinyDB tinyDB = new TinyDB(this);
        int id = RandomHelper.getRandomIdNumber();
        tinyDB.putInt(Constants.KEY_USER_ID, id);
        tinyDB.putBoolean(Constants.KEY_IS_CURRENT_SESSION, true);
        Button btnLogin = (Button)findViewById(R.id.btn_login);
        ActivityTransitionLauncher.with(LoginActivity.this).from(btnLogin).launch(intent);

        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        TinyDB tinyDB = new TinyDB(this);
        boolean isSession = tinyDB.getBoolean(Constants.KEY_IS_CURRENT_SESSION);
        if(isSession){
            succesfulLogin();
        }
    }
}
