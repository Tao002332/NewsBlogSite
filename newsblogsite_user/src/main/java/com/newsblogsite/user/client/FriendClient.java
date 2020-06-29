package com.newsblogsite.user.client;

import com.newsblogsite.user.interceptor.FeignConfig;
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
@FeignClient(value = "newsblogsite-friend",configuration = FeignConfig.class)
public interface FriendClient {


    /**
     * 用户是否关注
     * @param friendId
     * @return
     */
    @RequestMapping(value = "/friend/like/{friendId}",method = RequestMethod.GET)
    Boolean isFollow(@PathVariable("friendId") String friendId);

    /**
     * 关注
     * @param friendId
     * @return
     */
    @RequestMapping(value = "/friend/like/{friendId}",method = RequestMethod.PUT)
    Result like(@PathVariable("friendId") String friendId);


    /**
     * 取消关注
     * @param friendId
     * @return
     */
    @RequestMapping(value = "/friend/like/{friendId}",method = RequestMethod.DELETE)
    Result cancleLike(@PathVariable("friendId") String friendId);


    /**
     * 拉黑
     * @param friendId
     * @return
     */
    @RequestMapping(value = "/friend/unLike/{friendId}",method = RequestMethod.PUT)
    Result unLike(@PathVariable("friendId") String friendId);


    /**
     * 取消拉黑
     * @param friendId
     * @return
     */
    @RequestMapping(value = "/friend/unLike/{friendId}",method = RequestMethod.DELETE)
    Result cancleUnLike(@PathVariable("friendId") String friendId);
}
