<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--计划任务管理选择-->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false">
		<form id="dom_chooseSearchfarmqzscheduler">
			<table class="editTable">
				<tr>
					<td class="title">
						<a id="a_search" href="javascript:void(0)"
							class="easyui-linkbutton" iconCls="icon-search">查询</a>
					</td>
					<td>
						<a id="a_reset" href="javascript:void(0)"
							class="easyui-linkbutton" iconCls="icon-reload">清除条件</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table class="easyui-datagrid" id="dom_choosegridfarmqzscheduler">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="AUTOIS" data-options="sortable:true" width="80"> autois </th>
					<th field="TASKID" data-options="sortable:true" width="80"> taskid </th>
					<th field="TRIGGERID" data-options="sortable:true" width="80"> triggerid </th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	var choosegridfarmqzscheduler;
	var chooseSearchfarmqzscheduler;
	var toolbar_choosefarmqzscheduler = [ {
		text : '选择',
		iconCls : 'icon-ok',
		handler : function() {
			var selectedArray = $('#dom_choosegridfarmqzscheduler').datagrid(
					'getSelections');
			if (selectedArray.length > 0) {
				chooseWindowCallBackHandle(selectedArray);
			} else {
				$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
						'info');
			}
		}
	} ];
	$(function() {
		choosegridfarmqzscheduler = $('#dom_choosegridfarmqzscheduler').datagrid( {
			url : 'admin/FarmQzSchedulerqueryAll.do',
			fit : true,
			fitColumns : true,
			'toolbar' : toolbar_choosefarmqzscheduler,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true,
			fitColumns : true
		});
		chooseSearchfarmqzscheduler = $('#dom_chooseSearchfarmqzscheduler').searchForm( {
			gridObj : choosegridfarmqzscheduler
		});
	});
	//-->
</script>
<!-- 
<a id="form_farmqzscheduler_a_Choose" href="javascript:void(0)" class="easyui-linkbutton" style="color: #000000;">选择</a>
<script type="text/javascript">
	$(function() {
		$('#form_farmqzscheduler_a_Choose').bindChooseWindow('chooseWinfarmqzscheduler', {
			width : 600,
			height : 300,
			modal : true,
			url : 'admin/FarmQzScheduler_ACTION_ALERT.do',
			title : '选择',
			callback : function(rows) {
				//$('#NAME_like').val(rows[0].NAME);
			}
		});
	});
</script>
 -->








