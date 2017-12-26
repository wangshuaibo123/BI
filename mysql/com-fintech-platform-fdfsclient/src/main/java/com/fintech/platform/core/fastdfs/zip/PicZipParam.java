package com.fintech.platform.core.fastdfs.zip;

public class PicZipParam
{
  Integer width;
  Integer height;
  boolean keepAspectRatio = true;

  public int getWidth()
  {
    return this.width.intValue();
  }

  public void setWidth(int width) {
    this.width = Integer.valueOf(width);
  }

  public int getHeight() {
    return this.height.intValue();
  }

  public void setHeight(int height) {
    this.height = Integer.valueOf(height);
  }

  public boolean isKeepAspectRatio() {
    return this.keepAspectRatio;
  }

  public void setKeepAspectRatio(boolean keepAspectRatio) {
    this.keepAspectRatio = keepAspectRatio;
  }
}