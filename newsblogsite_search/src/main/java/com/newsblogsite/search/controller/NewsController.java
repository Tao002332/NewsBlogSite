package com.newsblogsite.search.controller;

import com.newsblogsite.search.pojo.News;
import com.newsblogsite.search.service.NewsService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


/**
 * *@author 83614
 * *@date 2020/4/16
 **/
@RestController
@CrossOrigin
@RequestMapping("/search")
public class NewsController {


    @Autowired
    private NewsService newsService;


    /**
     * 分页+多条件查询
     * @param searchNews 查询条件封装
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @RequestMapping(value="/search/{page}/{size}",method= RequestMethod.POST)
    public Result findSearch(@RequestBody News searchNews , @PathVariable int page, @PathVariable int size){
        Page<News> pageList = newsService.findSearch(searchNews, page, size);
        return  new Result(true, StatusCode.OK,"查询成功",  new PageResult<News>(pageList.getTotalElements(), pageList.getContent()) );
    }


}
