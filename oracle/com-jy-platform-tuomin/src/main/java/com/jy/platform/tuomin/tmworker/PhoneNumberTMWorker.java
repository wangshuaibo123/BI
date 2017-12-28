package com.jy.platform.tuomin.tmworker;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jy.platform.tuomin.Configuration;

/**
 * 固话，脱敏后四位；（因为部分客户的固话留的是手机号）
 * @author dell
 *
 */
public class PhoneNumberTMWorker {
	
	private static Logger logger = LoggerFactory.getLogger(PhoneNumberTMWorker.class);

	/**
	 * “新旧号码”的映射
	 */
	private static Map<String, String> numMap = null; 
	
	/**
	 * 持久化文件，避免中间出现异常，再次启动后，前后处理的规则不一致
	 */
	private static final String STORE_FILE = Configuration.getStorePath() + 
			File.separator + "phone.data";

	/**
	 * 数组长度，因为要脱敏4位，所以需要长度为10000
	 */
	private static final int SIZE = 10000;
	
	private static final int randomSeed = 999999;
	
	/**
	 * 映射偏移量
	 */
	private static int offset = Configuration.getOffset() % SIZE == 0 ? 1 : Configuration.getOffset() % SIZE;

	/**
	 * 固话，脱敏后四位；（因为部分客户的固话留的是手机号）
	 * @param oldValue 原始固话号码
	 * @return 脱敏后的固话号码
	 */
	public static String getNewValue(String oldValue) {
		// 如果为空或者长度小于4，返回空字符串
		if (oldValue == null || oldValue.length() < 4) {
			return "****";
		}
		
		// 获取后四位的映射值
		String head =  oldValue.substring(0, oldValue.length() - 4);
		String tail = oldValue.substring(oldValue.length() - 4);
		String newValue = numMap.get(tail);
		if (newValue != null) {
			return head + newValue;
		} else {
			return head + "****";
		}
	}

	/**
	 * 初始化，如果有持久化文件，则从文件中加载数据；没有的话，现在生成
	 */
	private static void init() {
		int[] numArray = null;
		if (new File(STORE_FILE).exists()) {
			logger.info("从文件中加载随机数组");
			numArray = (int[]) loadFromFile();
		} else {
			logger.info("创建随机数组");
			// 如果没有下面这行代码，执行又是会报错“Comparison method violates its general contract”
			System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
			
			SortedItem[] si = new SortedItem[SIZE];
			Random rd = new Random();
			Set<Integer> set = new HashSet<Integer>();
			for (int i=0; i<SIZE; ) {
				int random = rd.nextInt(randomSeed);
				if (!set.contains(random)) {
					si[i] = new SortedItem(i, random);
					i++;
				}
			}
			// 对随机数数组进行排序
			Arrays.sort(si);
			numArray = new int[SIZE];
			for (int i=0; i<SIZE; i++) {
				numArray[i] = si[i].value;
			}
			saveToFile(numArray);
		}

		numMap = new HashMap<String, String>();
		for (int i=0; i<SIZE; i++) {
			numMap.put((SIZE + numArray[i] + "").substring(1), (SIZE + numArray[(i+offset)%SIZE] + "").substring(1));
			//System.out.println((SIZE + numArray[i] + "").substring(1)+ "=" + (SIZE + numArray[(i+offset)%SIZE] + "").substring(1));
		}
		logger.info("电话号码脱敏类初始化完毕");
	}
	
	static {
		init();
	}

	/**
	 * 把打乱的数进行组持久化
	 * @param o
	 */
	private static void saveToFile(Object o) {
		try {
			FileOutputStream fos = new FileOutputStream(STORE_FILE);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(o);
			oos.flush();
			oos.close();
			bos.close();
			fos.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 从持久化文件中加载数组
	 * @return
	 */
	private static Object loadFromFile() {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(STORE_FILE);
			bis = new BufferedInputStream(fis);
			ois = new ObjectInputStream(bis);
			Object o = ois.readObject();
			ois.close();
			bis.close();
			fis.close();
			return o;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static class SortedItem implements Comparable<SortedItem> {
		final int value;
		final int sortNum;
		
		SortedItem(int value, int sortNum) {
			this.value = value;
			this.sortNum = sortNum;
		}

		@Override
		public int compareTo(SortedItem o) {
			int ret = this.sortNum - o.sortNum;
			if (ret > 0) {
				return 1;
			} else if (ret == 0) {
				return 0;
			} else {
				return -1;
			}
		}
	}


	/**
	 * 打乱顺序
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("123=" + getNewValue("123"));
		System.out.println("1234=" + getNewValue("1234"));
		System.out.println("12345=" + getNewValue("12345"));
		System.out.println("1234567=" + getNewValue("1234567"));
		System.out.println("12345678=" + getNewValue("12345678"));
		System.out.println("123-12345678=" + getNewValue("123-12345678"));
		System.out.println("1234-12345678=" + getNewValue("1234-12345678"));
		System.out.println("1234-1234567=" + getNewValue("1234-1234567"));
		System.out.println("13812345678=" + getNewValue("13812345678"));
	}
}
