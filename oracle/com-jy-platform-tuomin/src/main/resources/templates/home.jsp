<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN""http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>脱敏程序</title>

<style type="text/css">
</style>

</head>
<body>
<!-- 头 -->
    <span>
	    <B>脱敏程序监控</B>&nbsp;&nbsp;&nbsp;&nbsp;${currentTime}&nbsp;&nbsp;&nbsp;&nbsp;
	    <#if started != true>
		    <input type="button" value="开始执行" onclick="start()">
		    <form action="start" mothod="post" id="form1">
		    </form>
	    </#if>
    </span>
    <hr>

<!-- 表 -->
    <table width="100%" align="center" border="1" cellspacing="0" cellpadding="3">
    <thead style="background-color: gray;">
	    <tr>
	    <td width="10%">线程</td>
	    <td width="10%">系统</td>
	    <td width="20%">表名</td>
	    <td width="15%">开始时间</td>
	    <td width="15%">结束时间</td>
	    <td>状态</td>
	    </tr>
    </thead>
    <tbody>
	    <#list beans as b>
	    <tr>
	    <td>${b.getThreadNo()}</td>
	    <td>${b.getSysName()}</td>
	    <td>${b.getTableName()}</td>
	    <td>${b.getBeginTime()}</td>
	    <td>${b.getEndTime()}</td>
	    <td>${b.getStatus()}</td>
	    </tr>
	    </#list>
    </tbody>
    </table>
</body>

<script type="text/javascript">
function start() {
    if (confirm("要启动脱敏处理进程吗？")) {
        document.getElementById("form1").submit();
    }
}
</script>

</html>