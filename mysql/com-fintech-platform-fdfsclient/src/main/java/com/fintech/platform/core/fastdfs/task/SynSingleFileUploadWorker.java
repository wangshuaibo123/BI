package com.fintech.platform.core.fastdfs.task;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fintech.platform.core.fastdfs.IKeeper;
import com.fintech.platform.core.fastdfs.IPdfToImage;
import com.fintech.platform.core.fastdfs.StoragesKeeper;
import com.fintech.platform.core.fastdfs.common.NameValuePair;
import com.fintech.platform.core.fastdfs.exception.FastDfsException;
import com.fintech.platform.core.fastdfs.util.ConfigLoader;
import com.fintech.platform.core.fastdfs.zip.PicZipParam;

public class SynSingleFileUploadWorker
{
  private static final Logger logger = LoggerFactory.getLogger(SynSingleFileUploadWorker.class);
  private TrackerClient tracker;
  private static final AtomicInteger count = new AtomicInteger();
  private static ThreadPoolExecutor threadPool = null;

  static
  {
    logger.info("SynSingleFileUploadWorker创建了" + count.incrementAndGet() + "次" + new Date());

    int corePoolSize = ConfigLoader.getInstance().getInt("thumbnails.threadPool.corePoolSize");

    if (corePoolSize > 0) {
      int maximumPoolSize = ConfigLoader.getInstance().getInt("thumbnails.threadPool.maximumPoolSize", 10);
      int keepAliveTimeS = ConfigLoader.getInstance().getInt("thumbnails.threadPool.keepAliveTimeS", 60);
      int queueLength = ConfigLoader.getInstance().getInt("thumbnails.threadPool.queueLength", 8192);
      threadPool = new ThreadPoolExecutor(
        corePoolSize, 
        maximumPoolSize, 
        keepAliveTimeS, 
        TimeUnit.SECONDS, 
        new LinkedBlockingQueue(queueLength), 
        new ThreadFactory() {
        private final AtomicInteger threadCount = new AtomicInteger();

        public Thread newThread(Runnable r) {
          Thread t = new Thread(r, "SynSingleFileUploadWorker-slt-" + this.threadCount.incrementAndGet());
          return t;
        }

      });
    }
  }

  public SynSingleFileUploadWorker()
    throws Exception
  {
    ClientGlobal.init(new File(SynSingleFileUploadWorker.class.getResource("/").getFile()).getCanonicalPath() + File.separator + "biz_app.properties");
    this.tracker = new TrackerClient();
  }
  
  public SynSingleFileUploadWorker(String bizAppPath)
		    throws Exception{
		    ClientGlobal.init(bizAppPath);
		    this.tracker = new TrackerClient();
		  }

  public String upload(byte[] file_buff, NameValuePair[] metaList, String file_ext_name)
    throws IOException, FastDfsException
  {
    TrackerServer trackerServer = this.tracker.getConnection();
    String fileId = null;
    try {
      StorageServer storageServer = null;
      IKeeper ikeeper = new StoragesKeeper(trackerServer, storageServer);
      fileId = ikeeper.uploadFile(file_buff, file_ext_name, metaList);
    }
    finally {
      trackerServer.close();
    }

    return fileId;
  }

  public String upload(File file_buff, NameValuePair[] metaList, String file_ext_name)
    throws IOException, FastDfsException
  {
    TrackerServer trackerServer = this.tracker.getConnection();
    String fileId = null;
    try {
      StorageServer storageServer = null;
      IKeeper ikeeper = new StoragesKeeper(trackerServer, storageServer);
      fileId = ikeeper.uploadFile(file_buff, metaList, file_ext_name);
    }
    finally {
      trackerServer.close();
    }
    return fileId;
  }

  public String uploadImgAndZip(byte[] file_buff, NameValuePair[] metaList, String real_file_name, PicZipParam[] picZipParams)
    throws IOException, FastDfsException
  {
    TrackerServer trackerServer = this.tracker.getConnection();
    String fileId = null;
    try {
      StorageServer storageServer = null;
      IKeeper ikeeper = new StoragesKeeper(trackerServer, storageServer);

      fileId = ikeeper.uploadFile(file_buff, real_file_name, metaList);

      Runnable r = new PicZip(ikeeper, file_buff, fileId, metaList, real_file_name, picZipParams);

      if (threadPool != null) {
        threadPool.execute(r);
       }else{
    	   new Thread(r).start();
       
       }
    }
    finally {
      trackerServer.close();
    }

    return fileId;
  }
  /**
   * 上传PDF时同时 生成image
   * @param file_buff
   * @param metaList
   * @param real_file_name
   * @param picZipParams
   * @return
   * @throws IOException
   * @throws FastDfsException
   */
  public String uploadPDFAndImg(byte[] file_buff, NameValuePair[] metaList, String real_file_name, IPdfToImage pdfToImage,String imagExt)
		    throws IOException, FastDfsException{
	  
		    TrackerServer trackerServer = this.tracker.getConnection();
		    String fileId = null;
		    try {
		      StorageServer storageServer = null;
		      IKeeper ikeeper = new StoragesKeeper(trackerServer, storageServer);

		      fileId = ikeeper.uploadFile(file_buff, real_file_name, metaList);
		      Runnable r = new PdfPic(ikeeper, file_buff, fileId, metaList, real_file_name, pdfToImage,imagExt);

		      if (threadPool != null) {
		        threadPool.execute(r);
		       }else{
		    	   new Thread(r).start();
		       }
		    }finally {
		    	if(trackerServer != null) 
		    		trackerServer.close();
		    }

		    return fileId;
  }
  

