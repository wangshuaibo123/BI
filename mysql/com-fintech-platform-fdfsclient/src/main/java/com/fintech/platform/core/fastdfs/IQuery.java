package com.fintech.platform.core.fastdfs;

import com.fintech.platform.core.fastdfs.common.FileInfo;
import com.fintech.platform.core.fastdfs.common.NameValuePair;
import com.fintech.platform.core.fastdfs.exception.FastDfsException;

public abstract interface IQuery
{
  public abstract FileInfo queryFileInfo(String paramString)
    throws FastDfsException;

  public abstract NameValuePair[] queryMetaInfo(String paramString)
    throws FastDfsException;
}