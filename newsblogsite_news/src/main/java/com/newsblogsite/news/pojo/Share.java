package com.newsblogsite.news.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="tb_share")
public class Share implements Serializable{


	/**
	 * ID
	 */
	@Id
	private String id;



	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 新闻正文
	 */
	private String content;

	/**
	 * 新闻封面
	 */
	private String image;
	/**
	 * 发表时间
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date createTime;

	/**
	 * 发表日期
	 */
	@Transient
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date[] createDateTime;
	/**
	 * 修改时间
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date updateTime;

	/**
	 * 修改日期
	 */
	@Transient
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date[] updateDateTime;

	/**
	 * 是否公开
	 */
	private String isPublic;

	/**
	 * 是否置顶
	 */
	private String isTop;

	/**
	 * 浏览量
	 */
	private Integer visits;

	/**
	 * 点赞
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
	 * 是否死亡
	 */
	private String isDeath;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date[] getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date[] createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date[] getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(Date[] updateDateTime) {
		this.updateDateTime = updateDateTime;
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

	public String getIsDeath() {
		return isDeath;
	}

	public void setIsDeath(String isDeath) {
		this.isDeath = isDeath;
	}
}
