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
			<a id="OrganizationTreeReload" href="javascript:void(0)"
				class="easyui-linkbutton" data-options="plain:true"
				iconCls="icon-reload">刷新</a> <a id="OrganizationTreeOpenAll"
				href="javascript:void(0)" class="easyui-linkbutton"
				data-options="plain:true" iconCls="icon-sitemap">展开</a>
		</div>
		<ul id="OrganizationTree"></ul>
	</div>
	<div class="easyui-layout" data-options="region:'center',border:false">
		<div data-options="region:'north',border:false">
			<form id="searchOrganizationForm">
				<table class="editTable">
					<tr>
						<td class="title">上级节点:</td>
						<td><input id="PARENTTITLE_RULE" type="text"
							readonly="readonly" style="background: #F3F3E8"> <input
							id="PARENTID_RULE" name="PARENTID:=" type="hidden"></td>
						<td class="title"></td>
						<td></td>
						<td class="title"></td>
						<td></td>
					</tr>
					<tr>
						<td class="title">名称:</td>
						<td><input name="NAME:like" type="text"></td>
						<td class="title">组织类型:</td>
						<td colspan="3"><select name="TYPE:=" style="width: 120px;">
								<option value="">全部</option>
								<option value="1">本地</option>
								<option value="0">同步</option>
						</select></td>
					</tr>
					<tr style="text-align: center;">
						<td colspan="6"><a id="a_search" href="javascript:void(0)"
							class="easyui-linkbutton" iconCls="icon-search">查询</a> <a
							id="a_reset" href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-reload">清除条件</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataOrganizationGrid">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="NAME" data-options="sortable:true" width="40">名称</th>
						<th field="SORT" data-options="sortable:true" width="20">排序</th>
						<th field="TYPE" data-options="sortable:true" width="20">
							组织类型</th>
						<th field="COMMENTS" data-options="sortable:true" width="40">
							备注</th>
						<th field="APPID" data-options="sortable:true" width="40">
							APPID</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="orgToolbar">
			<a class="easyui-linkbutton"
				data-options="iconCls:'icon-tip',plain:true,onClick:viewDataOrganization">查看
			</a>
			<PF:IfParameterNoEquals key="config.sso.state" val="true">
				<a class="easyui-linkbutton"
					data-options="iconCls:'icon-add',plain:true,onClick:addDataOrganization">新增
				</a>
			</PF:IfParameterNoEquals>
			<a class="easyui-linkbutton"
				data-options="iconCls:'icon-edit',plain:true,onClick:editDataOrganization">修改/岗位设置
			</a>
			<PF:IfParameterNoEquals key="config.sso.state" val="true">
				<a class="easyui-linkbutton"
					data-options="iconCls:'icon-remove',plain:true,onClick:delDataOrganization">删除
				</a>
				<a class="easyui-linkbutton"
					data-options="iconCls:'icon-communication',plain:true,onClick:moveOrgtree">移动
				</a>
			</PF:IfParameterNoEquals>
			<PF:IfParameterEquals key="config.sso.state" val="true">
				<a class="easyui-linkbutton"
					data-options="iconCls:'icon-limited-edition',plain:true,onClick:sycnOrg">同步服务器
				</a>
			</PF:IfParameterEquals>
		</div>
	</div>
</body>
<script type="text/javascript">
	var url_delActionOrganization = "organization/del.do";//删除URL
	var url_tabsActionOrganization = "organization/organizationTabs.do"; //增加、修改、查看URL
	var url_searchActionOrganization = "organization/query.do";//查询URL
	var title_windowOrganization = "组织机构管理";//功能名称
	var gridOrganization;//数据表格对象
	var searchOrganization;//条件查询组件对象
	var currentType, currentTypeName;
	$(function() {
		//初始化数据表格
		gridOrganization = $('#dataOrganizationGrid').datagrid({
			url : url_searchActionOrganization,
			fit : true,
			fitColumns : true,
			'toolbar' : '#orgToolbar',
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			border : false,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchOrganization = $('#searchOrganizationForm').searchForm({
			gridObj : gridOrganization
		});
		$('#OrganizationTree').tree({
			url : 'organization/organizationTree.do',
			onSelect : function(node) {
				$('#PARENTID_RULE').val(node.id);
				$('#PARENTTITLE_RULE').val(node.text);
				searchOrganization.dosearch({
					'ruleText' : searchOrganization.arrayStr()
				});
			}
		});
		$('#OrganizationTreeReload').bind('click', function() {
			$('#OrganizationTree').tree('reload');
		});
		$('#OrganizationTreeOpenAll').bind('click', function() {
			$('#OrganizationTree').tree('expandAll');
		});
	});
	//查看
	function viewDataOrganization() {
		var selectedArray = $(gridOrganization).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_tabsActionOrganization + '?pageset.operateType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winOrganization',
				width : 600,
				height : 400,
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
	function addDataOrganization() {
		var parentID = $("#PARENTID_RULE").val();
		var url = url_tabsActionOrganization + '?operateType=' + PAGETYPE.ADD
				+ '&parentID=' + parentID;

		$.farm.openWindow({
			id : 'winOrganization',
			width : 600,
			height : 400,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDataOrganization() {
		var selectedArray = $(gridOrganization).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_tabsActionOrganization + '?operateType='
					+ PAGETYPE.EDIT + '&ids=' + selectedArray[0].ID;

			$.farm.openWindow({
				id : 'winOrganization',
				width : 600,
				height : 400,
				modal : true,
				url : url,
				title : '修改'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}

	//从服务器同步组织机构
	function sycnOrg() {
		$.messager.confirm(MESSAGE_PLAT.PROMPT, "同步机构可能需要执行较长时间，确定现在要同步么？",
				function(flag) {
					if (flag) {
						$(gridOrganization).datagrid('loading');
						$.post('ssosycn/sycnorgs.do?ids='
								+ $.farm.getCheckedIds(gridOrganization, 'ID'),
								{}, function(flag) {
									$(gridOrganization).datagrid('loaded');
									if (flag.STATE == 0) {
										$(gridOrganization).datagrid('reload');
										$('#OrganizationTree').tree('reload');
									} else {
										var str = MESSAGE_PLAT.ERROR_SUBMIT
												+ flag.MESSAGE;
										$.messager.alert(MESSAGE_PLAT.ERROR,
												str, 'error');
									}
								}, 'json');
					}
				});
	}

	//删除
	function delDataOrganization() {
		var selectedArray = $(gridOrganization).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridOrganization).datagrid('loading');
					$.post(url_delActionOrganization + '?ids='
							+ $.farm.getCheckedIds(gridOrganization, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridOrganization).datagrid('loaded');
								if (jsonObject.model.STATE == 0) {
									$(gridOrganization).datagrid('reload');
									$.messager.confirm('确认对话框',
											'数据更新,是否重新加载左侧组织机构树？', function(r) {
												if (r) {
													$('#OrganizationTree')
															.tree('reload');
												}
											});
								} else {
									var str = MESSAGE_PLAT.ERROR_SUBMIT
											+ jsonObject.model.MESSAGE;
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
	function moveOrgtree() {
		var selectedArray = $(gridOrganization).datagrid('getSelections');
		if (selectedArray.length > 0) {
			$.farm.openWindow({
				id : 'OrgTreeNodeWin',
				width : 250,
				height : 300,
				modal : true,
				url : "organization/orgTreeNodeTreeView.do?ids="
						+ $.farm.getCheckedIds(gridOrganization, 'ID'),
				title : '移动机构'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
</script>
</html>