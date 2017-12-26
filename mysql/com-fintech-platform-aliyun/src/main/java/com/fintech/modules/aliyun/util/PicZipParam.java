package com.fintech.modules.aliyun.util;

public class PicZipParam {
	private Integer width;
	private  Integer height;
	private  boolean keepAspectRatio = true;
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public boolean isKeepAspectRatio() {
		return keepAspectRatio;
	}
	public void setKeepAspectRatio(boolean keepAspectRatio) {
		this.keepAspectRatio = keepAspectRatio;
	}

}
