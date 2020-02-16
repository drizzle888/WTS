<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>消息模板数据管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<form id="searchMessagemodelForm">
			<table class="editTable">
				<tr>
					<td class="title">类型KEY:</td>
					<td>
						<input name="TYPEKEY:like" type="text">
					</td>
					<td class="title">标题模板:</td>
					<td>
						<input name="TITLEMODEL:like" type="text">
					</td>
					<td class="title">消息类型:</td>
					<td>
						<input name="TITLE:like" type="text">
					</td>
				</tr>
				<tr style="text-align: center;">
					<td colspan="6">
						<a id="a_search" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
						<a id="a_reset" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload">清除条件</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataMessagemodelGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="TITLE" data-options="sortable:true" width="20">消息类型</th>
					<th field="TYPEKEY" data-options="sortable:true" width="20">类型KEY</th>
					<th field="TITLEMODEL" data-options="sortable:true" width="40">标题模板</th>
					<th field="NUM" data-options="sortable:true" width="10">抄送人</th>
					<th field="PCONTENT" data-options="sortable:true" width="40">备注</th>
					<th field="PSTATE" data-options="sortable:true" width="20">状态</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="messageModelToolbar">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-limited-edition',plain:true,onClick:initMessageModel">初始化 </a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,onClick:editDataMessagemodel">修改模板 </a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-my-account',plain:true,onClick:setSenders">设置抄送人 </a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true,onClick:reFrashMessagemodelCache">刷新缓存</a>
		<a href="javascript:void(0)" id="mb" class="easyui-menubutton" data-options="menu:'#mm5',iconCls:'icon-edit'">设置状态</a>
		<div id="mm5" style="width: 150px;">
			<div data-options="iconCls:'icon-upcoming-work'" onclick="resetModelState(true)">启用</div>
			<div data-options="iconCls:'icon-busy'" onclick="resetModelState(false)">禁用</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	var url_delActionMessagemodel = "messagemodel/del.do";//删除URL
	var url_formActionMessagemodel = "messagemodel/form.do";//增加、修改、查看URL
	var url_searchActionMessagemodel = "messagemodel/query.do";//查询URL
	var title_windowMessagemodel = "消息模板管理";//功能名称
	var gridMessagemodel;//数据表格对象
	var searchMessagemodel;//条件查询组件对象
	$(function() {
		//初始化数据表格
		gridMessagemodel = $('#dataMessagemodelGrid').datagrid({
			url : url_searchActionMessagemodel,
			fit : true,
			fitColumns : true,
			'toolbar' : '#messageModelToolbar',
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchMessagemodel = $('#searchMessagemodelForm').searchForm({
			gridObj : gridMessagemodel
		});
	});
	//初始化模板
	function initMessageModel() {
		// 有数据执行操作
		$.messager.confirm(MESSAGE_PLAT.PROMPT, "模板恢复到初始状态，该操作不可逆,是否立即初始化模板?",
				function(flag) {
					if (flag) {
						$(gridMessagemodel).datagrid('loading');
						$.post("messagemodel/initModel.do", {}, function(flag) {
							var jsonObject = JSON.parse(flag, null);
							$(gridMessagemodel).datagrid('loaded');
							if (jsonObject.STATE == 0) {
								$(gridMessagemodel).datagrid('reload');
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
	//设置抄送人
	function setSenders() {
		var selectedArray = $(gridMessagemodel).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = "messagemodel/senders.do" + '?operateType='
					+ PAGETYPE.EDIT + '&id=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winsenders',
				width : 600,
				height : 400,
				modal : true,
				url : url,
				title : '设置抄送人'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}

	//修改
	function editDataMessagemodel() {
		var selectedArray = $(gridMessagemodel).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionMessagemodel + '?operateType='
					+ PAGETYPE.EDIT + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winMessagemodel',
				width : 600,
				height : 400,
				modal : true,
				url : url,
				title : '修改'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}
	//刷新缓存
	function reFrashMessagemodelCache() {
		$.messager.confirm(MESSAGE_PLAT.PROMPT, "是否立即刷新缓存？", function(flag) {
			if (flag) {
				$(gridMessagemodel).datagrid('loading');
				$.post('messagemodel/reLoadCache.do', {}, function(flag) {
					var jsonObject = JSON.parse(flag, null);
					$(gridMessagemodel).datagrid('loaded');
					if (jsonObject.STATE == 0) {
						$(gridMessagemodel).datagrid('reload');
					} else {
						var str = MESSAGE_PLAT.ERROR_SUBMIT
								+ jsonObject.MESSAGE;
						$.messager.alert(MESSAGE_PLAT.ERROR, str, 'error');
					}
				});
			}
		});
	}

	//修改模板状态
	function resetModelState(able) {
		var selectedArray = $(gridMessagemodel).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			$.messager.confirm(MESSAGE_PLAT.PROMPT, "是否立即"
					+ (able ? "启用" : "禁用") + "消息", function(flag) {
				if (flag) {
					$(gridMessagemodel).datagrid('loading');
					$.post('messagemodel/resetState.do' + '?isAble=' + able
							+ '&ids='
							+ $.farm.getCheckedIds(gridMessagemodel, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridMessagemodel).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridMessagemodel).datagrid('reload');
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