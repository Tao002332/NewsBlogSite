package com.newsblogsite.friend.controller;

import com.newsblogsite.friend.client.UserClient;
import com.newsblogsite.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * *@author 83614
 * *@date 2020/4/11
 **/

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserClient userClient;




    /**
     * 用户是否关注
     * @param friendId
     * @return
     */
    @RequestMapping(value = "/like/{friendId}",method = RequestMethod.GET)
    public Boolean isFollow(@PathVariable String friendId) {
        String id = (String)request.getAttribute("loginId");
        return friendService.isFollow(id, friendId);
    }




    /**
     * 关注
     * @param friendId
     * @return
     */
    @RequestMapping(value = "/like/{friendId}",method = RequestMethod.PUT)
    public Result like(@PathVariable String friendId) {
        String id = (String)request.getAttribute("loginId");
        int result = friendService.like(id,friendId);
        if(result == 0) {
            return new Result(false, StatusCode.REPERROR,"不能重复添加");
        }
        //更新关注数和粉丝数
        userClient.updateFansCountAndFollowCount(id,friendId,1  );
        return new Result(true, StatusCode.OK,"关注成功");
    }


    /**
     * 取消关注
     * @param friendId
     * @return
     */
    @RequestMapping(value = "/like/{friendId}",method = RequestMethod.DELETE)
    public Result cancleLike(@PathVariable String friendId) {
        String id = (String)request.getAttribute("loginId");
        friendService.cancleLike(id,friendId);
        //更新关注数和粉丝数
        userClient.updateFansCountAndFollowCount(id,friendId,-1  );
        return new Result(true, StatusCode.OK,"取消关注成功");
    }


    /**
     * 拉黑
     * @param friendId
     * @return
     */
    @RequestMapping(value = "/unLike/{friendId}",method = RequestMethod.PUT)
    public Result unLike(@PathVariable String friendId) {
        String id = (String)request.getAttribute("loginId");
        int result = friendService.unLike(id,friendId);
        if(result == 0) {
            return new Result(false, StatusCode.REPERROR,"不能重复拉黑");
        }
        //更新关注数和粉丝数
        return new Result(true, StatusCode.OK,"拉黑成功");
    }


    /**
     * 取消拉黑
     * @param friendId
     * @return
     */
    @RequestMapping(value = "/unLike/{friendId}",method = RequestMethod.DELETE)
    public Result cancleUnLike(@PathVariable String friendId) {
        String id = (String)request.getAttribute("loginId");
        friendService.deleteUnLike(id,friendId);
        return new Result(true, StatusCode.OK,"取消拉黑成功");
    }

}
