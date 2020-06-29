package com.newsblogsite.comment.dao;

import com.newsblogsite.comment.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * *@author 83614
 * *@date 2020/4/14
 **/

public interface CommentDao extends MongoRepository<Comment,String> {
}
