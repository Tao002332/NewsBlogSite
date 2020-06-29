package com.newsblogsite.comment.client;

import com.newsblogsite.comment.interceptor.FeignConfig;
import entity.Result;
import entity.StatusCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * *@author 83614
 * *@date 2020/4/14
 **/
@FeignClient(value = "newsblogsite-news",configuration = FeignConfig.class)
public interface NewsClient {

    /**
     * 增加评论数
     * @param newsId
     * @return
     */
    @RequestMapping(value = "/news/comment/{newsId}",method = RequestMethod.PUT)
    Result addNewsCommentCount(@PathVariable("newsId") String newsId);


    /**
     * 增加评论数
     * @param shareId
     * @return
     */
    @RequestMapping(value = "/share/comment/{shareId}",method = RequestMethod.PUT)
    Result addShareIdCommentCount(@PathVariable("shareId") String shareId);

}
