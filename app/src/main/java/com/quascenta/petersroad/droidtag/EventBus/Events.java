package com.quascenta.petersroad.droidtag.EventBus;

import android.graphics.Bitmap;

import com.quascenta.petersroad.droidtag.SensorCollection.model.DeviceViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AKSHAY on 2/10/2017.
 */

public class Events {

    public static class FragmentActivityMessage {
        private String message;
        private float low_limit;
        private float high_limit;

        public FragmentActivityMessage() {
        }

        public FragmentActivityMessage(float lowerlimit, float upperlimit) {
            this.low_limit = lowerlimit;
            this.high_limit = upperlimit;
        }

        public float getLow_limit() {
            return low_limit;
        }

        public void setLow_limit(float low_limit) {
            this.low_limit = low_limit;
        }

        public float getHigh_limit() {
            return high_limit;
        }

        public void setHigh_limit(float high_limit) {
            this.high_limit = high_limit;
        }

        public String getMessage() {
            return message;
        }
    }

    // Event used to send message from activity to fragment.
    public static class ActivityFragmentMessage {
        private String message;
        private float low_limit;
        private float high_limit;

        public ActivityFragmentMessage(float lowerlimit, float upperlimit) {

            this.low_limit = lowerlimit;
            this.high_limit = upperlimit;
        }

        public float getLow_limit() {
            return low_limit;
        }

        public float getHigh_limit() {
            return high_limit;
        }
    }

    // Event used to send message from activity to activity.
    public static class ActivityActivityMessage {
        private String message;

        public ActivityActivityMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }


    public static class DeviceList {

        private List<DeviceViewModel> arrayList = new ArrayList<>();
        private List<DeviceViewModel> arrayList_bitmap = new ArrayList<>();

        public DeviceList(List<DeviceViewModel> arrayList) {
            this.arrayList = arrayList;
        }

        public List<DeviceViewModel> getList() {
            return arrayList;
        }

        public void setList(List<DeviceViewModel> arrayList) {
            this.arrayList = arrayList;
        }
    }


    public static class ImageBitmap {

        private List<Bitmap> arrayList_bitmap = new ArrayList<>();

        public ImageBitmap(List<Bitmap> arrayList) {
            this.arrayList_bitmap = arrayList;
        }

        public List<Bitmap> getList() {
            return arrayList_bitmap;
        }

        public void setList(List<Bitmap> arrayList) {
            this.arrayList_bitmap = arrayList;
        }
    }

    public static class Send_BLEDeviceForRegistration<BleDevice> {


        private BleDevice content;

        public Send_BLEDeviceForRegistration(BleDevice content) {
            this.content = content;
        }

        public BleDevice getContent() {
            return content;
        }

        public void setContent(BleDevice content) {
            this.content = content;
        }


    }

    public static class Recieve_BLEDeviceForRegistration<BleDevice> {


        private BleDevice content;

        public Recieve_BLEDeviceForRegistration(BleDevice content) {
            this.content = content;
        }

        public BleDevice getContent() {
            return content;
        }

        public void setContent(BleDevice content) {
            this.content = content;
        }


    }


    public static class SendString {


        private String content;

        public SendString(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }


    }

}
