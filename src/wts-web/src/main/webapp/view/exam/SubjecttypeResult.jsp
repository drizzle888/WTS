<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>考题分类数据管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',split:true,border:false"
		style="width: 250px;">
		<div class="TREE_COMMON_BOX_SPLIT_DIV">
			<a id="subjectTypeTreeReload" href="javascript:void(0)"
				class="easyui-linkbutton" data-options="plain:true"
				iconCls="icon-reload">刷新</a> <a id="subjectTypeTreeOpenAll"
				href="javascript:void(0)" class="easyui-linkbutton"
				data-options="plain:true" iconCls="icon-sitemap">展开</a>
		</div>
		<ul id="subjectTypeTree"></ul>
	</div>
	<div class="easyui-layout" data-options="region:'center',border:false">
		<div data-options="region:'north',border:false">
			<form id="searchSubjecttypeForm">
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
			<table id="dataSubjecttypeGrid">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="NAME" data-options="sortable:true" width="50">名称</th>
						<th field="SORT" data-options="sortable:true" width="20">排序</th>
						<th field="STATE" data-options="sortable:true" width="20">状态</th>
						<th field="READPOP" data-options="sortable:true" width="20">使用权限</th>
						<th field="WRITEPOP" data-options="sortable:true" width="20">管理权限</th>
						<th field="COMMENTS" data-options="sortable:true" width="80">备注</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="SubjectTypeBar">
			<a class="easyui-linkbutton"
				data-options="iconCls:'icon-tip',plain:true,onClick:viewDataSubjecttype">查看
			</a> <a class="easyui-linkbutton"
				data-options="iconCls:'icon-add',plain:true,onClick:addDataSubjecttype">新增
			</a> <a class="easyui-linkbutton"
				data-options="iconCls:'icon-edit',plain:true,onClick:editDataSubjecttype">修改
			</a> <a class="easyui-linkbutton"
				data-options="iconCls:'icon-remove',plain:true,onClick:delDataSubjecttype">删除
			</a> <a class="easyui-linkbutton"
				data-options="iconCls:'icon-communication',plain:true,onClick:moveSubjectTypetree">移动
			</a>
			<!-- 1使用权、2编辑权 -->
			<a href="javascript:void(0)" id="mb" class="easyui-menubutton"
				data-options="menu:'#mm6',iconCls:'icon-lock'">权限控制</a>
			<div id="mm6" style="width: 150px;">
				<div data-options="iconCls:'icon-eye'" onclick="subjectPopMng(1)">使用权</div>
				<div class="menu-sep"></div>
				<div data-options="iconCls:'icon-group_green_edit'"
					onclick="subjectPopMng(2)">编辑权</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	var url_delActionSubjecttype = "subjecttype/del.do";//删除URL
	var url_formActionSubjecttype = "subjecttype/form.do";//增加、修改、查看URL
	var url_searchActionSubjecttype = "subjecttype/query.do";//查询URL
	var title_windowSubjecttype = "考题分类管理";//功能名称
	var gridSubjecttype;//数据表格对象
	var searchSubjecttype;//条件查询组件对象
	$(function() {
		//初始化数据表格
		gridSubjecttype = $('#dataSubjecttypeGrid').datagrid({
			url : url_searchActionSubjecttype,
			fit : true,
			fitColumns : true,
			'toolbar' : '#SubjectTypeBar',
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchSubjecttype = $('#searchSubjecttypeForm').searchForm({
			gridObj : gridSubjecttype
		});
		$('#subjectTypeTree').tree({
			url : 'subjecttype/subjecttypeTree.do?funtype=0',
			onSelect : function(node) {
				$('#PARENTID_RULE').val(node.id);
				$('#PARENTTITLE_RULE').val(node.text);
				searchSubjecttype.dosearch({
					'ruleText' : searchSubjecttype.arrayStr()
				});
			}
		});
		$('#subjectTypeTreeReload').bind('click', function() {
			$('#subjectTypeTree').tree('reload');
		});
		$('#subjectTypeTreeOpenAll').bind('click', function() {
			$('#subjectTypeTree').tree('expandAll');
		});
	});
	//查看
	function viewDataSubjecttype() {
		var selectedArray = $(gridSubjecttype).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionSubjecttype + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winSubjecttype',
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
	function addDataSubjecttype() {
		var parentID = $("#PARENTID_RULE").val();
		var url = url_formActionSubjecttype + '?operateType=' + PAGETYPE.ADD
				+ '&parentId=' + parentID;
		$.farm.openWindow({
			id : 'winSubjecttype',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDataSubjecttype() {
		var selectedArray = $(gridSubjecttype).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionSubjecttype + '?operateType='
					+ PAGETYPE.EDIT + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winSubjecttype',
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

	//试题权限管理
	function subjectPopMng(type) {
		var selectedArray = $(gridSubjecttype).datagrid('getSelections');
		if (selectedArray.length > 0) {
			var url = 'subjectpop/list.do?functype=' + type + '&typeids='
					+ $.farm.getCheckedIds(gridSubjecttype, 'ID');
			$.farm.openWindow({
				id : 'winSubjectPopMng',
				width : 700,
				height : 400,
				modal : true,
				url : url,
				title : '分类权限管理'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}

	//删除
	function delDataSubjecttype() {
		var selectedArray = $(gridSubjecttype).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridSubjecttype).datagrid('loading');
					$.post(url_delActionSubjecttype + '?ids='
							+ $.farm.getCheckedIds(gridSubjecttype, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridSubjecttype).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridSubjecttype).datagrid('reload');
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
	//移动分类
	function moveSubjectTypetree() {
		var selectedArray = $(gridSubjecttype).datagrid('getSelections');
		if (selectedArray.length > 0) {
			$.farm.openWindow({
				id : 'subjectTypeNodeWin',
				width : 250,
				height : 300,
				modal : true,
				url : "subjecttype/subjectTypeTreeView.do?funtype=0&ids="
						+ $.farm.getCheckedIds(gridSubjecttype, 'ID'),
				title : '移动分类'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
	//移动分类回调函数
	function chooseSubjectNodeBackFunc(node, typeid) {
		$.messager.confirm(MESSAGE_PLAT.PROMPT, "是否移分类到目标分类下？", function(flag) {
			if (flag) {
				$.post("subjecttype/moveNodeSubmit.do", {
					ids : typeid,
					id : node.id
				}, function(flag) {
					var jsonObject = JSON.parse(flag, null);
					if (jsonObject.STATE == 0) {
						$(gridSubjecttype).datagrid('reload');
						$.messager.confirm('确认对话框', '数据更新,是否重新加载左侧分类树？',
								function(r) {
									if (r) {
										$('#subjectTypeTree').tree('reload');
									}
								});
						$('#subjectTypeNodeWin').window('close');
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