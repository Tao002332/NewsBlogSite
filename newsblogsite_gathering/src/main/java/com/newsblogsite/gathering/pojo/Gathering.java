package com.newsblogsite.gathering.pojo;

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
@Table(name="tb_gathering")
public class Gathering implements Serializable{

	/**
	 * 活动ID
	 */
	@Id
	private String id;


	/**
	 * 活动名称
	 */
	private String name;

	/**
	 * 大会简介
	 */
	private String summary;

	/**
	 * 详细说明
	 */
	private String detail;
	/**
	 * 主办方
	 */
	private String sponsor;

	/**
	 * 活动图片
	 */
	private String image;
	/**
	 * 开始时间
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date startTime;
	/**
	 * 开始范围
	 */
	@Transient
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date[] startDateTime;
	/**
	 * 截止时间
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date endTime;

	/**
	 * 结束范围
	 */
	@Transient
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date[] endDateTime;
	/**
	 * 举办地点
	 */
	private String address;
	/**
	 * 报名截止
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date enrollTime;

	/**
	 * 截止范围
	 */
	@Transient
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date[] enrollDateTime;

	/**
	 * 活动状态
	 */
	private String state;
	/**
	 * 频道ID
	 */
	private String channelId;
	/**
	 * 参与人数
	 */
	private Integer joinCount;

	/**
	 * 城市
	 */
	private String city;


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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getEnrollTime() {
		return enrollTime;
	}

	public void setEnrollTime(Date enrollTime) {
		this.enrollTime = enrollTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public Integer getJoinCount() {
		return joinCount;
	}

	public void setJoinCount(Integer joinCount) {
		this.joinCount = joinCount;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date[] getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date[] startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date[] getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date[] endDateTime) {
		this.endDateTime = endDateTime;
	}

	public Date[] getEnrollDateTime() {
		return enrollDateTime;
	}

	public void setEnrollDateTime(Date[] enrollDateTime) {
		this.enrollDateTime = enrollDateTime;
	}
}
