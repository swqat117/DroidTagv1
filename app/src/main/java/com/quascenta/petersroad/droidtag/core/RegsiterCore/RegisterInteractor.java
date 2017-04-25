package com.quascenta.petersroad.droidtag.core.RegsiterCore;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.quascenta.petersroad.droidtag.Utils.Constants;
import com.quascenta.petersroad.droidtag.core.CompanyModel;
import com.quascenta.petersroad.droidtag.core.UserViewModel;

import org.joda.time.DateTime;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by AKSHAY on 3/23/2017.
 */

@SuppressWarnings("unchecked")
public class RegisterInteractor implements RegisterContract.Interactor {
    private static final String TAG = RegisterInteractor.class.getSimpleName();
    private static RegisterContract.OnRegistrationListener mOnRegistrationListener;
    WeakReference<DatabaseReference> database;


    public RegisterInteractor(RegisterContract.OnRegistrationListener onRegistrationListener) {
        mOnRegistrationListener = onRegistrationListener;
    }

    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }

    public static String DecodeString(String string) {
        return string.replace(",", ".");
    }

    @Override
    public void performFirebaseRegistration(Activity activity, final ArrayList<String> arrayList) {
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(arrayList.get(0), arrayList.get(1))
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> registrationTask) {
                        Log.i(TAG, "performFirebaseRegistration:onComplete:" + registrationTask.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!registrationTask.isSuccessful()) {
                            registrationTask.addOnFailureListener(activity, e -> {
                                e.printStackTrace();
                                mOnRegistrationListener.onFailure(e.getMessage());
                            });

                        } else {
                            mOnRegistrationListener.onProgress("Sending email to " + arrayList.get(0));
                            registrationTask.addOnSuccessListener(activity, authResult -> {
                                authResult.getUser().sendEmailVerification().addOnCompleteListener(emailVerificationTask -> {

                                    if (emailVerificationTask.isSuccessful()) {
                                        Log.d(TAG, " email sent");
                                        insertUserintoFirebase(arrayList);
                                        mOnRegistrationListener.onSuccess(authResult.getUser());


                                    } else {
                                        emailVerificationTask.addOnFailureListener(activity, e -> {
                                            e.printStackTrace();
                                            mOnRegistrationListener.onFailure(e.getMessage());


                                        });
                                    }
                                });
                            });
                        }
                    }
                });
    }


    public void insertUserintoFirebase(ArrayList<String> arrayList) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        UserViewModel user = new UserViewModel(arrayList.get(2), arrayList.get(0), DateTime.now().toString(), arrayList.get(6));
        HashMap<String, String> users = new HashMap<>();
        users.put("name", user.getEmail());
        users.put("id", String.valueOf(user.getId()));
        database.child(Constants.ARG_USERS).push().setValue(user).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mOnRegistrationListener.onFailure(e.getMessage());
            }
        });
        database.child(Constants.ARG_COMPANY).push().setValue(new CompanyModel(user.getCompanyName(), arrayList.get(4), arrayList.get(6), users)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mOnRegistrationListener.onProgress("Success");
                } else {
                    mOnRegistrationListener.onFailure(task.getException().getMessage());
                }
            }
        });

    }


}



