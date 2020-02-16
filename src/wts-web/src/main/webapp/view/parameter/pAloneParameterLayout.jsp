<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>系统参数定义</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<form id="searchparameterForm">
			<table class="editTable">
				<tr>
					<td class="title">名称:</td>
					<td><input name="NAME:like" type="text"></td>
					<td colspan="4"><a id="a_search" href="javascript:void(0)"
						class="easyui-linkbutton" iconCls="icon-search">查询</a> <a
						id="a_reset" href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-reload">清除条件</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataparameterGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="DOMAIN" data-options="sortable:true" width="20">域</th>
					<th field="NAME" data-options="sortable:true" width="80">名称</th>
					<th field="PKEY" data-options="sortable:true" width="80">键</th>
					<th field="COMMENTS" data-options="sortable:true" width="80">
						备注</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
<script type="text/javascript">
	var url_delActionparameter = "parameter/del.do";//删除URL
	var url_formActionparameter = "parameter/form.do";//增加、修改、查看URL
	var url_searchActionparameter = "parameter/query.do";//查询URL
	var title_windowparameter = "系统参数";//功能名称
	var gridparameter;//数据表格对象
	var searchparameter;//条件查询组件对象
	var toolBarparameter = [ {
		id : 'view',
		text : '查看',
		iconCls : 'icon-tip',
		handler : viewDataparameter
	}, {
		id : 'add',
		text : '新增',
		iconCls : 'icon-add',
		handler : addDataparameter
	}, {
		id : 'edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : editDataparameter
	}, {
		id : 'loadXml',
		text : '加载XML配置文件参数',
		iconCls : 'icon-refresh',
		handler : loadXmlParameter
	}, {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDataparameter
	} ];
	$(function() {
		//初始化数据表格
		gridparameter = $('#dataparameterGrid').datagrid({
			url : url_searchActionparameter,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarparameter,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchparameter = $('#searchparameterForm').searchForm({
			gridObj : gridparameter
		});
	});
	//查看
	function viewDataparameter() {
		var selectedArray = $(gridparameter).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionparameter + '?operateType=' + PAGETYPE.VIEW
					+ '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winparameter',
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
	function addDataparameter() {
		var url = url_formActionparameter + '?operateType=' + PAGETYPE.ADD;
		$.farm.openWindow({
			id : 'winparameter',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDataparameter() {
		var selectedArray = $(gridparameter).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionparameter + '?operateType=' + PAGETYPE.EDIT
					+ '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winparameter',
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
	function delDataparameter() {
		var selectedArray = $(gridparameter).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridparameter).datagrid('loading');
					$.post(url_delActionparameter + '?ids='
							+ $.farm.getCheckedIds(gridparameter, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridparameter).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridparameter).datagrid('reload');
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
	
	//加载XML配置文件
	function loadXmlParameter() {
		$.messager.confirm(MESSAGE_PLAT.PROMPT, "是否从XML配置文件中加载所有参数到数据库中？参数不会被重复注册。", function(flag) {
			if (flag) {
				$(gridparameter).datagrid('loading');
				$.post("parameter/loadxml.do", {},
						function(flag) {
							var jsonObject = JSON.parse(flag, null);
							$(gridparameter).datagrid('loaded');
							if (jsonObject.STATE == 0) {
								$(gridparameter).datagrid('reload');
							} else {
								var str = MESSAGE_PLAT.ERROR_SUBMIT
										+ jsonObject.MESSAGE;
								$.messager.alert(MESSAGE_PLAT.ERROR, str,
										'error');
							}
						});
			}
		});
	}	
	
</script>
</html>




