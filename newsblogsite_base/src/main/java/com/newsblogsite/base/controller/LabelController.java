package com.newsblogsite.base.controller;
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

import com.newsblogsite.base.pojo.Label;
import com.newsblogsite.base.service.LabelService;

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
@RequestMapping("/label")
public class LabelController {

	@Autowired
	private LabelService labelService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",labelService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",labelService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchLabel 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Label searchLabel , @PathVariable int page, @PathVariable int size){
		Page<Label> pageList = labelService.findSearch(searchLabel, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Label>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchLabel
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Label searchLabel){
        return new Result(true,StatusCode.OK,"查询成功",labelService.findSearch(searchLabel));
    }
	
	/**
	 * 增加
	 * @param label
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Label label  ){
		labelService.add(label);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param label
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Label label, @PathVariable String id ){
		label.setId(id);
		labelService.update(label);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		labelService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	/**
	 * 增加使用数量
	 * @param labelId
	 * @return
	 */
	@RequestMapping(value = "/count/{labelId}",method = RequestMethod.PUT)
	public Result addCount(@PathVariable String labelId) {
		labelService.addCount(labelId);
		return new Result(true,StatusCode.OK,"增加成功");
	}

	/**
	 * 是否推荐
	 * @param labelId
	 * @return
	 */
	@RequestMapping(value = "/recommend/{labelId}/{value}",method = RequestMethod.PUT)
	public Result recommend(@PathVariable String labelId,@PathVariable String value) {
		labelService.recommend(labelId,value);
		return new Result(true,StatusCode.OK,"增加成功");
	}

	/**
	 * 增加粉丝数
	 * @param labelId
	 * @return
	 */
	@RequestMapping(value = "/fans/{labelId}",method = RequestMethod.PUT)
	public Result addFans(@PathVariable String labelId) {
		labelService.addFans(labelId);
		return new Result(true,StatusCode.OK,"增加成功");
	}

	/**
	 * 审核
	 * @param labelId
	 * @return
	 */
	@RequestMapping(value = "/examine/{labelId}",method = RequestMethod.PUT)
	public Result examine(@PathVariable String labelId) {
		labelService.examine(labelId);
		return new Result(true,StatusCode.OK,"审核成功");
	}

}
