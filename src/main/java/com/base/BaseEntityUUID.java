package com.base;

import com.base.identity.IdEntityUUID;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * 统一定义对象基本操作信息的entity基类
 * @author heyong
 */
@MappedSuperclass
public class BaseEntityUUID extends IdEntityUUID {

	/**
	 * 创建用户标识
	 */
	private String createUserKey;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	

	/**
	 * 最后更新用户标识
	 */
	private String lastUpdateUserKey;
	
	/**
	 * 最后更新时间
	 */
	private Date lastUpdateTime;

	@Column(name = "create_user_key", length = 50)
	public String getCreateUserKey() {
		return createUserKey;
	}

	public void setCreateUserKey(String createUserKey) {
		this.createUserKey = createUserKey;
	}


	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "last_update_user_key", length = 50)
	public String getLastUpdateUserKey() {
		return lastUpdateUserKey;
	}

	public void setLastUpdateUserKey(String lastUpdateUserKey) {
		this.lastUpdateUserKey = lastUpdateUserKey;
	}

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "last_update_time")
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	
}
