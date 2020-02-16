<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>外部账户数据管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<form id="searchOutuserForm">
			<table class="editTable">
				<tr>
					<td class="title">外部名称:</td>
					<td><input name="ACCOUNTNAME:like" type="text"></td>
					<td class="title">用户名</td>
					<td><input name="NAME:like" type="text"></td>
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
		<table id="dataOutuserGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="ACCOUNTNAME" data-options="sortable:true" width="110">外部名称</th>
					<th field="ACCOUNTID" data-options="sortable:true" width="90">外部id</th>
					<th field="USERID" data-options="sortable:true" width="60">用户id</th>
					<th field="NAME" data-options="sortable:true" width="60">用户名</th>
					<th field="PCONTENT" data-options="sortable:true" width="80">备注</th>
					<th field="CTIME" data-options="sortable:true" width="50">创建时间</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
<script type="text/javascript">
	var url_delActionOutuser = "outuser/del.do";//删除URL
	var url_formActionOutuser = "outuser/form.do";//增加、修改、查看URL
	var url_searchActionOutuser = "outuser/query.do";//查询URL
	var title_windowOutuser = "外部账户管理";//功能名称
	var gridOutuser;//数据表格对象
	var searchOutuser;//条件查询组件对象
	var toolBarOutuser = [ /**{
		id : 'view',
		text : '查看',
		iconCls : 'icon-tip',
		handler : viewDataOutuser
	}, {
		id : 'add',
		text : '新增',
		iconCls : 'icon-add',
		handler : addDataOutuser
	}, {
		id : 'edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : editDataOutuser
	},**/
	{
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDataOutuser
	}, {
		id : 'bindUser',
		text : '绑定用户',
		iconCls : 'icon-group_green_new',
		handler : bindOutuser
	} ];
	$(function() {
		//初始化数据表格
		gridOutuser = $('#dataOutuserGrid').datagrid({
			url : url_searchActionOutuser,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarOutuser,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchOutuser = $('#searchOutuserForm').searchForm({
			gridObj : gridOutuser
		});
	});
	//绑定到用户
	function bindOutuser() {
		var selectedArray = $(gridOutuser).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = "outuser/bindUser.do";
			$.farm.openWindow({
				id : 'winChoosUser',
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

	//选定绑定用户后的回调函数
	function chooseWindowCallBackHandle(selectedArray) {
		var outSelectedArray = $(gridOutuser).datagrid('getSelections');
		var outUserId = outSelectedArray[0].ID;
		var userId = selectedArray[0].ID;
		if (selectedArray.length != 1) {
			$.messager.alert(MESSAGE_PLAT.ERROR, "请选择一个用户", 'error');
		} else {
			$.messager.confirm(MESSAGE_PLAT.PROMPT,"确定要绑定该用户么？", function(flag) {
				if (flag) {
					$.post('outuser/doBind.do', {
						outid : outUserId,
						id : userId
					}, function(flag) {
						$(gridOutuser).datagrid('loaded');
						if (flag.STATE == 0) {
							$('#winChoosUser').window('close');
							$(gridOutuser).datagrid('reload');
						} else {
							var str = MESSAGE_PLAT.ERROR_SUBMIT + flag.MESSAGE;
							$.messager.alert(MESSAGE_PLAT.ERROR, str, 'error');
						}
					}, 'json');
				}
			});
		}
	}

	//删除
	function delDataOutuser() {
		var selectedArray = $(gridOutuser).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridOutuser).datagrid('loading');
					$.post(url_delActionOutuser + '?ids='
							+ $.farm.getCheckedIds(gridOutuser, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridOutuser).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridOutuser).datagrid('reload');
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