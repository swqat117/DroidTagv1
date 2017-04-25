package com.quascenta.petersroad.droidtag.main;

import android.app.Activity;

/**
 * Created by AKSHAY on 4/10/2017.
 */

public interface Contract {

    interface View {
        void onSuccess(String message);

        void onFailure(String message);

        void onEmailResendSuccess(String message);

        void onUpdate(LoggerDevice message);
    }


    interface Presenter {
        void login(Activity activity, String email, String password);

        void onResendEmailConfirmation(Activity activity);

        boolean sendData(String device, String x, String u, String y, String z);

        boolean send(String key, String value);


    }


    interface Interactor {
        void performFirebaseLogin(Activity activity, String email, String password);

        void onResendEmailConfirmation(Activity activity);
    }


    interface OnLoginListener {
        void onSuccess(String message);

        void onFailure(String message);

        void onEmailSuccess(String message);
    }
}
