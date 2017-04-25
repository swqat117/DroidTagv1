package com.quascenta.petersroad.droidtag.main;

import java.util.HashMap;

/**
 * Created by AKSHAY on 4/12/2017.
 */

public class LoggerDevice {

    String name;
    String deviceAddress;
    String uuidQppservice;
    String uuidQppCharWrite;
    int lower_limit;
    int upper_limit;
    boolean configuration;
    boolean enabled;
    String startDate;
    String endDate;
    int time;
    String configuration_time;
    boolean transfer;
    private HashMap<String, String> source_company;
    private HashMap<String, String> destination_company;


    public LoggerDevice(String name, String company_start, String company_start_location, String company_end, String company_end_location) {
        this.name = name;
        source_company = new HashMap<>();
        destination_company = new HashMap<>();
        source_company.put(company_start, company_start_location);
        destination_company.put(company_end, company_end_location);

    }

    public HashMap<String, String> getDestination_company() {
        return destination_company;
    }

    public HashMap<String, String> getSource_company() {
        return source_company;
    }

    public boolean isConfiguration() {
        return configuration;
    }

    public void setConfiguration(boolean configuration) {
        this.configuration = configuration;
    }

    public String getConfiguration_time() {
        return configuration_time;
    }

    public void setConfiguration_time(String configuration_time) {
        this.configuration_time = configuration_time;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getLower_limit() {
        return lower_limit;
    }

    public void setLower_limit(int lower_limit) {
        this.lower_limit = lower_limit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public boolean isTransfer() {
        return transfer;
    }

    public void setTransfer(boolean transfer) {
        this.transfer = transfer;
    }

    public int getUpper_limit() {
        return upper_limit;
    }

    public void setUpper_limit(int upper_limit) {
        this.upper_limit = upper_limit;
    }
}
