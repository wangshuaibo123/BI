package com.jy.platform.tuomin.tmworker;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jy.platform.tuomin.Configuration;


/**
 * 身份证，第13位到第16位(.....日期序号...)；
 * @author dell
 *
 */
public class IDNumberTMWorker {
	
	static Logger logger = LoggerFactory.getLogger(IDNumberTMWorker.class);
	
	private static Map<String, String> map31 = null;
	private static Map<String, String> map30 = null;
	private static Map<String, String> map29 = null;
	private static Map<String, String> map28 = null;
	
	private static final int offset = Configuration.getOffset();
	
	private static final int randomSeed = 999999;

	private static final String STORE_FILE = Configuration.getStorePath() + 
			File.separator + "psnid.data";

	@SuppressWarnings("unchecked")
	private static void init() {
		List<SortedItem> list = null;
		if (new File(STORE_FILE).exists()) {
			logger.info("身份证处理程序从文件中加载随机数组");
			list = (List<SortedItem>) loadFromFile();
		} else {
			logger.info("身份证处理程序创建随机数组");
			// 如果没有下面这行代码，执行又是会报错“Comparison method violates its general contract”
			System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");

			list = new ArrayList<SortedItem>();
			Set<Integer> set = new HashSet<Integer>();
			Random rd = new Random();
			for (int i=1; i<=31; i++) {
				//for (int j=1; j<=99; ) { // 开始人为身份证号的序号位不会出现00，后来发现生产数据中有
				for (int j=0; j<=99; ) {
					int random = rd.nextInt(randomSeed);
					if (!set.contains(random)) {
						list.add(new SortedItem(i*100+j, random));
						j++;
					}
				}
			}
			
			// 持久化随机数组
			saveToFile(list);
		}

		// 31
		SortedItem[] numArray = new SortedItem[list.size()];
		numArray = list.toArray(numArray);
		Arrays.sort(numArray);
		map31 = new HashMap<String, String>();
		for (int i=0; i<numArray.length; i++) {
			map31.put((10000 + numArray[i].value + "").substring(1), (10000 + numArray[(i+offset)%numArray.length].value + "").substring(1));
			//System.out.println((10000 + numArray[i].value + "").substring(1) + "=" + (10000 + numArray[(i+offset)%numArray.length].value + "").substring(1));
		}
		
		// 30
		Iterator<SortedItem> it = list.iterator();
		while (it.hasNext()) {
			SortedItem si = it.next();
			if (si.value >= 3100) {
				it.remove();
			}
		}
		numArray = new SortedItem[list.size()];
		numArray = list.toArray(numArray);
		Arrays.sort(numArray);
		map30 = new HashMap<String, String>();
		for (int i=0; i<numArray.length; i++) {
			map30.put((10000 + numArray[i].value + "").substring(1), (10000 + numArray[(i+offset)%numArray.length].value + "").substring(1));
			//System.out.println((10000 + numArray[i].value + "").substring(1) + "=" + (10000 + numArray[(i+offset)%numArray.length].value + "").substring(1));
		}

		// 29
		it = list.iterator();
		while (it.hasNext()) {
			SortedItem si = it.next();
			if (si.value >= 3000) {
				it.remove();
			}
		}
		numArray = new SortedItem[list.size()];
		numArray = list.toArray(numArray);
		Arrays.sort(numArray);
		map29 = new HashMap<String, String>();
		for (int i=0; i<numArray.length; i++) {
			map29.put((10000 + numArray[i].value + "").substring(1), (10000 + numArray[(i+offset)%numArray.length].value + "").substring(1));
			//System.out.println((10000 + numArray[i].value + "").substring(1) + "=" + (10000 + numArray[(i+offset)%numArray.length].value + "").substring(1));
		}

		// 28
		it = list.iterator();
		while (it.hasNext()) {
			SortedItem si = it.next();
			if (si.value >= 2900) {
				it.remove();
			}
		}
		numArray = new SortedItem[list.size()];
		numArray = list.toArray(numArray);
		Arrays.sort(numArray);
		map28 = new HashMap<String, String>();
		for (int i=0; i<numArray.length; i++) {
			map28.put((10000 + numArray[i].value + "").substring(1), (10000 + numArray[(i+offset)%numArray.length].value + "").substring(1));
			//System.out.println((10000 + numArray[i].value + "").substring(1) + "=" + (10000 + numArray[(i+offset)%numArray.length].value + "").substring(1));
		}
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

	/**
	 * 获取脱敏后的新号码
	 * @param oldValue 原号码
	 * @return 脱敏后的新号码
	 */
	public static String getNewValue(String oldValue) {
		if (oldValue == null || oldValue.length() < 6) {
			return "****";
		}
		
		String newValue;
		switch (oldValue.length()) {
		case 18:
			newValue = getNewValue18(oldValue);
			// 18位的号码，为了有效性，需要计算最后一位校验码
			try {
				newValue = calcCheckDigit(newValue);
			} catch (Exception e) {
				// 如果计算异常，最后一位设成星号
				newValue = newValue.substring(0, 17) + "*";
			}
			break;
		case 15:
			newValue = getNewValue15(oldValue);
			break;
		default:
			String head = oldValue.substring(0, oldValue.length() - 6);
			String tail = oldValue.substring(oldValue.length() - 2);
			newValue = head + "****" + tail;
		}

		return newValue;
	}
	
	private static String getNewValue18(String oldValue) {
		String head = oldValue.substring(0, 12);
		String ymdn = oldValue.substring(6, 16);
		String tail = oldValue.substring(16);
		String mapping = getMapping(ymdn);
		return head + mapping + tail;
	}

	private static String getNewValue15(String oldValue) {
/*		String head = oldValue.substring(0, 6);
		String tail = oldValue.substring(6);
		String id18 = head + "19" + tail + oldValue.charAt(11);
		logger.debug("15位：{}={}", oldValue, id18);
		
		return getNewValue18(id18);*/
		String head = oldValue.substring(0, 10);
		String tail = oldValue.substring(14);
		String id18 = head + "****" + tail;
		logger.debug("15位：{}={}", oldValue, id18);
		return id18;
	}

	private static String getMapping(String ymdn) {
		String ym = ymdn.substring(0, 6);
		String dn = ymdn.substring(6);
		String mapping;
		int monthDays = 0;
		try {
			monthDays = getMonthDays(ym);
		} catch (Exception e) {
			logger.error("获取" + ym + "的天数出错", e);
		}
		switch (monthDays) {
		case 31:
			mapping = map31.get(dn);
			break;
		case 30:
			mapping = map30.get(dn);
			break;
		case 29:
			mapping = map29.get(dn);
			break;
		case 28:
			mapping = map28.get(dn);
			break;
		default:
			mapping = "****";
		}
		return mapping;
	}
	
	/**
	 * 获取指定月份的天数
	 * @return
	 */
	private static int getMonthDays(String ym) {
		int y = Integer.parseInt(ym.substring(0, 4));
		int m = Integer.parseInt(ym.substring(4));
		//System.out.println(y + "/" + m);
		if (1 == m ||
				3 == m ||
				5 == m ||
				7 == m ||
				8 == m ||
				10 == m ||
				12 == m) {
			return 31;
		} else if (4 == m ||
				6 == m ||
				9 == m ||
				11 == m) {
			return 30;
		} else {
			if (y % 4 == 0 && y % 100 != 0 || y % 400 == 0) {
				return 29;
			} else {
				return 28;
			}
		}
	}

	/**
	 * 计算身份证号的最后一位校验码(网上有公共算法)
	 * @param id 身份证号（18位长，最后一位是无效的）
	 * @return 合法的身份证号
	 */
	private static String calcCheckDigit(String id) {
		if (id == null || id.length() != 18) {
			return id;
		}
		int[] wi = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
		String[] xi = {"1","0","X","9","8","7","6","5","4","3","2"};
		int sum = 0;
		for (int i=0; i<=16; i++) {
			sum += Integer.parseInt(String.valueOf(id.charAt(i))) * wi[i];
		}
		
		return id.substring(0, 17) + xi[sum % 11];
	}

	private static class SortedItem implements Serializable, Comparable<SortedItem> {
		private static final long serialVersionUID = 1L;
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
		
		System.out.println(Integer.parseInt(String.valueOf("123".charAt(1)))*10);
		System.out.println(calcCheckDigit("110104196207230019"));
		String a = "a";
		String b = "a";
		if (a==b) {
			return;
		}
		System.out.println(getMonthDays("201701"));
		System.out.println(getMonthDays("201702"));
		System.out.println(getMonthDays("201703"));
		System.out.println(getMonthDays("201704"));
		System.out.println(getMonthDays("201705"));
		System.out.println(getMonthDays("201706"));
		System.out.println(getMonthDays("201707"));
		System.out.println(getMonthDays("201708"));
		System.out.println(getMonthDays("201709"));
		System.out.println(getMonthDays("201710"));
		System.out.println(getMonthDays("201711"));
		System.out.println(getMonthDays("201712"));
		System.out.println(getMonthDays("201602"));
		System.out.println(getMonthDays("201802"));
		System.out.println(getMonthDays("200002"));
		System.out.println(getMonthDays("210002"));
		System.out.println(getNewValue("123456789012345"));
		System.out.println(getNewValue("123456789012345678"));
		System.out.println(getNewValue("110110201704115678"));
		System.out.println(getNewValue("1234567890"));
		String ymdn = "2017030405";
		String y = ymdn.substring(0, 4);
		String m = ymdn.substring(4, 6);
		String dn = ymdn.substring(6);
		System.out.println(y + "/" + m + "/" + dn);
		System.out.println(getNewValue15("110110780308020"));
		System.out.println(getNewValue15("532201170117300"));
		System.out.println(getNewValue15("500227170117053"));
		System.out.println(getNewValue15("430381170117096"));
		System.out.println(getNewValue15("532401170117031"));
		System.out.println(getNewValue15("410927170117068"));
		System.out.println(getNewValue15("410821170117546"));
		System.out.println(getNewValue15("454411170117111"));
		System.out.println(getNewValue15("460026170117001"));
		System.out.println(getNewValue15("510231170117001"));
		System.out.println(getNewValue15("433002170117231"));
		System.out.println(getNewValue15("500239170117765"));
		System.out.println(getNewValue15("420203170117255"));
		System.out.println(getNewValue15("440520170117735"));
		System.out.println(getNewValue15("362121170117121"));
		System.out.println(getNewValue15("45441a170117111"));

	}
}
