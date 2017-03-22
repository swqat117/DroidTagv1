package com.quascenta.petersroad.droidtag.fragments;

/**
 * Created by AKSHAY on 3/22/2017.
 */

public interface RegisterView {

    void updateUI();

    void setError(String message, int code);

    void setPError(String error, int code);

    void onSuccess(String message, int code);


}
