<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--用户消息表单-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<div class="tableTitle_msg">${MESSAGE}</div>
		<div class="tableTitle_tag">
			<c:if test="${pageset.operateType==1}">消息发送</c:if>
		</div>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formUsermessage">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">接收人:</td>
					<td colspan="3">'${userDemo.name}'等${userNum}人 <input
						type="hidden" name="userids" value="${userIds}">
					</td>
				</tr>
				<tr>
					<td class="title">消息主题:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[32]']"
						id="entity_title" name="title" value="${entity.title}"></td>
				</tr>
				<tr>
					<td class="title">消息内容:</td>
					<td colspan="3"><textarea rows="5" style="width: 360px;"
							class="easyui-validatebox"
							data-options="required:true,validType:[,'maxLength[256]']" id="entity_content"
							name="content">${entity.content}</textarea></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<a id="dom_add_entityUsermessage" href="javascript:void(0)"
				iconCls="icon-save" class="easyui-linkbutton">发送</a> <a
				id="dom_cancle_formUsermessage" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var currentPageTypeUsermessage = '${pageset.operateType}';
	var submitAddActionUsermessage = 'usermessage/sendMessage.do';
	var submitFormUsermessage;
	$(function() {
		//表单组件对象
		submitFormUsermessage = $('#dom_formUsermessage').SubmitForm({
			pageType : currentPageTypeUsermessage,
			grid : gridMessageconsole,
			currentWindowId : 'winUsermessageSender'
		});
		//关闭窗口
		$('#dom_cancle_formUsermessage').bind('click', function() {
			$('#winUsermessageSender').window('close');
		});
		//提交新增数据
		$('#dom_add_entityUsermessage').bind('click', function() {
			$.messager.progress({
				title : '提示',
				msg : '',
				text : '执行中...'
			});
			submitFormUsermessage.postSubmit(submitAddActionUsermessage,function(flag){
				$.messager.progress('close');
				return true;
			});
		});
	});
//-->
</script>