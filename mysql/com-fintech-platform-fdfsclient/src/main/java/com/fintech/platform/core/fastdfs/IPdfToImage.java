package com.fintech.platform.core.fastdfs;

import java.io.File;
public interface IPdfToImage {
	/**
	 * 
	 * @param tempFile pdf文件
	 * @return
	 */
	public byte[] pdfToImage(byte[]  tempFile) throws Exception;
}
