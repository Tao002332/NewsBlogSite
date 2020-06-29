package com.newsblogsite.comment.controller;

import com.newsblogsite.comment.client.NewsClient;
import com.newsblogsite.comment.pojo.Comment;
import com.newsblogsite.comment.service.CommentService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * *@author 83614
 * *@date 2020/4/14
 **/
@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {
    
    @Autowired
    private CommentService commentService;

    @Autowired
    private NewsClient newsClient;

    /**
     * 查询全部数据
     * @return
     */
    @RequestMapping(method= RequestMethod.GET)
    public Result findAll(){
        return new Result(true,StatusCode.OK,"查询成功",commentService.findAll());
    }

    /**
     * 根据ID查询
     * @param id ID
     * @return
     */
    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public Result findById(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成功",commentService.findById(id));
    }


    /**
     * 分页+多条件查询
     * @param searchComment 查询条件封装
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
    public Result findSearch(@RequestBody Comment searchComment , @PathVariable int page, @PathVariable int size){
        Page<Comment> pageList = commentService.findSearch(searchComment, page, size);
        return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Comment>(pageList.getTotalElements(), pageList.getContent()) );
    }

    /**
     * 根据条件查询
     * @param searchComment
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Comment searchComment){
        return new Result(true,StatusCode.OK,"查询成功",commentService.findSearch(searchComment));
    }

    /**
     * 增加
     * @param comment
     */
    @RequestMapping(method= RequestMethod.POST)
    public Result add(@RequestBody Comment comment  ){
        commentService.add(comment);
        if("1".equals(comment.getOriginType())) {
           return newsClient.addNewsCommentCount(comment.getOriginId());
        }
        if("2".equals(comment.getOriginType())) {
            return newsClient.addShareIdCommentCount(comment.getOriginId());
        }
        return new Result(false,StatusCode.ERROR,"添加错误");
    }

    /**
     * 修改
     * @param comment
     */
    @RequestMapping(value="/{id}",method= RequestMethod.PUT)
    public Result update(@RequestBody Comment comment, @PathVariable String id ){
        comment.set_id(id);
        commentService.update(comment);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 删除
     * @param id
     */
    @RequestMapping(value="/{id}",method= RequestMethod.DELETE)
    public Result delete(@PathVariable String id ){
        commentService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }


    /**
     * 点赞
     * @param commentId
     * @return
     */
    @RequestMapping(value = "/thumbup/{commentId}",method = RequestMethod.PUT)
    public Result addthumupCount(@PathVariable String commentId){
        commentService.addthumupCount(commentId);
        return new Result(true,StatusCode.OK,"点赞成功");
    }



    /**
     * 审核
     * @param commentId
     * @return
     */
    @RequestMapping(value = "/examine/{commentId}",method = RequestMethod.PUT)
    public Result examine(@PathVariable String commentId){
        commentService.examine(commentId);
        return new Result(true,StatusCode.OK,"审核成功");
    }


}
