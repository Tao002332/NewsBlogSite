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
@Table(name="tb_column")
public class Column implements Serializable{

	/**
	 * ID
	 */
	@Id
	private String id;


	/**
	 * 专栏名称
	 */
	private String name;
	/**
	 * 专栏简介
	 */
	private String summary;
	/**
	 * 用户ID
	 */
	private String userId;
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
	 * 审核时间
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date checkTime;
	/**
	 * 审核日期
	 */
	@Transient
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date[] checkDateTime;

	/**
	 * 状态
	 */
	private String state;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public Date[] getCheckDateTime() {
		return checkDateTime;
	}

	public void setCheckDateTime(Date[] checkDateTime) {
		this.checkDateTime = checkDateTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
