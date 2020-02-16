<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<table id="dataPostGrid">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th field="EXTENDIS" data-options="sortable:true" width="20">
				子机构可用
			</th>
			<th field="NAME" data-options="sortable:true" width="40">
				岗位名称
			</th>
		</tr>
	</thead>
</table>
<script type="text/javascript">
	var url_delActionPost = "post/del.do";//删除URL
	var url_formActionPost = "post/form.do";//增加、修改、查看URL
	var url_searchActionPost = "post/query.do?ids="
			+ $('#domTabsId').val();//查询URL
	var title_windowPost = "岗位管理";//功能名称
	var gridPost;//数据表格对象
	var pageType = '${pageset.operateType}';
	var toolBarPost = [ {
		id : 'add',
		text : '新增',
		iconCls : 'icon-add',
		handler : addDataPost
	}, {
		id : 'edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : editDataPost
	}, {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDataPost
	}, {
		id : 'lock',
		text : '设置权限',
		iconCls : 'icon-lock',
		handler : setActions
	}];
	$(function() {
		/* if (pageType == '0') {
			//toolBarPost = [];
		} */
		//初始化数据表格
		gridPost = $('#dataPostGrid').datagrid( {
			url : url_searchActionPost,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarPost,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
	});
	//查看
	function viewDataPost() {
		var selectedArray = $(gridPost).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionPost + '?operateType=' + PAGETYPE.VIEW
					+ '&ids=' + selectedArray[0].ID;
			$.farm.openWindow( {
				id : 'winPostForm',
				width : 600,
				height : 200,
				modal : true,
				url : url,
				title : '浏览岗位'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}
	//新增
	function addDataPost() {
		if (!$('#domTabsId').val()) {
			$.messager.alert(MESSAGE_PLAT.PROMPT, "无法获得组织机构ID", 'info');
		}
		var url = url_formActionPost + '?ids=' + $('#domTabsId').val()
				+ '&operateType=' + PAGETYPE.ADD;
		$.farm.openWindow( {
			id : 'winPostForm',
			width : 600,
			height : 250,
			modal : true,
			url : url,
			title : '新增岗位'
		});
	}
	//修改
	function editDataPost() {
		var selectedArray = $(gridPost).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionPost + '?operateType=' + PAGETYPE.EDIT
					+ '&ids=' + selectedArray[0].ID;
			$.farm.openWindow( {
				id : 'winPostForm',
				width : 600,
				height : 250,
				modal : true,
				url : url,
				title : '修改岗位'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}
	//设置岗位权限
	function setActions() {
		var selectedArray = $(gridPost).datagrid('getSelections');
		if (selectedArray.length == 1) {
			$.farm.openWindow( {
				id : 'winPostActionsChoose',
				width : 300,
				height : 400,
				modal : true,
				url : "post/postActions.do?ids="
					+ selectedArray[0].ID,
				title : '设置权限'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}

	//删除
	function delDataPost() {
		var selectedArray = $(gridPost).datagrid('getSelections');
		//if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridPost).datagrid('loading');
					$.post(url_delActionPost + '?ids='
							+ $.farm.getCheckedIds(gridPost, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridPost).datagrid('loaded');
								if (jsonObject.model.STATE == 0) {
									$(gridPost).datagrid('reload');
								} else {
									var str = MESSAGE_PLAT.ERROR_SUBMIT
											+ flag.pageset.message;
									$.messager.alert(MESSAGE_PLAT.ERROR, str,
											'error');
								}
							});
				}
			});
		//} else {
		//	$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
		//			'info');
		//}
	}
</script>