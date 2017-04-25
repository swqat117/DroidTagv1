package com.quascenta.petersroad.droidtag.core;

/**
 * Created by AKSHAY on 3/24/2017.
 */

public class UserViewModel {


    private String id;

    private String username;
    private String email;
    private String dateTime;
    private String company = "";
    private boolean hasLoggedInWithPassword;


    /**
     * Required public constructor
     */
    public UserViewModel() {
    }

    /**
     * Use this constructor to create new User.
     * Takes user name, email and timestampJoined as params
     *
     * @param name
     * @param email
     * @param dateTime
     */
    public UserViewModel(String name, String email, String dateTime, String id) {
        this.username = name;
        this.email = email;

        this.id = id;
        this.dateTime = dateTime;
        this.company = getCompanyName();
        this.hasLoggedInWithPassword = false;
    }

    public String getName() {
        return username;
    }

    public String getEmail() {
        return email;
    }


    public String getCompanyName() {
        return email.substring((email.indexOf("@") + 1), email.lastIndexOf("."));
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isHasLoggedInWithPassword() {
        return hasLoggedInWithPassword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    }

