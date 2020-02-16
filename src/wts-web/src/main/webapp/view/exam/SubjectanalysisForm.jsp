<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--试题解析表单-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<span class="label label-primary"> <c:if
				test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
		</span>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formSubjectanalysis">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<input type="hidden" id="entity_subjectid" name="subjectid" value="${entity.subjectid}${subjectid}">
			<table class="editTable">
				<tr>
					<td class="title">备注:</td>
					<td colspan="3"><input type="text" style="width: 420px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[64]']" id="entity_pcontent"
						name="pcontent" value="${entity.pcontent}"></td>
				</tr>
				<tr>
					<td class="title"></td>
					<td colspan="3"><textarea id="entity_text"
							style="display: none;" name="text">${entity.text}</textarea>
						<div style="width: 420px;"><jsp:include
								page="comment/IncludeEditor.jsp">
								<jsp:param value="entity_text" name="fieldId" />
								<jsp:param value="${operateType}" name="type" />
								<jsp:param value="解析" name="fieldTitle" />
							</jsp:include></div></td>
				</tr>
				<tr>
					<td class="title">状态:</td>
					<td colspan="3"><select name="pstate" id="entity_pstate"
						val="${entity.pstate}"><option value="1">可用</option>
							<option value="0">禁用</option></select>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entitySubjectanalysis" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entitySubjectanalysis" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formSubjectanalysis" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionSubjectanalysis = 'subjectanalysis/add.do';
	var submitEditActionSubjectanalysis = 'subjectanalysis/edit.do';
	var currentPageTypeSubjectanalysis = '${pageset.operateType}';
	var submitFormSubjectanalysis;
	$(function() {
		//表单组件对象
		submitFormSubjectanalysis = $('#dom_formSubjectanalysis').SubmitForm({
			pageType : currentPageTypeSubjectanalysis,
			grid : gridSubjectanalysis,
			currentWindowId : 'winSubjectanalysis'
		});
		//关闭窗口
		$('#dom_cancle_formSubjectanalysis').bind('click', function() {
			$('#winSubjectanalysis').window('close');
		});
		//提交新增数据
		$('#dom_add_entitySubjectanalysis').bind(
				'click',
				function() {
					submitFormSubjectanalysis
							.postSubmit(submitAddActionSubjectanalysis);
				});
		//提交修改数据
		$('#dom_edit_entitySubjectanalysis').bind(
				'click',
				function() {
					submitFormSubjectanalysis
							.postSubmit(submitEditActionSubjectanalysis);
				});
	});
//-->
</script>