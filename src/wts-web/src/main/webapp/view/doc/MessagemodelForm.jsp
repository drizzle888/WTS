<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--消息模板表单-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<span class="label label-primary">
			<c:if test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if>
			<c:if test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if>
			<c:if test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
		</span>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formMessagemodel">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<table class="editTable">

				<tr>
					<td class="title">消息类型:</td>
					<td>${entity.title}</td>
					<td class="title">类型KEY:</td>
					<td>${entity.typekey}</td>
				</tr>
				<tr>
					<td class="title">接收人:</td>
					<td colspan="3">${entity.overer}</td>
				</tr>
				<tr>
					<td class="title">标题模板:</td>
					<td colspan="3">
						<span style="color: blue; font-weight: 700;">可用参数</span>
						:
						<span style="color: blue;">${messageType.titleDescribe}</span>
						<br />
						<textarea style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[256]']" id="entity_titlemodel" name="titlemodel">${entity.titlemodel}</textarea>
					</td>
				</tr>
				<tr>
					<td class="title">内容模板:</td>
					<td colspan="3">
						<span style="color: blue; font-weight: 700;">可用参数</span>
						:
						<span style="color: blue;">${messageType.contentDescribe}</span>
						<br />
						<textarea style="width: 360px;" class="easyui-validatebox" data-options="required:true,validType:[,'maxLength[256]']" id="entity_contentmodel" name="contentmodel">${entity.contentmodel}</textarea>
					</td>
				</tr>
				<tr>
					<td class="title">备注:</td>
					<td colspan="3">
						<textarea style="width: 360px;" class="easyui-validatebox" data-options="validType:[,'maxLength[64]']" id="entity_pcontent" name="pcontent">${entity.pcontent}</textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entityMessagemodel" href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityMessagemodel" href="javascript:void(0)" iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formMessagemodel" href="javascript:void(0)" iconCls="icon-cancel" class="easyui-linkbutton" style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionMessagemodel = 'messagemodel/add.do';
	var submitEditActionMessagemodel = 'messagemodel/edit.do';
	var currentPageTypeMessagemodel = '${pageset.operateType}';
	var submitFormMessagemodel;
	$(function() {
		//表单组件对象
		submitFormMessagemodel = $('#dom_formMessagemodel').SubmitForm({
			pageType : currentPageTypeMessagemodel,
			grid : gridMessagemodel,
			currentWindowId : 'winMessagemodel'
		});
		//关闭窗口
		$('#dom_cancle_formMessagemodel').bind('click', function() {
			$('#winMessagemodel').window('close');
		});
		//提交新增数据
		$('#dom_add_entityMessagemodel').bind('click', function() {
			submitFormMessagemodel.postSubmit(submitAddActionMessagemodel);
		});
		//提交修改数据
		$('#dom_edit_entityMessagemodel').bind('click', function() {
			submitFormMessagemodel.postSubmit(submitEditActionMessagemodel);
		});
	});
//-->
</script>