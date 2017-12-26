package com.fintech.modules.aliyun.service;

public abstract interface IPdfToImage{
	
  public abstract byte[] pdfToImage(byte[] paramArrayOfByte)  throws Exception;
  
}