<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--系统日志-->
<!--组件库表单-->
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
		<form id="dom_formLog">
			<input type="hidden" id="entity_id" name="id"
				value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">
						生成时间:
					</td>
					<td colspan="3">
						<input type="text" class="easyui-validatebox"
							data-options="required:true,validType:',maxLength[7]'"
							id="entity_ctime" name="ctime" value="${entity.ctime}">

					</td>
				</tr>
				<tr>
					<td class="title">
						日志:
					</td>
					<td colspan="3">
						<textarea rows="3" cols="32" class="easyui-validatebox"
							data-options="required:true,validType:',maxLength[256]'"
							id="entity_describes" name="describes">${entity.describes}</textarea>
					</td>
				</tr>
				<tr>
					<td class="title">
						操作人:
					</td>
					<td colspan="3">
						<input type="text" class="easyui-validatebox"
							data-options="required:true,validType:',maxLength[16]'"
							id="entity_appuser" name="appuser"
							value="${entity.appuser}">

					</td>
				</tr>
				<tr>
					<td class="title">
						日志级别:
					</td>
					<td colspan="3">
						<input type="text" class="easyui-validatebox"
							data-options="required:false,validType:',maxLength[16]'"
							id="entity_levels" name="levels" value="${entity.levels}">

					</td>
				</tr>
				<tr>
					<td class="title">
						方法:
					</td>
					<td colspan="3">
						<textarea rows="3" cols="32" class="easyui-validatebox"
							data-options="required:false,validType:',maxLength[64]'"
							id="entity_method" name="method">${entity.method}</textarea>
					</td>
				</tr>
				<tr>
					<td class="title">
						类:
					</td>
					<td colspan="3">
						<textarea rows="3" cols="32" class="easyui-validatebox"
							data-options="required:false,validType:',maxLength[64]'"
							id="entity_classname" name="classname">${entity.classname}</textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entityLog" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityLog" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formLog" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionLog = 'admin/AloneApplogaddCommit.do';
	var submitEditActionLog = 'admin/AloneApplogeditCommit.do';
	var currentPageTypeLog = '${pageset.operateType}';
	var submitFormLog ;
	$(function() {
		//表单组件对象
		submitFormLog = $('#dom_formLog').SubmitForm( {
			pageType : currentPageTypeLog,
			grid : gridLog,
			currentWindowId : 'winLog'
		});
		//关闭窗口
		$('#dom_cancle_formLog').bind('click', function() {
			$('#winLog').window('close');
		});
		//提交新增数据
		$('#dom_add_entityLog').bind('click', function() {
			submitFormLog.postSubmit(submitAddActionLog);
		});
		//提交修改数据
		$('#dom_edit_entityLog').bind('click', function() {
			submitFormLog.postSubmit(submitEditActionLog);
		});
		$('#chooseImg').bind(
				'click',
				function() {
					$.farm.openWindow( { id : 'windowChooseImg', width : 600, height :300,modal : true, url : "admin/ALONEMenu_ACTION_cssIcon.do", title : '选择图标' });
				});
	});
	//-->
</script>