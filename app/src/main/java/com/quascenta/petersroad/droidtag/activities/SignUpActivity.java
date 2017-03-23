package com.quascenta.petersroad.droidtag.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.quascenta.petersroad.droidtag.R;
import com.quascenta.petersroad.droidtag.fragments.RegisterUserFragment;
import com.quascenta.petersroad.droidtag.widgets.CustomViewPager;

/**
 * Created by AKSHAY on 3/22/2017.
 */

public class SignUpActivity extends FragmentActivity implements RegisterUserFragment.ChangePage {


    CustomViewPager viewPager;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        viewPager = (CustomViewPager) findViewById(R.id.viewpagermain);
        viewPager.setPagingEnabled(false);
        PagerAdapter1 register_adapter = new PagerAdapter1(getSupportFragmentManager());
        register_adapter.addFragment(new RegisterUserFragment(), "Registration");
        register_adapter.addFragment(new RegisterMobileFragment(), "REPORT");
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(register_adapter);


    }


    public void nextPage(CustomViewPager viewPager) {

        viewPager.setCurrentItem(1);

    }

    public void previousPage(CustomViewPager viewPager) {

        viewPager.setCurrentItem(0);

    }


    @Override
    public void i() {
        nextPage(viewPager);
    }
}






