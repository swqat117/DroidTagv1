package com.quascenta.petersroad.droidtag.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.quascenta.petersroad.droidtag.Bluetooth.BleVO.BleDevice;
import com.quascenta.petersroad.droidtag.Bluetooth.LeDeviceListAdapter;
import com.quascenta.petersroad.droidtag.EventBus.Events;
import com.quascenta.petersroad.droidtag.EventListeners.EventListener;
import com.quascenta.petersroad.droidtag.EventListeners.FragmentLifeCycle;
import com.quascenta.petersroad.droidtag.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AKSHAY on 3/10/2017.
 */

public class ScanDeviceFragment extends Fragment {

    private static final String TAG = "ScanDeviceFragment";
    @Bind(R.id.listView)
    ListView scanLeDeviceList;

    @Bind(R.id.devices_title)
    TextView title_scan;

    @Bind(R.id.devices_count)
    TextView count_scan;


    EventListener eventListener;


    BleDevice device;
    ProgressDialog progressDialog;

    LeDeviceListAdapter mLeDeviceListAdapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            eventListener = (EventListener) context;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Subscribe(sticky = true)
    public void recieveStickyEvent(Events.Send_BLEDeviceForRegistration<BleDevice> s) {

        device = s.getContent();
        mLeDeviceListAdapter = new LeDeviceListAdapter(getContext());
        mLeDeviceListAdapter.addDevice(device);
        System.out.println(device.getmBleName());
        title_scan.setVisibility(View.VISIBLE);
        count_scan.setVisibility(View.VISIBLE);
        title_scan.setText("Available Loggers");
        scanLeDeviceList.setAdapter(mLeDeviceListAdapter);

    }


    void dummyAdd() {
        mLeDeviceListAdapter.addDevice(new BleDevice("Test", "00:00:00:00:0a"));
        mLeDeviceListAdapter.addDevice(new BleDevice("Test", "00:00:00:00:0a"));
        mLeDeviceListAdapter.addDevice(new BleDevice("Test", "00:00:00:00:0a"));
        mLeDeviceListAdapter.addDevice(new BleDevice("Test", "00:00:00:00:0a"));
    }


    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().hasSubscriberForEvent(Events.Send_BLEDeviceForRegistration.class)) {
            EventBus.getDefault().register(this);
        }


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().hasSubscriberForEvent(Events.Send_BLEDeviceForRegistration.class)) {
            EventBus.getDefault().register(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View ConvertView = inflater.inflate(R.layout.fragment_scan, container, false);
        ButterKnife.bind(this, ConvertView);
        title_scan.setVisibility(View.GONE);
        count_scan.setVisibility(View.GONE);
        scanLeDeviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                eventListener.check((BleDevice) adapterView.getAdapter().getItem(i));

            }
        });
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

}






