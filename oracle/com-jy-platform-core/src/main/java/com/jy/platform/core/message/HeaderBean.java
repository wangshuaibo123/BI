package com.jy.platform.core.message;

import java.io.Serializable;

public class HeaderBean implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String svcID;
    
    private String oMsgNo;
    
    private String channelSerno;
    
    private String comsSysID;
    
    private String comsSubSysID;
    
    private String comsSysDate;
    
    private String comsSysTime;
    
    private String BRNO;
    
    private String subBRNO;
    
    private String TELLER;
    
    private String subTELLER1;
    
    private String terminalCode;
    
    private String BAK1;
    
    private String BAK2;

    public String getSvcID() {
        return svcID;
    }

    public void setSvcID(String svcID) {
        this.svcID = svcID;
    }

    public String getoMsgNo() {
        return oMsgNo;
    }

    public void setoMsgNo(String oMsgNo) {
        this.oMsgNo = oMsgNo;
    }

    public String getChannelSerno() {
        return channelSerno;
    }

    public void setChannelSerno(String channelSerno) {
        this.channelSerno = channelSerno;
    }

    public String getComsSysID() {
        return comsSysID;
    }

    public void setComsSysID(String comsSysID) {
        this.comsSysID = comsSysID;
    }

    public String getComsSubSysID() {
        return comsSubSysID;
    }

    public void setComsSubSysID(String comsSubSysID) {
        this.comsSubSysID = comsSubSysID;
    }

    public String getComsSysDate() {
        return comsSysDate;
    }

    public void setComsSysDate(String comsSysDate) {
        this.comsSysDate = comsSysDate;
    }

    public String getComsSysTime() {
        return comsSysTime;
    }

    public void setComsSysTime(String comsSysTime) {
        this.comsSysTime = comsSysTime;
    }

    public String getBRNO() {
        return BRNO;
    }

    public void setBRNO(String bRNO) {
        BRNO = bRNO;
    }

    public String getSubBRNO() {
        return subBRNO;
    }

    public void setSubBRNO(String subBRNO) {
        this.subBRNO = subBRNO;
    }

    public String getTELLER() {
        return TELLER;
    }

    public void setTELLER(String tELLER) {
        TELLER = tELLER;
    }

    public String getSubTELLER1() {
        return subTELLER1;
    }

    public void setSubTELLER1(String subTELLER1) {
        this.subTELLER1 = subTELLER1;
    }

    public String getTerminalCode() {
        return terminalCode;
    }

    public void setTerminalCode(String terminalCode) {
        this.terminalCode = terminalCode;
    }

    public String getBAK1() {
        return BAK1;
    }

    public void setBAK1(String bAK1) {
        BAK1 = bAK1;
    }

    public String getBAK2() {
        return BAK2;
    }

    public void setBAK2(String bAK2) {
        BAK2 = bAK2;
    }
    @Override
   	public String toString() {
   		StringBuilder builder = new StringBuilder();
   		builder.append("HeaderBean [oMsgNo=");
   		builder.append(getoMsgNo());
   		builder.append(", channelSerno=");
   		builder.append(getChannelSerno());
   		builder.append(", comsSysID=");
   		builder.append(getComsSysID());
   		builder.append(", comsSysDate=");
   		builder.append(getComsSysDate());
   		builder.append(", comsSysTime=");
   		builder.append(getComsSysTime());
   		builder.append("]");
   		return builder.toString();
   	}
    

}
