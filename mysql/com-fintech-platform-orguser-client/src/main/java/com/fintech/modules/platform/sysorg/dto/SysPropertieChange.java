package com.fintech.modules.platform.sysorg.dto;


public class SysPropertieChange {
	
	Object properte;
	
	Object propertesyn;

	Boolean ischange;
	
	public SysPropertieChange(Object properte,Object propertesyn){
		this.properte = properte;
		this.propertesyn = propertesyn;
	}

	public Object getProperte() {
		return properte;
	}

	public void setProperte(Object properte) {
		this.properte = properte;
	}

	public Object getPropertesyn() {
		return propertesyn;
	}

	public void setPropertesyn(Object propertesyn) {
		this.propertesyn = propertesyn;
	}
	
	public Object getShowProperte(){
		return getIschange()?getProperte():getPropertesyn();
	}
	
	public Object getShowProperteDesc(){
		return getIschange()? ( getProperte()+"<font color='red'>&nbsp;更新为&nbsp;</font>"+getPropertesyn() ) :getPropertesyn();
	}
	
	public Object getShowChange(){
		return getIschange()?getPropertesyn():null;
	}
	
	public Boolean getIschange() {
		if( properte!=null && propertesyn!=null  && ! properte.equals( propertesyn ) ){
			this.ischange = true;
		}else{
			this.ischange = false;
		}
		return this.ischange;
	}

	public void setIschange(Boolean ischange) {
		this.ischange = ischange;
	}
}