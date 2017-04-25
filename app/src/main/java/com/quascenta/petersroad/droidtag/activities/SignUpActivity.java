package com.quascenta.petersroad.droidtag.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.quascenta.petersroad.droidtag.R;
import com.quascenta.petersroad.droidtag.fragments.RegisterUserFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by AKSHAY on 3/22/2017.
 */

public class SignUpActivity extends BaseActivity {


    RegisterUserFragment userFragment;

    @Bind(R.id.main_content)
    View main_content;


    @Bind(R.id.next)
    FloatingActionButton next;


    @OnClick(R.id.next)
    public void click() {
        userFragment.prepareData();


    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);

        userFragment = new RegisterUserFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.viewpagermain, userFragment).commit();



    }


}









