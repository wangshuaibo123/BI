<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/StaticJavascript2.jsp"%>

<div id="formPageSwap">
	<form id="addForm" name="addForm" isCheck="true" action="com.fintech.modules.mdcertconfig.controller.MdCertConfigController">
		<table id="addTableId" class="formTableSwap" border="0" align="center" cellpadding="0" cellspacing="1">
			<tr>
				<th>配置类型：</th>
				<td >
					<input type="text" class="text" id="dtoconfigType" name="configType" notNull="true" maxLength="50" value="" checkChName="配置类型" />
				</td>
				<th>配置名称：</th>
				<td >
					<input type="text" class="text" id="dtoconfigName" name="configName" notNull="true" maxLength="50" value="" checkChName="配置名称" />
				</td>
			</tr>
			<tr>
				<th>配置KEY：</th>
				<td >
					<input type="text" class="text" id="dtoconfigKey" name="configKey" notNull="true" maxLength="50" value="" checkChName="配置KEY" />
				</td>
				<th>配置VALUE：</th>
				<td >
					<input type="text" class="text" id="dtoconfigValue" name="configValue" notNull="true" maxLength="50" value="" checkChName="配置VALUE" />
				</td>
			</tr>
			<tr>
				<th>配置备注：</th>
				<td >
					<input type="text" class="text" id="dtoconfigDesc" name="configDesc" notNull="true" maxLength="500" value="" checkChName="配置备注" />
				</td>
				<th>启用状态：</th>
				<td >
					<syscode:dictionary codeType="YESNO"  type="select"  className="text" id="dtoconfigStatu" name="configStatu" defaultValue="1"/>
				</td>
			</tr>
			<!--  
			<tr>
				<th>扩展字段：</th>
				<td >
					<input type="text" class="text" id="dtoext1" name="ext1" notNull="true" maxLength="20" value="" checkChName="扩展字段" />
				</td>
				<th>扩展字段：</th>
				<td >
					<input type="text" class="text" id="dtoext2" name="ext2" notNull="true" maxLength="20" value="" checkChName="扩展字段" />
				</td>
			</tr>
			<tr>
				<th>扩展字段：</th>
				<td >
					<input type="text" class="text" id="dtoext3" name="ext3" notNull="true" maxLength="20" value="" checkChName="扩展字段" />
				</td>
				<th>扩展字段：</th>
				<td >
					<input type="text" class="text" id="dtoext4" name="ext4" notNull="true" maxLength="20" value="" checkChName="扩展字段" />
				</td>
			</tr>
			
			<tr>
				<th>创建时间：</th>
				<td >
					<input type="text" class="text" id="dtocreateTime" name="createTime" notNull="true" maxLength="19" value="" checkChName="创建时间" />
				</td>
				<th>更新时间：</th>
				<td >
					<input type="text" class="text" id="dtoupdateTime" name="updateTime" notNull="true" maxLength="19" value="" checkChName="更新时间" />
				</td>
			</tr>
			-->
		</table>
		<!-- 保存 关闭 按钮 在 查询页面进行控制 -->
	</form>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		checkedInit();
	});
</script>
