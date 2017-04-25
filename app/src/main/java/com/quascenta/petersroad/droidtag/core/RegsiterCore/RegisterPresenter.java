package com.quascenta.petersroad.droidtag.core.RegsiterCore;

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

    //RegisterPresenter to RegisterInteractor
    @Override
    public void register(Activity activity) {
        mRegisterInteractor.performFirebaseRegistration(activity, CredentialList);
    }



    @Override
    public void sendString(String message) {
        CredentialList.add(message);
    }


    @Override
    public void onSuccess(FirebaseUser firebaseUser) {
        mRegisterView.onRegistrationSuccess(firebaseUser);
    }


    //Presenter to View (Activity)
    @Override
    public void onProgress(String message) {
        mRegisterView.onProgress(message);
    }




    @Override
    public void onFailure(String message) {
        mRegisterView.onRegistrationFailure(message);
    }
}
