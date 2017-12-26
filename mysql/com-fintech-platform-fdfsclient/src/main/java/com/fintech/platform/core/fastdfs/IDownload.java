package com.fintech.platform.core.fastdfs;

import java.io.IOException;

import com.fintech.platform.core.fastdfs.exception.FastDfsException;

public abstract interface IDownload
{
  public abstract byte[] downloadFile(String paramString)
    throws IOException, FastDfsException;
}