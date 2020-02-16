<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--任务定义-->
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
		<form id="dom_formfarmqztask">
			<input type="hidden" name="id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">
						名称:
					</td>
					<td colspan="3">
						<input class="easyui-validatebox" style="width: 360px;"
							data-options="required:true,validType:',maxLength[64]'"
							id="entity_name" name="name" value="${entity.name}">
					</td>
				</tr>
				<tr>
					<td class="title">
						类:
					</td>
					<td colspan="3">
						实现了org.quartz.Job接口的类（如：com.farm.quartz.test.MyJob）
						<br />
						<input class="easyui-combobox" style="width: 360px;"
							id="entity_jobclass" name="jobclass"
							value="${entity.jobclass}"
							data-options="required:true,validType:',maxLength[64]',valueField: 'value', textField: 'label', data: [{ label: '命令行任务', value: 'com.farm.quartz.job.impl.CommandRuningJob' }]" />
					</td>
				</tr>
				<tr>
					<td class="title">
						任务参数:
					</td>
					<td colspan="3">
						<textarea rows="1" style="width: 360px;"
							class="easyui-validatebox"
							data-options="required:false,validType:',maxLength[512]'"
							id="entity_pcontent" name="jobparas">${entity.jobparas}</textarea>
					</td>
				</tr>
				<tr>
					<td class="title">
						备注:
					</td>
					<td colspan="3">
						<textarea rows="3" style="width: 360px;"
							class="easyui-validatebox"
							data-options="required:false,validType:',maxLength[64]'"
							id="entity_pcontent" name="pcontent">${entity.pcontent}</textarea>
					</td>
				</tr>
				<c:if test="${pageset.operateType==1}">
					<!--新增-->
				</c:if>
				<c:if test="${pageset.operateType==2}">
					<!--修改-->
				</c:if>
				<c:if test="${pageset.operateType==0}">
					<!--浏览-->
				</c:if>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entityfarmqztask" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityfarmqztask" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formfarmqztask" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionfarmqztask = 'qzTask/add.do';
	var submitEditActionfarmqztask = 'qzTask/edit.do';
	var currentPageTypefarmqztask = '${pageset.operateType}';
	var submitFormfarmqztask;
	$(function() {
		submitFormfarmqztask = $('#dom_formfarmqztask').SubmitForm( {
			pageType : currentPageTypefarmqztask,
			grid : gridfarmqztask,
			currentWindowId : 'winfarmqztask'
		});
		$('#dom_cancle_formfarmqztask').bind('click', function() {
			$('#winfarmqztask').window('close');
		});
		$('#dom_add_entityfarmqztask').bind('click', function() {
			submitFormfarmqztask.postSubmit(submitAddActionfarmqztask);
		});
		$('#dom_edit_entityfarmqztask').bind('click', function() {
			submitFormfarmqztask.postSubmit(submitEditActionfarmqztask);
		});
	});
	//-->
</script>