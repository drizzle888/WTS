<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<PF:basePath/>">
		<title>数据实体</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<jsp:include page="/view/conf/include.jsp"></jsp:include>
	</head>
	<body class="easyui-layout">
		<div data-options="region:'north',border:false">
			<form id="searchDictinaryForm">
				<table class="editTable">
					<tr>
						<td class="title">
							名称:
						</td>
						<td>
							<input name="NAME:like" type="text">
						</td>
						<td class="title">
							键:
						</td>
						<td>
							<input name="ENTITYINDEX:like" type="text">
						</td>
					</tr>
					<tr style="text-align: center;">
						<td colspan="4">
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
			<table id="dataDictinaryGrid">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="NAME" data-options="sortable:true" width="80">
							名称
						</th>
						<th field="ENTITYINDEX" data-options="sortable:true" width="80">
							键
						</th>
						<th field="TYPE" data-options="sortable:true" width="80">
							类型
						</th>
						<th field="COMMENTS" data-options="sortable:true" width="80">
							备注
						</th>
					</tr>
				</thead>
			</table>
		</div>
	</body>
	<script type="text/javascript">
	var url_delActionDictinary = "dictionary/del.do";//删除URL
	var url_formActionDictinary = "dictionary/form.do";//增加、修改、查看URL
	var url_searchActionDictinary = "dictionary/query.do";//查询URL
	var title_windowDictinary = "数据实体";//功能名称
	var gridDictinary;//数据表格对象
	var searchDictinary;//条件查询组件对象
	var toolBarDictinary = [
			{
				id : 'view',
				text : '查看',
				iconCls : 'icon-tip',
				handler : viewDataDictinary
			},
			{
				id : 'add',
				text : '新增',
				iconCls : 'icon-add',
				handler : addDataDictinary
			},
			{
				id : 'edit',
				text : '修改',
				iconCls : 'icon-edit',
				handler : editDataDictinary
			},
			{
				id : 'del',
				text : '删除',
				iconCls : 'icon-remove',
				handler : delDataDictinary
			},
			{
				text : '设置字典项',
				iconCls : 'icon-address',
				handler : function() {
					var selectedArray = $(gridDictinary).datagrid(
							'getSelections');
					if (selectedArray.length == 1) {
						$.farm
								.openWindow( {
									id : 'winSetDicType',
									width : 700,
									height : 350,
									modal : true,
									url : 'dictionaryType/ALONEDictionaryType_ACTION_CONSOLE.do?ids='
											+ selectedArray[0].ID
											+ '&type='
											+ selectedArray[0].TYPES,
									title : '设置字典项'
								});
					} else {
						$.messager.alert(MESSAGE_PLAT.PROMPT,
								MESSAGE_PLAT.CHOOSE_ONE_ONLY, 'info');
					}
				}
			} ];
	$(function() {
		//初始化数据表格
		gridDictinary = $('#dataDictinaryGrid').datagrid( {
			url : url_searchActionDictinary,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarDictinary,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchDictinary = $('#searchDictinaryForm').searchForm( {
			gridObj : gridDictinary
		});
	});
	//查看
	function viewDataDictinary() {
		var selectedArray = $(gridDictinary).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionDictinary + '?operateType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow( {
				id : 'winDictinary',
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
	function addDataDictinary() {
		var url = url_formActionDictinary + '?operateType=' + PAGETYPE.ADD;
		$.farm.openWindow( {
			id : 'winDictinary',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDataDictinary() {
		var selectedArray = $(gridDictinary).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionDictinary + '?operateType='
					+ PAGETYPE.EDIT + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow( {
				id : 'winDictinary',
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
	function delDataDictinary() {
		var selectedArray = $(gridDictinary).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridDictinary).datagrid('loading');
					$.post(url_delActionDictinary + '?ids='
							+ $.farm.getCheckedIds(gridDictinary, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridDictinary).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridDictinary).datagrid('reload');
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




