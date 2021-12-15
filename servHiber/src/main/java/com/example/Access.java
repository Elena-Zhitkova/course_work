package com.example;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class Access {
    private int accessId;
    private String type;

    public Access(){

    }

    public Access(int accessId, String type){
        this.accessId = accessId;
        this.type = type;
    }

    public int getAccessId() {
        return accessId;
    }

    public void setAccessId(int accessId) {
        this.accessId = accessId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Access {" + "accessId=" + accessId + ", type='" + type + ' ' + '}';
    }

}
