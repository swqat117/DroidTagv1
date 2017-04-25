package com.quascenta.petersroad.droidtag.main;

/**
 * Created by AKSHAY on 4/7/2017.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quascenta.petersroad.droidtag.R;
import com.quascenta.petersroad.droidtag.Utils.AlphaNumericValidator;
import com.quascenta.petersroad.droidtag.Utils.AndValidator;
import com.quascenta.petersroad.droidtag.Utils.EmptyValidator;
import com.quascenta.petersroad.droidtag.widgets.FormEditText;


@SuppressWarnings("deprecation")
public class RegisterDeviceSlide extends Fragment {


    private static final String ARG_LAYOUT_RES_ID = "layoutResId";
    FormEditText alias_name;
    FormEditText destination_company;
    FormEditText source_company;
    FormEditText source_location_et;
    FormEditText destination_location_et;
    OnNext onNext;
    private int layoutResId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_bt, container, false);
        String error_message = "Error";
        init(view);

        source_company.addTextChangedListener(new TextObservables("error", source_company, new AndValidator(error_message, new AlphaNumericValidator(error_message), new EmptyValidator("Field is Empty"))));
        source_location_et.addTextChangedListener(new TextObservables("error", source_location_et, new EmptyValidator("Field is Empty")));
        destination_company.addTextChangedListener(new TextObservables("error", destination_company, new AndValidator(error_message, new AlphaNumericValidator(error_message), new EmptyValidator("Field is Empty"))));
        destination_location_et.addTextChangedListener(new TextObservables("error", destination_location_et, new EmptyValidator("Field is Empty")));

        destination_location_et.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_CENTER:
                    case KeyEvent.KEYCODE_ENTER:

                        return isPolicyRespected();
                    default:
                        break;
                }
            }
            return false;
        });
        return view;


    }

    public void init(View view) {

        source_company = (FormEditText) view.findViewById(R.id.source_company_name_et);
        source_location_et = (FormEditText) view.findViewById(R.id.source_location_et);
        alias_name = (FormEditText) view.findViewById(R.id.alias_name_et);
        destination_company = (FormEditText) view.findViewById(R.id.destination_company_name_et);
        destination_location_et = (FormEditText) view.findViewById(R.id.destination_location_et);
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPolicyRespected();
            }
        });

    }

    public boolean isPolicyRespected() {
        if (source_company.testValidity()
                && source_location_et.testValidity()
                && destination_company.testValidity()
                && destination_location_et.testValidity()
                ) {


            Bundle bundle = new Bundle();
            bundle.putString("alias-name", alias_name.getText().toString());
            bundle.putString("source-company", source_company.getText().toString());
            bundle.putString("source-location", source_location_et.getText().toString());
            bundle.putString("destination-company", destination_company.getText().toString());
            bundle.putString("destination-location", destination_location_et.getText().toString());
            try {
                if (((OnNext) getActivity()).RegisterBundle(bundle)) {
                    bundle.clear();
                }
            } catch (ClassCastException cce) {
                cce.printStackTrace();
            }


            return true;
        }

        return false;
    }


    interface OnNext {
        boolean RegisterBundle(Bundle bundle);
    }


}
