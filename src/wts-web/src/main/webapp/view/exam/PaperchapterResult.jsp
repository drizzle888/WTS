<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',collapsible:false,border:true"
		style="overflow: hidden;">
		<div
			style="height: 40px; text-align: center; overflow: hidden; padding-top: 10px; font-size: 16px; font-weight: 700;">答卷名称:${paper.name }</div>
	</div>
	<div id="tt">
		<a class="icon-reload" id="chapterTypeReload"></a>
	</div>
	<div
		data-options="region:'west',iconCls:'icon-comment',collapsible:false,tools:'#tt',title:'答卷章节'"
		style="width: 280px;">
		<div style="padding: 4px; text-align: center;">
			<a id="addchapterFun" class="easyui-linkbutton"
				data-options="iconCls:'icon-add'">添加章节</a>
			<!--  -->
			<a id="editchapterFun" class="easyui-linkbutton"
				data-options="iconCls:'icon-edit'">修改章节</a>
			<!--  -->
			<a id="delchapterFun" class="easyui-linkbutton"
				data-options="iconCls:'icon-remove'">删除章节</a>
		</div>
		<ul id="chapterTypeTree"
			style="width: 270px; overflow: hidden; height: 270px;">
			<li>请先添加章节...</li>
		</ul>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataPapersubjectGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="SORT" data-options="sortable:true" width="15">排序</th>
					<th field="TIPTYPE" data-options="sortable:true" width="20">试题类型</th>
					<th field="TIPSTR" data-options="sortable:true" width="90">试题题目</th>
					<th field="MTITLE" data-options="sortable:true" width="30">引用材料</th>
					<th field="POINT" data-options="sortable:true" width="20">得分</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	var url_delActionPapersubject = "papersubject/del.do";//删除URL
	var url_formActionPapersubject = "papersubject/form.do";//增加、修改、查看URL
	var url_searchActionPapersubject = "papersubject/query.do?paperId=${paper.id}";//查询URL
	var title_windowPapersubject = "答卷试题管理";//功能名称
	var gridPapersubject;//数据表格对象
	var searchPapersubject;//条件查询组件对象
	var toolBarPapersubject = [ {
		id : 'view',
		text : '预览答卷',
		iconCls : 'icon-tip',
		handler : viewDataPapersubject
	}, {
		id : 'add',
		text : '添加试题',
		iconCls : 'icon-add',
		handler : addDataPapersubject
	}, {
		id : 'del',
		text : '移除试题',
		iconCls : 'icon-remove',
		handler : delDataPapersubject
	}, {
		id : 'pointset',
		text : '设置分数',
		iconCls : 'icon-bestseller',
		handler : papersubjectPointSetting
	}, {
		id : 'upSort',
		text : '顺序上移',
		iconCls : 'icon-upcoming-work',
		handler : PaperSubjectUpsort
	} ];
	$(function() {
		//初始化数据表格
		gridPapersubject = $('#dataPapersubjectGrid').datagrid({
			url : url_searchActionPapersubject,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarPapersubject,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchPapersubject = $('#searchPapersubjectForm').searchForm({
			gridObj : gridPapersubject
		});
		$('#chapterTypeTree').tree({
			url : 'paperchapter/chapterTypeTree.do?paperId=${paperId}',
			onSelect : function(node) {
				$(gridPapersubject).datagrid('load', {
					chapterId : node.id
				});
			}
		});
		$('#chapterTypeReload').bind('click', function() {
			$('#chapterTypeTree').tree('reload');
		});
		$('#addchapterFun').bind('click', chapterAdd);
		$('#editchapterFun').bind('click', chapterEdit);
		$('#delchapterFun').bind('click', chapterDel);
	});
	//添加章节
	function chapterAdd() {
		var nodes = $('#chapterTypeTree').tree('getSelected');
		if (nodes) {
			var url = 'paperchapter/form.do?parentid=' + nodes.id
					+ '&paperid=${paper.id}&operateType=' + PAGETYPE.ADD;
			$.farm.openWindow({
				id : 'winPaperchapter',
				width : 600,
				height : 300,
				modal : true,
				url : url,
				title : '添加章节'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, "请选中一个章节!", 'info');
		}
	}
	//修改章节
	function chapterEdit() {
		var nodes = $('#chapterTypeTree').tree('getSelected');
		if (nodes) {
			if (nodes.id == 'NONE') {
				$.messager.alert(MESSAGE_PLAT.PROMPT, "根节点不能执行该操作!", 'info');
				return;
			}
			var url = 'paperchapter/form.do?&ids=' + nodes.id
					+ '&paperid=${paper.id}&operateType=' + PAGETYPE.EDIT;
			$.farm.openWindow({
				id : 'winPaperchapter',
				width : 600,
				height : 300,
				modal : true,
				url : url,
				title : '修改章节'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, "请选中一个章节!", 'info');
		}
	}
	//删除章节
	function chapterDel() {
		var nodes = $('#chapterTypeTree').tree('getSelected');
		if (nodes) {
			if (nodes.id == 'NONE') {
				$.messager.alert(MESSAGE_PLAT.PROMPT, "根节点不能执行该操作!", 'info');
				return;
			}
			// 有数据执行操作
			var str = "是否删除该章节?";
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$.post('paperchapter/del.do?ids=' + nodes.id, {}, function(
							flag) {
						var jsonObject = JSON.parse(flag, null);
						if (jsonObject.STATE == 0) {
							$('#chapterTypeTree').tree('reload');
						} else {
							var str = MESSAGE_PLAT.ERROR_SUBMIT
									+ jsonObject.MESSAGE;
							$.messager.alert(MESSAGE_PLAT.ERROR, str, 'error');
						}
					});
				}
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, "请选中一个章节!", 'info');
		}
	}

	//答卷预览
	function viewDataPapersubject() {
		$.farm.openWindow({
			id : 'winPaperView',
			width : 800,
			height : 400,
			modal : true,
			url : "paper/view.do?paperId=${paper.id}",
			title : '答卷预览'
		});
	}
	//新增
	function addDataPapersubject() {
		var nodes = $('#chapterTypeTree').tree('getSelected');
		if (nodes) {
			if (nodes.id == 'NONE') {
				$.messager.alert(MESSAGE_PLAT.PROMPT, "根节点不能执行该操作!", 'info');
				return;
			}
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, "请选中一个章节!", 'info');
			return;
		}
		var url = 'subject/chooselist.do?operateType=' + PAGETYPE.ADD;
		$.farm.openWindow({
			id : 'winPapersubject',
			width : 900,
			height : 450,
			modal : true,
			url : url,
			title : '添加试题'
		});
	}
	//设置分数
	function papersubjectPointSetting() {
		var selectedArray = $(gridPapersubject).datagrid('getSelections');
		if (selectedArray.length <= 0) {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
			return;
		}
		var url = 'paperchapter/pointSetPage.do?' + 'paperSubjectIds='
				+ $.farm.getCheckedIds(gridPapersubject, 'ID');
		$.farm.openWindow({
			id : 'winPapersubjectPoint',
			width : 400,
			height : 200,
			modal : true,
			url : url,
			title : '设置题目得分'
		});
	}
	//顺序上移
	function PaperSubjectUpsort() {
		var selectedArray = $(gridPapersubject).datagrid('getSelections');
		if (selectedArray.length <= 0) {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
			return;
		}
		var url = 'paperchapter/sortUp.do?' + 'paperSubjectIds='
				+ $.farm.getCheckedIds(gridPapersubject, 'ID');
		// 有数据执行操作
		var str = selectedArray.length + "条数据的位置将要前移？";
		$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
			if (flag) {
				$(gridPapersubject).datagrid('loading');
				$.post(url, {}, function(flag) {
					var jsonObject = JSON.parse(flag, null);
					$(gridPapersubject).datagrid('loaded');
					if (jsonObject.STATE == 0) {
						$(gridPapersubject).datagrid('reload');
					} else {
						var str = MESSAGE_PLAT.ERROR_SUBMIT
								+ jsonObject.MESSAGE;
						$.messager.alert(MESSAGE_PLAT.ERROR, str, 'error');
					}
				});
			}
		});
	}

	//删除
	function delDataPapersubject() {
		var selectedArray = $(gridPapersubject).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridPapersubject).datagrid('loading');
					$.post(url_delActionPapersubject + '?ids='
							+ $.farm.getCheckedIds(gridPapersubject, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridPapersubject).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridPapersubject).datagrid('reload');
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
	//选择试题到答卷中
	function chooseSubjectCallback(gridSubject) {
		var nodes = $('#chapterTypeTree').tree('getSelected');
		if (nodes) {
			if (nodes.id == 'NONE') {
				$.messager.alert(MESSAGE_PLAT.PROMPT, "根节点不能执行该操作!", 'info');
				return;
			}
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, "请选中一个章节!", 'info');
			return;
		}
		var selectedArray = $(gridSubject).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + "道题将被添加到答卷中，是否继续?";
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridPapersubject).datagrid('loading');
					$.post(
							'paperchapter/addSubject.do?chapterId=' + nodes.id
									+ '&ids='
									+ $.farm.getCheckedIds(gridSubject, 'ID'),
							{}, function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridPapersubject).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridPapersubject).datagrid('reload');
									$('#winPapersubject').window('close');
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