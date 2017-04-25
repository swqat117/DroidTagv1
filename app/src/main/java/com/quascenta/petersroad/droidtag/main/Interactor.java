package com.quascenta.petersroad.droidtag.main;

import android.app.Activity;

/**
 * Created by AKSHAY on 4/10/2017.
 */

public class Interactor implements Contract.Interactor {

    private final Contract.OnLoginListener mOnLoginListener;

    public Interactor(Contract.OnLoginListener onLoginListener) {
        this.mOnLoginListener = onLoginListener;
    }

    @Override
    public void performFirebaseLogin(Activity activity, String email, String password) {

    }

    @Override
    public void onResendEmailConfirmation(Activity activity) {

    }
}
