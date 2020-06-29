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
@Table(name = "tb_unfriend")
@IdClass(UnFriend.class)
public class UnFriend implements Serializable {

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

}
