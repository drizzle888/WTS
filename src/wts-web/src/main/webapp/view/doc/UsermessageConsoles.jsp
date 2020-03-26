<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>组织机构数据管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',split:true,border:false"
		style="width: 250px;">
		<div class="TREE_COMMON_BOX_SPLIT_DIV">
			<a id="MessageconsoleTreeReload" href="javascript:void(0)"
				class="easyui-linkbutton" data-options="plain:true"
				iconCls="icon-reload">刷新</a> <a id="MessageconsoleTreeOpenAll"
				href="javascript:void(0)" class="easyui-linkbutton"
				data-options="plain:true" iconCls="icon-sitemap">展开</a>
		</div>
		<ul id="MessageconsoleTree"></ul>
	</div>
	<div class="easyui-layout" data-options="region:'center',border:false">
		<div data-options="region:'north',border:false">
			<form id="searchMessageconsoleForm">
				<table class="editTable">
					<tr>
						<td class="title">组织机构:</td>
						<td><input id="PARENTTITLE_RULE" type="text"
							readonly="readonly" style="background: #F3F3E8"> <input
							id="PARENTID_RULE" name="PARENTID:=" type="hidden"></td>
						<td class="title">用户姓名:</td>
						<td><input name="a.NAME:like" type="text"></td>
						<td class="title">岗位名称:</td>
						<td><input name="e.NAME:like" type="text"></td>
					</tr>
					<tr style="text-align: center;">
						<td colspan="6"><a id="a_search" href="javascript:void(0)"
							class="easyui-linkbutton" iconCls="icon-search">查询</a> <a
							id="a_reset" href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-reload">清除条件</a></td>
					</tr>
					<tr style="text-align: center;">
						<td colspan="6" style="color: red;">
							当按照岗位查询时，回显岗位信息/其他时刻均不回显岗位，以提高查询效率.</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataMessageconsoleGrid">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="NAME" data-options="sortable:true" width="40">名称</th>
						<th field="LOGINNAME" data-options="sortable:true" width="40">
							登录名称</th>
						<th field="TYPE" data-options="sortable:true" width="40">类型</th>
						<th field="ORGNAME" data-options="sortable:true" width="40">
							组织机构</th>
						<th field="POSTNAME" data-options="sortable:false" width="80">
							岗位名称</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
<script type="text/javascript">
	var url_searchActionMessageconsole = "usermessage/queryConsoles.do";//查询URL
	var title_windowMessageconsole = "用户消息发送管理";//功能名称
	var gridMessageconsole;//数据表格对象
	var searchMessageconsole;//条件查询组件对象
	var currentType, currentTypeName;
	var toolBarMessageconsole = [ {
		id : 'send',
		text : '发送消息',
		iconCls : 'icon-email',
		handler : sendDataMessage
	} ];
	$(function() {
		//初始化数据表格
		gridMessageconsole = $('#dataMessageconsoleGrid').datagrid({
			url : url_searchActionMessageconsole,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarMessageconsole,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			border : false,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchMessageconsole = $('#searchMessageconsoleForm').searchForm({
			gridObj : gridMessageconsole
		});
		$('#MessageconsoleTree').tree({
			url : 'organization/organizationTree.do',
			onSelect : function(node) {
				$('#PARENTID_RULE').val(node.id);
				$('#PARENTTITLE_RULE').val(node.text);
				searchMessageconsole.dosearch({
					'ruleText' : searchMessageconsole.arrayStr()
				});
			}
		});
		$('#MessageconsoleTreeReload').bind('click', function() {
			$('#MessageconsoleTree').tree('reload');
		});
		$('#MessageconsoleTreeOpenAll').bind('click', function() {
			$('#MessageconsoleTree').tree('expandAll');
		});
	});

	//新增
	function sendDataMessage() {
		  var selectedArray = $(gridMessageconsole).datagrid('getSelections');
	      if (selectedArray.length > 0) {
	        // 有数据执行操作
	        var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
            var url = "usermessage/sendpage.do"+ '?operateType=' + PAGETYPE.ADD+'&ids=' + $.farm.getCheckedIds(gridMessageconsole,'ID');
    		$.farm.openWindow({
    			id : 'winUsermessageSender',
    			width : 600,
    			height : 300,
    			modal : true,
    			url : url,
    			title : '发送消息'
    		});
	      } else {
	        $.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
	            'info');
	      }
	}
</script>
</html>