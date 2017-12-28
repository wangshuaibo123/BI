package com.jy.modules.quartzJob.core.service;

import java.io.Serializable;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public interface IBaseJob extends Serializable{//,Job
	public void execute(JobExecutionContext context,String keyID) throws JobExecutionException ;
	
	public void rollback(JobExecutionContext context,String keyID) throws JobExecutionException;
}
