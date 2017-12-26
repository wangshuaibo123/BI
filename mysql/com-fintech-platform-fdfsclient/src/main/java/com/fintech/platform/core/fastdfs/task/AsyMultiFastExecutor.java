package com.fintech.platform.core.fastdfs.task;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.TrackerClient;

import com.fintech.platform.core.fastdfs.common.NameValuePair;

public class AsyMultiFastExecutor extends BaseMutilFastDealMaster
{
  private TrackerClient tracker;
  private ThreadPoolExecutor threadPool;

  public Future<?> runUploadTask(String localFilename, String fileExtName, NameValuePair[] meta_list)
    throws IOException
  {
    return this.threadPool.submit(new FileUploadCallable(this.tracker, localFilename, fileExtName, meta_list));
  }

  public Future<?> runUploadTask(String groupName, InputStream input, String fileExtName, NameValuePair[] meta_list) throws IOException
  {
    return this.threadPool.submit(new FileUploadCallable(this.tracker, input, fileExtName, meta_list));
  }

  public AsyMultiFastExecutor()
  {
    try
    {
      this.threadPool = 
        new ThreadPoolExecutor(getCorePoolSize(), getMaxNumPoolSize(), 
        getKeepAliveTime(), TimeUnit.SECONDS, new ArrayBlockingQueue(getTaskQueue()), 
        new ThreadPoolExecutor.DiscardOldestPolicy());
      ClientGlobal.init(null);
      this.tracker = new TrackerClient();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}