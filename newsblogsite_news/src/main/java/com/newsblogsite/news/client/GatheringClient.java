package com.newsblogsite.news.client;

import com.newsblogsite.news.interceptor.FeignConfig;
import entity.Result;
import entity.StatusCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * *@author 83614
 * *@date 2020/4/28
 **/
@FeignClient(value = "newsblogsite-gathering",configuration = FeignConfig.class)
public interface GatheringClient {

    /**
     * 用户参与活动
     * @param gatheringId
     * @return
     */
    @RequestMapping(value = "/gathering/join/{gatheringId}",method = RequestMethod.POST)
    Result join(@PathVariable String gatheringId);
}
