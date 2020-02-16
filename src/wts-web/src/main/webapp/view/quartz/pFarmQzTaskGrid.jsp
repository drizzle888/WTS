<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--任务定义选择-->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false">
		<form id="dom_chooseSearchfarmqztask">
			<table class="editTable">
				<tr>
					<td class="title">
						名称:
					</td>
					<td>
						<input name="NAME:like" type="text">
					</td>
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
		<table class="easyui-datagrid" id="dom_choosegridfarmqztask">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="NAME" data-options="sortable:true" width="80">
						名称
					</th>
					<th field="JOBCLASS" data-options="sortable:true" width="80">
						类
					</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	var choosegridfarmqztask;
	var chooseSearchfarmqztask;
	var toolbar_choosefarmqztask = [ {
		text : '选择',
		iconCls : 'icon-ok',
		handler : function() {
			var selectedArray = $('#dom_choosegridfarmqztask').datagrid(
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
		choosegridfarmqztask = $('#dom_choosegridfarmqztask').datagrid( {
			url : 'qzTask/query.do',
			fit : true,
			fitColumns : true,
			'toolbar' : toolbar_choosefarmqztask,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true,
			fitColumns : true
		});
		chooseSearchfarmqztask = $('#dom_chooseSearchfarmqztask').searchForm( {
			gridObj : choosegridfarmqztask
		});
	});
	//-->
</script>
<!-- 
<a id="form_farmqztask_a_Choose" href="javascript:void(0)" class="easyui-linkbutton" style="color: #000000;">选择</a>
<script type="text/javascript">
	$(function() {
		$('#form_farmqztask_a_Choose').bindChooseWindow('chooseWinfarmqztask', {
			width : 600,
			height : 300,
			modal : true,
			url : 'admin/FarmQzTask_ACTION_ALERT.do',
			title : '选择',
			callback : function(rows) {
				//$('#NAME_like').val(rows[0].NAME);
			}
		});
	});
</script>
 -->








