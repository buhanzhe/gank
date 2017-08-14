package com.buhanzhe.gank.bean;

import java.util.List;

/**
 * Created by buhanzhe on 17/8/6.
 */

public class User {

    /**
     * createdAt : 2017-08-04T08:58:21.164Z
     * updatedAt : 2017-08-04T08:58:21.164Z
     * id : 5984372d157ebfe247e164b7
     * realm : null
     * username : 13683869623
     * mobile : 13683869623
     * email : null
     * emailVerified : null
     * verificationToken : null
     * credentials : []
     * challenges : []
     * status : null
     */

    private String createdAt;
    private String updatedAt;
    private String id;
    private Object realm;
    private String username;
    private String mobile;
    private Object email;
    private Object emailVerified;
    private String iconUrl;
    private Object verificationToken;
    private Object status;
    private List<?> credentials;
    private List<?> challenges;


    @Override
    public String toString() {
        return "username:" + username + "\nmobile:" + mobile + "\nid:" + id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getRealm() {
        return realm;
    }

    public void setRealm(Object realm) {
        this.realm = realm;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Object emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Object getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(Object verificationToken) {
        this.verificationToken = verificationToken;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public List<?> getCredentials() {
        return credentials;
    }

    public void setCredentials(List<?> credentials) {
        this.credentials = credentials;
    }

    public List<?> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<?> challenges) {
        this.challenges = challenges;
    }
}
