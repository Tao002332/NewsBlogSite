package com.newsblogsite.friend.dao;

import com.newsblogsite.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


/**
 * *@author 83614
 * *@date 2020/4/11
 **/
public interface FriendDao extends JpaRepository<Friend, String>, JpaSpecificationExecutor<Friend> {

    Friend findByUserIdAndFriendId(String userId, String friendId);


    @Modifying
    @Query(value = "UPDATE tb_friend set is_like = ? where user_id= ? and friend_id=?",nativeQuery = true)
    void  updateIsLike(String like,String userId, String friendId);


    @Modifying
    @Query(value = "delete from tb_friend  where user_id= ? and friend_id=?",nativeQuery = true)
    void deleteLike(String userId, String friendId);

}
