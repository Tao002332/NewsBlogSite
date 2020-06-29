package com.newsblogsite.news.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.newsblogsite.news.pojo.Column;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ColumnDao extends JpaRepository<Column,String>,JpaSpecificationExecutor<Column>{

    @Modifying
    @Query(value = "update tb_column set state=? where id= ?",nativeQuery = true)
    void examine(String s, String columnId);
}
