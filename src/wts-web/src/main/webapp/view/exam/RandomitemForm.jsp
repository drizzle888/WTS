<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--答卷随机规则表单-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<span class="label label-primary"> <c:if
				test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
		</span>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formRandomitem">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">规则项名称:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[32]']"
						id="entity_name" name="name" value="${entity.name}"></td>
				</tr>
				<tr>
					<td class="title">备注:</td>
					<td colspan="3"><textarea style="width: 360px;"
							class="easyui-validatebox"
							data-options="validType:[,'maxLength[64]']" id="entity_pcontent"
							name="pcontent">${entity.pcontent}</textarea></td>
				</tr>
				<tr>
					<td class="title">状态:</td>
					<td colspan="3"><select style="width: 360px;"
						id="entity_pstate" name="pstate" val="${entity.pstate}">
							<option value="1">可用</option>
							<option value="0">禁用</option>
					</select></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entityRandomitem" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityRandomitem" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formRandomitem" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionRandomitem = 'randomitem/add.do';
	var submitEditActionRandomitem = 'randomitem/edit.do';
	var currentPageTypeRandomitem = '${pageset.operateType}';
	var submitFormRandomitem;
	$(function() {
		//表单组件对象
		submitFormRandomitem = $('#dom_formRandomitem').SubmitForm({
			pageType : currentPageTypeRandomitem,
			grid : gridRandomitem,
			currentWindowId : 'winRandomitem'
		});
		//关闭窗口
		$('#dom_cancle_formRandomitem').bind('click', function() {
			$('#winRandomitem').window('close');
		});
		//提交新增数据
		$('#dom_add_entityRandomitem').bind('click', function() {
			submitFormRandomitem.postSubmit(submitAddActionRandomitem);
		});
		//提交修改数据
		$('#dom_edit_entityRandomitem').bind('click', function() {
			submitFormRandomitem.postSubmit(submitEditActionRandomitem);
		});
	});
//-->
</script>