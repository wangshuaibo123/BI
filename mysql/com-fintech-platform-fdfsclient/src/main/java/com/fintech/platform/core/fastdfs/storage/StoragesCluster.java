package com.fintech.platform.core.fastdfs.storage;

import java.io.File;
import java.io.FileInputStream;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerServer;
import org.csource.fastdfs.UploadStream;

public class StoragesCluster extends StorageClient1{ 
 
	private static final byte cmd = ProtoCommon.STORAGE_PROTO_CMD_UPLOAD_FILE;

  public StoragesCluster(TrackerServer trackerServer, StorageServer storageServer)
  {
    super(trackerServer, storageServer);
  }

  public String upload_file(File localFile, String fileExtName, NameValuePair[] metaList)
    throws Exception
  {
    FileInputStream fis = new FileInputStream(localFile);
    if (fileExtName == null) {
      int nPos = localFile.getName().lastIndexOf(46);
      if ((nPos > 0) && (localFile.getName().length() - nPos <= 7))
        fileExtName = localFile.getName().substring(nPos + 1);

    }

    return upload_file(fis, metaList, localFile.length(), fileExtName);
  }

  private String upload_file(FileInputStream fis, NameValuePair[] metaList, long fileSize, String fileExtName)
    throws Exception
  {
    String[] parts = new String[2];
    try {
      parts = this.do_upload_file(cmd, null, null, null, fileExtName, fileSize, new UploadStream(fis, fileSize), 
        metaList);
    }
    finally {
      fis.close();
    }
    if (parts != null)
      return parts[0] + SPLIT_GROUP_NAME_AND_FILENAME_SEPERATOR + parts[1];

    return null;
  }
}