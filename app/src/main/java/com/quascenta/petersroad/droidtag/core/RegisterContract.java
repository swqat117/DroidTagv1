package com.quascenta.petersroad.droidtag.core;

import android.app.Activity;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Created by AKSHAY on 3/22/2017.
 */

public interface RegisterContract {

    interface View {
        void onRegistrationSuccess(FirebaseUser firebaseUser);

        void onRegistrationFailure(String message);
    }

    interface Presenter {
        void register(Activity activity);

        void sendString(String message);
    }

    interface Interactor {
        void performFirebaseRegistration(Activity activity, ArrayList<String> data);
    }

    interface OnRegistrationListener {
        void onSuccess(FirebaseUser firebaseUser);

        void onFailure(String message);
    }
}
