package com.quascenta.petersroad.droidtag.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.quascenta.petersroad.droidtag.EventListeners.RegisterClickListener;
import com.quascenta.petersroad.droidtag.EventListeners.TextObservables;
import com.quascenta.petersroad.droidtag.R;
import com.quascenta.petersroad.droidtag.Utils.AlphaNumericValidator;
import com.quascenta.petersroad.droidtag.Utils.AlphaValidator;
import com.quascenta.petersroad.droidtag.Utils.AndValidator;
import com.quascenta.petersroad.droidtag.Utils.EmailProviderValidator;
import com.quascenta.petersroad.droidtag.Utils.EmailValidator;
import com.quascenta.petersroad.droidtag.Utils.EmptyValidator;
import com.quascenta.petersroad.droidtag.Utils.NumericValidator;
import com.quascenta.petersroad.droidtag.Utils.SameValueValidator;
import com.quascenta.petersroad.droidtag.activities.LoginActivity;
import com.quascenta.petersroad.droidtag.core.RegsiterCore.RegisterContract;
import com.quascenta.petersroad.droidtag.core.RegsiterCore.RegisterPresenter;
import com.quascenta.petersroad.droidtag.widgets.FormAutoCompleteTextView;
import com.quascenta.petersroad.droidtag.widgets.FormEditText;

/**
 * Created by AKSHAY on 3/23/2017.
 */

public class RegisterUserFragment extends Fragment implements RegisterContract.View, RegisterClickListener {

    //TODO ADD COMMENT SECTIONs

    private static final String TAG = RegisterUserFragment.class.getSimpleName();


    FormEditText user_name_et;


    FormEditText password_et;


    FormEditText confirm_password_et;


    FormEditText email_et;


    FormEditText company_name_et;


    FormEditText company_address_et;


    FormEditText state_et;


    FormAutoCompleteTextView country_code_tv;


    FormEditText pin;

    ProgressDialog p;
    FormEditText mobile_number_et;

    RegisterPresenter presenter;


    public void init(View view) {
        p = new ProgressDialog(getContext());
        user_name_et = (FormEditText) view.findViewById(R.id.full_name);
        password_et = (FormEditText) view.findViewById(R.id.password);
        confirm_password_et = (FormEditText) view.findViewById(R.id.confirm_password);
        state_et = (FormEditText) view.findViewById(R.id.state_province);
        company_address_et = (FormEditText) view.findViewById(R.id.address);
        company_name_et = (FormEditText) view.findViewById(R.id.working_orginasation);
        email_et = (FormEditText) view.findViewById(R.id.email);
        mobile_number_et = (FormEditText) view.findViewById(R.id.mobile_number);
        country_code_tv = (FormAutoCompleteTextView) view.findViewById(R.id.Country);
        pin = (FormEditText) view.findViewById(R.id.pin_code);

    }


    public boolean prepareData() {
        p.setMessage("Validating Data. please wait..");
        p.setIndeterminate(false);
        p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        p.setCancelable(false);
        p.show();
        if (testValidity()) {
            if (new EmailProviderValidator(email_et).isValid()) {
                p.setMessage("Validated . please wait.");
                p.setMessage("Pushing . please wait..");
                presenter.sendString(email_et.getText().toString());
                presenter.sendString(password_et.getText().toString());
                presenter.sendString(user_name_et.getText().toString());
                presenter.sendString(company_name_et.getText().toString());
                presenter.sendString(company_address_et.getText().toString());
                presenter.sendString(state_et.getText().toString());
                presenter.sendString("+91" + mobile_number_et.getText().toString());
                presenter.register(getActivity());
                return true;
            } else {
                p.dismiss();
                Toast.makeText(getContext(), "Invalid email provider", Toast.LENGTH_SHORT).show();
            }

        } else {
            p.dismiss();
            Toast.makeText(getContext(), "Please rectify to post!", Toast.LENGTH_SHORT).show();
            return false;
        }


        return false;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Oncreate loaded");


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View ConvertView = inflater.inflate(R.layout.fragment_register_wizard1, container, false);
        Log.i(TAG, "Oncreateview  loaded");
        init(ConvertView);
        String error_message = "w33";
        addValidators("error", "erro1");
        presenter = new RegisterPresenter(this);


        return ConvertView;


    }

    public void addValidators(String errorMessage, String error_message) {

        email_et.addTextChangedListener(new TextObservables("em", email_et, new AndValidator(error_message, new EmailValidator("Invalid email format"), new EmptyValidator("Please enter your Email id"))));
        password_et.addTextChangedListener(new TextObservables("error", password_et, new AndValidator(error_message, new EmptyValidator("Field is Empty"))));
        user_name_et.addTextChangedListener(new TextObservables("error", user_name_et, new AndValidator(error_message, new AlphaValidator(error_message), new EmptyValidator("Field is Empty"))));
        company_name_et.addTextChangedListener(new TextObservables("error", company_name_et, new AndValidator(error_message, new AlphaNumericValidator(error_message), new EmptyValidator("Field is Empty"))));
        company_address_et.addTextChangedListener(new TextObservables("error", company_address_et, new EmptyValidator("Field is Empty")));
        state_et.addTextChangedListener(new TextObservables("error", state_et, new AndValidator(error_message, new AlphaValidator(error_message), new EmptyValidator("Field is Empty"))));
        mobile_number_et.addTextChangedListener(new TextObservables("error", mobile_number_et, new NumericValidator("No alpha chars")));
        pin.addTextChangedListener(new TextObservables("error", pin, new EmptyValidator("error")));
        confirm_password_et.addTextChangedListener(new TextObservables("error", confirm_password_et, new SameValueValidator(password_et, "Passwords dont match")));

    }


    public boolean testValidity() {


        boolean test1 = user_name_et.testValidity();
        boolean test2 = password_et.testValidity();
        boolean test3 = company_name_et.testValidity();
        boolean test4 = company_address_et.testValidity();
        boolean test5 = state_et.testValidity();
        boolean test6 = user_name_et.testValidity();
        boolean test7 = email_et.testValidity();
        boolean test9 = mobile_number_et.testValidity();
        boolean test10 = confirm_password_et.testValidity();

        return test1 && test2 && test3 && test4 && test5 && test6 && test7 && test9;
    }

    @Override
    public void onRegistrationSuccess(FirebaseUser firebaseUser) {
        p.dismiss();
        Snackbar.make(getView(), "Verification email is sent to" + firebaseUser.getEmail(), Snackbar.LENGTH_LONG).show();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        Log.d(TAG, "Registration Succeeded");

    }

    @Override
    public void onProgress(String message) {
        p.setMessage(message);
    }

    @Override
    public void onRegistrationFailure(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
        p.dismiss();

    }


    @Override
    public void get1() {
        Log.d(TAG, "Interface is set up");


    }
}
