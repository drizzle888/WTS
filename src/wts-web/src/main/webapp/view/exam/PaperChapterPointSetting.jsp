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
			<input type="hidden" id="entity_id" name="paperSubjectIds" value="${paperSubjectIds}">
			<table class="editTable">
				<tr>  
					<td class="title" style="width: 160px;">为当前${lenght}道题设置分数:</td>
					<td colspan="3"><input type="text" style="width: 120px;"
						class="easyui-validatebox"
						data-options="required:true,validType:['integer','maxLength[5]']"
						id="entity_point" name="point" value="0"></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<a id="dom_add_entityPapersubject" href="javascript:void(0)"
				iconCls="icon-save" class="easyui-linkbutton">保存</a> <a
				id="dom_cancle_formPapersubject" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionPapersubject = 'papersubject/editPoint.do';
	var currentPageTypePapersubject = '${pageset.operateType}';
	var submitFormPapersubject;
	$(function() {
		//表单组件对象
		submitFormPapersubject = $('#dom_formPapersubject').SubmitForm({
			pageType : currentPageTypePapersubject,
			grid : gridPapersubject,
			currentWindowId : 'winPapersubjectPoint'
		});
		//关闭窗口
		$('#dom_cancle_formPapersubject').bind('click', function() {
			$('#winPapersubjectPoint').window('close');
		});
		//提交新增数据
		$('#dom_add_entityPapersubject').bind('click', function() {
			submitFormPapersubject.postSubmit(submitAddActionPapersubject);
		});
	});
//-->
</script>