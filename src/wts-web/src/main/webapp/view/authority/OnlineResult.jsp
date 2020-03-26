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
	<div data-options="region:'center',border:false">
		<table id="dataActionGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="IP" data-options="sortable:false" width="40">用户IP</th>
					<th field="TIME" data-options="sortable:false" width="40">
						最近访问</th>
					<th field="STARTTIME" data-options="sortable:false" width="20">
						首次访问</th>
					<th field="VISITTIME" data-options="sortable:false" width="20">
						访问时长(分)</th>
					<th field="LOGINNAME" data-options="sortable:false" width="20">
						登录名</th>
					<th field="USERNAME" data-options="sortable:false" width="20">
						姓名</th>
					<th field="LOGINTIME" data-options="sortable:false" width="20">
						登录时间</th>
					<!--三小时为周期的资源请求（含动态和静态）-->
					<th field="LASTCOUNT" data-options="sortable:false" width="20">
						3HOURS-COUNT</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
<script type="text/javascript">
	var url_delActionAction = "user/downOnline.do";//删除URL
	var url_searchActionAction = "user/onlineQuery.do";//查询URL
	var title_windowAction = "权限资源管理";//功能名称
	var gridAction;//数据表格对象
	var searchAction;//条件查询组件对象
	var toolBarAction = [ {
		id : 'blankList',
		text : '加入黑名单',
		iconCls : 'icon-exclamation-red-frame',
		handler : addBlankList
	}, {
		id : 'viewbList',
		text : '查看黑名单',
		iconCls : 'icon-cv',
		handler : viewBlankList
	} ];
	$(function() {
		//初始化数据表格
		gridAction = $('#dataActionGrid').datagrid({
			url : url_searchActionAction,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarAction,
			pagination : true,
			closable : true,
			pageList : [ 1000 ],
			pageSize : 1000,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			border : false,
			ctrlSelect : true
		});
		//初始化条件查询
		searchAction = $('#searchActionForm').searchForm({
			gridObj : gridAction
		});
	});
	//添加到黑名单
	function addBlankList() {
		var selectedArray = $(gridAction).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			$.messager
					.confirm(
							MESSAGE_PLAT.PROMPT,
							"该ip加入黑名单后将无法继续访问系统，是否继续?",
							function(flag) {
								if (flag) {
									$(gridAction).datagrid('loading');
									$
											.post(
													'user/addBlank.do?ips='
															+ $.farm
																	.getCheckedIds(
																			gridAction,
																			'IP'),
													{},
													function(flag) {
														var jsonObject = JSON
																.parse(flag,
																		null);
														$(gridAction).datagrid(
																'loaded');
														if (jsonObject.STATE == 0) {
														} else {
															var str = MESSAGE_PLAT.ERROR_SUBMIT
																	+ jsonObject.MESSAGE;
															$.messager
																	.alert(
																			MESSAGE_PLAT.ERROR,
																			str,
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
	//查看黑名单
	function viewBlankList() {
		$.farm
				.openWindow({
					id : 'winSetDicType',
					width : 700,
					height : 350,
					modal : true,
					url : 'dictionaryType/ALONEDictionaryType_ACTION_CONSOLE.do?ids=${entityId}',
					title : '设置字典项'
				});
	}
</script>
</html>