<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
		<table id="dataRandomstepGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="NAME" data-options="sortable:true" width="60">名称</th>
					<th field="TYPENAME" data-options="sortable:true" width="60">题库分类</th>
					<th field="TIPTYPE" data-options="sortable:true" width="20">题型</th>
					<th field="SORT" data-options="sortable:true" width="20">排序</th>
					<th field="SUBPOINT" data-options="sortable:true" width="20">得分</th>
					<th field="SUBNUM" data-options="sortable:true" width="20">数量</th>
					<th field="PCONTENT" data-options="sortable:true" width="80">备注</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	var url_delActionRandomstep = "randomstep/del.do";//删除URL
	var url_formActionRandomstep = "randomstep/form.do";//增加、修改、查看URL
	var url_searchActionRandomstep = "randomstep/query.do?itemid=${itemid}";//查询URL
	var title_windowRandomstep = "答卷随机步骤管理";//功能名称
	var gridRandomstep;//数据表格对象
	var searchRandomstep;//条件查询组件对象
	var toolBarRandomstep = [{
		id : 'add',
		text : '新增',
		iconCls : 'icon-add',
		handler : addDataRandomstep
	}, {
		id : 'edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : editDataRandomstep
	}
	, {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDataRandomstep
	} ];
	$(function() {
		//初始化数据表格
		gridRandomstep = $('#dataRandomstepGrid').datagrid({
			url : url_searchActionRandomstep,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarRandomstep,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
	});
	//查看
	function viewDataRandomstep() {
		var selectedArray = $(gridRandomstep).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionRandomstep + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winRandomstep',
				width : 600,
				height : 350,
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
	function addDataRandomstep() {
		var url = url_formActionRandomstep + '?operateType=' + PAGETYPE.ADD+"&itemid=${itemid}";
		$.farm.openWindow({
			id : 'winRandomstep',
			width : 600,
			height : 350,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDataRandomstep() {
		var selectedArray = $(gridRandomstep).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionRandomstep + '?operateType='
					+ PAGETYPE.EDIT + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winRandomstep',
				width : 600,
				height : 350,
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
	function delDataRandomstep() {
		var selectedArray = $(gridRandomstep).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridRandomstep).datagrid('loading');
					$.post(url_delActionRandomstep + '?ids='
							+ $.farm.getCheckedIds(gridRandomstep, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridRandomstep).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridRandomstep).datagrid('reload');
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