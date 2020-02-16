<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%><%@ taglib
	uri="/view/conf/tip.tld" prefix="TIP"%>
<link rel="stylesheet" type="text/css"
	href="text/lib/kindeditor/editInner.css">
<!--引用材料表单-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<span class="label label-primary"> <c:if
				test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
		</span>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formMaterial">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">标题:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[256]']"
						id="entity_title" name="title" value="${entity.title}"></td>
				</tr>
				<c:if test="${pageset.operateType!=0}">
					<tr>
						<td colspan="4"><textarea id="entity_text"
								style="display: none;" name="text">${entity.text}</textarea>
							<div style="width: 100%;"><jsp:include
									page="comment/IncludeEditor.jsp">
									<jsp:param value="entity_text" name="fieldId" />
									<jsp:param value="${pageset.operateType}" name="type" />
									<jsp:param value="资料正文 " name="fieldTitle" />
								</jsp:include></div></td>
					</tr>
				</c:if>
				<c:if test="${pageset.operateType==0}">
					<tr>
						<td colspan="4" class="ke-content"><TIP:InitHtmlContentTag html="${entity.text}"></TIP:InitHtmlContentTag></td>
					</tr>
				</c:if>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entityMaterial" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityMaterial" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formMaterial" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionMaterial = 'material/add.do';
	var submitEditActionMaterial = 'material/edit.do';
	var currentPageTypeMaterial = '${pageset.operateType}';
	var submitFormMaterial;
	$(function() {
		//表单组件对象
		submitFormMaterial = $('#dom_formMaterial').SubmitForm({
			pageType : currentPageTypeMaterial,
			grid : gridMaterial,
			currentWindowId : 'winMaterial'
		});

		//关闭窗口
		$('#dom_cancle_formMaterial').bind('click', function() {
			$('#winMaterial').window('close');
		});
		//提交新增数据
		$('#dom_add_entityMaterial').bind('click', function() {
			submitFormMaterial.postSubmit(submitAddActionMaterial);
		});
		//提交修改数据
		$('#dom_edit_entityMaterial').bind('click', function() {
			submitFormMaterial.postSubmit(submitEditActionMaterial);
		});
	});
//-->
</script>