package com.newsblogsite.search.service;

import com.newsblogsite.search.dao.NewsDao;
import com.newsblogsite.search.pojo.News;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.Field;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

/**
 * *@author 83614
 * *@date 2020/4/16
 **/
@Service
public class NewsService {


    @Autowired
    private NewsDao newsDao;


    /**
     * 条件查询分页
     * @param news
     * @param page
     * @param size
     * @return
     */
    public Page<News> findByTitle(News news, int page, int size) {
        Pageable pageable= PageRequest.of(page-1,size);
        return  newsDao.findByTitle(news.getTitle(),pageable);
    }


    /**
     * 条件查询分页
     * @param news
     * @param page
     * @param size
     * @return
     */
    public Page<News> findSearch(News news, int page, int size) {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        if(news.getChannelId()!=null && !"".equals(news.getChannelId())) {
            builder.must(QueryBuilders.fuzzyQuery("channelId",news.getChannelId()));
        }
        if (news.getTitle()!=null && !"".equals(news.getTitle())) {
            builder.must(QueryBuilders.fuzzyQuery("title",news.getTitle()));
        }
        FieldSortBuilder sort = SortBuilders.fieldSort("createTime").order(SortOrder.DESC);
        Pageable pageable= PageRequest.of(page-1,size);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withPageable(pageable).withQuery(builder).withSort(sort).build();
        return  newsDao.search(searchQuery);
    }


}
