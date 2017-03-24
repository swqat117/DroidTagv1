package com.quascenta.petersroad.droidtag.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseUser;
import com.quascenta.petersroad.droidtag.R;
import com.quascenta.petersroad.droidtag.Utils.AlphaNumericValidator;
import com.quascenta.petersroad.droidtag.Utils.AlphaValidator;
import com.quascenta.petersroad.droidtag.Utils.AndValidator;
import com.quascenta.petersroad.droidtag.Utils.EmailValidator;
import com.quascenta.petersroad.droidtag.Utils.EmptyValidator;
import com.quascenta.petersroad.droidtag.Utils.NumericValidator;
import com.quascenta.petersroad.droidtag.core.RegisterContract;
import com.quascenta.petersroad.droidtag.core.RegisterPresenter;
import com.quascenta.petersroad.droidtag.widgets.FormAutoCompleteTextView;
import com.quascenta.petersroad.droidtag.widgets.FormEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by AKSHAY on 3/23/2017.
 */

public class RegisterUserFragment extends Fragment implements RegisterContract.View {

    //TODO ADD COMMENT SECTIONs

    private static final String TAG = RegisterUserFragment.class.getSimpleName();

    ChangePage changePage;

    @Bind(R.id.full_name)
    FormEditText user_name_et;

    @Bind(R.id.password)
    FormEditText password_et;

    @Bind(R.id.confirm_password)
    FormEditText confirm_password_et;

    @Bind(R.id.email)
    FormEditText email_et;

    @Bind(R.id.working_orginasation)
    FormEditText company_name_et;

    @Bind(R.id.address)
    FormEditText company_address_et;

    @Bind(R.id.state_province)
    FormEditText state_et;

    @Bind(R.id.country_code)
    FormAutoCompleteTextView country_code_tv;

    @Bind(R.id.mobile_number)
    FormEditText mobile_number_et;

    RegisterPresenter presenter;

    @OnClick(R.id.next)
    public void onClick() {
        if (testValidity()) {

            if (password_et.getText().toString().equals(confirm_password_et.getText().toString())) {
                presenter.sendString(email_et.getText().toString());
                presenter.sendString(password_et.getText().toString());
                presenter.sendString(user_name_et.getText().toString());
                presenter.sendString(company_name_et.getText().toString());
                presenter.sendString(company_address_et.getText().toString());
                presenter.sendString(state_et.getText().toString());
                presenter.sendString("+91" + mobile_number_et.getText().toString());
                presenter.register(getActivity());
            }

        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            changePage = (ChangePage) context;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View ConvertView = inflater.inflate(R.layout.fragment_scan, container, false);
        ButterKnife.bind(this, ConvertView);
        presenter = new RegisterPresenter(this);
        addValidators("error");
        return ConvertView;


    }

    public void addValidators(String errorMessage) {

        email_et.addValidator(new AndValidator(errorMessage, new EmailValidator("Invalid Format"), new EmptyValidator("Please enter your Email id")));
        password_et.addValidator(new AndValidator(errorMessage, new AlphaNumericValidator(errorMessage), new EmptyValidator("Field is Empty")));
        user_name_et.addValidator(new AndValidator(errorMessage, new AlphaValidator(errorMessage), new EmptyValidator("Field is Empty")));
        company_name_et.addValidator(new AndValidator(errorMessage, new AlphaNumericValidator(errorMessage), new EmptyValidator("Field is Empty")));
        company_address_et.addValidator(new EmptyValidator("Field is Empty"));
        state_et.addValidator(new AndValidator(errorMessage, new AlphaValidator(errorMessage), new EmptyValidator("Field is Empty")));
        mobile_number_et.addValidator(new NumericValidator("No alpha chars"));
        mobile_number_et.addValidator(new EmptyValidator("field is empty"));


    }


    public boolean testValidity() {


        boolean test1 = user_name_et.testValidity();
        boolean test2 = password_et.testValidity() & password_et.getText().toString().equals(confirm_password_et.getText().toString());
        boolean test3 = company_name_et.testValidity();
        boolean test4 = company_address_et.testValidity();
        boolean test5 = state_et.testValidity();
        boolean test6 = user_name_et.testValidity();
        boolean test7 = email_et.testValidity();
        boolean test9 = mobile_number_et.testValidity();


        return test1 & test2 & test3 & test4 & test5 & test6;
    }

    @Override
    public void onRegistrationSuccess(FirebaseUser firebaseUser) {

    }

    @Override
    public void onRegistrationFailure(String message) {

    }


    public interface ChangePage {
        void i();
    }


}
