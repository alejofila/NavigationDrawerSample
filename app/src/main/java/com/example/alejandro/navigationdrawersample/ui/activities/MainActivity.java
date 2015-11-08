package com.example.alejandro.navigationdrawersample.ui.activities;

import android.app.SharedElementCallback;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.alejandro.navigationdrawersample.R;
import com.example.alejandro.navigationdrawersample.adapter.PostAdapter;
import com.example.alejandro.navigationdrawersample.adapter.PostAdapter.OnPostClickedCallback;
import com.example.alejandro.navigationdrawersample.dbUtils.TinyDB;
import com.example.alejandro.navigationdrawersample.model.Post;
import com.example.alejandro.navigationdrawersample.ui.fragment.AlbumFragment;
import com.example.alejandro.navigationdrawersample.ui.fragment.OnFragmentChanged;
import com.example.alejandro.navigationdrawersample.ui.fragment.PostCommentsFragment;
import com.example.alejandro.navigationdrawersample.ui.fragment.PostsListFragment;
import com.example.alejandro.navigationdrawersample.ui.fragment.UserFragment;
import com.example.alejandro.navigationdrawersample.ui.fragment.WelcomeFragment;
import com.example.alejandro.navigationdrawersample.util.Constants;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnFragmentChanged,
        OnPostClickedCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null)
            replaceFragment(new WelcomeFragment(), false);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment newFragment = null;
        switch (id) {
            case R.id.nav_user:
                newFragment = new UserFragment();
                break;
            case R.id.nav_album:
                newFragment = new AlbumFragment();
                break;
            case R.id.nav_post:
                newFragment = new PostsListFragment();

                break;
            case R.id.nav_signout:
                break;
        }
        if (id != R.id.nav_signout)
            replaceFragment(newFragment, true);
        else {
            TinyDB tinyDB = new TinyDB(this);
            tinyDB.clear();

            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment newFragment, boolean addToBackstack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        ft.replace(R.id.fragment_container, newFragment, newFragment.getClass().getSimpleName());
        if (addToBackstack)
            ft.addToBackStack(null);
        ft.commit();

    }

    @Override
    public void onFragmentAttached(String newTitle) {
        setTitle(newTitle);
    }

    @Override
    public void onPostClicked(Post p, String transitionName, View sharedView) {
        Fragment postCommentsFragment = new PostCommentsFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        ft.replace(R.id.fragment_container, postCommentsFragment);
        ft.addToBackStack(null);
        ft.commit();

    }
}
