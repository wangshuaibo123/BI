package com.fintech.platform.core.fastdfs;

import com.fintech.platform.core.fastdfs.exception.FastDfsException;

public abstract interface ITruncate
{
  public abstract int truncateFile(String paramString)
    throws FastDfsException;
}