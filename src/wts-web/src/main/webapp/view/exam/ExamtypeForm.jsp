<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--业务分类表单-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<span class="label label-primary"> <c:if
				test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
		</span>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formExamtype">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">上级分类:</td>
					<td colspan="3"><c:if test="${parent!=null&&parent.id!=null}">
        ${parent.name}
        <input type="hidden" name='parentid' value="${parent.id}" />
						</c:if> <c:if test="${parent==null||parent.id==null}">
        无
      </c:if></td>
				</tr>
				<tr>
					<td class="title">名称:</td>
					<td><input type="text" style="width: 130px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[32]']"
						id="entity_name" name="name" value="${entity.name}"></td>
					<td class="title">状态:</td>
					<td><select name="state" id="entity_state"
						val="${entity.state}"><option value="1">可用</option>
							<option value="0">禁用</option></select></td>
				</tr>
				<tr>
					<td class="title">排序:</td>
					<td colspan="3"><input type="text" style="width: 130px;"
						class="easyui-validatebox"
						data-options="required:true,validType:['integer','maxLength[5]']"
						id="entity_sort" name="sort" value="${entity.sort}"></td>
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
				<a id="dom_add_entityExamtype" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityExamtype" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formExamtype" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionExamtype = 'examtype/add.do';
	var submitEditActionExamtype = 'examtype/edit.do';
	var currentPageTypeExamtype = '${pageset.operateType}';
	var submitFormExamtype;
	$(function() {
		//表单组件对象
		submitFormExamtype = $('#dom_formExamtype').SubmitForm({
			pageType : currentPageTypeExamtype,
			grid : gridExamtype,
			currentWindowId : 'winExamtype'
		});
		//关闭窗口
		$('#dom_cancle_formExamtype').bind('click', function() {
			$('#winExamtype').window('close');
		});
		//提交新增数据
		$('#dom_add_entityExamtype').bind('click', function() {
			submitFormExamtype.postSubmit(submitAddActionExamtype, function(flag) {
				$.messager.confirm('确认对话框', '数据更新,是否重新加载左侧组织机构树？', function(r){
					if (r){
						$('#examTypeTree').tree('reload');
					}
				});
				return true;
			});
		});
		//提交修改数据
		$('#dom_edit_entityExamtype').bind('click', function() {
			submitFormExamtype.postSubmit(submitEditActionExamtype, function(flag) {
				$.messager.confirm('确认对话框', '数据更新,是否重新加载左侧组织机构树？', function(r){
					if (r){
						$('#examTypeTree').tree('reload');
					}
				});
				return true;
			});
		});
	});
//-->
</script>