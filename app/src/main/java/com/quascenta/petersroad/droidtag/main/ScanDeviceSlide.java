package com.quascenta.petersroad.droidtag.main;

/**
 * Created by AKSHAY on 4/7/2017.
 */

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.quascenta.petersroad.droidtag.R;
import com.quascenta.petersroad.droidtag.widgets.Device;
import com.skyfishjy.library.RippleBackground;


@SuppressWarnings("deprecation")
public class ScanDeviceSlide extends Fragment {
    public static final String TAG = ScanDeviceSlide.class.getSimpleName();
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_BT = 1;

    //  private byte qppDataSend1[] = new byte[]{0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
    //          0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
    //Obtain the deviceName and deviceAddress from blueWelcomeActivity
    private static final String ARG_LAYOUT_RES_ID = "layoutResId";
    private static BluetoothAdapter mBluetoothAdapter;
    Handler mHandler;
    Contract.Presenter presenter;
    private BluetoothManager mBluetoothManager;
    private int layoutResId;
    private boolean enabled = false;
    private RippleBackground rippleBackground;
    private Device[] device = new Device[4];
    private SparseArray<BluetoothDevice> mDevices;
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, final int rssi, byte[] scanRecord) {
                    getActivity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        Log.i(TAG, device.getName() + "\t address : " + device.getAddress());
                                                        mDevices.put(device.hashCode(), device);
                                                        show(mDevices);
                                                    }


                                                }


                    );

                }

            };
    /**
     * STOP RUNNABLE - StopScan()
     */
    private Runnable mStopRunnable = new Runnable() {
        @Override
        public void run() {
            stopScan();
        }
    };
    /**
     * START RUNNABLE - StartScan()
     */
    private Runnable mStartRunnable = new Runnable() {
        @Override
        public void run() {
            startScan();
        }
    };

    private boolean initialize() {
        //For API level 18  and above ,get a reference to BluetoothAdapter through BluetoothManager
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
            //使用一个getSystemService()方法,返回一个BluetootManager对象
            if (mBluetoothManager == null) {
                Log.e(TAG, "--> Unable to initialize BluetoothManager.");
                return false;
            }
        }

        //从BluetoothManager对象中获取BluetoothAdapter适配器
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Log.e(TAG, "--> Unable to obtain a BluetoothAdapter.");
            return false;
        }
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();


        mDevices = new SparseArray<>();
        if (getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID)) {
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scan_bt, container, false);
        initialize();

        initDevices(view);
        FloatingActionButton mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);

        rippleBackground = (RippleBackground) view.findViewById(R.id.content);

        mFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color
                .parseColor("#839DDA")));
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startScan();

            }
        });

        return view;
    }

    public void initDevices(View view) {
        device[0] = (Device) view.findViewById(R.id.fd);
        device[1] = (Device) view.findViewById(R.id.fd1);
        device[2] = (Device) view.findViewById(R.id.fd2);
        device[3] = (Device) view.findViewById(R.id.fd3);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_BT: {
                // permission denied, boo! Disable the
// functionality that depends on this permission.
                enabled = grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED;
                return;
            }
        }
    }


    void sendDeviceAddress(String x) {
        Bundle bundle = new Bundle();
        bundle.putString("device-address", x);

        try {
            ((ScanDeviceSlide.OnNextScan) getActivity()).onScanResult(bundle);
        } catch (ClassCastException cce) {
            cce.printStackTrace();
        }
    }

    private void startScan() {
        mDevices.clear();
        if (!enabled) {
            enabled = true;
            rippleBackground.startRippleAnimation();
            mBluetoothAdapter.startLeScan(mLeScanCallback);
            mHandler.postDelayed(mStopRunnable, 5500);
        }
    }

    private void stopScan() {
        if (enabled) {
            enabled = false;
            unshow(mDevices);
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
            rippleBackground.stopRippleAnimation();

        }
    }

    public void unshow(SparseArray<BluetoothDevice> mDevices) {
        for (int i = 0; i < mDevices.size(); i++) {
            if (!device[i].isSelected())
                device[i].setVisibility(View.GONE);


        }
    }

    public void show(SparseArray<BluetoothDevice> mDevices) {
        for (int i = 0; i < mDevices.size(); i++) {

            device[i].setDeviceName(mDevices.valueAt(i).getName());
            device[i].setOnClickListener(new ViewOnClickListener());
            device[i].setVisibility(View.VISIBLE);

        }
    }


    /*private void foundDevice(Device foundDevice) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        ArrayList<Animator> animatorList = new ArrayList<>();
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(foundDevice, "ScaleX", 0f, 1.2f, 1f);
        animatorList.add(scaleXAnimator);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(foundDevice, "ScaleY", 0f, 1.2f, 1f);
        animatorList.add(scaleYAnimator);
        animatorSet.playTogether(animatorList);
        foundDevice.setVisibility(View.VISIBLE);
        animatorSet.start();
    }
*/


    public boolean isPolicyRespected() {
        String address = "";
        for (int i = 0; i < mDevices.size(); i++) {
            if (device[i].isSelected()) {
                sendDeviceAddress(mDevices.valueAt(i).getAddress());
                mDevices.clear();
                stopScan();
                Toast.makeText(getContext(), " Scan Stopped", Toast.LENGTH_SHORT).show();


                return true;

            }

        }
        return false;
    }


    public interface OnNextScan {
        boolean onScanResult(Bundle bundle);
    }

    class ViewOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int id = v.getId();

            switch (id) {
                case R.id.fd:
                    device[0].getSelected(true);
                    device[1].getSelected(false);
                    device[2].getSelected(false);
                    device[3].getSelected(false);
                    isPolicyRespected();

                    break;
                case R.id.fd1:
                    device[0].getSelected(false);
                    device[1].getSelected(true);
                    device[2].getSelected(false);
                    device[3].getSelected(false);
                    isPolicyRespected();

                    break;
                case R.id.fd2:
                    device[0].getSelected(false);
                    device[1].getSelected(false);
                    device[2].getSelected(true);
                    device[3].getSelected(false);
                    isPolicyRespected();

                    break;
                case R.id.fd3:
                    device[0].getSelected(false);
                    device[1].getSelected(false);
                    device[2].getSelected(false);
                    device[3].getSelected(true);
                    isPolicyRespected();

                    break;
            }
        }
    }
}





