package com.quascenta.petersroad.droidtag.core.LoginCore;

import android.app.Activity;

/**
 * Created by AKSHAY on 3/22/2017.
 */

public interface LoginContract {


    interface View {
        void onLoginSuccess(String message);

        void onLoginFailure(String message);

        void onEmailResendSuccess(String message);
    }


    interface Presenter {
        void login(Activity activity, String email, String password);

        void onResendEmailConfirmation(Activity activity);
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

