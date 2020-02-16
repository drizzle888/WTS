<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>任务定义</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<form id="dom_searchfarmqztask">
			<table class="editTable">
				<tr>
					<td class="title">名称:</td>
					<td><input name="NAME:like" type="text"></td>
					<td class="title"><a id="a_search" href="javascript:void(0)"
						class="easyui-linkbutton" iconCls="icon-search">查询</a></td>
					<td><a id="a_reset" href="javascript:void(0)"
						class="easyui-linkbutton" iconCls="icon-reload">清除条件</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table class="easyui-datagrid" id="dom_datagridfarmqztask">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="NAME" data-options="sortable:true" width="80">名称</th>
					<th field="JOBCLASS" data-options="sortable:true" width="80">
						类</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
<script type="text/javascript">
	var url_delActionfarmqztask = "qzTask/del.do";
	var url_formActionfarmqztask = "qzTask/form.do";
	var url_searchActionfarmqztask = "qzTask/query.do";
	var title_windowfarmqztask = "任务定义";
	var gridfarmqztask;
	var searchfarmqztask;
	var TOOL_BARfarmqztask = [ {
		id : 'view',
		text : '查看',
		iconCls : 'icon-tip',
		handler : viewDatafarmqztask
	}, {
		id : 'add',
		text : '新增',
		iconCls : 'icon-add',
		handler : addDatafarmqztask
	}, {
		id : 'edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : editDatafarmqztask
	}, {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDatafarmqztask
	} ];
	$(function() {
		gridfarmqztask = $('#dom_datagridfarmqztask').datagrid({
			url : url_searchActionfarmqztask,
			fit : true,
			fitColumns : true,
			'toolbar' : TOOL_BARfarmqztask,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true,
			fitColumns : true
		});
		searchfarmqztask = $('#dom_searchfarmqztask').searchForm({
			gridObj : gridfarmqztask
		});
	});
	function viewDatafarmqztask() {
		var selectedArray = $(gridfarmqztask).datagrid('getSelections');
		if (selectedArray.length > 0) {
			var url = url_formActionfarmqztask + '?operateType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winfarmqztask',
				width : 600,
				height : 300,
				modal : true,
				url : url,
				title : '浏览'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
	function addDatafarmqztask() {
		var url = url_formActionfarmqztask + '?operateType='
				+ PAGETYPE.ADD;
		$.farm.openWindow({
			id : 'winfarmqztask',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	function editDatafarmqztask() {
		var selectedArray = $(gridfarmqztask).datagrid('getSelections');
		if (selectedArray.length > 0) {
			var url = url_formActionfarmqztask + '?operateType='
					+ PAGETYPE.EDIT + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winfarmqztask',
				width : 600,
				height : 300,
				modal : true,
				url : url,
				title : '修改'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
	function delDatafarmqztask() {
		var selectedArray = $(gridfarmqztask).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$.post(url_delActionfarmqztask + '?ids='
							+ $.farm.getCheckedIds(gridfarmqztask), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								if (jsonObject.STATE == 0) {
									$(gridfarmqztask).datagrid('reload');
								} else {
									var str = MESSAGE_PLAT.ERROR_SUBMIT
											+ jsonObject.MESSAGE;
									$.messager.alert(MESSAGE_PLAT.ERROR, str,
											'error');
								}
							});
				}
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
</script>
</html>




