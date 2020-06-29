package com.newsblogsite.news.controller;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.newsblogsite.news.pojo.Channel;
import com.newsblogsite.news.service.ChannelService;

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
@RequestMapping("/channel")
public class ChannelController {

	@Autowired
	private ChannelService channelService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",channelService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",channelService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchChannel 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Channel searchChannel , @PathVariable int page, @PathVariable int size){
		Page<Channel> pageList = channelService.findSearch(searchChannel, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Channel>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchChannel
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Channel searchChannel){
        return new Result(true,StatusCode.OK,"查询成功",channelService.findSearch(searchChannel));
    }
	
	/**
	 * 增加
	 * @param channel
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Channel channel  ){
		channelService.add(channel);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param channel
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Channel channel, @PathVariable String id ){
		channel.setId(id);
		channelService.update(channel);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		channelService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}
