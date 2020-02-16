<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>业务权限数据管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<form id="searchPopForm">
			<table class="editTable">
				<tr>
					<td class="title">授权对象名称:</td>
					<td><input name="TARGETNAME:like" type="text"></td>
					<td class="title">授权对象ID:</td>
					<td><input name="TARGETID:like" type="text"></td>
					<td class="title">授权类型:</td>
					<td><input name="TARGETTYPE:=" type="text"></td>
				</tr>
				<tr>
					<td class="title">所有者名称:</td>
					<td><input name="ONAME:like" type="text"></td>
					<td class="title">所有者ID:</td>
					<td><input name="OID:like" type="text"></td>
					<td class="title">所有者类型:</td>
					<td><input name="POPTYPE:=" type="text"></td>
				</tr>
				<tr style="text-align: center;">
					<td colspan="6"><a id="a_search" href="javascript:void(0)"
						class="easyui-linkbutton" iconCls="icon-search">查询</a> <a
						id="a_reset" href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-reload">清除条件</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataPopGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="TARGETNAME" data-options="sortable:true" width="60">授权对象名称</th>
					<th field="TARGETID" data-options="sortable:true" width="60">授权对象ID</th>
					<th field="TARGETTYPE" data-options="sortable:true" width="40">授权类型</th>
					<th field="ONAME" data-options="sortable:true" width="50">所有者名称</th>
					<th field="OID" data-options="sortable:true" width="50">所有者ID</th>
					<th field="POPTYPE" data-options="sortable:true" width="50">所有者类型</th>
					<th field="PSTATE" data-options="sortable:true" width="20">状态</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
<script type="text/javascript">
	var url_delActionPop = "pop/del.do";//删除URL
	var url_formActionPop = "pop/form.do";//增加、修改、查看URL
	var url_searchActionPop = "pop/query.do";//查询URL
	var title_windowPop = "业务权限管理";//功能名称
	var gridPop;//数据表格对象
	var searchPop;//条件查询组件对象
	var toolBarPop = [ {
		id : 'view',
		text : '查看',
		iconCls : 'icon-tip',
		handler : viewDataPop
	}/**, {
	id : 'add',
	text : '新增',
	iconCls : 'icon-add',
	handler : addDataPop
}, {
	id : 'edit',
	text : '修改',
	iconCls : 'icon-edit',
	handler : editDataPop
}, {
	id : 'del',
	text : '删除',
	iconCls : 'icon-remove',
	handler : delDataPop
}**/ ];
	$(function() {
		//初始化数据表格
		gridPop = $('#dataPopGrid').datagrid({
			url : url_searchActionPop,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarPop,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchPop = $('#searchPopForm').searchForm({
			gridObj : gridPop
		});
	});
	//查看
	function viewDataPop() {
		var selectedArray = $(gridPop).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionPop + '?pageset.pageType=' + PAGETYPE.VIEW
					+ '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winPop',
				width : 600,
				height : 300,
				modal : true,
				url : url,
				title : '浏览'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}
	//新增
	function addDataPop() {
		var url = url_formActionPop + '?operateType=' + PAGETYPE.ADD;
		$.farm.openWindow({
			id : 'winPop',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDataPop() {
		var selectedArray = $(gridPop).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionPop + '?operateType=' + PAGETYPE.EDIT
					+ '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winPop',
				width : 600,
				height : 300,
				modal : true,
				url : url,
				title : '修改'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}
	//删除
	function delDataPop() {
		var selectedArray = $(gridPop).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridPop).datagrid('loading');
					$.post(url_delActionPop + '?ids='
							+ $.farm.getCheckedIds(gridPop, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridPop).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridPop).datagrid('reload');
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