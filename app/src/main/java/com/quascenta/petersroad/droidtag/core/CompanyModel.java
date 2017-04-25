package com.quascenta.petersroad.droidtag.core;

import org.joda.time.DateTime;

import java.util.HashMap;

/**
 * Created by AKSHAY on 3/27/2017.
 */

public class CompanyModel {

    private String registeration_date;
    private String name;
    private String address;
    private String state;
    private String city;
    private String country;
    private String numberid;
    private HashMap<String, String> user;

    /*
    *       Status code
    *
    *       0 - just created
    *       1 - inactive profile
    *       2 - active profile
    *       3 - unassigned
    *       4 - error
    *
    *       Request Code
    *
    *       0 - SuperAdmin Profile
    *       1 - Sub user Account profile
    *       2 - Full Access Previleged user
    *
    *
    *
    *
    * *
    *
    *
    *
     */

    public CompanyModel(String name, String address, String mobile1, HashMap<String, String> user) {

        this.name = name;
        this.address = address;
        this.numberid = mobile1;
        this.registeration_date = DateTime.now().toString();
        this.user = user;

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberid() {
        return numberid;
    }

    public void setNumberid(String numberid) {
        this.numberid = numberid;
    }

    public String getRegisteration_date() {
        return registeration_date;
    }

    public void setRegisteration_date(String registeration_date) {
        this.registeration_date = registeration_date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public HashMap<String, String> getUser() {
        return user;
    }

    public void setUser(HashMap<String, String> user) {
        this.user = user;
    }








}
