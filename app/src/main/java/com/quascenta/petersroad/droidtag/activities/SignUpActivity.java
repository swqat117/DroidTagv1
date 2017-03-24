package com.quascenta.petersroad.droidtag.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.firebase.auth.FirebaseUser;
import com.quascenta.petersroad.droidtag.R;
import com.quascenta.petersroad.droidtag.core.RegisterContract;
import com.quascenta.petersroad.droidtag.fragments.RegisterUserFragment;
import com.quascenta.petersroad.droidtag.widgets.CustomViewPager;

/**
 * Created by AKSHAY on 3/22/2017.
 */

public class SignUpActivity extends FragmentActivity implements RegisterContract.View {


    CustomViewPager viewPager;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        viewPager = (CustomViewPager) findViewById(R.id.viewpagermain);
        viewPager.setPagingEnabled(false);
        PagerAdapter1 register_adapter = new PagerAdapter1(getSupportFragmentManager());
        register_adapter.addFragment(new RegisterUserFragment(), "Registration");
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(register_adapter);


    }

    @Override
    public void onRegistrationSuccess(FirebaseUser firebaseUser) {

    }

    @Override
    public void onRegistrationFailure(String message) {

    }
}









