package com.quascenta.petersroad.droidtag.main;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.quascenta.petersroad.droidtag.R;
import com.quascenta.petersroad.droidtag.Utils.Hex;
import com.quascenta.petersroad.droidtag.widgets.FormEditText;


/**
 * Created by AKSHAY on 4/10/2017.
 */
@SuppressWarnings("deprecation")
public class SampleSlide3 extends Fragment {
    public static final String TAG = SampleSlide3.class.getSimpleName();
    private static final String HexStr = "0123456789abcdefABCDEF";
    int lowerlimit;
    int upperlimit;
    TextView textViewDevice;
    TextView textViewAddress;
    String hexString = "";
    RelativeLayout relativeLayout;
    StringBuilder stringBuilder;
    TextView textInvoice;
    FormEditText editText;
    FormEditText editText1;
    FormEditText lowerLimit;
    FormEditText upperLimit;
    String device_address;
    Switch twitch;
    BluetoothLeService bluetoothLeService;
    Handler mHandler;
    Intent intent;
    SharedPrefUtils sharedPrefUtils;
    ProgressDialog progressDialog, progressDialog1;
    final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BluetoothLeService.DATA_ERROR)) {
                Toast.makeText(getContext(), intent.getStringExtra(BluetoothLeService.DATA_ERROR), Toast.LENGTH_SHORT).show();

            } else if (intent.getAction().equals(BluetoothLeService.DATA_SUCCESS)) {

                change(intent);


            } else if (intent.getAction().equals(BluetoothLeService.DATA_STATUS)) {
                //      new ConfigureDeviceTask().execute();

            } else if (intent.getAction().equals(BluetoothLeService.ACTION_GATT_CONNECTED)) {
                progressDialog.dismiss();
                progressDialog.setMessage("Connected, Talking to the logger");
            }
        }
    };
    boolean isBound = false;
    private SparseArray<BluetoothDevice> mDevices;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            bluetoothLeService = ((BluetoothLeService.LocalBinder) iBinder).getService();
            if (!bluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                getActivity().finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            bluetoothLeService.connect(device_address);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            bluetoothLeService = null;
            isBound = false;
        }
    };

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.DATA_ERROR);
        intentFilter.addAction(BluetoothLeService.DATA_SUCCESS);
        return intentFilter;
    }

    public static int toUnsignedInt(byte x) {
        return ((int) x) & 0xff;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
        mDevices = new SparseArray<BluetoothDevice>();
        sharedPrefUtils = new SharedPrefUtils(getActivity().getApplicationContext());
        stringBuilder = new StringBuilder();
        bluetoothLeService = new BluetoothLeService();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.panel_bt, container, false);
        initMainPanel(view);

        device_address = sharedPrefUtils.getString("device-address");

            intent = new Intent(getActivity(), BluetoothLeService.class);
            intent.putExtra("device-address", device_address);
            textViewAddress.setText(device_address);
            getActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("WAIT");
        progressDialog.setMessage("Connecting to the logger");
        progressDialog1 = new ProgressDialog(getContext());
        progressDialog1.setIndeterminate(true);
        progressDialog1.setTitle("WAIT");
        progressDialog1.setMessage("Connecting to the logger");



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(broadcastReceiver, makeGattUpdateIntentFilter());
        if (bluetoothLeService != null) {
            final boolean result = bluetoothLeService.connect(device_address);
            Log.d(TAG, "Connect request result=" + result);
        }
    }

    public void initMainPanel(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.findViewById(R.id.fab).setBackgroundTintList(ColorStateList.valueOf(Color
                    .parseColor("#839DDA")));
        }
        textViewDevice = (TextView) view.findViewById(R.id.tx1);
        textViewAddress = (TextView) view.findViewById(R.id.tx2);

        twitch = (Switch) view.findViewById(R.id.twitch);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.rx1);
        textInvoice = (TextView) view.findViewById(R.id.tx12);
        editText = (FormEditText) view.findViewById(R.id.source_company_name_et);
        editText1 = (FormEditText) view.findViewById(R.id.source_company_name_det);
        lowerLimit = (FormEditText) view.findViewById(R.id.lower_et);
        upperLimit = (FormEditText) view.findViewById(R.id.higher_et);


        twitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                gsdf());


    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unbindService(serviceConnection);
        bluetoothLeService = null;
    }

    public void gsdf() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle("Alert dialog title");
        builder.setMessage("Please enter the lower limit and upper limit ");
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        // DO TASK
                        progressDialog1.show();
                        progressDialog1.setMessage("Configuring Logger");
                        new CheckBleConfigurationTask().execute();


                    }
                });

