package com.fintech.platform.core.fastdfs.task;

import java.io.File;
import java.io.IOException;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import com.fintech.platform.core.fastdfs.IKeeper;
import com.fintech.platform.core.fastdfs.StoragesKeeper;
import com.fintech.platform.core.fastdfs.common.NameValuePair;
import com.fintech.platform.core.fastdfs.exception.FastDfsException;
import com.fintech.platform.core.fastdfs.util.ConfigLoader;

public class SynSingleFileDownloadWorker
{
  private TrackerClient tracker;
  private boolean mateInfoSupported = ConfigLoader.getInstance().getBoolean("mateInfoSupported", false);

  private String bizAppPath;
  
  
  public SynSingleFileDownloadWorker()
    throws Exception{
	if(bizAppPath == null || "".equals(bizAppPath) ||"null".equalsIgnoreCase(bizAppPath) ){
		String url = new File(SynSingleFileDownloadWorker.class.getResource("/").getFile()).getCanonicalPath();
		ClientGlobal.init(url + File.separator + "biz_app.properties");
	}else{
		ClientGlobal.init(bizAppPath);
	}
    
    this.tracker = new TrackerClient();
  }
  
  public SynSingleFileDownloadWorker(String bizAppPath)
		    throws Exception{
	  		//ClientGlobal.init(new File(SynSingleFileDownloadWorker.class.getResource("/").getFile()).getCanonicalPath() + File.separator + "biz_app.properties");
	  ClientGlobal.init(bizAppPath);
      this.tracker = new TrackerClient();
  }

  public byte[] download(String fileId)
    throws IOException, FastDfsException
  {
    TrackerServer trackerServer = this.tracker.getConnection();
    StorageServer storageServer = null;
    IKeeper ikeeper = new StoragesKeeper(trackerServer, storageServer);
    byte[] fileByte = ikeeper.downloadFile(fileId);
    trackerServer.close();
    return fileByte;
  }

  public NameValuePair[] getFileInfo(String fileId)
    throws IOException, FastDfsException
  {
    if (!(this.mateInfoSupported)) {
      String simpleFileId = fileId.substring(fileId.lastIndexOf("/") + 1);
      NameValuePair[] nv = new NameValuePair[1];

      nv[0] = new NameValuePair("fileName", simpleFileId);
      return nv;
    }

    TrackerServer trackerServer = this.tracker.getConnection();
    StorageServer storageServer = null;
    IKeeper ikeeper = new StoragesKeeper(trackerServer, storageServer);
    NameValuePair[] filemeta = ikeeper.queryMetaInfo(fileId);
    trackerServer.close();
    return filemeta;
  }

  public static void main(String[] args)
  {
    String fileId = "group1/M00/00/01/wKgyDVSGpdeATWN4AAAa4SKVDoQ44.conf";
    String simpleFileId = fileId.substring(fileId.lastIndexOf("/") + 1);
    System.out.println(simpleFileId);
    try
    {
      NameValuePair[] arrayOfNameValuePair1;
      SynSingleFileDownloadWorker synSingleFileDownloadWorker = new SynSingleFileDownloadWorker();

      byte[] fileBytes = synSingleFileDownloadWorker.download("group1/M00/00/01/wKgyDVSGpdeATWN4AAAa4SKVDoQ44.conf");
      System.out.println(new String(fileBytes));
      NameValuePair[] fileInfo = synSingleFileDownloadWorker.getFileInfo("group1/M00/00/01/wKgyDVSGpdeATWN4AAAa4SKVDoQ44.conf");
      int j = (arrayOfNameValuePair1 = fileInfo).length; for (int i = 0; i < j; ++i) { NameValuePair nvp = arrayOfNameValuePair1[i];
        System.out.println(nvp.getName() + ":" + nvp.getValue());
      }

      System.out.println(synSingleFileDownloadWorker.download("group1/M00/00/02/wKhADVSqRuaAHAzSADkUGMzE5789691000×1200.jpg"));
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}