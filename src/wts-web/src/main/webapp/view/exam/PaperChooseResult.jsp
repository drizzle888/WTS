<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!-- 考场选考卷 -->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'west',split:true,border:false"
		style="width: 200px;">
		<div class="TREE_COMMON_BOX_SPLIT_DIV">
			<a id="examTypePaperTreeReload" href="javascript:void(0)"
				class="easyui-linkbutton" data-options="plain:true"
				iconCls="icon-reload">刷新</a> <a id="examTypePaperTreeOpenAll"
				href="javascript:void(0)" class="easyui-linkbutton"
				data-options="plain:true" iconCls="icon-sitemap">展开</a>
		</div>
		<ul id="examTypePaperTree"></ul>
	</div>
	<div class="easyui-layout" data-options="region:'center',border:false">
		<div data-options="region:'north',border:false">
			<form id="searchChoosePaperForm">
				<table class="editTable">
					<tr>
						<td class="title">业务分类:</td>
						<td><input id="PARENTTITLE_CHOOSEPAPER_RULE" type="text"
							readonly="readonly" style="background: #F3F3E8; width: 120px;">
							<input id="PARENTID_CHOOSEPAPER_RULE" name="b.TREECODE:like"
							type="hidden"></td>
						<td class="title">考卷名称:</td>
						<td><input name="a.NAME:like" style="width: 120px;"
							type="text"></td>
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
						<th field="NAME" data-options="sortable:true" width="60">考卷名称</th>
						<th field="ADVICETIME" data-options="sortable:true" width="30">答题时间(分)</th>
						<th field="SUBJECTNUM" data-options="sortable:true" width="20">题量</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="PaperToolbar">
			<a class="easyui-linkbutton"
				data-options="iconCls:'icon-add',plain:true,onClick:chooseThePaper">选中
			</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var url_delActionPaper = "paper/del.do";//删除URL
	var url_formActionPaper = "paper/form.do";//增加、修改、查看URL
	var url_searchActionPaper = "paper/chooseQuery.do";//查询URL
	var title_windowPaper = "考卷管理";//功能名称
	var gridChoosePaper;//数据表格对象
	var searchChoosePaper;//条件查询组件对象
	$(function() {
		//初始化数据表格
		gridChoosePaper = $('#dataPaperGrid').datagrid({
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
		searchChoosePaper = $('#searchChoosePaperForm').searchForm({
			gridObj : gridChoosePaper
		});
		$('#examTypePaperTree').tree({
			url : 'examTypeTree/examtypeTree.do?funtype=1',
			onSelect : function(node) {
				$('#PARENTID_CHOOSEPAPER_RULE').val(node.id);
				$('#PARENTTITLE_CHOOSEPAPER_RULE').val(node.text);
				searchChoosePaper.dosearch({
					'ruleText' : searchChoosePaper.arrayStr()
				});
			}
		});
		$('#examTypePaperTreeReload').bind('click', function() {
			$('#examTypePaperTree').tree('reload');
		});
		$('#examTypePaperTreeOpenAll').bind('click', function() {
			$('#examTypePaperTree').tree('expandAll');
		});
	});
	//新增
	function chooseThePaper() {
		var selectedArray = $(gridChoosePaper).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var paperids = $.farm.getCheckedIds(gridChoosePaper, 'ID');
			try {
				chooseThePaperBackFunction(paperids);
			} catch (e) {
				alert("请实现chooseThePaperBackFunction()");
			}
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
</script>
</html>