package com.quascenta.petersroad.droidtag.main;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.quascenta.petersroad.droidtag.R;
import com.quascenta.petersroad.droidtag.activities.BaseActivity;


/**
 * Created by AKSHAY on 4/7/2017.
 */
@SuppressWarnings("deprecation")
public class RegisterDeviceActivity extends BaseActivity implements Contract.View, RegisterDeviceSlide.OnNext, ScanDeviceSlide.OnNextScan {

    private static final String TAG = RegisterDeviceActivity.class.getSimpleName();
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private static final int PERMISSION_REQUEST_BT = 1;
    private static final int PERMISSION_REQUEST_BLUETOOTH = 1;
    private static final String DEVICE_NAME = "SensorTag";
    protected static String uuidQppService = "0000fee9-0000-1000-8000-00805f9b34fb";
    protected static String uuidQppCharWrite = "d44bc439-abfd-45a2-b575-925416129600";
    static BluetoothAdapter mBluetoothAdapter;
    String device_address = "";
    BluetoothManager mBluetoothManager;
    SharedPrefUtils sharedPrefUtils;

    Contract.Presenter presenter;


    protected void onDestroy() {
        mBluetoothAdapter = null;
        super.onDestroy();

    }

    /**
     * --> onStop()
     */
    @Override
    protected void onStop() {
        super.onStop();

    }

    /**
     * --> onResume()
     */
    @Override
    protected void onResume() {
        super.onResume();


    }


    /**
     * ---> onPause()
     */
    @Override
    protected void onPause() {
        super.onPause();


    }


    /**
     * --> onCreate()
     * <p>
     * <p>
     * Add all the require slides to generate the intro of adding a device
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_registerdevice);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkCallingOrSelfPermission(Manifest.permission.BLUETOOTH_PRIVILEGED) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("This app needs location access");
                builder1.setMessage("Please grant location access so this app can detect beacons.");
                builder1.setPositiveButton(android.R.string.ok, null);
                builder1.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH_PRIVILEGED}, PERMISSION_REQUEST_COARSE_LOCATION);

                        }
                    }
                }).show();
            }
        }
        initialize();
        sharedPrefUtils = new SharedPrefUtils(getApplicationContext());
        presenter = new Presenter(this);


        RegisterDeviceSlide registerDeviceSlide = new RegisterDeviceSlide();
        getSupportFragmentManager().beginTransaction().add(R.id.coordinator, registerDeviceSlide).commit();


        //---> Intro slider
     /*   addSlide(AppIntroFragment.newInstance("Device Setup","Welcome to Quintic Device setup , Please Follow\n" +
                "    the instructions carefully to set up the device .Note : once the device is set up or configured, it cannot be\n" +
                "        reconfigured", R.drawable.logo,getColor(R.color.colorAccent)));

        //---> Scan Slider
        addSlide(RegisterDeviceSlide.newInstance(R.layout.register_bt));
        addSlide(ScanDeviceSlide.newInstance(R.layout.scan_bt,mBluetoothAdapter));
        addSlide(SampleSlide3.newInstance(R.layout.panel_bt));*/


    }


    private boolean initialize() {
        //For API level 18  and above ,get a reference to BluetoothAdapter through BluetoothManager
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
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


    private boolean checkIfBluetoothIsEnabled() {

        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            //inif Bluetooth is disabled
            startActivity(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE));
            finish();
            return true;
        } else if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {

            Toast.makeText(this, "No LE Support", Toast.LENGTH_SHORT).show();
            finish();
            return false;

        }
        return false;
    }


    // Do something when the slide changes.


    @Override
    public void onSuccess(String message) {

    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public void onEmailResendSuccess(String message) {

    }


    void send(LoggerDevice message) {
        try {
            //   send.send(message);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdate(LoggerDevice message) {
        sharedPrefUtils.saveString("device-address", message.getDeviceAddress());
        send(message);
        Log.d(TAG, message.getDeviceAddress());
    }


    @Override
    public boolean RegisterBundle(Bundle bundle) {


        if (presenter.sendData(bundle.getString("device-name"), bundle.getString("source-company"), bundle.getString("source-location"),
                bundle.getString("destination-company"), bundle.getString("destination-location"))) {
            ScanDeviceSlide deviceSlide = new ScanDeviceSlide();
            getSupportFragmentManager().beginTransaction().replace(R.id.coordinator, deviceSlide).addToBackStack(null).commit();
            return true;

        }
        return false;
    }

    @Override
    public boolean onScanResult(Bundle bundle) {
        if (presenter.send("device-address", bundle.getString("device-address"))) {
            SampleSlide3 sampleSlide3 = new SampleSlide3();
            sampleSlide3.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.coordinator, sampleSlide3).addToBackStack(null).commit();
            return true;
        }
        return false;
    }


}