  class PicZip implements Runnable {
    byte[] fileBuff;
    String mainKey;
    NameValuePair[] metaList;
    String real_file_name;
    PicZipParam[] picZipParams;
    IKeeper ikeeper;
    StorageServer storageServer;
	TrackerServer trackerServer;
	
    public PicZip(IKeeper ikeeper, byte[] paramArrayOfByte, String paramString1, NameValuePair[] paramArrayOfNameValuePair, String paramString2, PicZipParam[] paramArrayOfPicZipParam)
    {
      //this.pzIkeeper = ikeeper;
      this.fileBuff = paramArrayOfByte;
      this.mainKey = paramString1;
      this.metaList = paramArrayOfNameValuePair;
      this.real_file_name = paramString2;
      this.picZipParams = paramArrayOfPicZipParam;
      this.ikeeper = new StoragesKeeper(trackerServer, storageServer);
    }

    public void run()
    {
      try {
    	  ikeeper.uploadImgAndZip(this.fileBuff, this.mainKey, this.metaList, this.real_file_name, this.picZipParams);
      } catch (Exception e) {
    	  logger.error("生成缩略图失败,mainKey=" + this.mainKey + "：", e);

        if (trackerServer != null){
        	try {
                trackerServer.close();
              } catch (IOException e1) {
              	logger.error("生成缩略图关闭trackerServer连接失败：", e1);
              }
        }
      }finally{
        if (trackerServer != null)
          try {
            trackerServer.close();
          } catch (IOException e) {
        	  logger.error("生成缩略图关闭trackerServer连接失败：", e);
          }
      }
    }
  }
  
  
  class PdfPic implements Runnable {
	    byte[] fileBuff;
	    String mainKey;
	    NameValuePair[] metaList;
	    String real_file_name;
	    PicZipParam[] picZipParams;
	    IKeeper ikeeper;
	    StorageServer storageServer;
		TrackerServer trackerServer;
		IPdfToImage pdfToImage;
		String imagExt;
	    public PdfPic(IKeeper ikeeper, byte[] paramArrayOfByte, String paramString1, NameValuePair[] paramArrayOfNameValuePair, String paramString2, IPdfToImage pdfToImage,String imagExt)
	    {
	      //this.ikeeper = ikeeper;
	      this.fileBuff = paramArrayOfByte;
	      this.mainKey = paramString1;
	      this.metaList = paramArrayOfNameValuePair;
	      this.real_file_name = paramString2;
	      this.pdfToImage = pdfToImage;
	      this.imagExt = imagExt;
	      this.ikeeper = new StoragesKeeper(this.trackerServer, storageServer);
	    }

	    public void run(){
	      try {
	    	  ikeeper.uploadPdfAndImg(this.fileBuff, this.mainKey, this.metaList, this.real_file_name, pdfToImage,imagExt);
	      } catch (Exception e) {
	    	  logger.error("PDF生成图失败,mainKey=" + this.mainKey + "：", e);

	        if (trackerServer != null){
	        	try {
	                trackerServer.close();
	              } catch (IOException e1) {
	              	logger.error("PDF生成图片关闭trackerServer连接失败：", e1);
	              }
	        }
	      }finally{
	        if (trackerServer != null)
	          try {
	            trackerServer.close();
	          } catch (IOException e) {
	        	  logger.error("生成缩略图关闭trackerServer连接失败：", e);
	          }
	      }
	    }
	  }
  
  
  
  public static void main(String[] args) throws Exception {
	    int n;
	    SynSingleFileUploadWorker sy = new SynSingleFileUploadWorker();
	    File file = new File("C:/generate/1.jpg");

	    FileInputStream fis = new FileInputStream(file);
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    byte[] b = new byte[1024];

	    while ((n = fis.read(b)) != -1){
	      bos.write(b, 0, n);
	    }
	    fis.close();
	    bos.close();
	    byte[] buffer = bos.toByteArray();

	    PicZipParam[] picZipParams = new PicZipParam[2];
	    picZipParams[0] = new PicZipParam();
	    picZipParams[0].setHeight(1000);
	    picZipParams[0].setWidth(300);
	    picZipParams[0].setKeepAspectRatio(false);

	    picZipParams[1] = new PicZipParam();
	    picZipParams[1].setHeight(1000);
	    picZipParams[1].setWidth(400);

	    String f = sy.uploadImgAndZip(buffer, null, "1.jpg", picZipParams);

	    System.out.println(f);
	  }
}