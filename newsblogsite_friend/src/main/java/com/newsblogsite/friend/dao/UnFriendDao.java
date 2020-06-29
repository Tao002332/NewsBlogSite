package com.newsblogsite.friend.dao;

import com.newsblogsite.friend.pojo.UnFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


/**
 * *@author 83614
 * *@date 2020/4/11
 **/
public interface UnFriendDao extends JpaRepository<UnFriend, String>, JpaSpecificationExecutor<UnFriend> {

    UnFriend findByUserIdAndFriendId(String userId, String friendId);



    @Modifying
    @Query(value = "delete from tb_unfriend where user_id= ? and friend_id=?",nativeQuery = true)
    void deleteUnLike(String userId, String friendId);

}
