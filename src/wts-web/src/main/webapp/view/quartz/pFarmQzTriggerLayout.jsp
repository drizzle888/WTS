<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>触发器定义</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<form id="dom_searchfarmqztrigger">
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
		<table class="easyui-datagrid" id="dom_datagridfarmqztrigger">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="NAME" data-options="sortable:true" width="80">名称</th>
					<th field="DESCRIPT" data-options="sortable:true" width="80">
						脚本</th>
					<th field="PCONTENT" data-options="sortable:true" width="80">
						备注</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
<script type="text/javascript">
	var url_delActionfarmqztrigger = "qzTrigger/del.do";
	var url_formActionfarmqztrigger = "qzTrigger/form.do";
	var url_searchActionfarmqztrigger = "qzTrigger/query.do";
	var title_windowfarmqztrigger = "触发器定义";
	var gridfarmqztrigger;
	var searchfarmqztrigger;
	var TOOL_BARfarmqztrigger = [ {
		id : 'view',
		text : '查看',
		iconCls : 'icon-tip',
		handler : viewDatafarmqztrigger
	}, {
		id : 'add',
		text : '新增',
		iconCls : 'icon-add',
		handler : addDatafarmqztrigger
	}, {
		id : 'edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : editDatafarmqztrigger
	}, {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDatafarmqztrigger
	} ];
	$(function() {
		gridfarmqztrigger = $('#dom_datagridfarmqztrigger').datagrid({
			url : url_searchActionfarmqztrigger,
			fit : true,
			fitColumns : true,
			'toolbar' : TOOL_BARfarmqztrigger,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true,
			fitColumns : true
		});
		searchfarmqztrigger = $('#dom_searchfarmqztrigger').searchForm({
			gridObj : gridfarmqztrigger
		});
	});
	function viewDatafarmqztrigger() {
		var selectedArray = $(gridfarmqztrigger).datagrid('getSelections');
		if (selectedArray.length > 0) {
			var url = url_formActionfarmqztrigger + '?operateType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winfarmqztrigger',
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
	function addDatafarmqztrigger() {
		var url = url_formActionfarmqztrigger + '?operateType=' + PAGETYPE.ADD;
		$.farm.openWindow({
			id : 'winfarmqztrigger',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	function editDatafarmqztrigger() {
		var selectedArray = $(gridfarmqztrigger).datagrid('getSelections');
		if (selectedArray.length > 0) {
			var url = url_formActionfarmqztrigger + '?operateType='
					+ PAGETYPE.EDIT + '&ids=' + selectedArray[0].ID;
			;
			$.farm.openWindow({
				id : 'winfarmqztrigger',
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
	function delDatafarmqztrigger() {
		var selectedArray = $(gridfarmqztrigger).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$.post(url_delActionfarmqztrigger + '?ids='
							+ $.farm.getCheckedIds(gridfarmqztrigger), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								if (jsonObject.STATE == 0) {
									$(gridfarmqztrigger).datagrid('reload');
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




