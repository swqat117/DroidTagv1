package com.quascenta.petersroad.droidtag.core;

import android.app.Activity;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Created by AKSHAY on 3/23/2017.
 */

public class RegisterPresenter implements RegisterContract.Presenter, RegisterContract.OnRegistrationListener {
    public static final String TAG = RegisterPresenter.class.getSimpleName();
    public ArrayList<String> CredentialList = new ArrayList<>();
    private RegisterContract.View mRegisterView;
    private RegisterInteractor mRegisterInteractor;


    public RegisterPresenter(RegisterContract.View registerView) {

        this.mRegisterView = registerView;
        mRegisterInteractor = new RegisterInteractor(this);

    }


    @Override
    public void register(Activity activity, String email, String password) {

    }

    @Override
    public void sendString(String message) {

    }

    @Override
    public void onSuccess(FirebaseUser firebaseUser) {

    }

    @Override
    public void onFailure(String message) {

    }
}
