package com.newsblogsite.news.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.newsblogsite.news.pojo.Share;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ShareDao extends JpaRepository<Share,String>,JpaSpecificationExecutor<Share>{


    @Modifying
    @Query(value = "update tb_share set `comment` = `comment` + 1 where id= ?",nativeQuery = true)
    void addCommentCount(String newsId);


    @Modifying
    @Query(value = "update tb_share set `visits` = `visits` + 1 where id= ?",nativeQuery = true)
    void addVisitsCount(String newsId);


    @Modifying
    @Query(value = "update tb_share set `thumbup` = `thumbup` + 1 where id= ?",nativeQuery = true)
    void addThumupCount(String shareId);
}
