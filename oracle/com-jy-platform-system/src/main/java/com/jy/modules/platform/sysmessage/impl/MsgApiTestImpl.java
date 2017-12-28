package com.jy.modules.platform.sysmessage.impl;

import java.util.Date;
import java.util.List;

import com.jy.modules.platform.sysmessage.util.CacheUtil;
import com.jy.platform.api.msg.Msg;
import com.jy.platform.api.msg.MsgAPI;

/**
 * @Description:消息服务测试类，后期删除
 * @author zhanglin
 * @date 2014年11月18日 上午9:30:50
 */
public class MsgApiTestImpl extends MsgAPImpl {
	
	public static void main(String[] args) {
		try {
			CacheUtil.printRedisData(pool, "*");
			//CacheUtil.clear(pool);
			//CacheUtil.printRedisData(pool, "*");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void add()throws Exception{
		MsgAPI api = new MsgApiAddImpl();
		System.out.println("全局消息：");
		for (int i = 0; i < 10; i++) {
			Msg msg = new Msg();
			msg.setStartTime(new Date());
			msg.setState("0");
			msg.setTitle("golbal_"+i);
			msg.setConent("conent"+i);
			msg.setEndTime(new Date());
			msg.setUrgentFlag("0");
			msg.setCreator("admin_"+i);
			msg.setUrl("www.baidu.com"+i);
			msg.setMsgType("0");
			msg.setSysFlag("sys"+i);
			try {
				api.addMsg(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("专有消息：");
		for (int i = 0; i < 10; i++) {
			Msg msg = new Msg();
			msg.setStartTime(new Date());
			msg.setState("0");
			msg.setTitle("specfic_"+i);
			msg.setConent("conent"+i);
			msg.setEndTime(new Date());
			msg.setUrgentFlag("0");
			msg.setCreator("admin_"+i);
			msg.setUrl("www.qq.com"+i);
			msg.setMsgType("1");
			msg.setSysFlag("sys"+i);
			msg.setUserIds("1,2,3");
			try {
				api.addMsg(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private void getMsg()throws Exception{
		MsgAPI api = new MsgApiGetImpl();
		try {
			List<Msg> data1 = api.getMsg("1", "sys1", "0");
			for (Msg msg: data1) {
				System.out.println("全局消息内容："+msg.toString());
			}
			
			List<Msg> data2 = api.getMsg("1", "sys1", "1");
			for (Msg msg: data2) {
				System.out.println("专有消息内容："+msg.toString());
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void delete()throws Exception {
		//MsgAPI api = new MsgApiDelImpl();
		//删除专有消息
		//api.deleteMsg("109", "sys9", "1");
		//api.deleteMsg("98", "sys8", null);
		
		System.out.println("清除前：");
		CacheUtil.printRedisData(pool, "*");
		CacheUtil.clear(pool);
		System.out.println("清除后：");
		CacheUtil.printRedisData(pool, "*");
	}
	
	
	public void  testMsg() throws Exception {
		
		//CacheUtil.clear(pool);
		//add();
		//CacheUtil.printRedisData(pool, "*");
		//MsgAPI api = new MsgApiUpdateImpl();
		//api.updateMsgHasRead("92", "sys2", "1");
		//api.updateMsgHasRead("100", "sys0", "1");
		//CacheUtil.printRedisData(pool, "*");
		//delete();
		//CacheUtil.printRedisData(pool, "*");
		System.out.println("清除前：");
		CacheUtil.printRedisData(pool, "*");
		CacheUtil.clear(pool);
		System.out.println("清除后：");
		CacheUtil.printRedisData(pool, "*");
	}

}
