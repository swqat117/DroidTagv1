package com.quascenta.petersroad.droidtag.main;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;

/**
 * @author fqZhang
 * @version 1.0
 * @Description QPP interface
 * @date 2014-7-10
 * @Copyright (c) 2014 Quintic Co., Ltd. Inc. All rights reserved.
 */

public interface iQppCallback {
    void onQppReceiveData(BluetoothGatt mBluetoothGatt, BluetoothGattCharacteristic qppUUIDForNotifyChar, byte[] qppData);
}
