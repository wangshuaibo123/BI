package com.fintech.platform.core.fastdfs.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileInfo
{
  protected String source_ip_addr;
  protected long file_size;
  protected Date create_timestamp;
  protected int crc32;

  public FileInfo(long file_size, int create_timestamp, int crc32)
  {
    this.file_size = file_size;
    this.create_timestamp = new Date(create_timestamp * 1000L);
    this.crc32 = crc32;
  }

  public void setSourceIpAddr(String source_ip_addr)
  {
    this.source_ip_addr = source_ip_addr;
  }

  public String getSourceIpAddr()
  {
    return this.source_ip_addr;
  }

  public void setFileSize(long file_size)
  {
    this.file_size = file_size;
  }

  public long getFileSize()
  {
    return this.file_size;
  }

  public void setCreateTimestamp(int create_timestamp)
  {
    this.create_timestamp = new Date(create_timestamp * 1000L);
  }

  public Date getCreateTimestamp()
  {
    return this.create_timestamp;
  }

  public void setCrc32(int crc32)
  {
    this.crc32 = crc32;
  }

  public long getCrc32()
  {
    return this.crc32;
  }

  public String toString()
  {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return "source_ip_addr = " + this.source_ip_addr + ", " + 
      "file_size = " + this.file_size + ", " + 
      "create_timestamp = " + df.format(this.create_timestamp) + ", " + 
      "crc32 = " + this.crc32;
  }
}