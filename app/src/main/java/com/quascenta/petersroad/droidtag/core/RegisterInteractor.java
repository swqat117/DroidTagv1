package com.quascenta.petersroad.droidtag.core;

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

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by AKSHAY on 3/23/2017.
 */

public class RegisterInteractor implements RegisterContract.Interactor {
    private static final String TAG = RegisterInteractor.class.getSimpleName();

    WeakReference<DatabaseReference> database;
    private RegisterContract.OnRegistrationListener mOnRegistrationListener;


    public RegisterInteractor(RegisterContract.OnRegistrationListener onRegistrationListener) {
        this.mOnRegistrationListener = onRegistrationListener;
    }


    @Override
    public void performFirebaseRegistration(Activity activity, final ArrayList<String> arrayList) {
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(arrayList.get(0), arrayList.get(1))
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.i(TAG, "performFirebaseRegistration:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            task.addOnFailureListener(activity, new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    e.printStackTrace();
                                    mOnRegistrationListener.onFailure(e.getMessage());
                                }
                            });

                        } else {
                            // Add the user to users table.
                            //        DatabaseReference database= FirebaseDatabase.getInstance().getReference();
                            //         User user = new User(task.getResult().getUser().getUid(), email);
                            //         database.child("users").push().setValue(user);
                            //        Company company = new Company(arrayList.get(3),arrayList.get(4),arrayList.get(0).substring(arrayList.get(0).indexOf('@')),arrayList.get(5),arrayList.get(5),arrayList.get(6),arrayList.get(6),)

                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                            CompanyModel company = new CompanyModel(arrayList.get(3), arrayList.get(4), arrayList.get(0).substring(arrayList.get(0).indexOf('@')), arrayList.get(5), arrayList.get(5), arrayList.get(6), arrayList.get(6), 0, 0);
                            company.addUser(new UserViewModel(arrayList.get(0), task.getResult().getUser().getUid(), arrayList.get(1), arrayList.get(5)));
                            databaseReference.child("companies").push().setValue(company);
                            databaseReference.child("users").push().setValue(task.getResult().getUser().getUid());
                            System.out.println("updated ================================================");

                            mOnRegistrationListener.onSuccess(task.getResult().getUser());
                        }
                    }
                });
    }


}
