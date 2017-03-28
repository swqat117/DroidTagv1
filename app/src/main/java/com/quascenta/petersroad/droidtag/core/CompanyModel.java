package com.quascenta.petersroad.droidtag.core;

import org.joda.time.DateTime;

/**
 * Created by AKSHAY on 3/27/2017.
 */

public class CompanyModel {

    int status_code;
    int request_code;
    DateTime registeration_date;
    private String company_name;
    private String company_address;
    private String company_domain;
    private String company_state;
    private String company_city;

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
    private String company_mobile_number;
    private String alterneate_mobile_number;
    private UserCollection userCollection;


    public CompanyModel(String name, String address, String domain_name, String state, String city, String mobile1, String mobile2, int status_code, int request_code) {

        this.company_name = name;
        this.company_address = address;
        this.company_domain = domain_name;
        this.company_state = state;
        this.company_city = city;
        this.company_mobile_number = mobile1;
        this.alterneate_mobile_number = mobile2;
        this.status_code = status_code;
        this.request_code = request_code;
        this.registeration_date = DateTime.now();
        userCollection = new UserCollection();


    }

    public String getAlterneate_mobile_number() {
        return alterneate_mobile_number;
    }

    public void setAlterneate_mobile_number(String alterneate_mobile_number) {
        this.alterneate_mobile_number = alterneate_mobile_number;
    }

    public String getCompany_address() {
        return company_address;
    }

    public void setCompany_address(String company_address) {
        this.company_address = company_address;
    }

    public String getCompany_city() {
        return company_city;
    }

    public void setCompany_city(String company_city) {
        this.company_city = company_city;
    }

    public String getCompany_domain() {
        return company_domain;
    }

    public void setCompany_domain(String company_domain) {
        this.company_domain = company_domain;
    }

    public String getCompany_mobile_number() {
        return company_mobile_number;
    }

    public void setCompany_mobile_number(String company_mobile_number) {
        this.company_mobile_number = company_mobile_number;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_state() {
        return company_state;
    }

    public void setCompany_state(String company_state) {
        this.company_state = company_state;
    }

    public DateTime getRegisteration_date() {
        return registeration_date;
    }

    public void setRegisteration_date(DateTime registeration_date) {
        this.registeration_date = registeration_date;
    }

    public int getRequest_code() {
        return request_code;
    }

    public void setRequest_code(int request_code) {
        this.request_code = request_code;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public UserCollection getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(UserCollection userCollection) {
        this.userCollection = userCollection;
    }

    public void addUser(UserViewModel sensorViewModel) {
        userCollection.add(sensorViewModel);
    }


}
