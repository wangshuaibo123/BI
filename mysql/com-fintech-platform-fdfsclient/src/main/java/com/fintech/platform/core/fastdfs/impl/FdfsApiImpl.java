package com.fintech.platform.core.fastdfs.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fintech.platform.api.fdfs.FdfsAPI;
import com.fintech.platform.core.fastdfs.task.SynSingleFileDownloadWorker;

@Component
public class FdfsApiImpl implements FdfsAPI{
  private static final long serialVersionUID = 1246718567L;
  private static final Logger logger = LoggerFactory.getLogger(FdfsApiImpl.class);
  static SynSingleFileDownloadWorker synSingleFileDownloadWorker = null;

  // ERROR //
  public void pack(String[] fileIds, HttpServletRequest request, HttpServletResponse response)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +398 -> 399
    //   4: aload_1
    //   5: arraylength
    //   6: ifle +393 -> 399
    //   9: aload_1
    //   10: arraylength
    //   11: anewarray 38	org/zeroturnaround/zip/ZipEntrySource
    //   14: astore 4
    //   16: new 40	com/jy/platform/core/fastdfs/task/SynSingleFileDownloadWorker
    //   19: dup
    //   20: invokespecial 42	com/jy/platform/core/fastdfs/task/SynSingleFileDownloadWorker:<init>	()V
    //   23: putstatic 27	com/jy/platform/core/fastdfs/impl/FdfsApiImpl:synSingleFileDownloadWorker	Lcom/jy/platform/core/fastdfs/task/SynSingleFileDownloadWorker;
    //   26: iconst_0
    //   27: istore 5
    //   29: goto +65 -> 94
    //   32: getstatic 27	com/jy/platform/core/fastdfs/impl/FdfsApiImpl:synSingleFileDownloadWorker	Lcom/jy/platform/core/fastdfs/task/SynSingleFileDownloadWorker;
    //   35: aload_1
    //   36: iload 5
    //   38: aaload
    //   39: invokevirtual 43	com/jy/platform/core/fastdfs/task/SynSingleFileDownloadWorker:getFileInfo	(Ljava/lang/String;)[Lcom/jy/platform/core/fastdfs/common/NameValuePair;
    //   42: astore 6
    //   44: aload 6
    //   46: aload 6
    //   48: arraylength
    //   49: iconst_1
    //   50: isub
    //   51: aaload
    //   52: invokevirtual 47	com/jy/platform/core/fastdfs/common/NameValuePair:getValue	()Ljava/lang/String;
    //   55: astore 7
    //   57: aload 4
    //   59: iload 5
    //   61: new 53	org/zeroturnaround/zip/ByteSource
    //   64: dup
    //   65: aload 7
    //   67: getstatic 27	com/jy/platform/core/fastdfs/impl/FdfsApiImpl:synSingleFileDownloadWorker	Lcom/jy/platform/core/fastdfs/task/SynSingleFileDownloadWorker;
    //   70: aload_1
    //   71: iload 5
    //   73: aaload
    //   74: invokevirtual 55	com/jy/platform/core/fastdfs/task/SynSingleFileDownloadWorker:download	(Ljava/lang/String;)[B
    //   77: invokespecial 59	org/zeroturnaround/zip/ByteSource:<init>	(Ljava/lang/String;[B)V
    //   80: aastore
    //   81: goto +10 -> 91
    //   84: astore 6
    //   86: aload 6
    //   88: invokevirtual 62	com/jy/platform/core/fastdfs/exception/FastDfsException:printStackTrace	()V
    //   91: iinc 5 1
    //   94: iload 5
    //   96: aload_1
    //   97: arraylength
    //   98: if_icmplt -66 -> 32
    //   101: new 67	java/lang/StringBuilder
    //   104: dup
    //   105: new 69	java/io/File
    //   108: dup
    //   109: ldc 1
    //   111: ldc 71
    //   113: invokevirtual 73	java/lang/Class:getResource	(Ljava/lang/String;)Ljava/net/URL;
    //   116: invokevirtual 79	java/net/URL:getFile	()Ljava/lang/String;
    //   119: invokespecial 84	java/io/File:<init>	(Ljava/lang/String;)V
    //   122: invokevirtual 87	java/io/File:getCanonicalPath	()Ljava/lang/String;
    //   125: invokestatic 90	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   128: invokespecial 96	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   131: getstatic 97	java/io/File:separator	Ljava/lang/String;
    //   134: invokevirtual 101	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   137: ldc 105
    //   139: invokevirtual 101	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   142: invokevirtual 107	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   145: astore 5
    //   147: new 69	java/io/File
    //   150: dup
    //   151: aload 5
    //   153: invokespecial 84	java/io/File:<init>	(Ljava/lang/String;)V
    //   156: astore 6
    //   158: aload 6
    //   160: invokevirtual 110	java/io/File:exists	()Z
    //   163: ifne +9 -> 172
    //   166: aload 6
    //   168: invokevirtual 114	java/io/File:mkdir	()Z
    //   171: pop
    //   172: aconst_null
    //   173: astore 7
    //   175: aconst_null
    //   176: astore 8
    //   178: new 67	java/lang/StringBuilder
    //   181: dup
    //   182: invokestatic 117	java/util/UUID:randomUUID	()Ljava/util/UUID;
    //   185: invokevirtual 123	java/util/UUID:toString	()Ljava/lang/String;
    //   188: invokestatic 90	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   191: invokespecial 96	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   194: ldc 124
    //   196: invokevirtual 101	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   199: invokevirtual 107	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   202: astore 9
    //   204: new 69	java/io/File
    //   207: dup
    //   208: new 67	java/lang/StringBuilder
    //   211: dup
    //   212: aload 5
    //   214: invokestatic 90	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   217: invokespecial 96	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   220: ldc 126
    //   222: invokestatic 128	java/lang/System:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   225: invokevirtual 101	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   228: aload 9
    //   230: invokevirtual 101	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   233: invokevirtual 107	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   236: invokespecial 84	java/io/File:<init>	(Ljava/lang/String;)V
    //   239: astore 7
    //   241: aload_0
    //   242: aload_2
    //   243: aload_3
    //   244: aload 9
    //   246: invokespecial 134	com/jy/platform/core/fastdfs/impl/FdfsApiImpl:setFileDownloadHeader	(Ljavax/servlet/http/HttpServletRequest;Ljavax/servlet/http/HttpServletResponse;Ljava/lang/String;)V
    //   249: aload 4
    //   251: aload 7
    //   253: invokestatic 138	org/zeroturnaround/zip/ZipUtil:pack	([Lorg/zeroturnaround/zip/ZipEntrySource;Ljava/io/File;)V
    //   256: aload_3
    //   257: invokeinterface 143 1 0
    //   262: astore 8
    //   264: aload 7
    //   266: aload 8
    //   268: invokestatic 149	org/zeroturnaround/zip/commons/FileUtils:copy	(Ljava/io/File;Ljava/io/OutputStream;)V
    //   271: goto +87 -> 358
    //   274: astore 9
    //   276: getstatic 25	com/jy/platform/core/fastdfs/impl/FdfsApiImpl:logger	Lorg/slf4j/Logger;
    //   279: ldc 155
    //   281: aload 9
    //   283: invokeinterface 157 3 0
    //   288: aload 8
    //   290: ifnull +18 -> 308
    //   293: aload 8
    //   295: invokevirtual 163	java/io/OutputStream:close	()V
    //   298: goto +10 -> 308
    //   301: astore 11
    //   303: aload 11
    //   305: invokevirtual 168	java/io/IOException:printStackTrace	()V
    //   308: aload 7
    //   310: ifnull +89 -> 399
    //   313: aload 7
    //   315: invokevirtual 171	java/io/File:delete	()Z
    //   318: pop
    //   319: goto +80 -> 399
    //   322: astore 10
    //   324: aload 8
    //   326: ifnull +18 -> 344
    //   329: aload 8
    //   331: invokevirtual 163	java/io/OutputStream:close	()V
    //   334: goto +10 -> 344
    //   337: astore 11
    //   339: aload 11
    //   341: invokevirtual 168	java/io/IOException:printStackTrace	()V
    //   344: aload 7
    //   346: ifnull +9 -> 355
    //   349: aload 7
    //   351: invokevirtual 171	java/io/File:delete	()Z
    //   354: pop
    //   355: aload 10
    //   357: athrow
    //   358: aload 8
    //   360: ifnull +18 -> 378
    //   363: aload 8
    //   365: invokevirtual 163	java/io/OutputStream:close	()V
    //   368: goto +10 -> 378
    //   371: astore 11
    //   373: aload 11
    //   375: invokevirtual 168	java/io/IOException:printStackTrace	()V
    //   378: aload 7
    //   380: ifnull +19 -> 399
    //   383: aload 7
    //   385: invokevirtual 171	java/io/File:delete	()Z
    //   388: pop
    //   389: goto +10 -> 399
    //   392: astore 4
    //   394: aload 4
    //   396: invokevirtual 174	java/lang/Exception:printStackTrace	()V
    //   399: return
    //
    // Exception table:
    //   from	to	target	type
    //   32	81	84	com/jy/platform/core/fastdfs/exception/FastDfsException
    //   178	271	274	java/lang/Exception
    //   293	298	301	java/io/IOException
    //   178	288	322	finally
    //   329	334	337	java/io/IOException
    //   363	368	371	java/io/IOException
    //   0	389	392	java/lang/Exception
  }

  private void setFileDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName)
  {
    String encodedfileName = null;

    encodedfileName = fileName.trim().replaceAll(" ", "_");
    String agent = request.getHeader("User-Agent");
    boolean isMSIE = (agent != null) && (agent.toUpperCase().indexOf("MSIE") != -1);
    try {
      if (isMSIE) {
    	  encodedfileName = URLEncoder.encode(fileName, "UTF-8");
      }else{
    	  encodedfileName = new String(fileName.getBytes(), "ISO-8859-1");
      }
    }
    catch (UnsupportedEncodingException e)
    {
      e.printStackTrace();
    }
    label93: response.setHeader("Content-Disposition", "attachment; filename=\"" + 
      encodedfileName + "\"");
  }
}