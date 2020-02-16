<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>业务分类数据管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',split:true,border:false"
		style="width: 250px;">
		<div class="TREE_COMMON_BOX_SPLIT_DIV">
			<a id="examTypeTreeReload" href="javascript:void(0)"
				class="easyui-linkbutton" data-options="plain:true"
				iconCls="icon-reload">刷新</a> <a id="examTypeTreeOpenAll"
				href="javascript:void(0)" class="easyui-linkbutton"
				data-options="plain:true" iconCls="icon-sitemap">展开</a>
		</div>
		<ul id="examTypeTree"></ul>
	</div>
	<div class="easyui-layout" data-options="region:'center',border:false">
		<div data-options="region:'north',border:false">
			<form id="searchExamtypeForm">
				<table class="editTable">
					<tr>
						<td class="title">上级节点:</td>
						<td><input id="PARENTTITLE_RULE" type="text"
							readonly="readonly" style="background: #F3F3E8"> <input
							id="PARENTID_RULE" name="PARENTID:=" type="hidden"></td>
						<td class="title">名称:</td>
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
			<table id="dataExamtypeGrid">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="NAME" data-options="sortable:true" width="20">名称</th>
						<th field="SORT" data-options="sortable:true" width="20">排序</th>
						<th field="STATE" data-options="sortable:true" width="20">状态</th>
						<th field="MNGPOP" data-options="sortable:true" width="20">管理权限</th>
						<th field="ADJUDGEPOP" data-options="sortable:true" width="20">阅卷权限</th>
						<!--<th field="QUERYPOP" data-options="sortable:true" width="20">查询权限</th>
						 <th field="SUPERPOP" data-options="sortable:true" width="20">超级权限</th> -->
						<th field="COMMENTS" data-options="sortable:true" width="20">备注</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="ExamTypeBar">
			<a class="easyui-linkbutton"
				data-options="iconCls:'icon-tip',plain:true,onClick:viewDataExamtype">查看
			</a>
			<a class="easyui-linkbutton"
				data-options="iconCls:'icon-add',plain:true,onClick:addDataExamtype">新增
			</a> 
			<a class="easyui-linkbutton"
				data-options="iconCls:'icon-edit',plain:true,onClick:editDataExamtype">修改
			</a> 
			<a class="easyui-linkbutton"
				data-options="iconCls:'icon-remove',plain:true,onClick:delDataExamtype">删除
			</a> 
			<a class="easyui-linkbutton"
				data-options="iconCls:'icon-communication',plain:true,onClick:moveTypetree">移动
			</a> 
			
			<!-- 1使用权、2管理权、3超级管理权 -->
			<a href="javascript:void(0)" id="mb" class="easyui-menubutton"
				data-options="menu:'#mm6',iconCls:'icon-lock'">权限控制</a>
			<div id="mm6" style="width: 150px;">
				<div data-options="iconCls:'icon-eye'"
					onclick="examPopMng(1)">管理权限</div>
				<div data-options="iconCls:'icon-group_green_edit'"
					onclick="examPopMng(2)">阅卷权限</div>
				<!-- <div data-options="iconCls:'icon-group_green_new'"
					onclick="examPopMng(3)">查询权限</div>
				<div class="menu-sep"></div>
				<div data-options="iconCls:'icon-special-offer'"
					onclick="examPopMng(4)">超级权限</div> -->
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	var url_delActionExamtype = "examtype/del.do";//删除URL
	var url_formActionExamtype = "examtype/form.do";//增加、修改、查看URL
	var url_searchActionExamtype = "examtype/query.do";//查询URL
	var title_windowExamtype = "业务分类管理";//功能名称
	var gridExamtype;//数据表格对象
	var searchExamtype;//条件查询组件对象
	$(function() {
		//初始化数据表格
		gridExamtype = $('#dataExamtypeGrid').datagrid({
			url : url_searchActionExamtype,
			fit : true,
			fitColumns : true,
			'toolbar' : '#ExamTypeBar',
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchExamtype = $('#searchExamtypeForm').searchForm({
			gridObj : gridExamtype
		});
		$('#examTypeTree').tree({
			url : 'examtype/examtypeTree.do?funtype=0',
			onSelect : function(node) {
				$('#PARENTID_RULE').val(node.id);
				$('#PARENTTITLE_RULE').val(node.text);
				searchExamtype.dosearch({
					'ruleText' : searchExamtype.arrayStr()
				});
			}
		});
		$('#examTypeTreeReload').bind('click', function() {
			$('#examTypeTree').tree('reload');
		});
		$('#examTypeTreeOpenAll').bind('click', function() {
			$('#examTypeTree').tree('expandAll');
		});
	});
	//查看
	function viewDataExamtype() {
		var selectedArray = $(gridExamtype).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionExamtype + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winExamtype',
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
	function addDataExamtype() {
		var parentId = $("#PARENTID_RULE").val();
		var url = url_formActionExamtype + '?operateType=' + PAGETYPE.ADD+'&parentId='+parentId;
		$.farm.openWindow({
			id : 'winExamtype',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDataExamtype() {
		var selectedArray = $(gridExamtype).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionExamtype + '?operateType=' + PAGETYPE.EDIT
					+ '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winExamtype',
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
	//考试权限管理
	function examPopMng(type) {
		var selectedArray = $(gridExamtype).datagrid('getSelections');
		if (selectedArray.length > 0) {
			var url = 'exampop/list.do?functype=' + type + '&typeids='
					+ $.farm.getCheckedIds(gridExamtype, 'ID');
			$.farm.openWindow({
				id : 'winExamPopMng',
				width : 700,
				height : 400,
				modal : true,
				url : url,
				title : '考试权限管理'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
	//删除
	function delDataExamtype() {
		var selectedArray = $(gridExamtype).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridExamtype).datagrid('loading');
					$.post(url_delActionExamtype + '?ids='
							+ $.farm.getCheckedIds(gridExamtype, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridExamtype).datagrid('loaded');
								if (jsonObject.STATE == 0) {
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
	function moveTypetree() {
		var selectedArray = $(gridExamtype).datagrid('getSelections');
		if (selectedArray.length > 0) {
			$.farm.openWindow({
				id : 'examTypeNodeWin',
				width : 250,
				height : 300,
				modal : true,
				url : "examtype/examTypeTreeView.do?funtype=0&ids="
						+ $.farm.getCheckedIds(gridExamtype, 'ID'),
				title : '移动分类'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
	//移动分类回调函数
	function chooseExamNodeBackFunc(node,typeid) {
		$.messager.confirm(MESSAGE_PLAT.PROMPT, "是否移分类到目标分类下？",
				function(flag) {
					if (flag) {
						$.post("examtype/moveNodeSubmit.do", {
							ids : typeid,
							id : node.id
						}, function(flag) {
							var jsonObject = JSON.parse(flag, null);
							if (jsonObject.STATE == 0) {
								$(gridExamtype).datagrid('reload');
								$.messager.confirm('确认对话框', '数据更新,是否重新加载左侧分类树？', function(r){
									if (r){
										$('#examTypeTree').tree('reload');
									}
								});
								$('#examTypeNodeWin').window('close');
							} else {
								$.messager.alert(MESSAGE_PLAT.ERROR,
										jsonObject.MESSAGE, 'error');
							}
						});
					}
				});
	}
</script>
</html>