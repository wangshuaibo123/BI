package com.fintech.platform.restservice.mybatis;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.fintech.platform.tools.common.DataSourceSwitch;
/**
 * @description: 定义数据源切换类
 * @author
 * @date: 2016年1月28日 下午5:17:51
 */
public class DataSources extends AbstractRoutingDataSource{

	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceSwitch.getDataSourceType(); 
	}

}
