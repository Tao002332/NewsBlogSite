package com.newsblogsite.search.dao;

import com.newsblogsite.search.pojo.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * *@author 83614
 * *@date 2020/4/16
 **/

public interface NewsDao extends ElasticsearchRepository<News,String> {


    Page<News> findByTitle(String key, Pageable pageable);


}
