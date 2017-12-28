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
 * 银行卡号，将后6位脱敏。
 * @author dell
 *
 */
public class CardNumberTMWorker {
	
	private static Logger logger = LoggerFactory.getLogger(CardNumberTMWorker.class);

	/**
	 * “新旧号码”的映射，因为要处理6为，拆成两个，可以减少映射数据量
	 */
	private static Map<String, String> numMap1 = null;
	private static Map<String, String> numMap2 = null;

	/**
	 * 持久化文件，避免中间出现异常，再次启动后，前后处理的规则不一致
	 */
	private static final String STORE_FILE = Configuration.getStorePath() + 
			File.separator + "card.data";

	/**
	 * 数组长度
	 */
	private static final int SIZE = 1000;
	
	private static final int randomSeed = 999999;

	/**
	 * 映射偏移量
	 */
	private static int offset = Configuration.getOffset() % SIZE == 0 ? 1 : Configuration.getOffset() % SIZE;


	public static String getNewValue(String oldValue) {
		if (oldValue == null || oldValue.length() < 6) {
			return "****";
		}
		
		String head = oldValue.substring(0, oldValue.length() - 6);

		String tail1 = oldValue.substring(oldValue.length() - 6, oldValue.length() - 3);
		String newTail1 = numMap1.get(tail1);
		if (newTail1 == null) {
			newTail1 = "***";
		}

		String tail2 = oldValue.substring(oldValue.length() - 3);
		String newTail2 = numMap2.get(tail2);
		if (newTail2 == null) {
			newTail2 = "***";
		}

		return head + newTail1 + newTail2;
	}

	private static void init() {
		int[] numArray = null;
		if (new File(STORE_FILE).exists()) {
			logger.info("卡号处理程序从文件中加载随机数组");
			numArray = (int[]) loadFromFile();
		} else {
			logger.info("卡号处理程序创建随机数组");
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
		numMap1 = new HashMap<String, String>();
		for (int i=0; i<SIZE; i++) {
			numMap1.put((SIZE + numArray[i] + "").substring(1), (SIZE + numArray[(i+offset)%SIZE] + "").substring(1));
			//System.out.println((SIZE + numArray[i] + "").substring(1)+ "=" + (SIZE + numArray[(i+offset)%SIZE] + "").substring(1));
		}
		numMap2 = new HashMap<String, String>();
		for (int i=0; i<SIZE; i++) {
			numMap2.put((SIZE + numArray[i] + "").substring(1), (SIZE + numArray[(i+offset+1)%SIZE] + "").substring(1));
			//System.out.println((SIZE + numArray[i] + "").substring(1)+ "=" + (SIZE + numArray[(i+offset)%SIZE] + "").substring(1));
		}
		
		logger.info("银行卡号脱敏类初始化完毕");
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

	public static void main(String[] args) {
		System.out.println("null=" + getNewValue(null));
		System.out.println("''=" + getNewValue(""));
		System.out.println("' '=" + getNewValue(" "));
		System.out.println("12345=" + getNewValue("12345"));
		System.out.println("123456=" + getNewValue("123456"));
		System.out.println("1234567=" + getNewValue("1234567"));
		System.out.println("1234567822345678=" + getNewValue("1234567822345678"));
		System.out.println("12345678901234abcdef=" + getNewValue("12345678901234abcdef"));
	}
}
