package com.fintech.platform.tools.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 打包下载文件工具类
 * @author
 *
 */
public class ZipUtil {

	/**
	 * 
	 * @param files
	 *            你想要打包的所有文件（也可以是文件夹，但文件夹结构不会保留，而是所有文件统一打包）
	 * @param resultZipFile
	 *            你要生成的zip文件（如果没有则会自动生成,存在的则会覆盖）
	 * @return
	 * @throws Exception
	 */
	public static boolean compressFiles(List<File> files, File resultZipFile)
			throws Exception {
		ZipOutputStream zipOut = null;
		FileOutputStream fous = null;
		try {

			if (!resultZipFile.exists()) {
				resultZipFile.createNewFile();
			}

			// 创建文件输出流
			fous = new FileOutputStream(resultZipFile);
			zipOut = new ZipOutputStream(fous);

			// 对文件列表打包
			zipFile(files, zipOut);
			zipOut.flush();
			zipOut.close();
			fous.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (zipOut != null) {
				zipOut.close();
			}
			if (fous != null) {
				fous.close();
			}
		}
		return true;
	}

	/**
	 * 对文件列表进行打包
	 * 
	 * @param files
	 * @param outputStream
	 */
	public static void zipFile(List<File> files, ZipOutputStream outputStream)
			throws Exception {
		int size = files.size();
		for (int i = 0; i < size; i++) {
			File file = (File) files.get(i);
			zipFile(file, outputStream);
		}
	}

	/**
	 * 根据输入的文件与输出流对文件进行打包
	 * 
	 * @param inputFile
	 * @param ouputStream
	 * @see 目前只打包文件（包括文件夹下的文件），对文件夹结构不做处理，统一放到一起打包
	 */
	public static void zipFile(File inputFile, ZipOutputStream ouputStream)
			throws Exception {
		FileInputStream IN = null;
		BufferedInputStream bins = null;
		try {
			if (inputFile.exists()) {
				if (inputFile.isFile()) {
					IN = new FileInputStream(inputFile);
					bins = new BufferedInputStream(IN, 512);
					ZipEntry entry = new ZipEntry(inputFile.getName());
					ouputStream.putNextEntry(entry);
					// 向压缩文件中输出数据
					int nNumber;
					byte[] buffer = new byte[512];
					while ((nNumber = bins.read(buffer)) != -1) {
						ouputStream.write(buffer, 0, nNumber);
					}

				} else {
					// 文件夹则循环内容进行打包
					try {
						File[] files = inputFile.listFiles();
						for (int i = 0; i < files.length; i++) {
							zipFile(files[i], ouputStream);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭创建的流对象
			if (bins != null) {
				bins.close();
			}
			if (IN != null) {
				IN.close();
			}

		}
	}

}
