package com.newsblogsite.user.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * 实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="tb_user")
public class User implements Serializable {

	/**
	 * ID
	 */
	@Id
	private String id;


	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 出生年月日
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date birthday;
	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * E-Mail
	 */
	private String email;
	/**
	 * 注册日期
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date regDate;
	/**
	 * 注册日期范围
	 */
	@Transient    //临时字段
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date[] regDateTime;
	/**
	 * 修改日期
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date updateDate;
	/**
	 * 修改日期范围
	 */
	@Transient
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date[] updateDateTime;
	/**
	 * 最后登陆日期
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date lastDate;
	/**
	 * 最后日期范围
	 */
	@Transient
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
	private Date[] lastDateTime;
	/**
	 * 在线时长（分钟）
	 */
	private Long online;
	/**
	 * 兴趣
	 */
	private String interest;
	/**
	 * 个性
	 */
	private String personality;
	/**
	 * 粉丝数
	 */
	private Integer fansCount;
	/**
	 * 关注数
	 */
	private Integer followCount;
	/**
	 * 是否认证
	 */
	private String isAuth;
	/**
	 * 认证名称
	 */
	private String authName;

	/**
	 * 用户状态
	 */
	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date[] getRegDateTime() {
		return regDateTime;
	}

	public void setRegDateTime(Date[] regDateTime) {
		this.regDateTime = regDateTime;
	}

	public Date[] getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(Date[] updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public Date[] getLastDateTime() {
		return lastDateTime;
	}

	public void setLastDateTime(Date[] lastDateTime) {
		this.lastDateTime = lastDateTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public Long getOnline() {
		return online;
	}

	public void setOnline(Long online) {
		this.online = online;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getPersonality() {
		return personality;
	}

	public void setPersonality(String personality) {
		this.personality = personality;
	}

	public Integer getFansCount() {
		return fansCount;
	}

	public void setFansCount(Integer fansCount) {
		this.fansCount = fansCount;
	}

	public Integer getFollowCount() {
		return followCount;
	}

	public void setFollowCount(Integer followCount) {
		this.followCount = followCount;
	}

	public String getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(String isAuth) {
		this.isAuth = isAuth;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", mobile='" + mobile + '\'' +
				", password='" + password + '\'' +
				", nickname='" + nickname + '\'' +
				", sex='" + sex + '\'' +
				", birthday=" + birthday +
				", avatar='" + avatar + '\'' +
				", email='" + email + '\'' +
				", regDate=" + regDate +
				", regDateTime=" + Arrays.toString(regDateTime) +
				", updateDate=" + updateDate +
				", updateDateTime=" + Arrays.toString(updateDateTime) +
				", lastDate=" + lastDate +
				", lastDateTime=" + Arrays.toString(lastDateTime) +
				", online=" + online +
				", interest='" + interest + '\'' +
				", personality='" + personality + '\'' +
				", fansCount=" + fansCount +
				", followCount=" + followCount +
				", isAuth='" + isAuth + '\'' +
				", authName='" + authName + '\'' +
				'}';
	}
}

