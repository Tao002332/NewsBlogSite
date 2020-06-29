package com.newsblogsite.news.dao;

import com.newsblogsite.news.pojo.UserCollected;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * *@author 83614
 * *@date 2020/4/16
 **/
public interface UserCollectedDao extends JpaRepository<UserCollected, String>, JpaSpecificationExecutor<UserCollected> {


    UserCollected findByUserIdAndOriginId(String userId, String originId);


    void  deleteByUserIdAndOriginId(String userId, String originId);
}
