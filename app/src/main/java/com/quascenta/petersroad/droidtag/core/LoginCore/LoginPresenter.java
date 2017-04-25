package com.quascenta.petersroad.droidtag.core.LoginCore;

import android.app.Activity;
import android.util.Log;

/**
 * Created by AKSHAY on 3/22/2017.
 */

public class LoginPresenter implements LoginContract.Presenter, LoginContract.OnLoginListener {

    private static final String TAG = LoginPresenter.class.getSimpleName();
    private LoginContract.View mLoginView;
    private LoginInteractor mLoginInteractor;


    public LoginPresenter(LoginContract.View loginView) {
        this.mLoginView = loginView;
        mLoginInteractor = new LoginInteractor(this);
    }



    @Override
    public void login(Activity activity, String email, String password) {
        Log.i(TAG, "Presenter -> Interactor");
        mLoginInteractor.performFirebaseLogin(activity, email, password);
    }

    @Override
    public void onResendEmailConfirmation(Activity activity) {

    }

    @Override
    public void onSuccess(String message) {
        mLoginView.onLoginSuccess(message);
    }

    @Override
    public void onFailure(String message) {
        mLoginView.onLoginFailure(message);
    }

    @Override
    public void onEmailSuccess(String message) {
        mLoginView.onEmailResendSuccess(message);
    }
}