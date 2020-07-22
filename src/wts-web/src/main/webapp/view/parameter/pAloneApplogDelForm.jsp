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
		<form id="dom_delformLog">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">起始日志时间:</td>
					<td colspan="3"><input type="text" class="easyui-datebox"
						data-options="required:true,validType:',maxLength[27]'"
						id="entity_stime" name="ctime" value="${entity.ctime}"></td>
				</tr>
				<tr>
					<td class="title">结束日志时间:</td>
					<td colspan="3"><input type="text" class="easyui-datebox"
						data-options="required:true,validType:',maxLength[27]'"
						id="entity_etime" name="etime" value="${entity.ctime}"></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<a id="dom_add_entityDelLog" href="javascript:void(0)"
				iconCls="icon-remove" class="easyui-linkbutton">删除</a> <a
				id="dom_cancle_delformLog" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitDelFormLog;
	$(function() {
		//表单组件对象
		submitDelFormLog = $('#dom_delformLog').SubmitForm({
			pageType : '1',
			grid : gridLog,
			currentWindowId : 'winDelLog'
		});
		//关闭窗口
		$('#dom_cancle_delformLog').bind('click', function() {
			$('#winDelLog').window('close');
		});
		//提交新增数据
		$('#dom_add_entityDelLog')
				.bind(
						'click',
						function() {
							if ($('#entity_stime').combo('getText') > $(
									'#entity_etime').combo('getText')) {
								alert('开始时间不能大于结束时间!');
							} else {
								alert('该操作执行后将无法恢复删除的日志!');
								submitDelFormLog.postSubmit("log/delsubmit.do");
							}
						});
	});
//-->
</script>