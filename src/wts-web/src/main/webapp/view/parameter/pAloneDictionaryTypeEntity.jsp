<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--数据类型-->
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
		<form id="dom_formparameter">
			<input type="hidden" id="entity_id" name="id"
				value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">
						名称:
					</td>
					<td colspan="3">
						<input type="text" style="width: 430px;"
							class="easyui-validatebox"
							data-options="required:true,validType:',maxLength[64]'"
							id="entity_name" name="name" value="${entity.name}" />
						<input type="hidden" name="entity" value="${entity.entity}">
					</td>
				</tr>
				<tr>
					<td class="title">
						类型:
					</td>
					<td colspan="3">
						<input type="text" style="width: 430px;"
							class="easyui-validatebox"
							data-options="required:true,validType:',maxLength[128]'"
							id="entity_entitytype" name="entitytype"
							value="${entity.entitytype}" />
					</td>
				</tr>
				<tr>
					<td class="title">
						排序号:
					</td>
					<td colspan="1">
						<input type="text" class="easyui-validatebox"
							data-options="required:true,validType:''" id="entity_sort"
							name="sort" value="${entity.sort}">

					</td>
					<td class="title">
						状态:
					</td>
					<td colspan="1">
						<select name="state" id="entity_state"
							val="${entity.state}">
							<option value="1">
								可用
							</option>
							<option value="0">
								禁用
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="title">
						备注:
					</td>
					<td colspan="3">
						<textarea rows="3" style="width: 430px;"
							class="easyui-validatebox"
							data-options="required:false,validType:',maxLength[64]'"
							id="entity_comments" name="comments">${entity.comments}</textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entityparameter" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityparameter" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formparameter" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionparameter = 'dictionaryType/add.do';
	var submitEditActionparameter = 'dictionaryType/edit.do';
	var currentPageTypeparameter = '${pageset.operateType}';
	var submitFormparameter;
	$(function() {
		//表单组件对象
		submitFormparameter = $('#dom_formparameter').SubmitForm( {
			pageType : currentPageTypeparameter,
			grid : gridDiclisttype,
			currentWindowId : 'winDiclisttype'
		});
		//关闭窗口
		$('#dom_cancle_formparameter').bind('click', function() {
			$('#winDiclisttype').window('close');
		});
		//提交新增数据
		$('#dom_add_entityparameter').bind('click', function() {
			submitFormparameter.postSubmit(submitAddActionparameter);
		});
		//提交修改数据
		$('#dom_edit_entityparameter').bind('click', function() {
			submitFormparameter.postSubmit(submitEditActionparameter);
		});
	});
	//-->
</script>