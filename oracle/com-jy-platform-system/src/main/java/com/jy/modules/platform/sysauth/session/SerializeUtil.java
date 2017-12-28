package com.jy.modules.platform.sysauth.session;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * @ClassName: SerializeUtil 
 * @Description: 序列化工具
 * @author luoyr
 * @date 2015年6月2日 下午5:00:08 
 *
 */
public class SerializeUtil {
	/**
	 * @Title: serialize 
	 * @Description: 序列化对象
	 * @param  value 将要序列化的对象
	 * @return byte[]    返回类型 
	 * @throws
	 */
	public static byte[] serialize(Object value) {
        if (value == null) {
            throw new NullPointerException("Can't serialize null");
        }
        byte[] rv = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(value);
            os.close();
            bos.close();
            rv = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(os);
            close(bos);
        }
        return rv;
    }
	/**
	 * @Title: deserialize 
	 * @Description: 反序列化
	 * @param  in
	 * @return Object    返回类型 
	 * @throws
	 */
    public static Object deserialize(byte[] in) {
        return deserialize(in, Object.class);
    }
    /**
	 * @Title: deserialize 
	 * @Description: 反序列化
	 * @param  in 对象字节数组
	 * @param  Class<T> 对象类型
	 * @return Object    返回类型 
	 * @throws
	 */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(byte[] in, Class<T> requiredType) {
        Object rv = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                rv = is.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(is);
            close(bis);
        }
        return (T) rv;
    }
    /**
     * @Title: close 
     * @Description: 释放资源 
     * @param closeable     
     * @return void    返回类型 
     */
    private static void close(Closeable closeable) {
        if (closeable != null)
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
