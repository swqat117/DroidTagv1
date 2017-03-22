package com.quascenta.petersroad.droidtag.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.quascenta.petersroad.droidtag.R;
import com.quascenta.petersroad.droidtag.ViewServer;
import com.quascenta.petersroad.droidtag.fragments.AlertListFragment;
import com.quascenta.petersroad.droidtag.fragments.ReportGenerationFragment;
import com.quascenta.petersroad.droidtag.widgets.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AKSHAY on 2/13/2017.
 */

public class DevicesActivity2 extends BaseActivity {
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    // OnCreate -  To init all views

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layoutwithviewpager);
        ViewServer.get(this).addWindow(this);

        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        System.out.println(user.getEmail());
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(DevicesActivity2.this, LoginActivity.class));
                    finish();
                }

            }
        };
        init();
    }


    @Override
    protected void onResume() {
        super.onResume();
        ViewServer.get(this).setFocusedWindow(this);
    }

    @Override
    protected void onDestroy() {


        super.onDestroy();
        ViewServer.get(this).removeWindow(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int x = item.getItemId();

        switch (x) {
            case R.id.Settings:
                return true;
            //Load Settings page
            case R.id.add:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.signout:
                signout();
            
            //Load theme

        }
        return false;
    }

    private void signout() {
        auth.signOut();

// this listener will be called when there is change in firebase user session
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(DevicesActivity2.this, LoginActivity.class));
                    finish();
                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    private void init() {

        final ActionBar ab = getSupportActionBar();
        CustomViewPager viewPager = (CustomViewPager) findViewById(R.id.viewpagermain);
        viewPager.setPagingEnabled(false);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AlertListFragment(), "ALERT TRACKER");
        adapter.addFragment(new ReportGenerationFragment(), "REPORT");
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager, true);
        if (viewPager != null) {

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    protected void onStop() {
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
        super.onStop();
    }

    static class PagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
