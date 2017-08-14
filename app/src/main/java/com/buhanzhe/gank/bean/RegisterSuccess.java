package com.buhanzhe.gank.bean;

import java.util.List;

/**
 * Created by buhanzhe on 17/8/4.
 */

public class RegisterSuccess {

    /**
     * createdAt : 2017-08-04T02:40:56.722Z
     * updatedAt : 2017-08-04T02:40:56.722Z
     * id : 5983deb830ffe85d57110ae4
     * username : bb
     * mobile : 123
     * credentials : []
     * challenges : []
     */

    private String createdAt;
    private String updatedAt;
    private String id;
    private String username;
    private String mobile;
    private List<?> credentials;
    private List<?> challenges;

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
