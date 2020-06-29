package com.newsblogsite.news.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * *@author 83614
 * *@date 2020/4/16
 **/

@Entity
@Table(name = "tb_user_collect")
@IdClass(UserCollected.class)
public class UserCollected implements Serializable {

    /**
     * 用户id
     */
    @Id
    private String userId;

    /**
     * 所属id
     */
    @Id
    private String originId;

    /**
     * 所属类型  1：news 2.share
     */
    private String originType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getOriginType() {
        return originType;
    }

    public void setOriginType(String originType) {
        this.originType = originType;
    }
}
