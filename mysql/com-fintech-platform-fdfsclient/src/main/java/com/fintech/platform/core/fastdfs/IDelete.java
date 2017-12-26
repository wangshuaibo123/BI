package com.fintech.platform.core.fastdfs;

import com.fintech.platform.core.fastdfs.exception.FastDfsException;

public abstract interface IDelete
{
  public abstract int deleteFile(String paramString)
    throws FastDfsException;
}