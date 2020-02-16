<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--答卷试题表单-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<span class="label label-primary"> <c:if
				test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
		</span>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formPapersubject">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">POINT:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[5]']"
						id="entity_point" name="point" value="${entity.point}"></td>
				</tr>
				<tr>
					<td class="title">SORT:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[5]']"
						id="entity_sort" name="sort" value="${entity.sort}"></td>
				</tr>
				<tr>
					<td class="title">CHAPTERID:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[16]']"
						id="entity_chapterid" name="chapterid" value="${entity.chapterid}">
					</td>
				</tr>
				<tr>
					<td class="title">SUBJECTID:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[16]']"
						id="entity_subjectid" name="subjectid" value="${entity.subjectid}">
					</td>
				</tr>
				<tr>
					<td class="title">VERSIONID:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[16]']"
						id="entity_versionid" name="versionid" value="${entity.versionid}">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entityPapersubject" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityPapersubject" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formPapersubject" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionPapersubject = 'papersubject/add.do';
	var submitEditActionPapersubject = 'papersubject/edit.do';
	var currentPageTypePapersubject = '${pageset.operateType}';
	var submitFormPapersubject;
	$(function() {
		//表单组件对象
		submitFormPapersubject = $('#dom_formPapersubject').SubmitForm({
			pageType : currentPageTypePapersubject,
			grid : gridPapersubject,
			currentWindowId : 'winPapersubject'
		});
		//关闭窗口
		$('#dom_cancle_formPapersubject').bind('click', function() {
			$('#winPapersubject').window('close');
		});
		//提交新增数据
		$('#dom_add_entityPapersubject').bind('click', function() {
			submitFormPapersubject.postSubmit(submitAddActionPapersubject);
		});
		//提交修改数据
		$('#dom_edit_entityPapersubject').bind('click', function() {
			submitFormPapersubject.postSubmit(submitEditActionPapersubject);
		});
	});
//-->
</script>