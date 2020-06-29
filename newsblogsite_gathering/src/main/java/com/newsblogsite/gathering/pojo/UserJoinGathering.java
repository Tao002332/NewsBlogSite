package com.newsblogsite.gathering.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * *@author 83614
 * *@date 2020/4/11
 **/

@Entity
@Table(name = "tb_user_gath")
@IdClass(UserJoinGathering.class)
public class UserJoinGathering implements Serializable {

    @Id
    private String userId;

    @Id
    private String gathId;

    private Date exeTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGathId() {
        return gathId;
    }

    public void setGathId(String gathId) {
        this.gathId = gathId;
    }

    public Date getExeTime() {
        return exeTime;
    }

    public void setExeTime(Date exeTime) {
        this.exeTime = exeTime;
    }
}
