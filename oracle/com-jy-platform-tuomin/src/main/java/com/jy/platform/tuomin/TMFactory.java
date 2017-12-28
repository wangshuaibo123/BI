package com.jy.platform.tuomin;

import com.jy.platform.tuomin.tmworker.AddressTMWorker;
import com.jy.platform.tuomin.tmworker.CardNumberTMWorker;
import com.jy.platform.tuomin.tmworker.IDNumberTMWorker;
import com.jy.platform.tuomin.tmworker.MobileNumberTMWorker;
import com.jy.platform.tuomin.tmworker.PersonNameTMWorker;
import com.jy.platform.tuomin.tmworker.PhoneNumberTMWorker;

/**
1、姓名
2、手机号，脱敏后四位；
3、地址，省市区不能脱敏，详细地址可以脱敏；
4、身份证，第13位到第16位；
5、邮箱，全部脱敏；
6、固话，脱敏后四位；（因为部分客户的固话留的是手机号）
7、银行卡号，将后6位脱敏。
 * @author dell
 *
 */
public class TMFactory {

	public static Object getNewValue(String oldValue, String type) {
		if ("清空".equals(type)) {
			return "";
		} else if ("姓名".equals(type)) {
			return PersonNameTMWorker.getNewValue(oldValue);
		} else if ("固话".equals(type)) {
			return PhoneNumberTMWorker.getNewValue(oldValue);
		} else if ("手机号".equals(type)) {
			return MobileNumberTMWorker.getNewValue(oldValue);
		}  else if ("地址".equals(type)) {
			return AddressTMWorker.getNewValue(oldValue);
		}  else if ("银行卡".equals(type)) {
			return CardNumberTMWorker.getNewValue(oldValue);
		}  else if ("身份证".equals(type)) {
			return IDNumberTMWorker.getNewValue(oldValue);
		} else {
			throw new RuntimeException("不支持的类型[" + type + "]");
		}

	}
}
