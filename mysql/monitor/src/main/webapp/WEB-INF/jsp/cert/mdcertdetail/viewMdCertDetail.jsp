<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/StaticJavascript2.jsp"%>

<div id="formPageSwap">
	<form id="viewForm" name="viewForm" isCheck="true" action="com.fintech.modules.mdcertdetail.controller.MdCertDetailController">
		<input type="hidden" class="text" id="dtoid" name="id" notNull="false" maxLength="11" value="${dto.id}" />
		<table id="viewTableId" class="formTableSwap" border="0" align="center" cellpadding="0" cellspacing="1">
			<tr>
				<th>客户姓名：</th>
				<td >${dto.custName}</td>
				<th>证件类型：</th>
				<td>${dto.cardType}</td>
			</tr>
			<tr>
				<th>证件号码：</th>
				<td >${dto.cardNo}</td>
				<th>认证状态：</th>
				<td >${dto.certStatu}</td>
			</tr>
			<tr>
				<th>认证渠道：</th>
				<td >${dto.certChannel}</td>
				<th>系统来源：</th>
				<td >${dto.sysSource}</td>
			</tr>
		</table>
		<!-- 关闭 按钮 在 查询页面进行控制 -->
	</form>
</div>