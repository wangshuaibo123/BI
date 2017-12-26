package com.fintech.modules.platform.sysorg.dto;

import java.util.ArrayList;
import java.util.List;

import com.fintech.modules.platform.sysorg.dto.SysOrgUserDTO;

public class SysOrgUserChangeDTO{
	
	SysOrgUserDTO sysOrgUserDTO;
	
	SysOrgUserSynDTO sysOrgUserSynDTO;
	
	String changeType;
	
	public SysOrgUserDTO getSysOrgUserDTO() {
		return sysOrgUserDTO;
	}

	public void setSysOrgUserDTO(SysOrgUserDTO sysOrgUserDTO) {
		this.sysOrgUserDTO = sysOrgUserDTO;
	}

	public SysOrgUserSynDTO getSysOrgUserSynDTO() {
		return sysOrgUserSynDTO;
	}

	public void setSysOrgUserSynDTO(SysOrgUserSynDTO sysOrgUserSynDTO) {
		this.sysOrgUserSynDTO = sysOrgUserSynDTO;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	/**Description: 分析任职和归属机构变化
	 * Create Date: 2015年1月28日下午3:42:21<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param sysOrgUserDTOs
	 * @param sysOrgUserSynDTOs
	 * @return
	 */
	public static List<SysOrgUserChangeDTO> getSysOrgUserChangeDTOs(List<SysOrgUserDTO> sysOrgUserDTOs, List< SysOrgUserSynDTO>  sysOrgUserSynDTOs){
		List<SysOrgUserChangeDTO> equals = new ArrayList<SysOrgUserChangeDTO>();
		
		List<SysOrgUserChangeDTO> add = new ArrayList<SysOrgUserChangeDTO>();
		List<SysOrgUserChangeDTO> sub = new ArrayList<SysOrgUserChangeDTO>();
		if(sysOrgUserDTOs!=null && sysOrgUserDTOs.size()>0){
			for (SysOrgUserDTO sysOrgUserDTO : sysOrgUserDTOs) {
				boolean sysOrgUserDTOMatch = false;
				if(sysOrgUserSynDTOs!=null && sysOrgUserSynDTOs.size()>0){
					for (SysOrgUserSynDTO sysOrgUserSynDTO:sysOrgUserSynDTOs){
						if( sysOrgUserDTO.getOrgId() .equals( sysOrgUserSynDTO.getOrgId() ) && 
								sysOrgUserDTO.getPositionId().equals(sysOrgUserSynDTO.getPositionId() )){
								//相等没有变化的数据
								SysOrgUserChangeDTO sysOrgUserChangeDTO = new SysOrgUserChangeDTO();
								sysOrgUserChangeDTO.setSysOrgUserDTO(sysOrgUserDTO);
								sysOrgUserChangeDTO.setSysOrgUserSynDTO(sysOrgUserSynDTO);
								sysOrgUserChangeDTO.setChangeType("nochange");
								equals.add(sysOrgUserChangeDTO);
								sysOrgUserDTOMatch= true;
						}
					}
				}
				if(!sysOrgUserDTOMatch){
					SysOrgUserChangeDTO sysOrgUserChangeDTO = new SysOrgUserChangeDTO();
					sysOrgUserChangeDTO.setSysOrgUserDTO(sysOrgUserDTO);
					sysOrgUserChangeDTO.setChangeType("sub");
					sub.add(sysOrgUserChangeDTO);
				}
			}
		}
		if(sysOrgUserSynDTOs!=null && sysOrgUserSynDTOs.size() >0 ){
			for (SysOrgUserSynDTO sysOrgUserSynDTO:sysOrgUserSynDTOs){
				boolean sysOrgUserDTOMatch = false;
				
				if(sysOrgUserDTOs!=null && sysOrgUserDTOs.size() >0){
					for (SysOrgUserDTO sysOrgUserDTO : sysOrgUserDTOs) {
						if( sysOrgUserDTO.getOrgId() .equals( sysOrgUserSynDTO.getOrgId() ) && 
								sysOrgUserDTO.getPositionId().equals(sysOrgUserSynDTO.getPositionId() )){
								sysOrgUserDTOMatch= true;
						}
					}
				}
				if(!sysOrgUserDTOMatch){
					SysOrgUserChangeDTO sysOrgUserChangeDTO = new SysOrgUserChangeDTO();
					sysOrgUserChangeDTO.setSysOrgUserSynDTO( sysOrgUserSynDTO );
					sysOrgUserChangeDTO.setChangeType("add");
					add.add(sysOrgUserChangeDTO);
				}
			}
		}
		equals.addAll(add);
		equals.addAll(sub);
		return equals;
	}
}