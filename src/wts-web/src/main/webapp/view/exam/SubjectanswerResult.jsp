<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>考题答案数据管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<form id="searchSubjectanswerForm">
			<table class="editTable">
				<tr>
					<td class="title">答案:</td>
					<td><input name="ANSWER:like" type="text"></td>
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
		<table id="dataSubjectanswerGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="RIGHTANSWER" data-options="sortable:true" width="40">正确答案</th>
					<th field="POINT" data-options="sortable:true" width="40">得分权重</th>
					<th field="ANSWERNOTE" data-options="sortable:true" width="40">答案描述</th>
					<th field="SORT" data-options="sortable:true" width="20">排序</th>
					<th field="ANSWER" data-options="sortable:true" width="20">答案</th>
					<th field="VERSIONID" data-options="sortable:true" width="60">题目版本ID</th>
					<th field="PCONTENT" data-options="sortable:true" width="20">备注</th>
					<th field="PSTATE" data-options="sortable:true" width="20">状态</th>
					<th field="CUSER" data-options="sortable:true" width="50">CUSER</th>
					<th field="CUSERNAME" data-options="sortable:true" width="90">CUSERNAME</th>
					<th field="CTIME" data-options="sortable:true" width="50">CTIME</th>
					<th field="ID" data-options="sortable:true" width="20">ID</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
<script type="text/javascript">
	var url_delActionSubjectanswer = "subjectanswer/del.do";//删除URL
	var url_formActionSubjectanswer = "subjectanswer/form.do";//增加、修改、查看URL
	var url_searchActionSubjectanswer = "subjectanswer/query.do";//查询URL
	var title_windowSubjectanswer = "考题答案管理";//功能名称
	var gridSubjectanswer;//数据表格对象
	var searchSubjectanswer;//条件查询组件对象
	var toolBarSubjectanswer = [ {
		id : 'view',
		text : '查看',
		iconCls : 'icon-tip',
		handler : viewDataSubjectanswer
	}, {
		id : 'add',
		text : '新增',
		iconCls : 'icon-add',
		handler : addDataSubjectanswer
	}, {
		id : 'edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : editDataSubjectanswer
	}, {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDataSubjectanswer
	} ];
	$(function() {
		//初始化数据表格
		gridSubjectanswer = $('#dataSubjectanswerGrid').datagrid({
			url : url_searchActionSubjectanswer,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarSubjectanswer,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchSubjectanswer = $('#searchSubjectanswerForm').searchForm({
			gridObj : gridSubjectanswer
		});
	});
	//查看
	function viewDataSubjectanswer() {
		var selectedArray = $(gridSubjectanswer).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionSubjectanswer + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winSubjectanswer',
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
	function addDataSubjectanswer() {
		var url = url_formActionSubjectanswer + '?operateType=' + PAGETYPE.ADD;
		$.farm.openWindow({
			id : 'winSubjectanswer',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDataSubjectanswer() {
		var selectedArray = $(gridSubjectanswer).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionSubjectanswer + '?operateType='
					+ PAGETYPE.EDIT + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winSubjectanswer',
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
	function delDataSubjectanswer() {
		var selectedArray = $(gridSubjectanswer).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridSubjectanswer).datagrid('loading');
					$.post(url_delActionSubjectanswer + '?ids='
							+ $.farm.getCheckedIds(gridSubjectanswer, 'ID'),
							{}, function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridSubjectanswer).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridSubjectanswer).datagrid('reload');
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