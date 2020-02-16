<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false">
		<form id="searchActionForm">
			<table class="editTable">
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
</div>
<script type="text/javascript">
	var url_searchActionAction = "action/query.do";//查询URL
	var title_windowAction = "权限资源管理";//功能名称
	var gridAction;//数据表格对象
	var searchAction;//条件查询组件对象
	var toolBarAction = [ {
		id : 'view',
		text : '选择',
		iconCls : 'icon-ok',
		handler : viewDataAction
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
		if (selectedArray.length > 0) {
			chooseWindowCallBackHandle(selectedArray);
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
</script>
