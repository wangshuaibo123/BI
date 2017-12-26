 package com.fintech.platform.core.fastdfs;
 
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import net.coobird.thumbnailator.Thumbnails;

import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerServer;

import com.fintech.platform.core.fastdfs.exception.FastDfsException;
import com.fintech.platform.core.fastdfs.storage.StoragesCluster;
import com.fintech.platform.core.fastdfs.util.ConfigLoader;
import com.fintech.platform.core.fastdfs.zip.PicZipParam;
import com.fintech.platform.tools.common.FileUtils;
 
public class StoragesKeeper implements IKeeper{
   private StoragesCluster storagesCluster;
   public static final String SPLIT_GROUP_NAME_AND_FILENAME_SEPERATOR = "/";
   private String tempPicPath = ConfigLoader.getInstance().getValue("tempPicPath");
   private boolean mateInfoSupported = ConfigLoader.getInstance().getBoolean("mateInfoSupported", false);
	//默认压缩质量
   public static final double DEFAULT_QUALITY=0.1;
   public StoragesKeeper() {}
   
   public StoragesKeeper(TrackerServer trackerServer, StorageServer storageServer)
   {
     this.storagesCluster = new StoragesCluster(trackerServer, storageServer);
   }
   
   public String uploadFile(String local_filename, String file_ext_name, com.fintech.platform.core.fastdfs.common.NameValuePair[] meta_list)
     throws FastDfsException
   {
     try
     {
       return this.storagesCluster.upload_file1(local_filename, file_ext_name, toNameValuePair(meta_list));
     }
     catch (Exception e)
     {
       throw new FastDfsException(e.getMessage());
     }
   }
   
   public int deleteFile(String file_id)
     throws FastDfsException
   {
     try
     {
       return this.storagesCluster.delete_file1(file_id);
     }
     catch (Exception e)
     {
       throw new FastDfsException(e.getMessage());
     }
   }
   
   public byte[] downloadFile(String file_id)
     throws FastDfsException
   {
     try
     {
       return this.storagesCluster.download_file1(file_id);
     }
     catch (Exception e)
     {
       throw new FastDfsException(e.getMessage());
     }
   }
   
   public com.fintech.platform.core.fastdfs.common.FileInfo queryFileInfo(String file_id)
     throws FastDfsException
   {
     try
     {
       return toFileInfo(this.storagesCluster.query_file_info1(file_id));
     }
     catch (Exception e)
     {
       throw new FastDfsException(e.getMessage());
     }
   }
   
   public int truncateFile(String appender_file_id)
     throws FastDfsException
   {
     try
     {
       return this.storagesCluster.truncate_file1(appender_file_id);
     }
     catch (Exception e)
     {
       throw new FastDfsException(e.getMessage());
     }
   }
   
   public int modifyFile(String appender_file_id, long file_offset, String local_filename)
     throws FastDfsException
   {
     try
     {
       return this.storagesCluster.modify_file1(appender_file_id, file_offset, local_filename);
     }
     catch (Exception e)
     {
       throw new FastDfsException(e.getMessage());
     }
   }
   
   public String uploadFile(byte[] file_buff, String real_file_name, com.fintech.platform.core.fastdfs.common.NameValuePair[] meta_list)
     throws FastDfsException
   {
     try
     {
       String file_ext_name = FileUtils.getFileExtName(real_file_name);
       return this.storagesCluster.upload_file1(file_buff, file_ext_name, toNameValuePair(appendNameValuePairFileInfo(meta_list, real_file_name)));
     }
     catch (Exception e)
     {
       throw new FastDfsException(e.getMessage());
     }
   }
   
   com.fintech.platform.core.fastdfs.common.NameValuePair[] appendNameValuePairFileInfo(com.fintech.platform.core.fastdfs.common.NameValuePair[] metaList, String real_file_name)
   {
     List<com.fintech.platform.core.fastdfs.common.NameValuePair> metaArrayList = new ArrayList();
     if ((metaList != null) && (metaList.length != 0)) {
       metaArrayList.addAll(Arrays.asList((com.fintech.platform.core.fastdfs.common.NameValuePair[])metaList.clone()));
     }
     com.fintech.platform.core.fastdfs.common.NameValuePair real_file_namePair = new com.fintech.platform.core.fastdfs.common.NameValuePair();
     real_file_namePair.setName("fileName");
     real_file_namePair.setValue(real_file_name);
     metaArrayList.add(real_file_namePair);
     
     return (com.fintech.platform.core.fastdfs.common.NameValuePair[])metaArrayList.toArray(new com.fintech.platform.core.fastdfs.common.NameValuePair[metaArrayList.size()]);
   }
   
   public String uploadFile(File input, com.fintech.platform.core.fastdfs.common.NameValuePair[] metaList, String file_ext_name)
     throws FastDfsException
   {
     try
     {
       return this.storagesCluster.upload_file(input, file_ext_name, toNameValuePair(metaList));
     }
     catch (Exception e)
     {
       throw new FastDfsException(e.getMessage());
     }
   }
   
