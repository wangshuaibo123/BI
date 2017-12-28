package com.jy.platform.tools.ftp;

import java.io.File;
import java.util.ResourceBundle;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPMessageCollector;
import com.enterprisedt.net.ftp.FTPTransferType;
/**
 * 
 */
public class FtpFile {
    private String host;
    private int port=21;
    private String user;
    private String password;
    private String ftpPath;
    private final Logger logger = LoggerFactory
			.getLogger(FtpFile.class);
    private boolean passive = true;
    public boolean isPassive() {
		return passive;
	}

	public void setPassive(boolean passive) {
		this.passive = passive;
	}

	/**
     * 
     */
    public FtpFile() {

    }
    /**
     * 
     */
    public FtpFile(String host, String username, String pwd) {
        this.host = host;
        this.user = username;
        this.password = pwd;

    }
    
    public FtpFile(String host, String username, String pwd,int port) {
        this.host = host;
        this.user = username;
        this.password = pwd;
        this.port=port;

    }
    
    public FTPClient login() throws Exception{
    	 /**
         * 服务器建立连接
         */
        FTPClient ftp = new FTPClient();
        ftp.setRemoteHost(host);
        ftp.setRemotePort(port);
        ftp.setControlEncoding("gbk");
        FTPMessageCollector listener = new FTPMessageCollector();
        ftp.setMessageListener(listener);
        ftp.connect();
        

        /**
         * 登陆到Ftp服务器
         */
        ftp.login(user, password);
        return ftp;
    }

    /**
     * TODO 获取FTP文件
     * 
     * @param remotehost
     *                主机IP地址
     * @param username
     *                用户名称
     * @param pwd
     *                用户密码
     * @param filepath
     *                ftp文件路径
     * @param downpath
     *                下载文件保存路径
     * @param action
     *                int 下载后是否删除 0不删除1删除
     * @return filename 返回文件名称
     */

