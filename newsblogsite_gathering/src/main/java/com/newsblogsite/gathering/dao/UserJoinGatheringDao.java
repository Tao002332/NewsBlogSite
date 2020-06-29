package com.newsblogsite.gathering.dao;

import com.newsblogsite.gathering.pojo.UserJoinGathering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * *@author 83614
 * *@date 2020/4/11
 **/

public interface UserJoinGatheringDao extends JpaRepository<UserJoinGathering,String>, JpaSpecificationExecutor<UserJoinGathering> {

    UserJoinGathering findByGathIdAndUserId(String gath,String userId);
}
