package com.newsblogsite.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.newsblogsite.base.pojo.Label;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface LabelDao extends JpaRepository<Label,String>,JpaSpecificationExecutor<Label>{


    @Modifying
    @Query(value = "update tb_label set state = '1' where id= ?",nativeQuery = true)
    void examine(String labelId);


    @Modifying
    @Query(value = "update tb_label set count = count + 1  where id= ?",nativeQuery = true)
    void addCount(String labelId);


    @Modifying
    @Query(value = "update tb_label set recommend = ?  where id= ?",nativeQuery = true)
    void recommend(String value, String labelId);


    @Modifying
    @Query(value = "update tb_label set fans = fans + 1  where id= ?",nativeQuery = true)
    void addFans(String labelId);
}
