package com.newsblogsite.user.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * 实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="tb_admin")
public class Admin implements Serializable{

	/**
	 * ID
	 */
	@Id
	private String id;


	/**
	 * 登陆名称
 	 */
	private String loginName;
	/**
	 * 密码
	 */
	private String password;
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

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
