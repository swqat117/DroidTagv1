package com.quascenta.petersroad.droidtag.main;

import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

/**
 * Created by AKSHAY on 4/12/2017.
 */

public class BluetoothLeService extends IntentService {
    //Obtain the deviceName and deviceAddress from blueWelcomeActivity
    public static final String EXTRAS_DEVICE_NAME = "deviceName";
    public static final String EXTRAS_DEVICE_ADDRESS = "deviceAddress";
    public static final String BROADCAST_ACTION = "com.quascenta.petersroad.droidtag.BROADCAST";
    public static final String DATA_STATUS = "com.quascenta.petersroad.droidtag.STATUS";
    public static final String DATA_SUCCESS = "com.quascenta.petersroad.droidtag.DATA";
    public static final String DATA_ERROR = "com.quascenta.petersroad.droidtag.DATA_ERR";
    public final static String ACTION_GATT_CONNECTED = "com.quascenta.petersroad.droidtag.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED =
            "com.quascenta.petersroad.droidtag.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED =
            "com.quascenta.petersroad.droidtag.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE =
            "com.quascenta.petersroad.droidtag.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA =
            "com.quascenta.petersroad.droidtag.EXTRA_DATA";
    private final static String TAG = BluetoothLeService.class.getSimpleName();
    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;
    protected static String uuidQppService = "0000fee9-0000-1000-8000-00805f9b34fb";
    protected static String uuidQppCharWrite = "d44bc439-abfd-45a2-b575-925416129600";
    private final IBinder mBinder = new LocalBinder();
    String device_address = "";
    StringBuilder stringBuilder;
    private byte qppDataSend1[] = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
    private boolean dataRecvFlag = false;
    private boolean qppSendDataState = false;
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private String mBluetoothDeviceAddress;
    private BluetoothGatt mBluetoothGatt;
    private int mConnectionState = STATE_DISCONNECTED;
    private boolean isInitialize;
    private boolean mConnected;
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            Log.i(TAG, "--> onConnectionStateChange : " + status + " newState : " + newState);

