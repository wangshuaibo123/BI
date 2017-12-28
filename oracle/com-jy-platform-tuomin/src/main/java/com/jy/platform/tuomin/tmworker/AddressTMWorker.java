package com.jy.platform.tuomin.tmworker;


/**
 * 地址脱敏类
 * 1、贷款系统中有的地址是“省市区”+“家庭住址”，这种类型的可以把“家庭住址”字段清空即可
 * 2、理财系统中有的地址是“省市区家庭住址”在一个字段中，这种要进行处理（1-*、2-截取）
 * 截取的方式难度较大，目前采取*处理方式
 * @author dell
 *
 */
public class AddressTMWorker {

	/**
	 * 把地址中的字母数字用*逐个替换，多个连续的字母数字表示成一个*
	 * @param oldValue
	 * @return
	 */
	public static String getNewValue(String oldValue) {
		if (oldValue == null || oldValue.isEmpty()) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		char preChar = 0;
		for (int i=0; i<oldValue.length(); i++) {
			char c = oldValue.charAt(i);
			// 非字母数字部分，保留
			if (c > 127 || c < 32) {
				//System.out.println(c + "=" + (int)c);
				sb.append(c);
				preChar = c;
			} else {
				if (preChar != '*') {
					sb.append("*");
					preChar = '*';
				}
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(getNewValue("北京市朝阳区朝阳门SOHO1202"));
		System.out.println(getNewValue("北京市朝阳区银河SOHO A座1201"));
	}
}
