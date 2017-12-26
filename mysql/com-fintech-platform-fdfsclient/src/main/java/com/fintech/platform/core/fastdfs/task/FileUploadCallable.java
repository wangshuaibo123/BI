package com.fintech.platform.core.fastdfs.task;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;

import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import com.fintech.platform.core.fastdfs.IKeeper;
import com.fintech.platform.core.fastdfs.StoragesKeeper;
import com.fintech.platform.core.fastdfs.common.NameValuePair;

public class FileUploadCallable
  implements Callable<String>
{
  private TrackerClient tracker;
  private String localName;
  private String extName;
  private NameValuePair[] metaList;
  private InputStream inputStream;
  private boolean dealFlag = false;

  public FileUploadCallable(TrackerClient tracker, String localFilename, String fileExtName, NameValuePair[] meta_list)
  {
    this.tracker = tracker;
    this.localName = localFilename;
    this.extName = fileExtName;
    this.metaList = meta_list;
  }

  public FileUploadCallable(TrackerClient tracker, InputStream inputStream, String fileExtName, NameValuePair[] meta_list)
  {
    this.tracker = tracker;
    this.inputStream = inputStream;
    this.extName = fileExtName;
    this.metaList = meta_list;
    this.dealFlag = true;
  }

  public String call()
    throws Exception
  {
    TrackerServer trackerServer = null;
    String fileId = null;
    StorageServer storageServer = null;
    try {
      trackerServer = this.tracker.getConnection();
      IKeeper ikeeper = new StoragesKeeper(trackerServer, storageServer);
      if (this.dealFlag) {
        fileId = ikeeper.uploadFile(read(this.inputStream), this.extName, this.metaList);
       }else{
    	   fileId = ikeeper.uploadFile(this.localName, this.extName, this.metaList);
       }
      
    }
    finally
    {
      if (trackerServer != null)
        trackerServer.close();
    }

    return fileId;
  }

  public byte[] read(InputStream inputStream)
    throws IOException
  {
    byte[] buff = new byte[262144];
    int length = inputStream.read(buff);
    byte[] buffer = new byte[length];
    System.arraycopy(buff, 0, buffer, 0, length);
    return buffer;
  }
}