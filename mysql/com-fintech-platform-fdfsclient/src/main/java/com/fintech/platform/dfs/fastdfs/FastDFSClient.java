package com.fintech.platform.dfs.fastdfs;

import java.io.File;
import java.io.FileNotFoundException;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class FastDFSClient
{
  private static TrackerClient trackerClient;

  static
  {
    try
    {
      ClientGlobal.init(new File(FastDFSClient.class.getResource("/").getFile
        ()).getCanonicalPath
        () + 
        File.separator + 
        "biz_app.properties");
    } catch (FileNotFoundException e) {
      throw new RuntimeException("配置文件biz_app.properties不存在");
    } catch (Exception e) {
      throw new RuntimeException("FastDFSClient初始化失败：", e);
    }
    trackerClient = new TrackerClient();
  }

  public String uploadFile(byte[] fileBuff, String simpleFileName, NameValuePair[] metaList)
    throws Exception
  {
    TrackerServer trackerServer = trackerClient.getConnection();
    String fileId = null;
    try {
      StorageServer storageServer = null;
      StorageClient1 storageClient1 = new StorageClient1(trackerServer, storageServer);
      fileId = storageClient1.upload_file1(fileBuff, simpleFileName, metaList);
    }
    finally {
      trackerServer.close();
    }

    return fileId;
  }

  public String uploadFile(String source, String originalName, NameValuePair[] metaList) throws Exception
  {
    TrackerServer trackerServer = trackerClient.getConnection();
    String fileId = null;
    try {
      StorageServer storageServer = null;
      StorageClient1 storageClient1 = new StorageClient1(trackerServer, storageServer);
      fileId = storageClient1.upload_file1(source, originalName, metaList);
    }
    finally {
      trackerServer.close();
    }

    return fileId;
  }
}