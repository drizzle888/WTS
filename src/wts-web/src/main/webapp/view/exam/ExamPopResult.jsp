<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false">
		<form id="searchExampopForm">
			<table class="editTable">
				<tr>
					<td class="title">用户名称:</td>
					<td><input name="A.USERNAME:like" type="text"></td>
					<td class="title">分类名称:</td>
					<td><input name="B.NAME:like" type="text"></td>
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
		<table id="dataExampopGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="USERNAME" data-options="sortable:true" width="40">用户名称</th>
					<th field="FUNTYPE" data-options="sortable:true" width="40">权限类型</th>
					<th field="TYPENAME" data-options="sortable:true" width="40">分类名称</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	var url_delActionExampop = "exampop/del.do";//删除URL
	var url_formActionExampop = "exampop/form.do";//增加、修改、查看URL
	var url_searchActionExampop = "exampop/query.do?functype=${functype}&typeids=${typeids}";//查询URL
	var title_windowExampop = "考试权限管理";//功能名称
	var gridExampop;//数据表格对象
	var searchExampop;//条件查询组件对象
	var toolBarExampop = [/**{
	              		id : 'view',
	              		text : '查看',
	              		iconCls : 'icon-tip',
	              		handler : viewDataExampop
	              	}, {
	              		id : 'edit',
	              		text : '修改',
	              		iconCls : 'icon-edit',
	              		handler : editDataExampop
	              	}, **/  {
		id : 'add',
		text : '添加人员',
		iconCls : 'icon-add',
		handler : addDataExampop
	},{
		id : 'del',
		text : '删除人员',
		iconCls : 'icon-remove',
		handler : delDataExampop
	} ];
	$(function() {
		//初始化数据表格
		gridExampop = $('#dataExampopGrid').datagrid({
			url : url_searchActionExampop,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarExampop,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchExampop = $('#searchExampopForm').searchForm({
			gridObj : gridExampop
		});
	});
	//查看
	function viewDataExampop() {
		var selectedArray = $(gridExampop).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionExampop + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winExampop',
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
	function addDataExampop() {
		var url = "user/chooseUser.do";
		$.farm.openWindow({
			id : 'chooseUserWin',
			width : 600,
			height : 400,
			modal : true,
			url : url,
			title : '导入用户'
		});
		chooseWindowCallBackHandle = function(row) {
			var userids;
			$(row).each(function(i, obj) {
				if (userids) {
					userids = userids + ',' + obj.ID;
				} else {
					userids = obj.ID;
				}
			});
			$.post("exampop/add.do", {
				'userids' : userids,
				'functype' : '${functype}',
				'typeids' : '${typeids}'
			},
					function(flag) {
						if (flag.STATE == 0) {
							$('#chooseUserWin').window('close');
							$(gridExampop).datagrid('reload');
							$(gridExamtype).datagrid('reload');
						} else {
							$.messager.alert(MESSAGE_PLAT.ERROR, flag.MESSAGE,
									'error');
						}
					}, 'json');
		};
	}
	//修改
	function editDataExampop() {
		var selectedArray = $(gridExampop).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionExampop + '?operateType=' + PAGETYPE.EDIT
					+ '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winExampop',
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
	function delDataExampop() {
		var selectedArray = $(gridExampop).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridExampop).datagrid('loading');
					$.post(url_delActionExampop + '?ids='
							+ $.farm.getCheckedIds(gridExampop, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridExampop).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridExampop).datagrid('reload');
									$(gridExamtype).datagrid('reload');
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