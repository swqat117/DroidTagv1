package com.quascenta.petersroad.droidtag.main;

import android.app.Activity;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by AKSHAY on 4/10/2017.
 */

public class Presenter implements Contract.Presenter, Contract.OnLoginListener {

    private static final String TAG = Presenter.class.getSimpleName();
    public static HashMap<String, String> deviceHashMap = new HashMap<>();
    LoggerDevice device1;
    private Contract.View mLoginView;
    private Contract.Interactor interactor;

    public Presenter(Contract.View loginView) {
        this.mLoginView = loginView;
        interactor = new Interactor(this);
    }

    @Override
    public void login(Activity activity, String email, String password) {

    }

    @Override
    public void onResendEmailConfirmation(Activity activity) {

    }

    @Override
    public boolean sendData(String name, String company_start, String company_start_location, String company_end, String company_end_location) {
        Log.d(TAG, "Pass");
        device1 = new LoggerDevice(name, company_start, company_start_location, company_end, company_end_location);

        return true;
    }

    @Override
    public boolean send(String key, String value) {
        Log.d(TAG, value);
        device1.setDeviceAddress(value);
        mLoginView.onUpdate(device1);
        return true;
    }


    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public void onEmailSuccess(String message) {

    }
}
