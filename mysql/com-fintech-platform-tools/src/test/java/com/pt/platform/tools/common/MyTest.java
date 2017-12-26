package com.pt.platform.tools.common;

import com.fintech.platform.tools.pwdhash.util.AESUtils;

public class MyTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String key = "abcdnnnnnn123456";
		String newpwd = "i/urUHvLlepO+NWthBU7AA==";//密码123456
		try {
			String newpasswordString = AESUtils.aesDecodeStr(newpwd.trim(), key.trim());
			System.out.println("===newpasswordString=:"+newpasswordString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
