package com.newsblogsite.news.controller;
import java.util.List;
import java.util.Map;

import com.newsblogsite.news.pojo.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.newsblogsite.news.pojo.Share;
import com.newsblogsite.news.service.ShareService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/share")
public class ShareController {

	@Autowired
	private ShareService shareService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",shareService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",shareService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchShare 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Share searchShare , @PathVariable int page, @PathVariable int size){
		Page<Share> pageList = shareService.findSearch(searchShare, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Share>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchShare
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Share searchShare){
        return new Result(true,StatusCode.OK,"查询成功",shareService.findSearch(searchShare));
    }
	
	/**
	 * 增加
	 * @param share
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Share share  ){
		shareService.add(share);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param share
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Share share, @PathVariable String id ){
		share.setId(id);
		shareService.update(share);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		shareService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}


	/**
	 * 审核分享
	 * @param shareId
	 * @return
	 */
	@RequestMapping(value = "/examine/{shareId}",method = RequestMethod.PUT)
	public Result examine(@PathVariable String shareId) {
		shareService.examine(shareId);
		return new Result(true,StatusCode.OK,"审核成功");
	}


	/**
	 * 是否公开
	 * @param shareId
	 * @param value
	 * @return
	 */
	@RequestMapping(value = "/public/{shareId}/{value}",method = RequestMethod.PUT)
	public  Result setPublic(@PathVariable String shareId,@PathVariable String value) {
		shareService.setPublic(shareId,value);
		return new Result(true,StatusCode.OK,"操作成功");
	}

	/**
	 * 是否置顶
	 * @param shareId
	 * @param value
	 * @return
	 */
	@RequestMapping(value = "/top/{shareId}/{value}",method = RequestMethod.PUT)
	public  Result setTop(@PathVariable String shareId,@PathVariable String value) {
		shareService.setTop(shareId,value);
		return new Result(true,StatusCode.OK,"操作成功");
	}

	/**
	 * 是否死亡
	 * @param shareId
	 * @param value
	 * @return
	 */
	@RequestMapping(value = "/death/{shareId}/{value}",method = RequestMethod.PUT)
	public  Result setDeath(@PathVariable String shareId,@PathVariable String value) {
		shareService.setDeath(shareId,value);
		return new Result(true,StatusCode.OK,"操作成功");
	}

	/**
	 * 增加评论数
	 * @param shareId
	 * @return
	 */
	@RequestMapping(value = "/comment/{shareId}",method = RequestMethod.PUT)
	public Result addCommentCount(@PathVariable String shareId) {
		shareService.addCommentCount(shareId);
		return new Result(true,StatusCode.OK,"操作成功");
	}


	/**
	 * 增加浏览数
	 * @param shareId
	 * @return
	 */
	@RequestMapping(value = "/visits/{shareId}",method = RequestMethod.PUT)
	public Result addVisitsCount(@PathVariable String shareId) {
		shareService.addVisitsCount(shareId);
		return new Result(true,StatusCode.OK,"操作成功");
	}


	/**
	 * 增加点赞数
	 * @param shareId
	 * @return
	 */
	@RequestMapping(value = "/thumbup/{shareId}",method = RequestMethod.PUT)
	public Result addThumupCount(@PathVariable String shareId) {
		shareService.addThumupCount(shareId);
		return new Result(true,StatusCode.OK,"操作成功");
	}


	/**
	 * my分页+多条件查询
	 * @param searchShare 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/mySearch/{page}/{size}",method=RequestMethod.POST)
	public Result findMySearch(@RequestBody Share searchShare , @PathVariable int page, @PathVariable int size){
		Page<Share> pageList = shareService.findMySearch(searchShare, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Share>(pageList.getTotalElements(), pageList.getContent()) );
	}




}
