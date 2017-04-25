package com.quascenta.petersroad.droidtag.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.quascenta.petersroad.droidtag.R;


/**
 * Created by AKSHAY on 4/7/2017.
 */

public class Device extends RelativeLayout {

    TextView mDeviceName;
    FloatingActionButton floatingActionButton;
    boolean selected;


    public Device(Context context) {
        super(context);
        init();
    }

    public Device(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Device(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Device(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    public void init() {
        inflate(getContext(), R.layout.device, this);

        mDeviceName = (TextView) findViewById(R.id.text_device_name);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating2);
        floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color
                .parseColor("#839DDA")));
        selected = false;

    }

    public void setDeviceName(String deviceName) {
        mDeviceName.setText(String.valueOf(deviceName));
    }


    public boolean isSelected() {
        return selected;

    }

    public void getSelected(boolean selected) {
        this.selected = selected;
        if (selected) {
            floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color
                    .parseColor("#D4AF37")));
        }
    }


}