   public String uploadImgAndZip(byte[] fileBuff, String mainKey, com.fintech.platform.core.fastdfs.common.NameValuePair[] metaList, String real_file_name, PicZipParam[] picZipParams)
     throws FastDfsException
   {
     File tempFile = null;
     FileOutputStream os = null;
     ByteArrayOutputStream baos = null;
     String tempPicName = null;
     byte[] tempByte = null;
     try
     {
       String file_ext_name = FileUtils.getFileExtName(real_file_name);
       
 
       String mainPicKey = mainKey;
       System.out.println(mainPicKey);
       
 
       tempPicName = UUID.randomUUID().toString();
       tempFile = new File(this.tempPicPath + System.getProperty("file.separator") + tempPicName + "." + file_ext_name);
       os = new FileOutputStream(tempFile);
       os.write(fileBuff);
       os.flush();
       for (PicZipParam picZipParam : picZipParams)
       {
         baos = new ByteArrayOutputStream();
         Thumbnails.of(new File[] { tempFile }).size(picZipParam.getWidth(), picZipParam.getHeight()).keepAspectRatio(picZipParam.isKeepAspectRatio()).toOutputStream(baos);
         tempByte = baos.toByteArray();
         
         String prefix_name = "_" + picZipParam.getWidth() + "X" + picZipParam.getHeight();
         
         String slaveKey = this.storagesCluster.upload_file1(mainPicKey, prefix_name, tempByte, file_ext_name, toNameValuePair(appendNameValuePairFileInfo(metaList, FileUtils.getFile_NoExtName(real_file_name) + prefix_name + "." + file_ext_name)));
         System.out.println(slaveKey);
       }
       return mainPicKey;
     }
     catch (Exception e)
     {
       throw new FastDfsException(e.getMessage());
     }
     finally
     {
       if (baos != null) {
         try
         {
           baos.close();
         }
         catch (IOException localIOException2) {}
       }
       if (os != null) {
         try
         {
           os.close();
         }
         catch (IOException localIOException3) {}
       }
       if (tempFile != null) {
         tempFile.delete();
       }
     }
   }
   
   /**
    * 上传PDF时，同时将pdf转出图片
    */
   public String uploadPdfAndImg(byte[] fileBuff, String mainKey, com.fintech.platform.core.fastdfs.common.NameValuePair[] metaList, String real_file_name,IPdfToImage pdfToImage,String imagExt)
		     throws FastDfsException{
		     byte[] tempByte = null;
		     try{
		       String mainPicKey = mainKey;
		       System.out.println(mainPicKey);
	           tempByte = pdfToImage.pdfToImage(fileBuff);
	           String prefix_name = "_IMAGE";
	           String slaveKey = this.storagesCluster.upload_file1(mainPicKey, prefix_name, tempByte, imagExt, toNameValuePair(appendNameValuePairFileInfo(metaList, FileUtils.getFile_NoExtName(real_file_name) + prefix_name + "." + imagExt)));
	           System.out.println("=====image="+slaveKey);
		       return mainPicKey;
		     }catch (Exception e){
		    	 e.getMessage();
		       throw new FastDfsException(e.getMessage());
		     }
		   }
   public com.fintech.platform.core.fastdfs.common.NameValuePair[] queryMetaInfo(String file_id)
     throws FastDfsException
   {
     try
     {
       return toNameValuePair(this.storagesCluster.get_metadata1(file_id));
     }
     catch (Exception e)
     {
       throw new FastDfsException(e.getMessage());
     }
   }
   
   public org.csource.common.NameValuePair[] toNameValuePair(com.fintech.platform.core.fastdfs.common.NameValuePair[] metas)
   {
     if (!this.mateInfoSupported) {
       return null;
     }
     org.csource.common.NameValuePair[] metaArray = new org.csource.common.NameValuePair[metas.length];
     for (int i = 0; i < metas.length; i++)
     {
       org.csource.common.NameValuePair meta = new org.csource.common.NameValuePair();
       meta.setName(metas[i].getName());
       meta.setValue(metas[i].getValue());
       metaArray[i] = meta;
     }
     return metaArray;
   }
   
   public com.fintech.platform.core.fastdfs.common.NameValuePair[] toNameValuePair(org.csource.common.NameValuePair[] metas)
   {
     com.fintech.platform.core.fastdfs.common.NameValuePair[] metaArray = new com.fintech.platform.core.fastdfs.common.NameValuePair[metas.length];
     for (int i = 0; i < metas.length; i++)
     {
       com.fintech.platform.core.fastdfs.common.NameValuePair meta = new com.fintech.platform.core.fastdfs.common.NameValuePair();
       meta.setName(metas[i].getName());
       meta.setValue(metas[i].getValue());
       metaArray[i] = meta;
     }
     return metaArray;
   }
   
   public com.fintech.platform.core.fastdfs.common.FileInfo toFileInfo(org.csource.fastdfs.FileInfo info)
   {
     if (info != null) {
       return new com.fintech.platform.core.fastdfs.common.FileInfo(info.getFileSize(), new Integer(Long.toString(info.getCreateTimestamp().getTime())).intValue(), new Integer(Long.toString(info.getCrc32())).intValue());
     }
     return null;
   }
 }