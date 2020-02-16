<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<PF:basePath/>">
		<title>权限资源数据管理</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<jsp:include page="/view/conf/include.jsp"></jsp:include>
	</head>
	<body class="easyui-layout">
		<div data-options="region:'north',border:false">
			<form id="searchActionForm">
				<table class="editTable">
					<tr>
						<td class="title">
							是否需要登录:
						</td>
						<td>
							<select name="LOGINIS:=" style="width: 120px;">
								<option value="">
								</option>
								<option value="0">
									否
								</option>
								<option value="1">
									是
								</option>
							</select>
						</td>
						<td class="title">
							是否检查:
						</td>
						<td>
							<select name="CHECKIS:=" style="width: 120px;">
								<option value="">
								</option>
								<option value="0">
									否
								</option>
								<option value="1">
									是
								</option>
							</select>
						</td>
						<td class="title">
							状态:
						</td>
						<td>
							<select name="STATE:=" style="width: 120px;">
								<option value="">
								</option>
								<option value="0">
									不可用
								</option>
								<option value="1">
									可用
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="title">
							名称:
						</td>
						<td>
							<input name="NAME:like" type="text" style="width: 120px;">
						</td>
						<td class="title">
							资源KEY:
						</td>
						<td>
							<input name="AUTHKEY:like" type="text" style="width: 120px;">
						</td>
						<td class="title"></td>
						<td></td>
					</tr>
					<tr style="text-align: center;">
						<td colspan="6">
							<a id="a_search" href="javascript:void(0)"
								class="easyui-linkbutton" iconCls="icon-search">查询</a>
							<a id="a_reset" href="javascript:void(0)"
								class="easyui-linkbutton" iconCls="icon-reload">清除条件</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataActionGrid">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="NAME" data-options="sortable:true" width="40">
							名称
						</th>
						<th field="AUTHKEY" data-options="sortable:true" width="40">
							资源KEY
						</th>
						<th field="LOGINIS" data-options="sortable:true" width="20">
							是否需要登录
						</th>
						<th field="CHECKIS" data-options="sortable:true" width="20">
							是否检查
						</th>
						<th field="COMMENTS" data-options="sortable:true" width="50">
							备注
						</th>
						<th field="STATE" data-options="sortable:true" width="20">
							状态
						</th>
					</tr>
				</thead>
			</table>
		</div>
	</body>
	<script type="text/javascript">
	var url_delActionAction = "action/del.do";//删除URL
	var url_formActionAction = "action/form.do";//增加、修改、查看URL
	var url_searchActionAction = "action/query.do";//查询URL
	var title_windowAction = "权限资源管理";//功能名称
	var gridAction;//数据表格对象
	var searchAction;//条件查询组件对象
	var toolBarAction = [ {
		id : 'view',
		text : '查看',
		iconCls : 'icon-tip',
		handler : viewDataAction
	}, {
		id : 'add',
		text : '新增',
		iconCls : 'icon-add',
		handler : addDataAction
	}, {
		id : 'edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : editDataAction
	}, {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDataAction
	} ];
	$(function() {
		//初始化数据表格
		gridAction = $('#dataActionGrid').datagrid( {
			url : url_searchActionAction,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarAction,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			border : false,
			ctrlSelect : true
		});
		//初始化条件查询
		searchAction = $('#searchActionForm').searchForm( {
			gridObj : gridAction
		});
	});
	//查看
	function viewDataAction() {
		var selectedArray = $(gridAction).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionAction + '?pageset.operateType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow( {
				id : 'winAction',
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
	function addDataAction() {
		var url = url_formActionAction + '?operateType=' + PAGETYPE.ADD;
		$.farm.openWindow( {
			id : 'winAction',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDataAction() {
		var selectedArray = $(gridAction).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionAction + '?operateType='
					+ PAGETYPE.EDIT + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow( {
				id : 'winAction',
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
	function delDataAction() {
		var selectedArray = $(gridAction).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridAction).datagrid('loading');
					$.post(url_delActionAction + '?ids='
							+ $.farm.getCheckedIds(gridAction, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridAction).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridAction).datagrid('reload');
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