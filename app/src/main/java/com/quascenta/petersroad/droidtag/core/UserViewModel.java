package com.quascenta.petersroad.droidtag.core;

/**
 * Created by AKSHAY on 3/24/2017.
 */
public class UserViewModel {

    private final String userid;


    private String username;

    private String hashValue;

    private String work_company;

    private String address;

    private String state_province;

    private String country_code;

    private String mobile_no;


    public UserViewModel(String username, String userid, String password, String mobile_number) {

        this.username = username;
        this.userid = userid;
        this.hashValue = password;
        this.mobile_no = mobile_number;

    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getHashValue() {
        return hashValue;
    }

    public void setHashValue(String hashValue) {
        this.hashValue = hashValue;
    }

    public String getMobile_no() {
        return mobile_no;

    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getState_province() {
        return state_province;
    }

    public void setState_province(String state_province) {
        this.state_province = state_province;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWork_company() {
        return work_company;
    }

    public void setWork_company(String work_company) {
        this.work_company = work_company;
    }
}
