package com.buhanzhe.gank.bean;

/**
 * Created by buhanzhe on 17/7/11.
 */

public class LoginSuccess {

    /**
     * createdAt : 2017-07-11T06:27:08.945Z
     * updatedAt : 2017-07-11T06:27:08.945Z
     * id : wVkpJqFG2dP41YQ8DcY3kw5z6VrBUzyHRNDywjmu3WmWm27GefQjDNSnjJhhiCuT
     * ttl : 1209600
     * userId : 595df743bdf111b04d15c92d
     */

    private String createdAt;
    private String updatedAt;
    private String id;
    private int ttl;
    private String userId;

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

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
