package com.newsblogsite.user.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newsblogsite.user.pojo.Token;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.newsblogsite.user.pojo.Admin;
import com.newsblogsite.user.service.AdminService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import util.JwtUtil;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;


	@Autowired
	private JwtUtil jwtUtil;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",adminService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",adminService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchAdmin 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Admin searchAdmin , @PathVariable int page, @PathVariable int size){
		Page<Admin> pageList = adminService.findSearch(searchAdmin, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Admin>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchAdmin
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Admin searchAdmin){
        return new Result(true,StatusCode.OK,"查询成功",adminService.findSearch(searchAdmin));
    }
	
	/**
	 * 增加
	 * @param admin
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Admin admin  ){
		adminService.add(admin);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param admin
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Admin admin, @PathVariable String id ){
		admin.setId(id);
		adminService.update(admin);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		adminService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}


	/**
	 * 登录
	 * @param admin
	 * @return token
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public Result Login(@RequestBody Admin admin) {
		Admin checkAdmin = adminService.login(admin.getLoginName(), admin.getPassword());
		if(checkAdmin== null) {
			return new Result(false,StatusCode.ERROR,"登录失败");
		}
		if("0".equals(checkAdmin.getState())) {
			return new Result(false,StatusCode.ERROR,"管理员已被删除");
		}
		String token = jwtUtil.createJWT(checkAdmin.getId(),checkAdmin.getLoginName(),"admin");
		Map<String,Object> map = new HashMap<>();
		map.put("token",token);
		map.put("roles","admin");
		return  new Result(true,StatusCode.OK,"登录成功",map);
	}


	/**
	 * 获取管理员详细信息
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/info",method = RequestMethod.POST)
	public Result getInfo(@RequestBody Token token) {
		Admin checkAdmin = adminService.getInfo(token.getToken());
		if(checkAdmin== null) {
			return new Result(false,StatusCode.ERROR,"获取详细信息失败");
		}
		return  new Result(true,StatusCode.OK,"获取详细信息成功",checkAdmin);
	}


	/**
	 * 修改权限
	 * @param adminId
	 * @param level
	 * @return
	 */
	@RequestMapping(value = "/grant/{adminId}/{level}",method = RequestMethod.PUT)
	public Result grant(@PathVariable String adminId, @PathVariable String level) {
		adminService.grant(adminId,level);
		return  new Result(true,StatusCode.OK,"修改权限成功");
	}

}
