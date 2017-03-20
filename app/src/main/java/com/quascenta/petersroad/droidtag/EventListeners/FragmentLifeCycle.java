package com.quascenta.petersroad.droidtag.EventListeners;

import com.quascenta.petersroad.droidtag.Bluetooth.BleVO.BleDevice;

/**
 * Created by AKSHAY on 3/15/2017.
 */

public interface FragmentLifeCycle {


    public void onPauseFragment();
    public void onResumeFragment(BleDevice device);

    public void send(BleDevice device);



}