            if (newState == BluetoothProfile.STATE_CONNECTED) {
                mConnected = true;
                broadcastUpdate(ACTION_GATT_CONNECTED);
                Log.i(TAG, "Attempting to start service discovery :" + mBluetoothGatt.discoverServices());

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Log.i(TAG, "--> Disconnected from GATT server.");
                mConnected = false;
                dataRecvFlag = false;
                broadcastUpdate(ACTION_GATT_DISCONNECTED);
                if (qppSendDataState) {

                    qppSendDataState = false;
                }

            }

        }

        /**
         *Desc:
         *  通过onConnectionStateChange调用OnServiceDiscovered,来发现服务
         */
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (QppApi.qppEnable(gatt, uuidQppService, uuidQppCharWrite)) {
                isInitialize = true;
                broadcastUpdate(ACTION_DATA_AVAILABLE);
            } else {
                isInitialize = false;

            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            QppApi.updateValueForNotifition(gatt, characteristic);

        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            Log.w(TAG, "onDescriptorWrite");
            QppApi.setQppNextNotify(gatt, true);
        }

        //Callback indicating the result of a characteristic write operation
        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS && qppSendDataState) {

            } else if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.i(TAG, "--> A GATT Characteristic Write operation completed successfully");
            } else if (status == BluetoothGatt.GATT_FAILURE) {
                Log.i(TAG, "--> A GATT Characteristic Write operation completed is failure");
            }
        }
    };
    private String output = "";

    public BluetoothLeService() {
        super("BluetoothLeService");

    }

    @Override
    public void onCreate() {

        initialize();
        stringBuilder = new StringBuilder();
        QppApi.setCallback(new iQppCallback() {
            @Override
            public void onQppReceiveData(BluetoothGatt mBluetoothGatt, BluetoothGattCharacteristic qppUUIDForNotifyChar, byte[] qppData) {
                output = new String(qppData);
                stringBuilder.append(output);
                Log.d(TAG, stringBuilder.toString());
                broadcastUpdate(stringBuilder.toString(), qppData);

            }
        });
        super.onCreate();
    }

    public boolean clearLogger() {
        return configure(new byte[]{Commands.COMMAND_DEVICE_CLEARCONFIGURATION});
    }

    // Implements callback methods for GATT events that the app cares about.  For example,
    // connection change and services discovered.

    public boolean configureLogger(byte b[]) {

        return configure(b);

    }

    public boolean configure(byte b[]) {
        if (mConnected) {
            // Thread.sleep(1000);
            try {
                stringBuilder.setLength(0);
                Log.d(TAG, "Its connected before");
                if (QppApi.qppSendData(mBluetoothGatt, b)) {
                    disconnect();
                    Thread.sleep(1000);
                    connect(device_address);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
        return false;
    }

    private void broadcastUpdate(final String action) {
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    private void broadcastUpdate(final String action, byte[] qpp) {

        final Intent intent = new Intent(action);
        String x = stringBuilder.toString();

        if (x.contains("Can't reconfigure : Device is Locked.")) {
            intent.putExtra(DATA_ERROR, x);
        } else if (x.contains("Can't configure : Parameters Invalid.")) {
            intent.putExtra(DATA_ERROR, x);
        } else if (x.contains("Command Length is invalid")) {
            intent.putExtra(DATA_ERROR, x);
        } else if (x.contains("Can't start : Datalog is in progress")) {
            intent.putExtra(DATA_ERROR, x);
        } else if (x.contains("Can't start : Device is not configured")) {
            intent.putExtra(DATA_ERROR, x);
        } else if (x.contains("Can't Clear Memory :Datalog is Active.")) {
            intent.putExtra(DATA_ERROR, x);
        } else if (x.contains("Can't start : Date and Time is Invalid")) {
            intent.putExtra(DATA_ERROR, x);
        } else if (x.contains("No Alarm Messages to send.")) {
            intent.putExtra(DATA_ERROR, x);
        } else if (x.contains("Can't transfer data : Datalog is Active")) {
            intent.putExtra(DATA_ERROR, x);
        } else if (x.contains("No temperature data to send.")) {
            intent.putExtra(DATA_ERROR, x);
        } else if (x.contains("Can't start : Date and Time is Invalid")) {
            intent.putExtra(DATA_ERROR, x);
        } else if (x.contains("Can't Clear Config :Datalog is Active.")) {
            intent.putExtra(DATA_ERROR, x);
        } else if (x.contains("Can't resume : Datalog is Active") || x.contains("Can't resume : Datalog is Idle")) {
            intent.putExtra(DATA_ERROR, x);
        } else if (x.contains("Can't start : Device is not configured")) {
            intent.putExtra(DATA_ERROR, x);
        } else {
            intent.putExtra(DATA_SUCCESS, x);
        }

        sendBroadcast(intent);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // After using a given device, you should make sure that BluetoothGatt.close() is called
        // such that resources are cleaned up properly.  In this particular example, close() is
        // invoked when the UI is disconnected from the Service.
        close();
        return super.onUnbind(intent);
    }

    /**
     * Initializes a reference to the local Bluetooth adapter.
     *
     * @return Return true if the initialization is successful.
     */
    public boolean initialize() {
        // For API level 18 and above, get a reference to BluetoothAdapter through
        // BluetoothManager.
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                Log.e(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }

        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
            return false;
        }

        return true;
    }

    /**
     * Connects to the GATT server hosted on the Bluetooth LE device.
     *
     * @param address The device address of the destination device.
     * @return Return true if the connection is initiated successfully. The connection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public boolean connect(final String address) {
        if (mBluetoothAdapter == null || address == null) {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }

        // Previously connected device.  Try to reconnect.
        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress)
                && mBluetoothGatt != null) {
            Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection.");
            if (mBluetoothGatt.connect()) {
                mConnectionState = STATE_CONNECTING;
                return true;
            } else {
                return false;
            }
        }

        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        device_address = address;
        if (device == null) {
            Log.w(TAG, "Device not found.  Unable to connect.");
            return false;
        }
        // We want to directly connect to the device, so we are setting the autoConnect
        // parameter to false.
        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
        Log.d(TAG, "Trying to create a new connection.");
        mBluetoothDeviceAddress = address;
        mConnectionState = STATE_CONNECTING;

        return true;
    }

    /**
     * Disconnects an existing connection or cancel a pending connection. The disconnection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public void disconnect() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");

        }
        mConnected = false;
        mBluetoothGatt.disconnect();

    }

    /**
     * After using a given BLE device, the app must call this method to ensure resources are
     * released properly.
     */
    public void close() {
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    /**
     * Request a read on a given {@code BluetoothGattCharacteristic}. The read result is reported
     * asynchronously through the {@code BluetoothGattCallback#onCharacteristicRead(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int)}
     * callback.
     *
     * @param characteristic The characteristic to read from.
     */
    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.readCharacteristic(characteristic);
    }

    /**
     * Retrieves a list of supported GATT services on the connected device. This should be
     * invoked only after {@code BluetoothGatt#discoverServices()} completes successfully.
     *
     * @return A {@code List} of supported services.
     */
    public List<BluetoothGattService> getSupportedGattServices() {
        if (mBluetoothGatt == null) return null;

        return mBluetoothGatt.getServices();


    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    public class LocalBinder extends Binder {
        BluetoothLeService getService() {
            return BluetoothLeService.this;
        }
    }
}
