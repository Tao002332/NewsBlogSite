package com.newsblogsite.news.controller;
import java.util.List;
import java.util.Map;

import com.newsblogsite.news.client.GatheringClient;
import com.newsblogsite.news.dao.UserCollectedDao;
import com.newsblogsite.news.pojo.UserCollected;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.newsblogsite.news.pojo.News;
import com.newsblogsite.news.service.NewsService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/news")
public class NewsController {

	@Autowired
	private NewsService newsService;



	@Autowired
	private GatheringClient gatheringClient;


	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",newsService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",newsService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchNews 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody News searchNews , @PathVariable int page, @PathVariable int size){
		Page<News> pageList = newsService.findSearch(searchNews, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<News>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchNews
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody News searchNews){
        return new Result(true,StatusCode.OK,"查询成功",newsService.findSearch(searchNews));
    }
	
	/**
	 * 增加
	 * @param news
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody News news  ){
		if(news.getGatheringId()!=null && !"".equals(news.getGatheringId())) {
			Result result = gatheringClient.join(news.getGatheringId());
			if(result.getCode()!=StatusCode.OK){
				return result;
			}
		}
		newsService.add(news);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param news
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody News news, @PathVariable String id ){
		news.setId(id);
		newsService.update(news);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		newsService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}


	/**
	 * 审核新闻
	 * @param newsId
	 * @return
	 */
	@RequestMapping(value = "/examine/{newsId}",method = RequestMethod.PUT)
	public Result examine(@PathVariable String newsId) {
		newsService.examine(newsId);
		return new Result(true,StatusCode.OK,"审核成功");
	}


	/**
	 * 是否公开
	 * @param newsId
	 * @param value
	 * @return
	 */
	@RequestMapping(value = "/public/{newsId}/{value}",method = RequestMethod.PUT)
	public  Result setPublic(@PathVariable String newsId,@PathVariable String value) {
		newsService.setPublic(newsId,value);
		return new Result(true,StatusCode.OK,"操作成功");
	}

	/**
	 * 是否置顶
	 * @param newsId
	 * @param value
	 * @return
	 */
	@RequestMapping(value = "/top/{newsId}/{value}",method = RequestMethod.PUT)
	public  Result setTop(@PathVariable String newsId,@PathVariable String value) {
		newsService.setTop(newsId,value);
		return new Result(true,StatusCode.OK,"操作成功");
	}

	/**
	 * 是否死亡
	 * @param newsId
	 * @param value
	 * @return
	 */
	@RequestMapping(value = "/death/{newsId}/{value}",method = RequestMethod.PUT)
	public  Result setDeath(@PathVariable String newsId,@PathVariable String value) {
		newsService.setDeath(newsId,value);
		return new Result(true,StatusCode.OK,"操作成功");
	}


	/**
	 * 增加评论数
	 * @param newsId
	 * @return
	 */
	@RequestMapping(value = "/comment/{newsId}",method = RequestMethod.PUT)
	public Result addCommentCount(@PathVariable String newsId) {
		newsService.addCommentCount(newsId);
		return new Result(true,StatusCode.OK,"操作成功");
	}


	/**
	 * 增加浏览数
	 * @param newsId
	 * @return
	 */
	@RequestMapping(value = "/visits/{newsId}",method = RequestMethod.PUT)
	public Result addVisitsCount(@PathVariable String newsId) {
		newsService.addVisitsCount(newsId);
		return new Result(true,StatusCode.OK,"操作成功");
	}



	/**
	 * my分页+多条件查询
	 * @param searchNews 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/mySearch/{page}/{size}",method=RequestMethod.POST)
	public Result findMySearch(@RequestBody News searchNews , @PathVariable int page, @PathVariable int size){
		Page<News> pageList = newsService.findMySearch(searchNews, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<News>(pageList.getTotalElements(), pageList.getContent()) );
	}


	/**
	 * 用户收藏
	 * @param newsId
	 * @return
	 */
	@RequestMapping(value = "/collect/{newsId}",method = RequestMethod.POST)
	public Result collected(@PathVariable String newsId) {
		newsService.collected(newsId);
		return new Result(true,StatusCode.OK,"操作成功");
	}

	/**
	 * 用户取消收藏
	 * @param newsId
	 * @return
	 */
	@RequestMapping(value = "/collect/{newsId}",method = RequestMethod.DELETE)
	public Result cancelCollect(@PathVariable String newsId) {
		newsService.cancelCollect(newsId);
		return new Result(true,StatusCode.OK,"操作成功");
	}

	/**
	 * 用户是否收藏
	 * @param newsId
	 * @return
	 */
	@RequestMapping(value = "/isCollected/{newsId}",method = RequestMethod.GET)
	public Result isCollected(@PathVariable String newsId) {
		boolean flag = newsService.isCollected(newsId);
		if(flag) {
			return new Result(true,StatusCode.OK,"操作成功");
		}
		return new Result(false,StatusCode.ERROR,"操作失败");
	}


	/**
	 * 用户收藏News列表
	 * @return
	 */
	@RequestMapping(value = "/user/collectedList",method= RequestMethod.GET)
	public Result findCollectList(){
		return new Result(true,StatusCode.OK,"查询成功",newsService.findCollectList());
	}


	/**
	 * 用户收藏News列表
	 * @return
	 */
	@RequestMapping(value = "/getLastTop5/{userId}",method= RequestMethod.GET)
	public Result getLastTop5(@PathVariable String userId){
		return new Result(true,StatusCode.OK,"查询成功",newsService.getLastTop5(userId));
	}




}
