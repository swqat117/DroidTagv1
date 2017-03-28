package com.quascenta.petersroad.droidtag.EventListeners;

import android.text.Editable;
import android.text.TextWatcher;

import com.quascenta.petersroad.droidtag.R;
import com.quascenta.petersroad.droidtag.Utils.Validator;
import com.quascenta.petersroad.droidtag.widgets.FormEditText;

/**
 * Created by AKSHAY on 3/27/2017.
 */

public class TextObservables implements TextWatcher {

    int status;
    FormEditText editText;


    public TextObservables(String s, FormEditText editText, Validator validator) {
        this.editText = editText;
        this.editText.addValidator(validator);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        if (editText.testValidity() && i > 4) {
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.img, 0);
        } else {
            System.out.println("---------------------" + editText.getText().toString());
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editText.testValidity()) {
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.img, 0);
        }
    }
}
