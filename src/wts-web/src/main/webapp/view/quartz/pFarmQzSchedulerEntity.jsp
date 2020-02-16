<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--计划任务管理-->
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
		<form id="dom_formfarmqzscheduler">
			<input type="hidden" name="id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">
						启动类型:
					</td>
					<td colspan="3">
						<select name="autois" id="entity_autois"
							val="${entity.autois}">
							<option value="1">
								自动
							</option>
							<option value="0">
								手动
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="title">
						任务:
					</td>
					<td colspan="3">
						<input type="text" style="width: 360px;" readonly="readonly"
							class="easyui-validatebox" id="entity_taskidtext"
							value="${taskStr}"
							data-options="required:true,validType:',maxLength[16]'">
						<input type="hidden" id="entity_taskid" name="taskid"
							value="${entity.taskid}" />
						<c:if test="${pageset.operateType==1}">
							<a id="form_farmqztask_a_Choose" href="javascript:void(0)"
								class="easyui-linkbutton" style="color: #000000;">选择</a>
						</c:if>
					</td>
				</tr>
				<tr>
					<td class="title">
						触发计划:
					</td>
					<td colspan="3">
						<input type="text" style="width: 360px;" readonly="readonly"
							class="easyui-validatebox" id="entity_triggeridtext"
							value="${triggerStr}"
							data-options="required:true,validType:',maxLength[16]'">
						<input type="hidden" id="entity_triggerid" name="triggerid"
							value="${entity.triggerid}">
						<c:if test="${pageset.operateType==1}">
							<a id="form_farmqztrigger_a_Choose" href="javascript:void(0)"
								class="easyui-linkbutton" style="color: #000000;">选择</a>
						</c:if>
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
				<a id="dom_add_entityfarmqzscheduler" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityfarmqzscheduler" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formfarmqzscheduler" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionfarmqzscheduler = 'qzScheduler/addSubmit.do';
	var submitEditActionfarmqzscheduler = 'qzScheduler/editSubmit.do';
	var currentPageTypefarmqzscheduler = '${pageset.operateType}';
	var submitFormfarmqzscheduler;
	$(function() {
		//表单组件对象
		submitFormfarmqzscheduler = $('#dom_formfarmqzscheduler').SubmitForm( {
			pageType : currentPageTypefarmqzscheduler,
			grid : gridfarmqzscheduler,
			currentWindowId : 'winfarmqzscheduler'
		});
		//关闭窗口
		$('#dom_cancle_formfarmqzscheduler').bind('click', function() {
			$('#winfarmqzscheduler').window('close');
		});
		//提交新增数据
		$('#dom_add_entityfarmqzscheduler').bind(
				'click',
				function() {
					submitFormfarmqzscheduler
							.postSubmit(submitAddActionfarmqzscheduler);
				});
		//提交修改数据
		$('#dom_edit_entityfarmqzscheduler').bind(
				'click',
				function() {
					submitFormfarmqzscheduler
							.postSubmit(submitEditActionfarmqzscheduler);
				});
		$('#form_farmqztask_a_Choose').bindChooseWindow('chooseWinfarmqztask',
				{
					width : 600,
					height : 300,
					modal : true,
					url : 'qzScheduler/chooseTask.do',
					title : '选择',
					callback : function(rows) {
						$('#entity_taskidtext').val(rows[0].NAME);
						$('#entity_taskid').val(rows[0].ID);
					}
				});
		$('#form_farmqztrigger_a_Choose').bindChooseWindow(
				'chooseWinfarmqztrigger', {
					width : 600,
					height : 300,
					modal : true,
					url : 'qzScheduler/chooseTrigger.do',
					title : '选择',
					callback : function(rows) {
						$('#entity_triggeridtext').val(rows[0].NAME);
						$('#entity_triggerid').val(rows[0].ID);
					}
				});
	});
	//-->
</script>