<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--考题版本表单-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<span class="label label-primary"> <c:if
				test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
		</span>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formSubjectversion">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">题目类型:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[1]']"
						id="entity_tiptype" name="tiptype" value="${entity.tiptype}">
					</td>
				</tr>
				<tr>
					<td class="title">试题id:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[16]']"
						id="entity_subjectid" name="subjectid" value="${entity.subjectid}">
					</td>
				</tr>
				<tr>
					<td class="title">题目描述:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="validType:[,'maxLength[32,767.5]']"
						id="entity_tipnote" name="tipnote" value="${entity.tipnote}">
					</td>
				</tr>
				<tr>
					<td class="title">题目:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[128]']"
						id="entity_tipstr" name="tipstr" value="${entity.tipstr}">
					</td>
				</tr>
				<tr>
					<td class="title">备注:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="validType:[,'maxLength[64]']" id="entity_pcontent"
						name="pcontent" value="${entity.pcontent}"></td>
				</tr>
				<tr>
					<td class="title">状态:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[1]']"
						id="entity_pstate" name="pstate" value="${entity.pstate}">
					</td>
				</tr>
				<tr>
					<td class="title">CUSER:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[16]']"
						id="entity_cuser" name="cuser" value="${entity.cuser}"></td>
				</tr>
				<tr>
					<td class="title">CUSERNAME:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[32]']"
						id="entity_cusername" name="cusername" value="${entity.cusername}">
					</td>
				</tr>
				<tr>
					<td class="title">CTIME:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[8]']"
						id="entity_ctime" name="ctime" value="${entity.ctime}"></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entitySubjectversion" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entitySubjectversion" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formSubjectversion" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionSubjectversion = 'subjectversion/add.do';
	var submitEditActionSubjectversion = 'subjectversion/edit.do';
	var currentPageTypeSubjectversion = '${pageset.operateType}';
	var submitFormSubjectversion;
	$(function() {
		//表单组件对象
		submitFormSubjectversion = $('#dom_formSubjectversion').SubmitForm({
			pageType : currentPageTypeSubjectversion,
			grid : gridSubjectversion,
			currentWindowId : 'winSubjectversion'
		});
		//关闭窗口
		$('#dom_cancle_formSubjectversion').bind('click', function() {
			$('#winSubjectversion').window('close');
		});
		//提交新增数据
		$('#dom_add_entitySubjectversion').bind('click', function() {
			submitFormSubjectversion.postSubmit(submitAddActionSubjectversion);
		});
		//提交修改数据
		$('#dom_edit_entitySubjectversion').bind(
				'click',
				function() {
					submitFormSubjectversion
							.postSubmit(submitEditActionSubjectversion);
				});
	});
//-->
</script>