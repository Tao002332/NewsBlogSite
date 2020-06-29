package com.newsblogsite.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * *@author 83614
 * *@date 2020/4/14
 **/
@FeignClient("newsblogsite-user")
public interface UserClient {

    @RequestMapping(value = "/user/{userId}/{friendId}/{x}",method = RequestMethod.PUT)
    void updateFansCountAndFollowCount(@PathVariable("userId") String userId, @PathVariable("friendId") String friendId, @PathVariable("x") int x);
}
