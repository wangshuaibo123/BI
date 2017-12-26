package com.fintech.platform.core.fastdfs;

import com.fintech.platform.core.fastdfs.exception.FastDfsException;

public abstract interface IModify
{
  public abstract int modifyFile(String paramString1, long paramLong, String paramString2)
    throws FastDfsException;
}