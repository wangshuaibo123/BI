<%@ page language="java" import="java.util.*,com.fintech.platform.StringUtilTools" pageEncoding="UTF-8"%>

<%
String path1 = request.getContextPath();
String basePath1 = path1+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="basePath1" value="<%=basePath1 %>"></c:set>
<script>
var JBPM4_PATH = '${basePath1}';
</script>
