package com.newsblogsite.news.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.newsblogsite.news.pojo.News;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface NewsDao extends JpaRepository<News,String>,JpaSpecificationExecutor<News>{


    @Modifying
    @Query(value = "update tb_news set `comment` = `comment` + 1 where id= ?",nativeQuery = true)
    void addCommentCount(String newsId);


    @Modifying
    @Query(value = "update tb_news set `visits` = `visits` + 1 where id= ?",nativeQuery = true)
    void addVisitsCount(String newsId);



    @Query(value = "select a.* from tb_news a LEFT JOIN tb_user_collect b on a.id=b.origin_id  WHERE b.origin_type='1' and b.user_id=?",nativeQuery = true)
    List<News> findByUserCollected(String id);


    @Query(value = "select  * from tb_news where user_id=? limit 0,5",nativeQuery = true)
    List<News> getLastTop5(String userId);
}
