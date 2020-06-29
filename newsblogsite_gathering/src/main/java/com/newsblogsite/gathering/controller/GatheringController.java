package com.newsblogsite.gathering.controller;
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

import com.newsblogsite.gathering.pojo.Gathering;
import com.newsblogsite.gathering.service.GatheringService;

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
@RequestMapping("/gathering")
public class GatheringController {

	@Autowired
	private GatheringService gatheringService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",gatheringService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",gatheringService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchGathering 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Gathering searchGathering , @PathVariable int page, @PathVariable int size){
		Page<Gathering> pageList = gatheringService.findSearch(searchGathering, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Gathering>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchGathering
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Gathering searchGathering){
        return new Result(true,StatusCode.OK,"查询成功",gatheringService.findSearch(searchGathering));
    }
	
	/**
	 * 增加
	 * @param gathering
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Gathering gathering  ){
		gatheringService.add(gathering);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param gathering
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Gathering gathering, @PathVariable String id ){
		gathering.setId(id);
		gatheringService.update(gathering);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		gatheringService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}


	/**
	 * 修改状态
	 * @param gatheringId
	 * @param value
	 */
	@RequestMapping(value = "/state/{gatheringId}/{value}",method = RequestMethod.PUT)
	public Result stateChange(@PathVariable String gatheringId, @PathVariable String value) {
		gatheringService.stateChange(gatheringId,value);
		return new Result(true,StatusCode.OK,"修改活动状态");
	}

	/**
	 * 用户参与活动
	 * @param gatheringId
	 * @return
	 */
	@RequestMapping(value = "/join/{gatheringId}",method = RequestMethod.POST)
	public Result join(@PathVariable String gatheringId) {
		gatheringService.join(gatheringId);
		return new Result(true,StatusCode.OK,"活动参与成功");
	}


}