// Set `EditText` to `dialog`. You can add `EditText` from `xml` too.
        View promptView = getActivity().getLayoutInflater().inflate(R.layout.limit_dialog, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final FormEditText editText_lower = (FormEditText) promptView.findViewById(R.id.lower_et);
        final FormEditText editText_higher = (FormEditText) promptView.findViewById(R.id.higher_et);
        builder.setView(promptView);
        final AlertDialog dialog = builder.create();
        dialog.show();
// Initially disable the button
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setEnabled(false);
// OR you can use here setOnShowListener to disable button at first
// time.

// Now set the textchange listener for edittext
        editText_lower.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Check if edittext is empty
                if (!s.toString().equals("")) {
                    lowerlimit = Integer.parseInt(s.toString());
                    if (TextUtils.isEmpty(s) || TextUtils.isEmpty(editText_higher.getText())) {
                        // Disable ok button
                        dialog.getButton(
                                AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                    } else {
                        // Something into edit text. Enable the button.
                        dialog.getButton(
                                AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    }
                }
            }
        });
        editText_higher.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Check if edittext is empty
                if (!s.toString().equals("")) {
                    upperlimit = Integer.parseInt(s.toString());
                    lowerlimit = Integer.parseInt(editText_lower.getText().toString());

                    if (TextUtils.isEmpty(s) || lowerlimit >= upperlimit) {
                        // Disable ok button
                        dialog.getButton(
                                AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                    } else {
                        // Something into edit text. Enable the button.
                        dialog.getButton(
                                AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    }

                }
            }
        });
    }

    public void change(Intent intent) {
        if (intent.getStringExtra(BluetoothLeService.DATA_SUCCESS).contains("The last saved configuration is clear.")) {

            progressDialog1.setMessage("Setting up device..");
            new ConfigureDeviceTask().execute();
        }
        if (intent.getStringExtra(BluetoothLeService.DATA_SUCCESS).contains("Device Configured.")) {
            progressDialog1.dismiss();
            relativeLayout.setVisibility(View.VISIBLE);
            editText.setText(sharedPrefUtils.getString("source-company") + " , " + sharedPrefUtils.getString("source-location"));
            editText1.setText(sharedPrefUtils.getString("destination-company") + " , " + sharedPrefUtils.getString("destination-location"));
            lowerLimit.setText(String.valueOf(lowerlimit + " C"));
            upperLimit.setText(String.valueOf(upperlimit + " C"));
        }
    }


    public String padZero(int number, int radix, int length) {
        number = number * 10;
        String string = Long.toString(number, radix);
        StringBuilder builder = new StringBuilder().append(String.format("%0" + length + "d", 0));
        String x = builder.replace(length - string.length(), length, string).toString();
        byte[] n = Hex.toBytes(x);
        return x;
    }

    private class CheckBleConfigurationTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            bluetoothLeService.clearLogger();
            return null;

        }
    }


    private class ConfigureDeviceTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            int val = Integer.parseInt(padZero(lowerlimit, 16, 4), 16);
            int val1 = Integer.parseInt(padZero(upperlimit, 16, 4), 16);
            byte[] finalarray3 = new byte[6];
            finalarray3[5] = (byte) (0x05);
            finalarray3[4] = (byte) ((val >>> 8) & 0x00ff);
            finalarray3[3] = (byte) ((val >>> 0) & 0x00ff);
            finalarray3[2] = (byte) ((val1 >>> 8) & 0x00ff);
            finalarray3[1] = (byte) ((val1 >>> 0) & 0x00ff);
            finalarray3[0] = (byte) (0xcd & 0xff);
            bluetoothLeService.configureLogger(finalarray3);
            return null;
        }
    }

    }











