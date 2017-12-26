<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/StaticJavascript2.jsp"%>

<div id="formPageSwap">
	<form id="updateForm" name="updateForm" isCheck="true" action="com.fintech.modules.mdcertdetail.controller.MdCertDetailController">
		<input type="hidden" class="text" id="dtoid" name="id" notNull="false" maxLength="11" value="${dto.id}" />
		<table id="updateTableId" class="formTableSwap" border="0" align="center" cellpadding="0" cellspacing="1">
			<tr>
				<th>客户姓名：</th>
				<td >
					<input type="text" class="text" id="dtocustName" name="custName" notNull="true" maxLength="50" value="${dto.custName}" checkChName="客户姓名" />
				</td>
				<th>证件类型：</th>
				<td >
					<input type="text" class="text" id="dtocardType" name="cardType" notNull="true" maxLength="4" value="${dto.cardType}" checkChName="证件类型" />
				</td>
			</tr>
			<tr>
				<th>证件号码：</th>
				<td >
					<input type="text" class="text" id="dtocardNo" name="cardNo" notNull="true" maxLength="50" value="${dto.cardNo}" checkChName="证件号码" />
				</td>
				<th>认证状态：</th>
				<td >
					<input type="text" class="text" id="dtocertStatu" name="certStatu" notNull="true" maxLength="50" value="${dto.certStatu}" checkChName="认证状态" />
				</td>
			</tr>
			<tr>
				<th>认证渠道：</th>
				<td >
					<input type="text" class="text" id="dtocertChannel" name="certChannel" notNull="true" maxLength="50" value="${dto.certChannel}" checkChName="认证渠道" />
				</td>
				<th>系统来源：</th>
				<td >
					<input type="text" class="text" id="dtosysSource" name="sysSource" notNull="true" maxLength="50" value="${dto.sysSource}" checkChName="系统来源" />
				</td>
			</tr>
			<tr>
				<th>扩展字段：</th>
				<td >
					<input type="text" class="text" id="dtoext1" name="ext1" notNull="true" maxLength="20" value="${dto.ext1}" checkChName="扩展字段" />
				</td>
				<th>扩展字段：</th>
				<td >
					<input type="text" class="text" id="dtoext2" name="ext2" notNull="true" maxLength="20" value="${dto.ext2}" checkChName="扩展字段" />
				</td>
			</tr>
			<tr>
				<th>扩展字段：</th>
				<td >
					<input type="text" class="text" id="dtoext3" name="ext3" notNull="true" maxLength="20" value="${dto.ext3}" checkChName="扩展字段" />
				</td>
				<th>扩展字段：</th>
				<td >
					<input type="text" class="text" id="dtoext4" name="ext4" notNull="true" maxLength="20" value="${dto.ext4}" checkChName="扩展字段" />
				</td>
			</tr>
			<tr>
				<th>创建时间：</th>
				<td >
					<input type="text" class="text" id="dtocreateTime" name="createTime" notNull="true" maxLength="19" value="${dto.createTime}" checkChName="创建时间" />
				</td>
				<th>更新时间：</th>
				<td >
					<input type="text" class="text" id="dtoupdateTime" name="updateTime" notNull="true" maxLength="19" value="${dto.updateTime}" checkChName="更新时间" />
				</td>
			</tr>
		</table>
		<!-- 保存 关闭 按钮 在 查询页面进行控制 -->
	</form>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		checkedInit();
	});
</script>
