package com.easub.bi.dto;

import java.util.Date;

import com.fintech.platform.core.common.BaseDTO;

public class ShopsDTO extends BaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4607400897960940633L;

	private Integer id; //id
	private String uuid;
	private Long userId; //用户ID
	private String name; //名称或标题
	private String showName;//商户展示名称
	private String description;//描述
	private String cover;//封面
	private String choiceCover;//精选封面
	private String themeCover;//主题封面
	private Integer type;//版权方
	private String sn;//8位随机字母
	private Integer visible;//是否显示 0不显示 1显示
	private Integer isDeleted;//是否删除
	private Date createdAt;
	private Date updatedAt;
	private Date deletedAt;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getChoiceCover() {
		return choiceCover;
	}
	public void setChoiceCover(String choiceCover) {
		this.choiceCover = choiceCover;
	}
	public String getThemeCover() {
		return themeCover;
	}
	public void setThemeCover(String themeCover) {
		this.themeCover = themeCover;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Integer getVisible() {
		return visible;
	}
	public void setVisible(Integer visible) {
		this.visible = visible;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Date getDeletedAt() {
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}
	
}
