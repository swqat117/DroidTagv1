package com.quascenta.petersroad.droidtag.EventListeners;

import android.util.EventLog;

import com.quascenta.petersroad.droidtag.Bluetooth.BleVO.BleDevice;
import com.quascenta.petersroad.droidtag.EventBus.Events;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by AKSHAY on 3/14/2017.
 */

public interface EventListener  {

    @Subscribe
    public void check(BleDevice s);

}
