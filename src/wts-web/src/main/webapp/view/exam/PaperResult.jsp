<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>考卷数据管理</title>
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
			<form id="searchPaperForm">
				<table class="editTable">
					<tr>
						<td class="title">业务分类:</td>
						<td><input id="PARENTTITLE_RULE" type="text"
							readonly="readonly" style="background: #F3F3E8"> <input
							id="PARENTID_RULE" name="b.TREECODE:like" type="hidden"></td>
						<td class="title">考卷名称:</td>
						<td><input name="a.NAME:like" type="text"></td>
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
			<table id="dataPaperGrid">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="NAME" data-options="sortable:true" width="40">考卷名称</th>
						<th field="TYPENAME" data-options="sortable:true" width="40">业务分类</th>
						<th field="MODELTYPE" data-options="sortable:true" width="20">考卷类型</th>
						<th field="USERNAME" data-options="sortable:true" width="20">创建人</th>
						<th field="CTIME" data-options="sortable:true" width="20">创建时间</th>
						<th field="ADVICETIME" data-options="sortable:true" width="40">建议答题时间(分)</th>
						<th field="SUBJECTNUM" data-options="sortable:true" width="20">题目数量</th>
						<th field="PSTATE" data-options="sortable:true" width="20">状态</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="PaperToolbar">
			<a class="easyui-linkbutton"
				data-options="iconCls:'icon-tip',plain:true,onClick:viewDataPaper">预览答卷
			</a> <a href="javascript:void(0)" id="mb8" class="easyui-menubutton"
				data-options="menu:'#mm1',iconCls:'icon-add'">创建答卷</a>
			<div id="mm1" style="width: 150px;">
				<div data-options="iconCls:'icon-old-versions'"
					onclick="addDataPaper()">创建空白卷</div>
				<div class="menu-sep"></div>
				<div data-options="iconCls:'icon-archives'"
					onclick="addRandomPapers()">批量创建随机卷</div>
			</div>
			<a href="javascript:void(0)" id="mb8" class="easyui-menubutton"
				data-options="menu:'#mm8',iconCls:'icon-edit'">编辑</a>
			<div id="mm8" style="width: 150px;">
				<div data-options="iconCls:'icon-edit'" onclick="editDataPaper()">修改</div>
				<div data-options="iconCls:'icon-communication'"
					onclick="moveTypetree()">设置分类</div>
				<div class="menu-sep"></div>
				<div data-options="iconCls:'icon-remove'" onclick="delDataPaper()">删除</div>
			</div>
			<a href="javascript:void(0)" id="mb9" class="easyui-menubutton"
				data-options="menu:'#mm9',iconCls:'icon-communication'">题管理</a>
			<div id="mm9" style="width: 150px;">
				<div data-options="iconCls:'icon-edit'" onclick="manageSubject()">手工管理</div>
				<div class="menu-sep"></div>
				<div data-options="iconCls:'icon-showreel'"
					onclick="addRandomSubject()">随机出题</div>
			</div>
			<a href="javascript:void(0)" id="mb7" class="easyui-menubutton"
				data-options="menu:'#mm7',iconCls:'icon-networking'">发布</a>
			<div id="mm7" style="width: 150px;">
				<div onclick="examPublic()">发布</div>
				<div class="menu-sep"></div>
				<div onclick="examPrivate()">禁用</div>
				<div class="menu-sep"></div>
				<div onclick="examWord()">导出word答卷</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	var url_delActionPaper = "paper/del.do";//删除URL
	var url_formActionPaper = "paper/form.do";//增加、修改、查看URL
	var url_searchActionPaper = "paper/query.do";//查询URL
	var title_windowPaper = "考卷管理";//功能名称
	var gridPaper;//数据表格对象
	var searchPaper;//条件查询组件对象
	$(function() {
		//初始化数据表格
		gridPaper = $('#dataPaperGrid').datagrid({
			url : url_searchActionPaper,
			fit : true,
			fitColumns : true,
			'toolbar' : '#PaperToolbar',
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchPaper = $('#searchPaperForm').searchForm({
			gridObj : gridPaper
		});
		$('#examTypeTree').tree({
			url : 'examtype/examtypeTree.do?funtype=1',
			onSelect : function(node) {
				$('#PARENTID_RULE').val(node.id);
				$('#PARENTTITLE_RULE').val(node.text);
				searchPaper.dosearch({
					'ruleText' : searchPaper.arrayStr()
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
	function viewDataPaper() {
		var selectedArray = $(gridPaper).datagrid('getSelections');
		if (selectedArray.length == 1) {
			$.farm.openWindow({
				id : 'winPaperView',
				width : 800,
				height : 500,
				modal : true,
				url : "paper/view.do?paperId=" + selectedArray[0].ID,
				title : '答卷预览'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}
	//新增空白卷
	function addDataPaper() {
		if (!$('#PARENTID_RULE').val() || $('#PARENTID_RULE').val() == 'NONE') {
			$.messager.alert(MESSAGE_PLAT.PROMPT, "请选中一个左侧的分类后再添加答卷!", 'info');
			return;
		}
		var url = url_formActionPaper + '?operateType=' + PAGETYPE.ADD;
		$.farm.openWindow({
			id : 'winPaper',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '新增空白卷'
		});
	}
	//新增批量随机卷
	function addRandomPapers() {
		if (!$('#PARENTID_RULE').val() || $('#PARENTID_RULE').val() == 'NONE') {
			$.messager.alert(MESSAGE_PLAT.PROMPT, "请选中一个左侧的分类后再添加答卷!", 'info');
			return;
		}
		var url = 'paper/addRandomsPage.do?typeid=' + $('#PARENTID_RULE').val();
		$.farm.openWindow({
			id : 'winRandom',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '新增批量随机卷'
		});
	}

	//随机组题
	function addRandomSubject() {
		var selectedArray = $(gridPaper).datagrid('getSelections');
		if (selectedArray.length > 0 && selectedArray.length <= 5) {
			$.farm.openWindow({
				id : 'winPaper',
				width : 600,
				height : 350,
				modal : true,
				url : "paper/addRandomSubjects.do?paperids="
						+ $.farm.getCheckedIds(gridPaper, 'ID'),
				title : '添加随机题'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, "请至少选择一套题,最多不超过5套!", 'info');
		}
	}

	//修改
	function editDataPaper() {
		var selectedArray = $(gridPaper).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionPaper + '?operateType=' + PAGETYPE.EDIT
					+ '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winPaper',
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
	function delDataPaper() {
		var selectedArray = $(gridPaper).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridPaper).datagrid('loading');
					$.post(url_delActionPaper + '?ids='
							+ $.farm.getCheckedIds(gridPaper, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridPaper).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridPaper).datagrid('reload');
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
	function moveTypetree() {
		var selectedArray = $(gridPaper).datagrid('getSelections');
		if (selectedArray.length > 0) {
			$.farm.openWindow({
				id : 'examTypeNodeWin',
				width : 250,
				height : 300,
				modal : true,
				url : "examtype/examTypeTreeView.do?funtype=1&ids="
						+ $.farm.getCheckedIds(gridPaper, 'ID'),
				title : '设置分类'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
	//设置分类回调函数
	function chooseExamNodeBackFunc(node, ids) {
		var selectedArray = $(gridPaper).datagrid('getSelections');
		$.messager.confirm(MESSAGE_PLAT.PROMPT, "是否设置为该业务分类？", function(flag) {
			if (flag) {
				$.post("paper/examtypeSetting.do", {
					ids : $.farm.getCheckedIds(gridPaper, 'ID'),
					examtypeId : node.id
				}, function(flag) {
					var jsonObject = JSON.parse(flag, null);
					$(gridPaper).datagrid('loaded');
					$('#examTypeNodeWin').window('close');
					if (jsonObject.STATE == 0) {
						$(gridPaper).datagrid('reload');
					} else {
						var str = MESSAGE_PLAT.ERROR_SUBMIT
								+ jsonObject.MESSAGE;
						$.messager.alert(MESSAGE_PLAT.ERROR, str, 'error');
					}
				});
			}
		});
	}
	//发布
	function examPublic() {
		var selectedArray = $(gridPaper).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + "条数据将被发布，是否继续?";
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridPaper).datagrid('loading');
					$.post("paper/examPublic.do" + '?ids='
							+ $.farm.getCheckedIds(gridPaper, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridPaper).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridPaper).datagrid('reload');
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
	//导出答卷为word
	function examWord() {
		var selectedArray = $(gridPaper).datagrid('getSelections');
		if (selectedArray.length == 1) {
			$.messager.confirm('确认', '答卷导出为word后有可能会有部分题无法正常显示,请检查和手动调整后再使用!',
					function(r) {
						if (r) {
							$.messager.alert('导出答卷',
									'答卷生成中，等待浏览器下载... (完成后请手动关闭此对话框)', 'info');
							window.location.href = basePath
									+ "paper/exportWord.do?paperid="
									+ selectedArray[0].ID;
						}
					});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}

	//禁用
	function examPrivate() {
		var selectedArray = $(gridPaper).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + "条数据将被禁用，是否继续?";
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridPaper).datagrid('loading');
					$.post("paper/examPrivate.do" + '?ids='
							+ $.farm.getCheckedIds(gridPaper, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridPaper).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridPaper).datagrid('reload');
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

	//管理試卷
	function manageSubject(type) {
		var selectedArray = $(gridPaper).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = 'paperchapter/list.do?operateType=' + PAGETYPE.EDIT
					+ '&paperId=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winSubjectMng',
				width : 900,
				height : 500,
				modal : true,
				url : url,
				title : '试题管理'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}
</script>
</html>