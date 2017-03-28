package com.quascenta.petersroad.droidtag.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.quascenta.petersroad.droidtag.Bluetooth.BleVO.BleDevice;
import com.quascenta.petersroad.droidtag.EventListeners.FragmentLifeCycle;
import com.quascenta.petersroad.droidtag.R;
import com.quascenta.petersroad.droidtag.Utils.AlphaValidator;
import com.quascenta.petersroad.droidtag.Utils.AndValidator;
import com.quascenta.petersroad.droidtag.Utils.EmptyValidator;
import com.quascenta.petersroad.droidtag.adapters.GooglePlacesAutocompleteAdapter;
import com.quascenta.petersroad.droidtag.widgets.FormAutoCompleteTextView;
import com.quascenta.petersroad.droidtag.widgets.FormEditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AKSHAY on 3/14/2017.
 */

public class RegisterDeviceFragment extends Fragment implements FragmentLifeCycle, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "RegisterDeviceFragment";
    private static final LatLngBounds BOUNDS_INDIA = new LatLngBounds(new LatLng(23.63936, 68.14712), new LatLng(28.20453, 97.34466));
    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));
    protected GoogleApiClient mGoogleApiClient;
    GooglePlacesAutocompleteAdapter autocompleteAdapter;
    GooglePlacesAutocompleteAdapter autocompleteAdapter_city_filters;
    @Bind(R.id.logger_name)
    FormEditText logger_name_et;
    @Bind(R.id.alias_name_et)
    FormEditText alias_name;
    @Bind(R.id.destination_company_name_et)
    FormAutoCompleteTextView destination_company;
    @Bind(R.id.source_company_name_et)
    FormAutoCompleteTextView source_company;
    @Bind(R.id.source_location_et)
    FormAutoCompleteTextView source_location_et;
    @Bind(R.id.destination_location_et)
    FormAutoCompleteTextView destination_location_et;

    String country;
    // TO PROVIDE MORE INFORMATION ABOUT THE RESULT SET (GOOGLE PLACES API ) , NOT REQUIRED RIGHT NOW


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
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .enableAutoManage(getActivity(), 0 /* clientId */, this)
                    .addApi(Places.GEO_DATA_API)
                    .build();
            View ConvertView = inflater.inflate(R.layout.fragment_register, container, false);
            ButterKnife.bind(this, ConvertView);
            initAutoCompleteTextViews();
            AddValidators(alias_name, "incorrect");
            return ConvertView;
        }

    public void AddValidators(FormEditText text, String errorMessage) {

        text.addValidator(new AndValidator(errorMessage, new AlphaValidator(errorMessage), new EmptyValidator("Field is Empty")));


    }

    public void initAutoCompleteTextViews() {
        AutocompleteFilter filter_postaltown_location = new AutocompleteFilter.Builder().setTypeFilter(Place.TYPE_POSTAL_TOWN).build();
        AutocompleteFilter filter_company_name = new AutocompleteFilter.Builder().setTypeFilter(Place.TYPE_OTHER).build();
        //Adding city filter to the search and binding it to adapter
        autocompleteAdapter_city_filters = new GooglePlacesAutocompleteAdapter(getContext(), mGoogleApiClient, BOUNDS_INDIA,
                filter_postaltown_location, 1);
        //no filter
        autocompleteAdapter = new GooglePlacesAutocompleteAdapter(getContext(), mGoogleApiClient, BOUNDS_INDIA, filter_company_name, 1);

        initAutoCompleteTextView(source_company, autocompleteAdapter, false);
        initAutoCompleteTextView(source_location_et, autocompleteAdapter_city_filters, false);
        initAutoCompleteTextView(destination_company, autocompleteAdapter, false);
        initAutoCompleteTextView(destination_location_et, autocompleteAdapter, false);
    }

    public void initAutoCompleteTextView(FormAutoCompleteTextView textView, GooglePlacesAutocompleteAdapter adapter, boolean s) {

        textView.setAdapter(adapter);
        textView.setTextColor(Color.BLUE);
        textView.setOnItemClickListener((adapterView, view, i, l) -> {
            final CharSequence primaryText = adapter.getItem(i).getPrimaryText(null);
            Log.i(TAG, "Autocomplete item selected: " + primaryText);
            textView.setText(primaryText);

            /*  if(s) {
                  PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                          .getPlaceById(mGoogleApiClient, placeId);
                  placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
              }
                else{
                  PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                          .getPlaceById(mGoogleApiClient, placeId);
                  placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
              }
*/
        });


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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());

        // TODO(Developer): Check error code and notify the user of error state and resolution.
        Toast.makeText(getContext(),
                "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();

    }

   /* private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = places -> {
        if (!places.getStatus().isSuccess()) {
            // Request did not complete successfully
            Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
            places.release();
            return;
        }
        // Get the Place object from the buffer.
        final Place place = places.get(0);
        final Locale locale = place.getLocale();
        LatLng coordinates = place.getLatLng(); // Get the coordinates from your place
        Geocoder geocoder = new Geocoder(getContext(), locale);

        List<Address> addresses = null; // Only retrieve 1 address
        try {
            addresses = geocoder.getFromLocation(
                    coordinates.latitude,
                    coordinates.longitude,
                    1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address address = addresses.get(0);
        source_company.setText(place.getName());
        source_location_et.append(locale.getCountry());


        places.release();
    };
    private ResultCallback<PlaceBuffer> mUpdateCitisCallBack
            = places -> {
        if (!places.getStatus().isSuccess()) {
            // Request did not complete successfully
            Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
            places.release();
            return;
        }
        // Get the Place object from the buffer.
        final Place place = places.get(0);
        final Locale locale = place.getLocale();
        LatLng coordinates = place.getLatLng(); // Get the coordinates from your place
        Geocoder geocoder = new Geocoder(getContext(), locale);

        List<Address> addresses = null; // Only retrieve 1 address
        try {
            addresses = geocoder.getFromLocation(
                    coordinates.latitude,
                    coordinates.longitude,
                    1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address address = addresses.get(0);
        source_company.setText(place.getName());
        source_location_et.append(locale.getCountry());


        places.release();
    };*/


}