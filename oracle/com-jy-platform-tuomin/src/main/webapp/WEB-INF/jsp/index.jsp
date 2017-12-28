<%@page import="com.jy.platform.tuomin.monitor.MonitorBean"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN""http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>脱敏程序</title>
</head>
<body>
    <span>
    <B>脱敏程序监控</B>&nbsp;&nbsp;&nbsp;&nbsp;<%=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()) %>
    </span>
    <hr>
    <table width="100%" align="center" border="1">
    <thead style="background-color: gray;">
    <tr>
    <td>线程</td>
    <td>系统</td>
    <td>表名</td>
    <td>开始时间</td>
    <td>结束时间</td>
    <td>状态</td>
    </tr>
    </thead>
    <tbody>
    <%
    List<MonitorBean> list = (List<MonitorBean>)request.getAttribute("list");
    for (int i=0; i<list.size(); i++) {
        MonitorBean b = list.get(i);
    %>
    <tr>
    <td><%=b.getThreadNo() %></td>
    <td><%=b.getSysName() %></td>
    <td><%=b.getTableName() %></td>
    <td><%=b.getBeginTime() %></td>
    <td><%=b.getEndTime() %></td>
    <td><%=b.getStatus() %></td>
    </tr>
    <%} %>
    </tbody>
    </table>
</body>
</html>