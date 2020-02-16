<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<form id="dom_var_entity">
	<div class="TableTitle">
		<div class="tableTitle_msg">${MESSAGE}</div>
		<div class="tableTitle_tag">
			<c:if test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if>
			<c:if test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if>
			<c:if test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
		</div>
		<input type="hidden" name="entity.id" value="${entity.id}">
	</div>
	<table class="editTable">
		<tr>
			<td class="title">
				原密码:
			</td>
			<td>
				<input type="password" class="easyui-validatebox"
					data-options="required:true" id="passwordl" name="passwordl">
			</td>
		</tr>
		<tr>
			<td class="title">
				新密码:
			</td>
			<td>
				<input type="password" class="easyui-validatebox"
					data-options="required:true" id="passwordn1" name="passwordn1">
			</td>
		</tr>
		<tr>
			<td class="title">
				重复新密码:
			</td>
			<td>
				<input type="password" class="easyui-validatebox"
					data-options="required:true,validType:'same[\'passwordn1\']'" id="passwordn2" name="passwordn2">
			</td>
		</tr>
		<tr>
			<th colspan="2">
				<div class="div_button" style="text-align: center;">
					<a id="dom_var_edit_entity" href="javascript:void(0)"
						class="easyui-linkbutton" >修改</a>
				</div>
			</th>
		</tr>
	</table>
</form>
<script type="text/javascript">
	var submitEditAction = 'user/LoginUser_PassWordUpdata.do';
	var currentPageType = '${pageset.operateType}';
	var DOM_DATAFORM_FORM = '#dom_var_entity';
	var LINK_FORM_EDIT = '#dom_var_edit_entity';
	$(function() {
		initEntity();
	});
	function initEntity() {
		var submitForm = $(DOM_DATAFORM_FORM).SubmitForm( {
			pageType : currentPageType,
			currentWindow : '#DIV_EDIT_PASSWORD_WINDOW'
		});
		$(LINK_FORM_EDIT).bind('click', function() {
				submitForm.postSubmit(submitEditAction, null);
		});
	}
	//-->
</script>
