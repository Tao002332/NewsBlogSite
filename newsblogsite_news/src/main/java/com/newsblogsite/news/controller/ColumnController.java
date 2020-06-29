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

import com.newsblogsite.news.pojo.Column;
import com.newsblogsite.news.service.ColumnService;

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
@RequestMapping("/column")
public class ColumnController {

	@Autowired
	private ColumnService columnService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",columnService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",columnService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchColumn 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Column searchColumn , @PathVariable int page, @PathVariable int size){
		Page<Column> pageList = columnService.findSearch(searchColumn, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Column>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchColumn
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Column searchColumn){
        return new Result(true,StatusCode.OK,"查询成功",columnService.findSearch(searchColumn));
    }
	
	/**
	 * 增加
	 * @param column
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Column column  ){
		columnService.add(column);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param column
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Column column, @PathVariable String id ){
		column.setId(id);
		columnService.update(column);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		columnService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}


	/**
	 * 专栏申请
	 * @param column
	 */
	@RequestMapping(value = "/apply",method=RequestMethod.POST)
	public Result apply(@RequestBody Column column){
		columnService.add(column);
		return new Result(true,StatusCode.OK,"增加成功");
	}


	/**
	 * 审核专栏
	 * @param columnId
	 * @return
	 */
	@RequestMapping(value = "/examine/{columnId}",method = RequestMethod.PUT)
	public Result examine(@PathVariable String columnId) {
		columnService.examine(columnId);
		return new Result(true,StatusCode.OK,"审核成功");
	}


	/**
	 * my分页+多条件查询
	 * @param searchColumn 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/mySearch/{page}/{size}",method=RequestMethod.POST)
	public Result findMySearch(@RequestBody Column searchColumn , @PathVariable int page, @PathVariable int size){
		Page<Column> pageList = columnService.findMySearch(searchColumn, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Column>(pageList.getTotalElements(), pageList.getContent()) );
	}


	
}
