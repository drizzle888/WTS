<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--用户消息表单-->
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
		<form id="dom_formUsermessage">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">阅读人:</td>
					<td colspan="3"><c:if test="${pageset.operateType!=0}">
							<input type="text" style="width: 165px;" id="entity_readuserid"
								name="readuserid">
						</c:if> <c:if test="${pageset.operateType==0}">
					        ${readusername}
				         </c:if></td>
				</tr>
				<tr>
					<td class="title">消息主题:</td>
					<td><input type="text" style="width: 150px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[32]']"
						id="entity_title" name="title" value="${entity.title}"></td>
					<td class="title"><c:if test="${pageset.operateType!=1}">阅读状态:</c:if></td>
					<td><c:if test="${pageset.operateType!=1}">
							<select name="readstate" val="${entity.readstate }">
								<option value="0">未读</option>
								<option value="1">已读</option>
							</select>
						</c:if></td>
				</tr>
				<tr>
					<td class="title">消息内容:</td>
					<td colspan="3"><textarea rows="2" style="width: 360px;"
							class="easyui-validatebox"
							data-options="validType:[,'maxLength[256]']" id="entity_content"
							name="content">${entity.content}</textarea></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entityUsermessage" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityUsermessage" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formUsermessage" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionUsermessage = 'usermessage/add.do';
	var submitEditActionUsermessage = 'usermessage/edit.do';
	var currentPageTypeUsermessage = '${pageset.operateType}';
	var submitFormUsermessage;
	$(function() {
		//表单组件对象
		submitFormUsermessage = $('#dom_formUsermessage').SubmitForm({
			pageType : currentPageTypeUsermessage,
			grid : gridUsermessage,
			currentWindowId : 'winUsermessage'
		});
		//关闭窗口
		$('#dom_cancle_formUsermessage').bind('click', function() {
			$('#winUsermessage').window('close');
		});
		//提交新增数据
		$('#dom_add_entityUsermessage').bind('click', function() {
			submitFormUsermessage.postSubmit(submitAddActionUsermessage);
		});
		//提交修改数据
		$('#dom_edit_entityUsermessage').bind('click', function() {
			submitFormUsermessage.postSubmit(submitEditActionUsermessage);
		});

		$('#entity_readuserid').textbox({
			required : true,
			icons : [ {
				iconCls : 'icon-special-offer',
				handler : chooseUser
			} ],
			editable : false,
			validType : [ , 'maxLength[16]' ]
		});

		chooseWindowCallBackHandle = function(row) {
			$('#entity_readuserid').textbox('setValue', row[0].ID);
			$('#entity_readuserid').textbox('setText', row[0].NAME);
			$("#chooseUserWin").window('close');
		};

		$('#entity_readuserid').textbox('setValue', "${entity.readuserid}");
		$('#entity_readuserid').textbox('setText', "${readusername}");
	});

	//选择用户
	function chooseUser() {
		$.farm.openWindow({
			id : 'chooseUserWin',
			width : 600,
			height : 400,
			modal : true,
			url : 'user/chooseUser.do',
			title : '导入用户'
		});
	}
//-->
</script>