<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--权限资源表单-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<div class="tableTitle_msg">${MESSAGE}</div>
		<div class="tableTitle_tag">
			<c:if test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if>
			<c:if test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if>
			<c:if test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
		</div>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formAction">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">名称:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[32]']"
						id="entity_name" name="name" value="${entity.name}"></td>
				</tr>
				<tr>
					<td class="title">资源KEY:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:['key','maxLength[64]']"
						id="entity_authkey" name="authkey" value="${entity.authkey}">
					</td>
				</tr>
				<tr>
					<td class="title">是否需要登录:</td>
					<td><select name="loginis" id="entity_loginis"
						val="${entity.loginis}">
							<option value="1">是</option>
							<option value="0">否</option>
					</select></td>
					<td class="title">是否检查用户权限:</td>
					<td><select name="checkis" id="entity_checkis"
						val="${entity.checkis}">
							<option value="1">是</option>
							<option value="0">否</option>
					</select></td>
				</tr>
				<tr>
					<td class="title">状态:</td>
					<td colspan="3"><select name="state" id="entity_state"
						val="${entity.state}">
							<option value="1">可用</option>
							<option value="0">不可用</option>
					</select></td>
				</tr>
				<tr>
					<td class="title">备注:</td>
					<td colspan="3"><textarea rows="2" style="width: 360px;"
							class="easyui-validatebox"
							data-options="validType:[,'maxLength[64]']" id="entity_comments"
							name="comments">${entity.comments}</textarea></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entityAction" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityAction" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formAction" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionAction = 'action/add.do';
	var submitEditActionAction = 'action/edit.do';
	var currentPageTypeAction = '${pageset.operateType}';
	var submitFormAction;
	$(function() {
		//表单组件对象
		submitFormAction = $('#dom_formAction').SubmitForm({
			pageType : currentPageTypeAction,
			grid : gridAction,
			currentWindowId : 'winAction'
		});
		//关闭窗口
		$('#dom_cancle_formAction').bind('click', function() {
			$('#winAction').window('close');
		});
		//提交新增数据
		$('#dom_add_entityAction').bind('click', function() {
			submitFormAction.postSubmit(submitAddActionAction);
		});
		//提交修改数据
		$('#dom_edit_entityAction').bind('click', function() {
			submitFormAction.postSubmit(submitEditActionAction);
		});
	});
//-->
</script>