package com.newsblogsite.user.dao;

import com.newsblogsite.user.pojo.OtherUser;
import javafx.scene.chart.ValueAxis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.newsblogsite.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{

    /**
     * 修改密码
     * @param password
     * @param mobile
     */
    @Modifying
    @Query(value = "update tb_user set `password` = ? where mobile = ? ",nativeQuery = true)
    void modifyPassword(String password, String mobile);


    User findByMobile(String mobile);


    /**
     * 用户认证
     * @param isAuth
     * @param userId
     */
    @Modifying
    @Query(value = "update tb_user set is_auth = ? where  id= ? ",nativeQuery = true)
    void examin(String isAuth, String userId);

    /**
     * 修改用户状态
     * @param stateId
     * @param userId
     */
    @Modifying
    @Query(value = "update tb_user set state = ? where  id= ? ",nativeQuery = true)
    void stateChange(String stateId, String userId);


    /**
     * 查询其他用户信息
     * @param userId
     * @return
     */
    @Query(value = "select id,nickname,sex,avatar,interest,personality,fans_count,follow_count from tb_user where id= ?",nativeQuery = true)
    List<Object[]> visitOther(String userId);


    /**
     * 增加粉丝数
     * @param x
     * @param userId
     */
    @Modifying
    @Query(value = "UPDATE tb_user  set fans_count = fans_count+ ? where id= ?",nativeQuery = true)
    void updateFansCount(int x,String userId);


    /**
     * 增加关注数
     * @param x
     * @param userId
     */
    @Modifying
    @Query(value = "UPDATE tb_user  set follow_count = follow_count+ ? where id= ?",nativeQuery = true)
    void updateFollowCount(int x,String userId);


    /**
     * 查询我的关注
     * @param id
     * @return
     */
    @Query(value = "select c.id,c.nickname,c.sex,c.avatar,c.interest,c.personality,c.fans_count,c.follow_count from tb_user c \n" +
            "where id in (select b.friend_id from tb_user a LEFT JOIN newsblogsite_friend.tb_friend b on a.id=b.user_id\n" +
            "where a.id=?)",nativeQuery = true)
    List<Object[]> myFollow(String id);


    /**
     * 查询我的粉丝
     * @param id
     * @return
     */
    @Query(value = "select c.id,c.nickname,c.sex,c.avatar,c.interest,c.personality,c.fans_count,c.follow_count from tb_user c \n" +
            "where id in (select b.user_id from tb_user a LEFT JOIN newsblogsite_friend.tb_friend b on a.id=b.friend_id\n" +
            "where a.id=?)",nativeQuery = true)
    List<Object[]> myFans(String id);



    /**
     * 查询我的黑名单
     * @param id
     * @return
     */
    @Query(value = "select c.id,c.nickname,c.sex,c.avatar,c.interest,c.personality,c.fans_count,c.follow_count from tb_user c \n" +
            "where id in (select b.friend_id from tb_user a LEFT JOIN newsblogsite_friend.tb_unfriend b on a.id=b.user_id\n" +
            "where a.id=?)",nativeQuery = true)
    List<Object[]> myBlacklist(String id);
}
