package com.jy.modules.tools.pwd;

import java.util.LinkedList;
import java.util.Random;

public class GenSeed {
	// 大写字母
	public static String[] up_chars = new String[] { "A", "B", "C", "D", "E",
			"F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W", "X", "Y", "Z" };
	// 小写字母
	public static String[] lower_chars = new String[] { "a", "b", "c", "d",
			"e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
			"r", "s", "t", "u", "v", "w", "x", "y", "z" };
	// 数字
	public static String[] digit = new String[] { "0","1", "2", "3", "4", "5", "6",
			"7", "8", "9" };

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(generatePwd());
	}

	public static String genPwd(int count) {
		StringBuffer buf = new StringBuffer(
				"a,b,c,d,e,f,g,h,i,g,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z");
		buf.append(",A,B,C,D,E,F,G,H,I,G,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z");
		buf.append(",~,@,#,$,%,^,&,*,(,),_,+,|,`,.");
		buf.append(",1,2,3,4,5,6,7,8,9,0");
		String[] arr = buf.toString().split(",");
		String pwd = getPswd(arr, count);
		System.out.println("pwd:" + pwd);

		return pwd;
	}

	public static String getPswd(String[] arr, int cunt) {
		StringBuffer b = new StringBuffer();
		int k;
		for (int i = 0; i < cunt; i++) {
			k = new Random().nextInt();
			b.append(String.valueOf(arr[Math.abs(k % 76)]));
		}

		return b.toString();
	}

	/**
	 * 生成8位密码，由大小写字母+数字组成
	 */
	public static String generatePwd() {
		Random random = new Random();

		// 生成大小写字母，数字的随机个数
		int upCaseNum = random.nextInt(6) + 1;// 生成1-6之间的随机数
		int lowerCaseNum = random.nextInt(8 - upCaseNum) + 1;
		int digitNum = 0;
		if (upCaseNum + lowerCaseNum == 8) {
			digitNum = 1;
			if (random.nextInt(2) % 2 == 0 && upCaseNum != 1)
				upCaseNum--;
			else if (lowerCaseNum != 1)
				lowerCaseNum--;
		} else {
			digitNum = 8 - upCaseNum - lowerCaseNum;
		}

		LinkedList<String> ll = new LinkedList<String>();
		for (int i = 0; i < upCaseNum; i++) {
			ll.addLast("up");
		}
		for (int i = 0; i < lowerCaseNum; i++) {
			ll.add("lower");
		}
		for (int i = 0; i < digitNum; i++) {
			ll.add("digit");
		}

		StringBuffer pwd = new StringBuffer();
		int len = ll.size();
		while (ll.size() > 0) {
			int index = random.nextInt(len);
			String type = ll.get(index);
			if ("up".equals(type)) {
				pwd.append(up_chars[random.nextInt(26)]);
			} else if ("lower".equals(type)) {
				pwd.append(lower_chars[random.nextInt(26)]);
			} else {
				pwd.append(digit[random.nextInt(10)]);
			}
			ll.remove(index);
			len--;
		}

		return pwd.toString();
	}
}
