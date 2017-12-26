package com.fintech.platform.core.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * 序列化 redis缓存对象
 * @author
 *
 */
public class SerializeUtil implements Serializable{
	private static final long serialVersionUID = 5029026454933644272L;

	public static byte[] serialise(Object obj){
		ObjectOutputStream os=null;
		ByteArrayOutputStream bos=null;
		
		try {
			bos=new ByteArrayOutputStream();
			os=new ObjectOutputStream(bos);
			os.writeObject(obj);
			byte[] bytes=bos.toByteArray();
			return bytes;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object unserialize(byte[] bytes){
		ByteArrayInputStream bin=null;
		
		try {
			bin=new ByteArrayInputStream(bytes);
			ObjectInputStream in=new ObjectInputStream(bin);
			return in.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
