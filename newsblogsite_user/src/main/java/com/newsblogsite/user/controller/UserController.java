package com.newsblogsite.user.controller;


import com.newsblogsite.user.client.FriendClient;
import com.newsblogsite.user.pojo.OtherUser;
import com.newsblogsite.user.pojo.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.newsblogsite.user.pojo.User;
import com.newsblogsite.user.service.UserService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import util.JwtUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private FriendClient friendClient;


	/**
	 * 更新用户粉丝数和 关注数
	 * @param userId
	 * @param friendId
	 * @param x
	 */
	@RequestMapping(value = "/{userId}/{friendId}/{x}",method =RequestMethod.PUT)
	public void updateFansCountAndFollowCount(@PathVariable String userId,@PathVariable String friendId,@PathVariable int x) {
		userService.updateFansCountAndFollowCount(userId,friendId,x);
	}


	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchUser 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody User searchUser , @PathVariable int page, @PathVariable int size){
		Page<User> pageList = userService.findSearch(searchUser, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchUser
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody User searchUser){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchUser));
    }
	
	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		userService.add(user);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		userService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}


	/**
	 * 发送验证码
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/sendSms",method = RequestMethod.POST)
	public Result sendSms(@RequestBody User user) {
		userService.sendSms(user.getMobile());
		return new Result(true,StatusCode.OK,"手机验证码发送成功");
	}


	/**
	 * 注册
	 * @param code
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/register/{code}",method =RequestMethod.POST)
	public Result register(@PathVariable String code,@RequestBody User user) {
		String checkCode = (String)redisTemplate.opsForValue().get("checkCode_"+user.getMobile());
		if (checkCode.isEmpty()) {
			return new Result(false,StatusCode.ERROR,"重新获取验证码");
		}
		if(!checkCode.equals(code)) {
			return new Result(false,StatusCode.ERROR,"验证码错误");
		}
		userService.add(user);
		return new Result(true,StatusCode.OK,"注册成功");
	}


	/**
	 * 修改密码
	 * @param code
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/modifyPassword/{code}",method = RequestMethod.POST)
	public Result modifyPassword(@PathVariable String code,@RequestBody User user) {
		String checkCode = (String)redisTemplate.opsForValue().get("checkCode_"+user.getMobile());
		if (checkCode.isEmpty()) {
			return new Result(false,StatusCode.ERROR,"重新获取验证码");
		}
		if(!checkCode.equals(code)) {
			return new Result(false,StatusCode.ERROR,"验证码错误");
		}
		userService.modifyPassword(user);
		return new Result(true,StatusCode.OK,"修改密码成功");
	}


	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/saveInfo",method = RequestMethod.PUT)
	public Result saveInfo(@RequestBody User user){
		userService.update(user);
		return new Result(true,StatusCode.OK,"修改个人信息成功");
	}


	/**
	 * 登录
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public Result login(@RequestBody User user) {
		User checkUser = userService.login(user.getMobile(), user.getPassword());
		if(checkUser== null) {
			return new Result(false,StatusCode.ERROR,"登录失败");
		}
		if("0".equals(checkUser.getState())) {
			return new Result(false,StatusCode.ERROR,"用户已被封禁");
		}
		String token = jwtUtil.createJWT(checkUser.getId(),checkUser.getMobile(),"user");
		Map<String,Object> map = new HashMap<>();
		map.put("token",token);
		return  new Result(true,StatusCode.OK,"登录成功",map);
	}


	/**
	 * 获取用户详细信息
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/info",method = RequestMethod.POST)
	public Result getInfo(@RequestBody Token token) {
		User checkUser = userService.getInfo(token.getToken());
		if(checkUser== null) {
			return new Result(false,StatusCode.ERROR,"获取详细信息失败");
		}
		return  new Result(true,StatusCode.OK,"获取详细信息成功",checkUser);
	}


	/**
	 * 用户认证
	 * @param userId
	 */
	@RequestMapping(value = "/examine/{userId}",method = RequestMethod.PUT)
	public Result examine(@PathVariable String userId) {
		userService.examine(userId);
		return new Result(true,StatusCode.OK,"用户认证成功");
	}


	/**
	 * 修改用户状态
	 * @param userId
	 * @param stateId
	 * @return
	 */
	@RequestMapping(value = "/state/{userId}/{stateId}",method = RequestMethod.PUT)
	public Result stateChange(@PathVariable String userId,@PathVariable String stateId) {
		userService.stateChange(userId,stateId);
		return new Result(true,StatusCode.OK,"修改用户状态成功");
	}


	/**
	 * 查询其他用户的 信息
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/visitOther/{userId}",method = RequestMethod.GET)
	public Result visitOther(@PathVariable String userId) {
		return new Result(true,StatusCode.OK,"查询成功",userService.visitOther(userId));
	}


	/**
	 * 关注用户
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/follow/{userId}",method = RequestMethod.POST)
	public Result follow(@PathVariable String userId) {
		return friendClient.like(userId);
	}


	/**
	 * 取消关注用户
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/follow/{userId}",method = RequestMethod.DELETE)
	public Result cancleFollow(@PathVariable String userId) {
		return friendClient.cancleLike(userId);
	}

	/**
	 * 拉黑用户
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/blacklist/{userId}",method = RequestMethod.POST)
	public Result blacklist(@PathVariable String userId) {
		return friendClient.unLike(userId);
	}

	/**
	 * 取消拉黑用户
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/blacklist/{userId}",method = RequestMethod.DELETE)
	public Result cancleBlacklist(@PathVariable String userId) {
		return friendClient.cancleUnLike(userId);
	}


	/**
	 * 我的关注
	 * @return
	 */
	@RequestMapping(value = "/follow/myFollow",method = RequestMethod.GET)
	public Result myFollow() {
		return new Result(true,StatusCode.OK,"查询成功",userService.myFollow());
	}

	/**
	 * 我的粉丝
	 * @return
	 */
	@RequestMapping(value = "/fans/myFans",method = RequestMethod.GET)
	public Result myFans() {
		return new Result(true,StatusCode.OK,"查询成功",userService.myFans());
	}

	/**
	 * 我的黑名单
	 * @return
	 */
	@RequestMapping(value = "/blacklist/myBlacklist",method = RequestMethod.GET)
	public Result myBlacklist() {
		return new Result(true,StatusCode.OK,"查询成功",userService.myBlacklist());
	}


	/**
	 * 用户是否关注
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/isFollow/{userId}",method = RequestMethod.GET)
	public Result isFollow(@PathVariable String userId) {
		if (friendClient.isFollow(userId)) {
			return new Result(true,StatusCode.OK,"已关注");
		}
		return new Result(false,StatusCode.OK,"未关注");
	}

}
