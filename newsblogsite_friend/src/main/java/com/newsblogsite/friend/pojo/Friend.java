package com.newsblogsite.friend.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * *@author 83614
 * *@date 2020/4/11
 **/

@Entity
@Table(name = "tb_friend")
@IdClass(Friend.class)
public class Friend implements Serializable {

    /**
     * 用户id
     */
    @Id
    private String userId;


    /**
     * 其他用户id
     */
    @Id
    private String friendId;

    /**
     * 是否喜欢
     */
    private String isLike;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }
}
