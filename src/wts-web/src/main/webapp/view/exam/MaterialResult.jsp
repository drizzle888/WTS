<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>引用材料数据管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<form id="searchMaterialForm">
			<table class="editTable">
				<tr>
					<td class="title">标题:</td>
					<td><input name="TITLE:like" type="text"></td>
					<td class="title"></td>
					<td></td>
				</tr>
				<tr style="text-align: center;">
					<td colspan="4"><a id="a_search" href="javascript:void(0)"
						class="easyui-linkbutton" iconCls="icon-search">查询</a> <a
						id="a_reset" href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-reload">清除条件</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataMaterialGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="TITLE" data-options="sortable:true" width="50">标题</th>
					<th field="RFNUM" data-options="sortable:true" width="50">引用次数</th>
					<th field="USERNAME" data-options="sortable:true" width="50">创建人</th>
					<th field="CTIME" data-options="sortable:true" width="50">创建时间</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
<script type="text/javascript">
	var url_delActionMaterial = "material/del.do";//删除URL
	var url_formActionMaterial = "material/form.do";//增加、修改、查看URL
	var url_searchActionMaterial = "material/query.do";//查询URL
	var title_windowMaterial = "引用材料管理";//功能名称
	var gridMaterial;//数据表格对象
	var searchMaterial;//条件查询组件对象
	var toolBarMaterial = [ {
		id : 'view',
		text : '查看',
		iconCls : 'icon-tip',
		handler : viewDataMaterial
	}, {
		id : 'add',
		text : '新增',
		iconCls : 'icon-add',
		handler : addDataMaterial
	}, {
		id : 'edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : editDataMaterial
	}, {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDataMaterial
	} ];
	$(function() {
		//初始化数据表格
		gridMaterial = $('#dataMaterialGrid').datagrid({
			url : url_searchActionMaterial,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarMaterial,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchMaterial = $('#searchMaterialForm').searchForm({
			gridObj : gridMaterial
		});
	});
	//查看
	function viewDataMaterial() {
		var selectedArray = $(gridMaterial).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionMaterial + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winMaterial',
				width : 600,
				height : 400,
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
	function addDataMaterial() {
		var url = url_formActionMaterial + '?operateType=' + PAGETYPE.ADD;
		$.farm.openWindow({
			id : 'winMaterial',
			width : 600,
			height : 400,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDataMaterial() {
		var selectedArray = $(gridMaterial).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionMaterial + '?operateType=' + PAGETYPE.EDIT
					+ '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winMaterial',
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
	//删除
	function delDataMaterial() {
		var selectedArray = $(gridMaterial).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridMaterial).datagrid('loading');
					$.post(url_delActionMaterial + '?ids='
							+ $.farm.getCheckedIds(gridMaterial, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridMaterial).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridMaterial).datagrid('reload');
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