package com.quascenta.petersroad.droidtag.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.quascenta.petersroad.droidtag.R;
import com.quascenta.petersroad.droidtag.core.LoginContract;
import com.quascenta.petersroad.droidtag.core.LoginPresenter;

import butterknife.Bind;

/**
 * Created by AKSHAY on 3/22/2017.
 */

public class LoginActivity extends BaseActivity implements LoginContract.View {


    @Bind(R.id.email)
    EditText inputEmail;

    @Bind(R.id.password)
    EditText password_et;
    LoginPresenter loginPresenter = new LoginPresenter(this);
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, DevicesActivity2.class));
            finish();
        }

        // set the view now
        setContentView(R.layout.activity_login);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
          
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = password_et.getText().toString();
                Log.i(TAG, "onClick: ");
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginPresenter.login(LoginActivity.this, "abv", "jsdfjsd");
                progressBar.setVisibility(View.VISIBLE);

                //authenticate user

            }
        });

    }

    @Override
    public void onLoginSuccess(String message) {

    }

    @Override
    public void onLoginFailure(String message) {

    }
}
