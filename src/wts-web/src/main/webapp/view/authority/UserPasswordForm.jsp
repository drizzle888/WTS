<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--用户表单-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<div class="tableTitle_msg">${MESSAGE}</div>
		<div class="tableTitle_tag">设置密码</div>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formPasswordUser">
			<input type="hidden" id="entity_id" name="userid" value="${user.id}">
			<table class="editTable">
				<tr>
					<td class="title">人员:</td>
					<td>${user.name}</td>
				</tr>
				<tr>
					<td class="title">新密码:</td>
					<td><input type="password" style="width: 240px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[32]']"
						id="entity_password" name="password"></td>
				</tr>
				<tr>
					<td class="title">重复新密码:</td>
					<td><input type="password" style="width: 240px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[32]']"
						id="entity_passwordnew" name="passwordnew"></td>
				</tr>
				<tr>
					<td class="title">管理员密码:</td>
					<td><input type="password" style="width: 240px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[32]']"
						id="entity_mngpassword" name="mngpassword"></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<a id="dom_edit_passwordUser" href="javascript:void(0)"
				iconCls="icon-save" class="easyui-linkbutton">修改</a> 
			<a
				id="dom_cancle_passwordformUser" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitEditPasswordUser = 'user/setingPassWordSubmit.do';
	var currentPageTypePassUser = '${pageset.operateType}';
	var submitPassWordFormUser;
	$(function() {
		//表单组件对象
		submitPassWordFormUser = $('#dom_formPasswordUser').SubmitForm({
			pageType : currentPageTypePassUser,
			grid : gridUser,
			currentWindowId : 'winPasswordUser'
		});
		//关闭窗口
		$('#dom_cancle_passwordformUser').bind('click', function() {
			$('#winPasswordUser').window('close');
		});
		//提交修改数据
		$('#dom_edit_passwordUser').bind('click', function() {
			submitPassWordFormUser.postSubmit(submitEditPasswordUser);
		});
	});
//-->
</script>