package com.fintech.platform.core.fastdfs;

import java.io.File;

import com.fintech.platform.core.fastdfs.common.NameValuePair;
import com.fintech.platform.core.fastdfs.exception.FastDfsException;
import com.fintech.platform.core.fastdfs.zip.PicZipParam;

public abstract interface IUpload
{
  public abstract String uploadFile(File paramFile, NameValuePair[] paramArrayOfNameValuePair, String paramString)
    throws FastDfsException;

  public abstract String uploadFile(String paramString1, String paramString2, NameValuePair[] paramArrayOfNameValuePair)
    throws FastDfsException;

  public abstract String uploadFile(byte[] paramArrayOfByte, String paramString, NameValuePair[] paramArrayOfNameValuePair)
    throws FastDfsException;

  public abstract String uploadImgAndZip(byte[] paramArrayOfByte, String paramString1, NameValuePair[] paramArrayOfNameValuePair, String paramString2, PicZipParam[] paramArrayOfPicZipParam)
    throws FastDfsException;
  
  public abstract String uploadPdfAndImg(byte[] paramArrayOfByte, String paramString1, NameValuePair[] paramArrayOfNameValuePair, String paramString2, IPdfToImage pdfToImage,String imagExt)
		    throws FastDfsException;
}