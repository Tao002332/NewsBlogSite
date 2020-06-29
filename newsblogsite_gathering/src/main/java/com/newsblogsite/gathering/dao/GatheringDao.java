package com.newsblogsite.gathering.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.newsblogsite.gathering.pojo.Gathering;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface GatheringDao extends JpaRepository<Gathering,String>,JpaSpecificationExecutor<Gathering>{


    @Modifying
    @Query(value = "update tb_gathering set state = ? where id = ?",nativeQuery = true)
    void stateChange(String value, String gatheringId);


    @Modifying
    @Query(value = "update tb_gathering set join_count =join_count+ ? where id= ? ",nativeQuery = true)
    void addJoinCount(int num, String gatheringId);
}
