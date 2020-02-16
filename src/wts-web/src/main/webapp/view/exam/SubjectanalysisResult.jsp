<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
		<table id="dataSubjectanalysisGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="PCONTENT" data-options="sortable:true" width="80">备注</th>
					<th field="PSTATE" data-options="sortable:true" width="60">状态</th>
					<th field="CTIME" data-options="sortable:true" width="50">创建时间</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	var url_delActionSubjectanalysis = "subjectanalysis/del.do";//删除URL
	var url_formActionSubjectanalysis = "subjectanalysis/form.do";//增加、修改、查看URL
	var url_searchActionSubjectanalysis = "subjectanalysis/query.do?subjectid=${subjectid}";//查询URL
	var title_windowSubjectanalysis = "试题解析管理";//功能名称
	var gridSubjectanalysis;//数据表格对象
	var searchSubjectanalysis;//条件查询组件对象
	var toolBarSubjectanalysis = [ {
		id : 'add',
		text : '新增',
		iconCls : 'icon-add',
		handler : addDataSubjectanalysis
	}, {
		id : 'edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : editDataSubjectanalysis
	}, {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDataSubjectanalysis
	} ];
	$(function() {
		//初始化数据表格
		gridSubjectanalysis = $('#dataSubjectanalysisGrid').datagrid({
			url : url_searchActionSubjectanalysis,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarSubjectanalysis,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
	});
	//新增
	function addDataSubjectanalysis() {
		var url = url_formActionSubjectanalysis + '?operateType='
				+ PAGETYPE.ADD + "&subjectid=${subjectid}";
		$.farm.openWindow({
			id : 'winSubjectanalysis',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDataSubjectanalysis() {
		var selectedArray = $(gridSubjectanalysis).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionSubjectanalysis + '?operateType='
					+ PAGETYPE.EDIT + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winSubjectanalysis',
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
	function delDataSubjectanalysis() {
		var selectedArray = $(gridSubjectanalysis).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridSubjectanalysis).datagrid('loading');
					$.post(url_delActionSubjectanalysis + '?ids='
							+ $.farm.getCheckedIds(gridSubjectanalysis, 'ID'),
							{}, function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridSubjectanalysis).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridSubjectanalysis).datagrid('reload');
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