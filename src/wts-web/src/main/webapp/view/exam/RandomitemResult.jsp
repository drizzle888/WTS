<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>答卷随机规则数据管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<form id="searchRandomitemForm">
			<table class="editTable">
				<tr style="text-align: center;">
					<td class="title">名称:</td>
					<td><input name="b.NAME:like" style="width: 100%;" type="text"></td>
					<td class="title">状态:</td>
					<td><select name="a.PSTATE:like">
							<option value="">全部</option>
							<option value="1">可用</option>
							<option value="0">禁用</option>
					</select></td>
					<td colspan="4"><a id="a_search" href="javascript:void(0)"
						class="easyui-linkbutton" iconCls="icon-search">查询</a> <a
						id="a_reset" href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-reload">清除条件</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataRandomitemGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="NAME" data-options="sortable:true" width="20">名称</th>
					<th field="PCONTENT" data-options="sortable:true" width="80">备注</th>
					<th field="PSTATE" data-options="sortable:true" width="20">状态</th>
					<th field="NUM" data-options="sortable:true" width="20">规则数量</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
<script type="text/javascript">
	var url_delActionRandomitem = "randomitem/del.do";//删除URL
	var url_formActionRandomitem = "randomitem/form.do";//增加、修改、查看URL
	var url_searchActionRandomitem = "randomitem/query.do";//查询URL
	var title_windowRandomitem = "答卷随机规则管理";//功能名称
	var gridRandomitem;//数据表格对象
	var searchRandomitem;//条件查询组件对象
	var toolBarRandomitem = [
	//                            {
	//   id : 'view',
	//    text : '查看',
	//    iconCls : 'icon-tip',
	//    handler : viewDataRandomitem
	//  }, 
	{
		id : 'add',
		text : '新增',
		iconCls : 'icon-add',
		handler : addDataRandomitem
	}, {
		id : 'edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : editDataRandomitem
	}, {
		id : 'edit',
		text : '规则配置',
		iconCls : 'icon-issue',
		handler : editSteps
	}, {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDataRandomitem
	} ];
	$(function() {
		//初始化数据表格
		gridRandomitem = $('#dataRandomitemGrid').datagrid({
			url : url_searchActionRandomitem,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarRandomitem,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchRandomitem = $('#searchRandomitemForm').searchForm({
			gridObj : gridRandomitem
		});
	});
	//查看
	function viewDataRandomitem() {
		var selectedArray = $(gridRandomitem).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionRandomitem + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winRandomitem',
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
	function addDataRandomitem() {
		var url = url_formActionRandomitem + '?operateType=' + PAGETYPE.ADD;
		$.farm.openWindow({
			id : 'winRandomitem',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDataRandomitem() {
		var selectedArray = $(gridRandomitem).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionRandomitem + '?operateType='
					+ PAGETYPE.EDIT + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winRandomitem',
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
	
	//配置随机项的步骤
	function editSteps() {
		var selectedArray = $(gridRandomitem).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url =  'randomitem/steps.do?itemid=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winRandomitem',
				width : 800,
				height : 400,
				modal : true,
				url : url,
				title : '规则步骤'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}
	
	
	//删除
	function delDataRandomitem() {
		var selectedArray = $(gridRandomitem).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridRandomitem).datagrid('loading');
					$.post(url_delActionRandomitem + '?ids='
							+ $.farm.getCheckedIds(gridRandomitem, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridRandomitem).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridRandomitem).datagrid('reload');
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