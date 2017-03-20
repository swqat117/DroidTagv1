package com.quascenta.petersroad.droidtag.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.quascenta.petersroad.droidtag.Bluetooth.BleVO.BleDevice;
import com.quascenta.petersroad.droidtag.EventListeners.EventListener;
import com.quascenta.petersroad.droidtag.EventListeners.FragmentLifeCycle;
import com.quascenta.petersroad.droidtag.R;
import com.quascenta.petersroad.droidtag.adapters.GooglePlacesAutocompleteAdapter;
import com.quascenta.petersroad.droidtag.widgets.FormAutoCompleteTextView;
import com.quascenta.petersroad.droidtag.widgets.FormEditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by AKSHAY on 3/14/2017.
 */

public class RegisterDeviceFragment extends Fragment implements FragmentLifeCycle{
    private static final String TAG = "RegisterDeviceFragment";

    EventListener eventListener;
    @Bind(R.id.logger_name)
    FormEditText logger_name_et;


    @Bind(R.id.alias_name_et)
    FormEditText alias_name;


    @Bind(R.id.source_company_name_et)
    FormEditText source_company_name;


    @Bind(R.id.source_location_et)
    FormAutoCompleteTextView source_location;


    @Bind(R.id.destination_company_name_et)
    FormEditText destination_company;


    @Bind(R.id.destination_
            location_et)
    FormAutoCompleteTextView destination_location;



    @Bind(R.id.upper_limit_et)
    FormEditText temp_upper_limit;


    @Bind(R.id.lower_limit_et)
    FormEditText temp_lower_limit;


    @Bind(R.id.upper_limit_rh_et)
    FormEditText rh_upper_limit;


    @Bind(R.id.lower_limit_rh_et)
    FormEditText rh_lower_limit;



@OnClick(R.id.destination_location_et)
public void ck(){

    destination_location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String  itemValue    = (String) adapterView.getItemAtPosition(i);
            destination_location.setText(itemValue);

        }
    });
}




    @Override
        public void onStop() {
            EventBus.getDefault().unregister(this);
            Log.d(TAG,"onStop");
            super.onStop();

        }

    @Override
    public void onResume() {
        super.onResume();

    }




        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View ConvertView = inflater.inflate(R.layout.fragment_register2, container, false);
            ButterKnife.bind(this, ConvertView);
            destination_location.requestFocus();
            destination_location.setAdapter(new GooglePlacesAutocompleteAdapter(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1));
            destination_location.setThreshold(3);
            return ConvertView;
        }


        @Override
        public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
            super.onViewStateRestored(savedInstanceState);

        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);

        }


    @Override
    public void onPauseFragment() {
        Log.i(TAG, "onPauseFragment()");
        Toast.makeText(getActivity(), "onPauseFragment():" + TAG, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResumeFragment(BleDevice device) {
        Log.i(TAG, "onResumeFragment()");
        logger_name_et.setTextColor(Color.BLUE);
        logger_name_et.setText(device.getmBleName());
        logger_name_et.setEnabled(false);
        alias_name.requestFocus();
    }

    @Override
    public void send(BleDevice device) {
        Log.d(TAG,device.getmBleName());


    }
}