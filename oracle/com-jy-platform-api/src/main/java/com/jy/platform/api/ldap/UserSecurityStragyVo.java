package com.jy.platform.api.ldap;
/**
 * 用户安全策略
 * @author luoyr
 *
 */
public class UserSecurityStragyVo {
	/***单位秒***/
	public static final String UNIT_S="S";
	/***单位分***/
	public static final String UNIT_M="M";
	/***单位小时***/
	public static final String UNIT_H="H";
	/***单位天***/
	public static final String UNIT_D="D";
	
	/***登录时最大失败次数  ***/
    private String pwdMaxFailure;
    /***错误次数重置为0,周期时间 单位秒****************/
    private String pwdFailureCountInterval;
    /***密码锁定自动恢复时间 单位秒   自动解锁功能****************/
    private String pwdLockoutDuration;
    /***密码有效期 单位秒****************/
    private String pwdMaxAge;
    /**
     * 密码质量 正则表达示,当不为空,则使用正则表达式校验密码,
     * 新增与修改密码时才有效
     * [a-zA-Z0-9]{6,20} 包含数字或字母的表达式
     * [\d\w\_\-\*\$\@\!]{6,} 必须包含数字,字母或特殊字符
     * 对应openldap字段为description
     */
    private String pwdQuality;
    /**
     * 密码初次必须修改
     */
    private String pwdMustChange;
    /**
     * 各个设置时间单位,对应openldap策略节点的ou字段
     * h小时,m分,s秒,d天,以|分隔记录按顺序以下几个字段
     * pwdFailureCountInterval|pwdLockoutDuration|pwdMaxAge
     * 此三个字段的单位,安全策略控制页面显示使用,保存到openldap的值都以秒为单位
     */
    private String units;
    /**
     * 密码保存历史次数,新密码不能与历史相同,0为不校验,大于0则校验;
     * 仅当有历史记录时才会校验
     * 连接openldap必须以非root dn用户,默认使用安全策略下的sys用户连接
     */
    private String pwdInHistory;
    /***********单位处理字段 必须声明,否则转json不识别字段**************/
    private String pwdFailureCountIntervalUnit;
    private String pwdLockoutDurationUnit;
    private String pwdMaxAgeUnit;
    private String pwdFailureCountIntervalShow;
    private String pwdLockoutDurationShow;
    private String pwdMaxAgeShow;
    
	public String getPwdMaxFailure() {
		return pwdMaxFailure;
	}
	public void setPwdMaxFailure(String pwdMaxFailure) {
		this.pwdMaxFailure = pwdMaxFailure;
	}
	public String getPwdFailureCountInterval() {
		return pwdFailureCountInterval;
	}
	public void setPwdFailureCountInterval(String pwdFailureCountInterval) {
		this.pwdFailureCountInterval = pwdFailureCountInterval;
	}
	public String getPwdLockoutDuration() {
		return pwdLockoutDuration;
	}
	public void setPwdLockoutDuration(String pwdLockoutDuration) {
		this.pwdLockoutDuration = pwdLockoutDuration;
	}
	public String getPwdMaxAge() {
		return pwdMaxAge;
	}
	public void setPwdMaxAge(String pwdMaxAge) {
		this.pwdMaxAge = pwdMaxAge;
	}
	@Override
	public String toString() {
		return "pwdMaxFailure:"+pwdMaxFailure+" pwdFailureCountInterval:"+pwdFailureCountInterval+
				" pwdLockoutDuration:"+pwdLockoutDuration+" pwdMaxAge:"+pwdMaxAge+" "+
				" pwdQuality:"+pwdQuality+" pwdMustChange:"+pwdMustChange+" pwdInHistory:"+pwdInHistory+" units:"+units;
	}
	public String getPwdQuality() {
		return pwdQuality;
	}
	public void setPwdQuality(String pwdQuality) {
		this.pwdQuality = pwdQuality;
	}
	public String getPwdMustChange() {
		return pwdMustChange;
	}
	public void setPwdMustChange(String pwdMustChange) {
		this.pwdMustChange = pwdMustChange;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public String getPwdInHistory() {
		return pwdInHistory;
	}
	public void setPwdInHistory(String pwdInHistory) {
		this.pwdInHistory = pwdInHistory;
	}
	/**
	 * 返回复位单位,必须保证更新记录时顺序正确,默认返回秒
	 * @return
	 */
	public String getPwdFailureCountIntervalUnit() {
		return parseUnit(0);
	}
	/**
	 * 返回自动解锁单位,必须保证更新记录时顺序正确,默认返回秒
	 * @return
	 */
	public String getPwdLockoutDurationUnit() {
		return parseUnit(1);
	}
	/**
	 * 返回最大有效期期单位,必须保证更新记录时顺序正确,默认返回秒
	 * @return
	 */
	public String getPwdMaxAgeUnit() {
		return parseUnit(2);
	}
	/**
	 * 解析单位存储字段
	 * @param index
	 * @return
	 */
	private String parseUnit(int index){
		if(units != null && !units.equals("")){
			String[] unitArray = units.split("\\|");
			if(unitArray.length == 3){
				return unitArray[index];
			}
		}
		return UNIT_S;
	}
	public void setPwdFailureCountIntervalUnit(String pwdFailureCountIntervalUnit) {
		this.pwdFailureCountIntervalUnit = pwdFailureCountIntervalUnit;
	}
	public void setPwdLockoutDurationUnit(String pwdLockoutDurationUnit) {
		this.pwdLockoutDurationUnit = pwdLockoutDurationUnit;
	}
	public void setPwdMaxAgeUnit(String pwdMaxAgeUnit) {
		this.pwdMaxAgeUnit = pwdMaxAgeUnit;
	}
	public String getPwdFailureCountIntervalShow() {
		String unit = getPwdFailureCountIntervalUnit();
		String input = getPwdFailureCountInterval();
		return calTimeset(unit, input);
	}
	/**
	 * 计算转化页面最终显示值
	 * @param unit
	 * @param input
	 * @return
	 */
	public String calTimeset(String unit,String input){
		long result = Long.parseLong(input);
		if(unit.equals(UNIT_M)){
			result = Long.parseLong(input)/60;
		}
		if(unit.equals(UNIT_H)){
			result = Long.parseLong(input)/(60*60);
		}
		if(unit.equals(UNIT_D)){
			result = Long.parseLong(input)/(60*60*24);
		}
		return result+"";
	}
	
	public void setPwdFailureCountIntervalShow(String pwdFailureCountIntervalShow) {
		this.pwdFailureCountIntervalShow = pwdFailureCountIntervalShow;
	}
	public String getPwdLockoutDurationShow() {
		String unit = getPwdLockoutDurationUnit();
		String input = getPwdLockoutDuration();
		return calTimeset(unit, input);
	}
	public void setPwdLockoutDurationShow(String pwdLockoutDurationShow) {
		this.pwdLockoutDurationShow = pwdLockoutDurationShow;
	}
	public String getPwdMaxAgeShow() {
		String unit = getPwdMaxAgeUnit();
		String input = getPwdMaxAge();
		return calTimeset(unit, input);
	}
	public void setPwdMaxAgeShow(String pwdMaxAgeShow) {
		this.pwdMaxAgeShow = pwdMaxAgeShow;
	}
	
	
}
