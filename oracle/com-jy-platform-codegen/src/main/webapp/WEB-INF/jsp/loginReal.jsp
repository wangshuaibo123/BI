<%@ page language="java" import="java.util.*,com.jy.modules.common.util.ObtainPropertiesInfo" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
    <meta charset="UTF-8">
    <title><%=ObtainPropertiesInfo.getValByKey("app.name") %>用户登录</title>
    <link rel="stylesheet" href="<%=basePath%>css/login/login.css">
	<style>.error{color:red;}</style>
</head>
<body>


<div class="top">
<div class="title"><%=ObtainPropertiesInfo.getValByKey("app.name") %></div>
</div>
<div class="main">
    <div class="center">
        <div class="login">
        	<form action="<%=request.getContextPath() %>/user/login" method="post">
	            <ul>
	                <li> <label>用户名：</label><input input type="text" id="username" name="username" class="txt" value="用户名"  ></li>
	                <li> <label>密&nbsp;码：</label><input input type="password" id="pwd" name="password" class="txt" value="密码" ></li>
	                <li style="text-align:right;">  <a href="#" class="link" >忘记密码</a></li>
	                <li style="text-align:center;">
		                <div class="btn-side" >
		                    <input class="btn" type="submit" value="登录">
		                </div>
	                   </li></ul>

			</form>
    	</div>
    </div>

</div>
<div class="foot">

    <table  class="foot-table" cellpadding="0" cellspacing="0"><tr><td style="   border-right: 1px solid #fff;">
        <ul><li style="font-size: 14px; font-weight: bold">
        系统信息：
    </li>
        <li>
            当前浏览器：<span id="browser"></span>  建议IE9+、Chrome、Firefox
        </li>
        <li>
            当前分辨率：<span id="screen"></span>  建议1920
        </li>
        <li>
            <a href="#" style="color: #5c6d7e">版本信息更新记录</a>

        </li>
        </ul>
    </td>
        <td class="foot-center">捷越联合信息咨询有限公司
            <br>
         &nbsp;&nbsp;&nbsp;&nbsp;www.jieyuechina.com</td>
        <td style="border-left: 1px solid #cacaca; padding-left: 80px">  <ul><li style="font-size: 14px; font-weight: bold">
            技术支持：
        </li>
            <li>
                电话:010-52327119
            </li>
            <li>
                邮箱：itsupport@jieyuechina.com
            </li>
            <li>
                工作时间：工作日(9:00-18:00)
            </li>
        </ul></td>
    </tr></table>

</div>


 
</body>

<script >
    var user=document.getElementById("username");
    user.onfocus=function(){if(this.value==this.defaultValue)this.value=''};
    user.onblur=function (){if(/^\s*$/.test(this.value)){this.value=this.defaultValue;this.style.color='#b3b3b3'}}
    user.onkeydown=function(){this.style.color='#000'}
    var pwd=document.getElementById("pwd");
    pwd.onfocus=function(){if(this.value==this.defaultValue)this.value=''};
    pwd.onblur=function (){if(/^\s*$/.test(this.value)){this.value=this.defaultValue;this.style.color='#b3b3b3'}}
    pwd.onkeydown=function(){this.style.color='#000'}
    
	    function get_os() {  
	        var sUserAgent =navigator.userAgent;  
	        var isWin = (navigator.platform =="Win32") || (navigator.platform == "Windows");  
	        var isMac = (navigator.platform =="Mac68K") || (navigator.platform == "MacPPC") ||(navigator.platform == "Macintosh") || (navigator.platform =="MacIntel");
	        if (isMac) return "Mac";  
	        var isUnix = (navigator.platform =="X11") && !isWin && !isMac;  
	        if (isUnix) return "Unix";  
	        var isLinux =(String(navigator.platform).indexOf("Linux") > -1);  
	        if (isLinux) return"Linux";  
	        if (isWin) {  
	               var isWin2K =sUserAgent.indexOf("Windows NT 5.0") > -1 ||sUserAgent.indexOf("Windows 2000") > -1;  
	               if (isWin2K) return"Win2000";  
	               var isWinXP =sUserAgent.indexOf("Windows NT 5.1") > -1 ||sUserAgent.indexOf("Windows XP") > -1;  
	               if (isWinXP) return"WinXP";  
	               var isWin2003 =sUserAgent.indexOf("Windows NT 5.2") > -1 ||sUserAgent.indexOf("Windows 2003") > -1;  
	               if (isWin2003) return"Win2003";
	               var isWinVista=sUserAgent.indexOf("Windows NT 6.0") > -1 ||sUserAgent.indexOf("Windows Vista") > -1;  
	               if (isWinVista) return"WinVista";
	               var isWin7 =sUserAgent.indexOf("Windows NT 6.1") > -1 || sUserAgent.indexOf("Windows7") > -1;  
	               if (isWin7) return"Win7";
	        }  
	        return "other";  
	  }  
	    
	    function getServerIp(){
	    	<%
	    		 
	    		String ip = request.getHeader("x-forwarded-for"); 
	    		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	    		ip = request.getHeader("Proxy-Client-IP"); 
	    		} 
	    		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	    		ip = request.getHeader("WL-Proxy-Client-IP"); 
	    		} 
	    		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	    		ip = request.getRemoteAddr(); 
	    		} 
	    		 
	    	%>
	    	return "<%=ip%>";
	    }
	    
	    function getBrowser()  
	    {  
	        var Sys = {};
	        var ua = navigator.userAgent.toLowerCase();
	        var s;
	        (s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] :
	        (s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :
	        (s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
	        (s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :
	        (s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;
	        if (Sys.ie) {
	           return "ie-"+Sys.ie;
	        }
	        else if (Sys.firefox) {
	           return "firefox-"+Sys.firefox;
	        }
	        else if (Sys.chrome) {
	           return "chrome-"+Sys.chrome;
	        }
	        else if (Sys.opera) {
	           return "opera-"+Sys.opera;
	        }
	        else if (Sys.safari) {
	           return "safari-"+Sys.safari;
	        }
	        else {
	           return "无法检测浏览器信息"
	        }
	    }  
    (function(){
    	var screenXY= window.screen.height+ "&nbsp;*&nbsp;"+window.screen.width ; 
    	var os=get_os();
    	var browser=getBrowser();
    	var serverIp=getServerIp();
    	//alert(screenXY+"---"+os+"----"+browser+"----"+serverIp);
    })();
    
</script>
</html>