package com.quascenta.petersroad.droidtag.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.quascenta.petersroad.droidtag.EventListeners.TextObservables;
import com.quascenta.petersroad.droidtag.R;
import com.quascenta.petersroad.droidtag.Utils.AndValidator;
import com.quascenta.petersroad.droidtag.Utils.EmailValidator;
import com.quascenta.petersroad.droidtag.Utils.EmptyValidator;
import com.quascenta.petersroad.droidtag.core.LoginCore.LoginContract;
import com.quascenta.petersroad.droidtag.core.LoginCore.LoginPresenter;
import com.quascenta.petersroad.droidtag.widgets.FormEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by AKSHAY on 3/22/2017.
 */

public class LoginActivity extends BaseActivity implements LoginContract.View {

    //TODO: Adding comments

    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int MAX_NUMBER_OF_ATTEMPTS = 3;
    @Bind(R.id.coordinator)
    View snackBarView;
    @Bind(R.id.email)
    FormEditText inputEmail;

    @Bind(R.id.password)
    FormEditText password_et;


    @Bind(R.id.progressBar)
    ProgressBar progressBar;


    @Bind(R.id.btn_signup)
    Button Signup_bt;


    @Bind(R.id.btn_login)
    Button login_bt;


    @Bind(R.id.btn_reset_password)
    Button reset_password_bt;


    LoginPresenter loginPresenter;


    int attempts_failed_login;


    @OnClick(R.id.btn_reset_password)
    public void loadResetPassword() {
        Log.i(TAG, "---> onClick reset ---> Loading reset Password");
        startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));

    }


    @OnClick(R.id.btn_signup)
    public void loadSignUp() {
        Log.i(TAG, "---> onClick Sign Up ---> Loading SignUp");
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }


    @OnClick(R.id.btn_login)
    public void ValidateLogin() {
        attempts_failed_login = attempts_failed_login + 1;
        progressBar.setVisibility(View.VISIBLE);
        Log.i(TAG, "---> onClick() ---> getUserCredentials() ");
        getUserCredentials(inputEmail, password_et, "Error:", progressBar);
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.i(TAG, "init onCreate()");
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        attempts_failed_login = 0;
        loginPresenter = new LoginPresenter(this);
        ifUserExistsLoadMain();


    }


    @Override
    public void onLoginSuccess(String message) {
        progressBar.setVisibility(View.GONE);
        ifUserExistsLoadMain();
    }

    @Override
    public void onLoginFailure(String message) {
        progressBar.setVisibility(View.GONE);
        ++attempts_failed_login;
        Log.e(TAG, "---> FireBase -->onLoginFailiure() " + attempts_failed_login);
        Snackbar.make(snackBarView, message, Snackbar.LENGTH_SHORT).show();


        if (attempts_failed_login > MAX_NUMBER_OF_ATTEMPTS) {
            Log.e(TAG, "---> Disabling login button contact admin");
            Snackbar.make(snackBarView, "Disabling Login, Contact admin", Snackbar.LENGTH_SHORT).show();
            login_bt.setEnabled(false);
            Log.i(TAG, "---> onClick reset ---> Loading reset Password");
            startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            // TODO:Technically need to start a delay service for a single day (24 hours) and then enable login button
            //For now setting attempts_failed_login to 0

        }
    }

    @Override
    public void onEmailResendSuccess(String message) {
        Snackbar.make(snackBarView, message, Snackbar.LENGTH_SHORT).show();
    }


    public void getUserCredentials(FormEditText username_et, FormEditText password_et, String error_message, ProgressBar progressBar) {


        attempts_failed_login = validate(username_et, password_et, error_message) ? 1 : attempts_failed_login;

        if (attempts_failed_login < 3) {
            loginPresenter.login(this, username_et.getText().toString(), password_et.getText().toString());
            Log.i(TAG, "---> getUserCredentials() ---> Email and password passed to the presenter");
                progressBar.setVisibility(View.VISIBLE);
        } else if (attempts_failed_login > MAX_NUMBER_OF_ATTEMPTS) {

            progressBar.setVisibility(View.GONE);
            Log.e(TAG, "---> getUserCredentials ---> Disabling login button contact admin");
            Toast.makeText(this, "Disabling Login . Please contact the admin", Toast.LENGTH_SHORT).show();
            login_bt.setEnabled(false);
            Log.i(TAG, "---> onClick reset ---> Loading reset Password");
            startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));


        } else {
            progressBar.setVisibility(View.GONE);
            Log.e(TAG, "---> getUserCredentials ---> Invalid attempt count = " + attempts_failed_login);
        }


    }

    public boolean validate(FormEditText username_et, FormEditText password_et, String error_message) {

        username_et.addTextChangedListener(new TextObservables("em", username_et, new AndValidator(error_message, new EmailValidator("Invalid email format"), new EmptyValidator("Please enter your Email id"))));
        password_et.addTextChangedListener(new TextObservables("error", password_et, new AndValidator(error_message, new EmptyValidator("Field is Empty"))));
        Log.i(TAG, "---> Validators() ---> added");
        return (username_et.testValidity() && password_et.testValidity());
    }


    public void ifUserExistsLoadMain() throws NullPointerException {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                Log.i(TAG, "--->ifUserExistsLoadMain ---> Starting next activity since user exists");
            startActivity(new Intent(LoginActivity.this, DevicesActivity2.class));
            finish();
            } else if (attempts_failed_login > 0) {

                //Load Email confirmation dialog
                Snackbar.make(snackBarView, "Verify your registered email address to login ", Snackbar.LENGTH_SHORT).setAction("RESEND", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loginPresenter.onResendEmailConfirmation(LoginActivity.this);
                    }
                }).show();

            }
        } else {
            //  Snackbar.make(snackBarView,"Welcome, Please Login",Snackbar.LENGTH_SHORT).show();

        }
    }


}
