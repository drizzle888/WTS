<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--考卷表单-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<span class="label label-primary"> <c:if
				test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
		</span>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formPaper">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">考卷名称:</td>
					<td colspan="3"><input type="text" style="width: 420px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[64]']"
						id="entity_name" name="name" value="${entity.name}"></td>
				</tr>
				<tr>
					<td class="title">业务分类:</td>
					<td colspan="3"><input type="hidden" style="width: 120px;"
						id="entity_examtypeid" name="examtypeid"
						value="${entity.examtypeid}"><span id="lable_examtypeid">${examType.name}</span></td>
				</tr>
				<tr>
					<td class="title">建议答题时间:</td>
					<td><input type="text" style="width: 45px;"
						class="easyui-validatebox"
						data-options="required:true,validType:['integer','maxLength[5]']"
						id="entity_advicetime" name="advicetime"
						value="${entity.advicetime}">（分）</td>
					<td class="title">状态:</td>
					<td><select name="pstate" id="entity_pstate"
						val="${entity.pstate}"><option value="1">新建</option>
							<option value="0">禁用</option></select></td>
				</tr>
				<tr>
					<td class="title">答卷简介:</td>
					<td colspan="3"><textarea rows="2" style="width: 420px;"
							class="easyui-validatebox"
							data-options="validType:[,'maxLength[64]']" id="entity_pcontent"
							name="pcontent">${entity.pcontent}</textarea></td>
				</tr>
				<tr>
					<td class="title">答卷说明:</td>
					<td colspan="3"><textarea id="entity_papernote"
							style="display: none;" name="papernote">${entity.papernote}
						</textarea>
						<div style="width: 425px;">
							<jsp:include page="comment/IncludeEditor.jsp">
								<jsp:param value="entity_papernote" name="fieldId" />
								<jsp:param value="${pageset.operateType}" name="type" />
								<jsp:param value="答卷说明" name="fieldTitle" />
							</jsp:include></div></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entityPaper" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityPaper" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formPaper" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionPaper = 'paper/add.do';
	var submitEditActionPaper = 'paper/edit.do';
	var currentPageTypePaper = '${pageset.operateType}';
	var submitFormPaper;
	$(function() {
		//表单组件对象
		submitFormPaper = $('#dom_formPaper').SubmitForm({
			pageType : currentPageTypePaper,
			grid : gridPaper,
			currentWindowId : 'winPaper'
		});
		//关闭窗口
		$('#dom_cancle_formPaper').bind('click', function() {
			$('#winPaper').window('close');
		});
		//提交新增数据
		$('#dom_add_entityPaper').bind('click', function() {
			submitFormPaper.postSubmit(submitAddActionPaper);
		});
		//提交修改数据
		$('#dom_edit_entityPaper').bind('click', function() {
			submitFormPaper.postSubmit(submitEditActionPaper);
		});
		//新增表单
		if (currentPageTypePaper == '1') {
			$('#lable_examtypeid').text($('#PARENTTITLE_RULE').val());
			$('#entity_examtypeid').val($('#PARENTID_RULE').val());
		}
	});
//-->
</script>