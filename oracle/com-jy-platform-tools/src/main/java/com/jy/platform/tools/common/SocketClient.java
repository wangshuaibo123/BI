
/**
* Copyright (C) 2006-2014 版权所有者为北京捷越联合信息咨询有限公司。本系统是商用软件,未经授权擅自复制或传播本程序的部分或全部将是非法的。 
* @title: SocketClient.java
* @package com.jy.core.util
* @author liangjl
* @date 2014年9月3日 下午3:19:30
* @version v1.00
* @description: TODO(用一句话描述该文件做什么)
*/ 

package com.jy.platform.tools.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;


	
/**
 * @classname: SocketClient
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public class SocketClient {

    private final  String ip;
    private final  int port;
    
    private final  Socket socket;
    
    public SocketClient(String ip,int port) throws IOException{
        this.ip=ip;
        this.port= port;
        
        this.socket = new Socket();
        this.socket.connect(new InetSocketAddress(this.ip, this.port));
    }
    
    public String readMsg() throws IOException{
       
        byte[] len_byte = this.readBytes(this.socket.getInputStream(), 8);
        
        int len=Integer.parseInt(new String(len_byte).trim());
        
        String data = new String(this.readBytes(this.socket.getInputStream(), len));
        
        return data;
    }
    
    public void writeMsg(String msg) throws IOException{
        
        byte[] data = msg.getBytes();
        String len = String.format("%08d",data.length);
        this.socket.getOutputStream().write(len.getBytes());
        this.socket.getOutputStream().write(data);
        this.socket.getOutputStream().flush();
    }
    
    private byte[] readBytes(InputStream input,int len) throws IOException{
        
        byte[] data=new byte[len];
        int offset = 0;
        
        while(offset!=len){
            
            offset = offset + input.read(data, offset, len-offset);
        }
        
        return data;
    }
    
    public void close(){
        
        if(this.socket!=null && !this.socket.isClosed()){
            try {
                this.socket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
