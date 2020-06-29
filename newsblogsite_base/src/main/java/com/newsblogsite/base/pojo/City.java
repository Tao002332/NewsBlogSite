package com.newsblogsite.base.pojo;

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
@Table(name="tb_city")
public class City implements Serializable{
	/**
	 * ID
	 */
	@Id
	private String id;


	/**
	 * 父级名称
	 */
	private String pid;
	/**
	 * 城市名称
	 */
	private String cityName;

	/**
	 * 所属类型
	 */
	private String type;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "City{" +
				"id='" + id + '\'' +
				", pid='" + pid + '\'' +
				", cityName='" + cityName + '\'' +
				", type='" + type + '\'' +
				'}';
	}
}
