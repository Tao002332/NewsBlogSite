package com.newsblogsite.comment.service;

import com.newsblogsite.comment.dao.CommentDao;
import com.newsblogsite.comment.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * *@author 83614
 * *@date 2020/4/14
 **/
@Transactional
@Service
public class CommentService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private CommentDao commentDao;


    /**
     * 查询全部列表
     * @return
     */
    public List<Comment> findAll() {
        return commentDao.findAll();
    }


    /**
     * 条件查询+分页
     * @param whereComment
     * @param page
     * @param size
     * @return
     */
    public Page<Comment> findSearch(Comment whereComment, int page, int size) {
        Query query = getQuery(whereComment);
        PageRequest pageRequest =  PageRequest.of(page-1, size);
        query.with(pageRequest);
        long count = mongoTemplate.count(query, Comment.class);
        List<Comment> comments = mongoTemplate.find(query, Comment.class);
        Page<Comment> commentPage= new PageImpl<>(comments,pageRequest,count);
        return commentPage;
    }


    /**
     * 条件查询
     * @param whereComment
     * @return
     */
    public List<Comment> findSearch(Comment whereComment) {
        Query query = getQuery(whereComment);
        return mongoTemplate.find(query,Comment.class);
    }

    /**
     * 根据ID查询实体
     * @param id
     * @return
     */
    public Comment findById(String id) {
        return commentDao.findById(id).get();
    }

    /**
     * 增加
     * @param comment
     */
    public void add(Comment comment) {
        checkRole("userClaims");
        String id= (String)request.getAttribute("loginId");
        comment.set_id(idWorker.nextId()+"" );
        comment.setUserId(id);
        comment.setPublishTime(new Date());
        comment.setThumbup(0);
        comment.setComment(0);
        comment.setState("0");
        commentDao.save(comment);

        //增加父评论的评论数
        if( !"0".equals(comment.getParentId())) {
            addCommentCount(comment.getParentId());
        }

    }

    /**
     * 修改
     * @param comment
     */
    public void update(Comment comment) {
        commentDao.save(comment);
    }

    /**
     * 删除
     * @param id
     */
    public void deleteById(String id) {
        commentDao.deleteById(id);
    }

    /**
     * 动态条件构建
     * @param comment
     * @return
     */
    private Query getQuery(Comment comment) {
        Query query = new Query();
        if( comment.get_id()!= null && !"".equals(comment.get_id())) {
            query.addCriteria(Criteria.where("_id").is(comment.get_id()));
        }
        if( comment.getUserId()!= null && !"".equals(comment.getUserId())) {
            query.addCriteria(Criteria.where("user_id").is(comment.getUserId()));
        }
        if( comment.getState()!= null && !"".equals(comment.getState())) {
            query.addCriteria(Criteria.where("state").is(comment.getState()));
        }
        if( comment.getParentId()!= null && !"".equals(comment.getParentId())) {
            query.addCriteria(Criteria.where("parent_id").is(comment.getParentId()));
        }
        if( comment.getOriginId()!= null && !"".equals(comment.getOriginId())) {
            query.addCriteria(Criteria.where("origin_id").is(comment.getOriginId()));
        }
        if( comment.getOriginType()!= null && !"".equals(comment.getOriginType())) {
            query.addCriteria(Criteria.where("origin_type").is(comment.getOriginType()));
        }
        if( comment.getPublishDateTime()!= null) {
            Criteria sub= Criteria.where("publish_time");
            query.addCriteria(sub.gte(comment.getPublishDateTime()[0]).lte(comment.getPublishDateTime()[1]));
        }
        return query;
    }


    /**
     * 增加点赞
     * @param commentId
     * @return
     */
    public void addthumupCount(String commentId) {
        checkRole("userClaims");
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(commentId));
        Update update = new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,"comment");
    }


    /**
     * 增加评论数
     * @param commentId
     * @return
     */
    public void addCommentCount(String commentId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(commentId));
        Update update = new Update();
        update.inc("comment",1);
        mongoTemplate.updateFirst(query,update,"comment");
    }



    /**
     * 审核评论
     * @param commentId
     * @return
     */
    public void examine(String commentId) {
        checkRole("adminClaims");
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(commentId));
        Update update = new Update();
        update.set("state","1");
        mongoTemplate.updateFirst(query,update,"comment");
    }



    /**
     * 验证权限 提取
     * @param rolename
     */
    protected void checkRole(String rolename) {
        String reqToken =(String)request.getAttribute(rolename);
        if(reqToken == null || "".equals(reqToken)) {
            throw  new RuntimeException("权限不足");
        }
    }

}
