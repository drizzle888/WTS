<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--考题答案表单-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<span class="label label-primary"> <c:if
				test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
		</span>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formSubjectanswer">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<input type="hidden" id="entity_id" name="versionid"
				value="${versionid}">
			<table class="editTable">
				<tr>
					<td class="title">排序:</td>
					<td><input type="text" style="width: 120px;"
						class="easyui-validatebox"
						data-options="required:true,validType:['integer','maxLength[5]']"
						id="entity_sort" name="sort" value="${entity.sort}"></td>
					<td class="title">正确答案:</td>
					<td><select id="entity_rightanswer" name="rightanswer"
						val="${entity.rightanswer}">
							<option value="0">错误答案</option>
							<option value="1">正确答案</option>
					</select></td>
				</tr>
				<tr>
					<td class="title">选项:</td>
					<td colspan="3"><textarea rows="3" style="width: 420px;"
							class="easyui-validatebox"
							data-options="required:true,validType:[,'maxLength[256]']"
							id="entity_answer" name="answer">${entity.answer}</textarea></td>
				</tr>
				<tr>
					<td class="title"></td>
					<td colspan="3"><textarea id="entity_answernote"
							style="display: none;" name="answernote">${entity.answernote}</textarea>
						<div style="width: 420px;"><jsp:include
								page="../../comment/IncludeEditor.jsp">
								<jsp:param value="entity_answernote" name="fieldId" />
								<jsp:param value="${operateType}" name="type" />
								<jsp:param value="选项描述 " name="fieldTitle" />
							</jsp:include></div></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entitySubjectanswer" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entitySubjectanswer" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formSubjectanswer" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionSubjectanswer = 'subjectanswer/add.do';
	var submitEditActionSubjectanswer = 'subjectanswer/edit.do';
	var currentPageTypeSubjectanswer = '${pageset.operateType}';
	var submitFormSubjectanswer;
	$(function() {
		//表单组件对象
		submitFormSubjectanswer = $('#dom_formSubjectanswer').SubmitForm({
			pageType : currentPageTypeSubjectanswer,
			grid : gridSubjectanswer,
			currentWindowId : 'winSubjectanswer'
		});
		//关闭窗口
		$('#dom_cancle_formSubjectanswer').bind('click', function() {
			$('#winSubjectanswer').window('close');
		});
		//提交新增数据
		$('#dom_add_entitySubjectanswer').bind('click', function() {
			submitFormSubjectanswer.postSubmit(submitAddActionSubjectanswer);
		});
		//提交修改数据
		$('#dom_edit_entitySubjectanswer').bind('click', function() {
			submitFormSubjectanswer.postSubmit(submitEditActionSubjectanswer);
		});
	});
//-->
</script>