package com.jy.platform.schedule.demo;

import org.springframework.stereotype.Component;

import com.jy.platform.schedule.Job;

@Component("com.jy.platform.schedule.demo.DemoJobBean")
public class DemoJob implements Job {
	
	private int duration = 120;
	
	@SuppressWarnings("unused")
    private String stating;

	public void setStating(String stating) {
		this.stating = stating;
	}

	@Override
	public void execute() throws Exception {
		StringBuffer sb = new StringBuffer("hashCode:" + this.hashCode() + "\r\n");
		sb.append("**************************************" + "\r\n");
		sb.append("*****************job begin....**********" + "\r\n");
		sb.append("**************************************" + "\r\n");
		System.out.println(sb.toString());

		for (int i = 1; i < duration; i++) {
			this.setStating("第" + i + "阶段更新。。。。。。。");
			System.out.println("第" + i + "阶段更新。。。。。。。");
			try {
				Thread.sleep(50000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		sb = new StringBuffer();
		sb.append("**************************************" + "\r\n");
		sb.append("*****************job end....**********" + "\r\n");
		sb.append("**************************************" + "\r\n");
		System.out.println(sb.toString());
	}

	@Override
	public void rollback() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public String getState() {
		// TODO Auto-generated method stub
		return null;//stating;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}
