<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/StaticJavascript2.jsp"%>

<div id="formPageSwap">
	<form id="viewForm" name="viewForm" isCheck="true" action="com.fintech.modules.mdcertconfig.controller.MdCertConfigController">
		<input type="hidden" class="text" id="dtoid" name="id" notNull="false" maxLength="11" value="${dto.id}" />
		<table id="viewTableId" class="formTableSwap" border="0" align="center" cellpadding="0" cellspacing="1">
			<tr>
				<th>配置类型：</th>
				<td >${dto.configType}</td>
				<th>配置名称：</th>
				<td >${dto.configName}</td>
			</tr>
			<tr>
				<th>配置KEY：</th>
				<td >${dto.configKey}</td>
				<th>配置VALUE：</th>
				<td >${dto.configValue}</td>
			</tr>
			<tr>
				<th>配置备注：</th>
				<td >${dto.configDesc}</td>
				<th>启用状态：</th>
				<td >
			    <syscode:dictionary codeType="YESNO"  type="text"  className="text"  defaultValue="${dto.configStatu}"  />
				</td>
			</tr>
			<!--  
			<tr>
				<th>扩展字段：</th>
				<td >${dto.ext1}</td>
				<th>扩展字段：</th>
				<td >${dto.ext2}</td>
			</tr>
			<tr>
				<th>扩展字段：</th>
				<td >${dto.ext3}</td>
				<th>扩展字段：</th>
				<td >${dto.ext4}</td>
			</tr>
			<tr>
				<th>创建时间：</th>
				<td >${dto.createTime}</td>
				<th>更新时间：</th>
				<td >${dto.updateTime}</td>
			</tr>
			-->
		</table>
		<!-- 关闭 按钮 在 查询页面进行控制 -->
	</form>
</div>