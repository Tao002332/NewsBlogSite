package com.newsblogsite.friend.service;

import com.newsblogsite.friend.dao.FriendDao;
import com.newsblogsite.friend.dao.UnFriendDao;
import com.newsblogsite.friend.pojo.Friend;
import com.newsblogsite.friend.pojo.UnFriend;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * *@author 83614
 * *@date 2020/4/11
 **/
@Transactional
@Service
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private UnFriendDao unFriendDao;

    /**
     * 用户是否关注
     * @param id
     * @param friendId
     * @return
     */
    public boolean isFollow(String id,String friendId) {
        Friend friend = friendDao.findByUserIdAndFriendId(id, friendId);
        return  friend!=null? true: false;
    }


    /**
     * 关注
     * @param id
     * @param friendId
     * @return
     */
    public int like(String id, String friendId) {
        Friend friend = friendDao.findByUserIdAndFriendId(id, friendId);
        if( friend != null) {
            return 0;
        }
        friend = new Friend();
        friend.setUserId(id);
        friend.setFriendId(friendId);
        friend.setIsLike("0");
        friendDao.save(friend);

        if(friendDao.findByUserIdAndFriendId(friendId,id) != null) {
            friendDao.updateIsLike("1",id,friendId);
            friendDao.updateIsLike("1",friendId,id);
        }
        return  1;
    }

    /**
     * 取消关注
     * @param id
     * @param friendId
     */
    public void cancleLike(String id, String friendId) {
        friendDao.deleteLike(id,friendId);
        if(friendDao.findByUserIdAndFriendId(friendId,id) != null) {
            friendDao.updateIsLike("0",friendId,id);
        }
    }


    /**
     * 拉黑
     * @param id
     * @param friendId
     * @return
     */
    public int unLike(String id, String friendId) {
        UnFriend unFriend = unFriendDao.findByUserIdAndFriendId(id, friendId);
        if( unFriend != null) {
            return 0;
        }
        unFriend= new UnFriend();
        unFriend.setUserId(id);
        unFriend.setFriendId(friendId);
        unFriendDao.save(unFriend);
        cancleLike(id,friendId);
        return 1;
    }

    /**
     * 取消拉黑
     * @param id
     * @param friendId
     */
    public void deleteUnLike(String id, String friendId) {
        unFriendDao.deleteUnLike(id,friendId);
    }
}
