package com.fintech.platform.tools.common;

import com.fintech.platform.core.fastdfs.exception.FastDfsException;

public class FileUtils
{
  public static String getFileExtName(String real_file_name)
    throws FastDfsException
  {
    if ((real_file_name != null) && (real_file_name.contains(".")))
    {
      return real_file_name.substring(real_file_name.lastIndexOf(".") + 1, real_file_name.length());
    }
    throw new FastDfsException(" real file name is failure!");
  }

  public static String getFile_NoExtName(String real_file_name)
    throws FastDfsException
  {
    if ((real_file_name != null) && (real_file_name.contains(".")))
      return real_file_name.substring(0, real_file_name.indexOf(".") - 1);

    throw new FastDfsException(" real file name is failure!");
  }
}