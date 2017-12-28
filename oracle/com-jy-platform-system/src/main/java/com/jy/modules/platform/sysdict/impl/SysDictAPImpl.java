package com.jy.modules.platform.sysdict.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.jy.modules.platform.sysdict.dto.SysDictDetailDTO;
import com.jy.modules.platform.sysdict.service.SysDictDetailService;
import com.jy.modules.platform.sysdict.service.SysDictService;
import com.jy.platform.api.sysdict.SysDictAPI;
import com.jy.platform.api.sysdict.SysDictDetailVo;

public class SysDictAPImpl implements SysDictAPI {

	@Autowired
	private SysDictDetailService sysDictDetailsrv;
	@Autowired
	private SysDictService sysServce;

	@Override
	public List<Map> getDictByKey(String key) {
		List<Map> detailList = sysDictDetailsrv.queryDetailByDictCode(key);
		return detailList;
	}

	@Override
	public SysDictDetailVo queryDetailByDictCodeAndDeatailValue(
			String dict_code, String detail_value) {
		SysDictDetailDTO dto = sysDictDetailsrv.queryDetailByDictCodeAndDeatailValue(dict_code, detail_value);
		if(dto!=null){
			SysDictDetailVo vo = new SysDictDetailVo();
			vo.setDictDetailName(dto.getDictDetailName());
			vo.setDictDetailValue(dto.getDictDetailValue());
			vo.setOrderBy(dto.getOrderBy());
			return vo;
		}else{
			return null;
		}
	}

	@Override
	public Map<String, String> queryDictByDictCode(String code) {
		return sysServce.queryDictInfoByCode(code);
	}

	@Override
	public String codeToName(String type_, String code_) {
		// TODO Auto-generated method stub
		List<Map> list = this.getDictByKey(type_);
		if(list != null && list.size()>0){
			for(int i=0;i<list.size();i++){
				if(code_ != null && (","+code_ + ",").indexOf(","+list.get(i).get("DICVALUE")+",") !=-1){
				//if(list.get(i).get("DICVALUE").equals(code_)){
					return "" +list.get(i).get("DICNAME") + " ";
				}
			}
		}
		return "";
	}

}