    public String getFile(String remotehost, String username, String pwd,
        String filepath, String filename, String downpath, int action) {
        try {
        	FTPClient ftp = login();
            if(passive){
            	ftp.setConnectMode(FTPConnectMode.PASV);
            }else{
            	ftp.setConnectMode(FTPConnectMode.ACTIVE);
            }
            
            ftp.setType(FTPTransferType.BINARY);

            /**
             * 下载文件
             */
            ftp.chdir(filepath);
            ftp.get(downpath + File.separator + filename, filename);

            /**
             * 删除文件
             */
            if (action == 1) {
                ftp.delete(filename);
                this.logger.info("delete ok");
            }

            ftp.quit();
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return filename;
    }

    /**
     * 
     */
    public String getFile(boolean pasv, boolean ascii, String remotehost,
        String username, String pwd, String filepath, String filename,
        String downpath, int action) {
        try {
        	FTPClient ftp = login();
            if (pasv) {
                ftp.setConnectMode(FTPConnectMode.PASV);
            } else {
                ftp.setConnectMode(FTPConnectMode.ACTIVE);
            }
            if (ascii) {
                ftp.setType(FTPTransferType.ASCII);
            } else {
                ftp.setType(FTPTransferType.BINARY);
            }

            /**
             * 下载文件
             */
            ftp.chdir(filepath);
            ftp.get(downpath + File.separator + filename, filename);

            /**
             * 删除文件
             */
            if (action == 1) {
                ftp.delete(filename);
                this.logger.info("delete ok");
            }

            ftp.quit();
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return filename;
    }

    /**
     * 
     */
    public int getFileFromFtp(String remotehost, String username, String pwd,
        String filepath, String ftpName,String localName, String downpath, int action) {
        int flag = 0;
        try {
        	FTPClient ftp = login();
            if (this.passive) {
                ftp.setConnectMode(FTPConnectMode.PASV);
            } else {
                ftp.setConnectMode(FTPConnectMode.ACTIVE);
            }
            ftp.setType(FTPTransferType.BINARY);

            /**
             * 下载文件
             */
            ftp.chdir(filepath);
            ftp.get(downpath + File.separator + localName, ftpName);
            flag = 1;
            /**
             * 删除文件
             */
            if (action == 1) {
                ftp.delete(ftpName);
                this.logger.info("delete ok");
            }

            ftp.quit();
        } catch (Exception e) {
            this.logger.error("Failed to open file");
        }
        return flag;
    }

    /**
     * 
     */
    private boolean putFileToFtp(String localfile, String uploadFileName) {
        boolean retftp = false;
        try {
        	FTPClient ftp = login();
            if(passive){
            	ftp.setConnectMode(FTPConnectMode.PASV);
            }else{
            	ftp.setConnectMode(FTPConnectMode.ACTIVE);
            }
            ftp.setType(FTPTransferType.BINARY);

            /**
             * 上传文件
             */
            if(StringUtil.isNotEmpty(ftpPath)){
            	try {
                    ftp.chdir(this.ftpPath);
                } catch (Exception ex) {
                    
                    ftp.mkdir(this.ftpPath);
                    
                    ftp.chdir(this.ftpPath);
                }
            }
            

            if ("".equals(uploadFileName)) {
                File file = new File(localfile);
                uploadFileName = file.getName();
            }
            ftp.put(localfile, uploadFileName);
            ftp.quit();
            retftp = true;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return retftp;
    }

    /**
     * 
     */
    public boolean putFile(String remotehost, String username, String pwd,
        String ftppath, String filename, String localfile) {
        this.ftpPath = ftppath;
        this.host = remotehost;
        this.user = username;
        this.password = pwd;
        return putFileToFtp(localfile, filename);
    }

    /**
     * 
     */
    public boolean putFile(String localfile, String ftppath, String filename) {
        this.ftpPath = ftppath;
        return putFileToFtp(localfile, filename);
    }

    /**
     * 
     */
    public boolean putFile(String localfile, String ftppath) {
        this.ftpPath = ftppath;
        return putFileToFtp(localfile, "");
    }

    /**
     * 
     */
    public boolean putFile(String localfile) {
        return putFileToFtp(localfile, "");
    }

    /**
     * 
     */
    public boolean initFtpConfig() {
        return initFtpConfig("ftp.conf");
    }

    /**
     * 
     */
    public boolean initFtpConfig(String confFile) {
        ResourceBundle CONF = ResourceBundle.getBundle(confFile);
        this.host = CONF.getString("host");
        this.user = CONF.getString("user");
        this.password = CONF.getString("password");
        this.ftpPath = CONF.getString("ftppath");
        return true;
    }

    /**
     * 
     */
    public String getHost() {
        return host;
    }

    /**
     * 
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     */
    public String getUser() {
        return user;
    }

    /**
     * 
     */
    public String getFtpPath() {
        return ftpPath;
    }

    /**
     * 
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * 
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 
     */
    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	/**
     * 
     * @param ftpPath
     */
    public void setFtpPath(String ftpPath) {
        this.ftpPath = ftpPath;
    }

    
    public boolean renameFile(String oldName, String newName) {
            try {
            	FTPClient ftp = login();
                ftp.rename(oldName, newName);

                ftp.quit();
            } catch (Exception e) {
                this.logger.error(e.getMessage());
                return false;
            }
            return true;
        }
    
    public boolean delFile(String filename) {
            try {
            	FTPClient ftp = login();
                if(passive){
                	ftp.setConnectMode(FTPConnectMode.PASV);
                }else{
                	ftp.setConnectMode(FTPConnectMode.ACTIVE);
                }
                
                ftp.setType(FTPTransferType.BINARY);

                ftp.delete(filename);
                this.logger.info("delete ok");

                ftp.quit();
            } catch (Exception e) {
                this.logger.error(e.getMessage());
                return false;
            }
            return true;
        }
    public static void main(String[] args) { 
    	FtpFile ftp = new
    			FtpFile("172.18.100.157", "jyftp", "gy2Z(eNLzMAJ");
//    	ftp.putFile("H:\\DbVisualizer.rar", "/home/jyapp/jyintofile"); 
    	ftp.renameFile("DbVisualizer.rar","1111111111111111.rar");
    }
    
}
