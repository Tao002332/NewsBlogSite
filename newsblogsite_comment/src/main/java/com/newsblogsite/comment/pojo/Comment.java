package com.newsblogsite.comment.pojo;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * *@author 83614
 * *@date 2020/4/14
 **/
public class Comment implements Serializable {

    /**
     * _id
     */
    @Id
    private String _id;

    /**
     * 内容
     */
    private String content;

    /**
     * 发布时间
     */
    @Field("publish_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date publishTime;

    /**
     * 发布日期
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date[] publishDateTime;

    /**
     * 发布人id
     */
    @Field("user_id")
    private String userId;

    /**
     * 发布人昵称
     */
    private  String nickname;

    /**
     * 点赞数
     */
    private Integer thumbup;

    /**
     * 评论数
     */
    private Integer comment;

    /**
     * 审核状态
     */
    private String state;

    /**
     * 评论父id
     */
    @Field("parent_id")
    private String parentId;

    /**
     * 所属id
     */
    @Field("origin_id")
    private  String originId;

    /**
     * 所属类型
     */
    @Field("origin_type")
    private  String originType;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Date[] getPublishDateTime() {
        return publishDateTime;
    }

    public void setPublishDateTime(Date[] publishDateTime) {
        this.publishDateTime = publishDateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getThumbup() {
        return thumbup;
    }

    public void setThumbup(Integer thumbup) {
        this.thumbup = thumbup;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getOriginType() {
        return originType;
    }

    public void setOriginType(String originType) {
        this.originType = originType;
    }
}
