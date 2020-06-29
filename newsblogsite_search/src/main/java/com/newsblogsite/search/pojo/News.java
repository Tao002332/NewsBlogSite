package com.newsblogsite.search.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


import java.util.Date;

/**
 * *@author 83614
 * *@date 2020/4/16
 **/
@Document(indexName = "newsblogsite_news",type = "news")
@JsonIgnoreProperties
public class News {

    /**
     * 新闻id
     */
    @Id
    private String id;

    /**
     * 专栏id
     */
    private String columnId;

    /**
     * 专栏名称
     */
    private String columnName;

    /**
     * 发布者id
     */
    private String userId;

    /**
     * 发布者昵称
     */
    private String userNickname;


    /**
     * 发布者头像
     */
    private String userAvator;

    /**
     * 文章标题
     */
    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer ="ik_max_word" )
    private String title;

    /**
     * 文章图片
     */
    private String image;

    /**
     *  创建时间
     */
    private String createTime;

    /**
     * 公开
     */
    private String isPublic;

    /**
     * 置顶
     */
    private String isTop;

    /**
     * 浏览量
     */
    private Integer visits;

    /**
     * 评论量
     */
    private Integer comment;

    /**
     * 频道id
     */
    private String channelId;

    /**
     * 频道名称
     */
    private String channelName;


    /**
     * 标签集合
     */
    private String labels;

    /**
     * 置顶
     */
    private String isDeath;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserAvator() {
        return userAvator;
    }

    public void setUserAvator(String userAvator) {
        this.userAvator = userAvator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }

    public String getIsTop() {
        return isTop;
    }

    public void setIsTop(String isTop) {
        this.isTop = isTop;
    }

    public Integer getVisits() {
        return visits;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getIsDeath() {
        return isDeath;
    }

    public void setIsDeath(String isDeath) {
        this.isDeath = isDeath;
    }

    @Override
    public String toString() {
        return "News{" +
                "id='" + id + '\'' +
                ", columnId='" + columnId + '\'' +
                ", columnName='" + columnName + '\'' +
                ", userId='" + userId + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", userAvator='" + userAvator + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", createTime='" + createTime + '\'' +
                ", isPublic=" + isPublic +
                ", isTop=" + isTop +
                ", visits=" + visits +
                ", comment=" + comment +
                ", channelId='" + channelId + '\'' +
                ", channelName='" + channelName + '\'' +
                ", labels='" + labels + '\'' +
                '}';
    }
}
