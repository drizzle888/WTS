<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<PF:basePath/>">
		<title>构造权限数据管理</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<jsp:include page="/view/conf/include.jsp"></jsp:include>
	</head>
	<body class="easyui-layout">
		<div data-options="region:'west',split:true,border:false"
			style="width: 260px;">
			<div class="TREE_COMMON_BOX_SPLIT_DIV">
				<select id="menuDomainId">
					<option value="alone">
						标准权限
					</option>
					<PF:OptionDictionary index="ALONE_MENU_DOMAIN" isTextValue="0" />
				</select>
				<a id="ActiontreeTreeReload" href="javascript:void(0)"
					class="easyui-linkbutton" data-options="plain:true"
					iconCls="icon-reload">刷新</a>
				<a id="ActiontreeTreeOpenAll" href="javascript:void(0)"
					class="easyui-linkbutton" data-options="plain:true"
					iconCls="icon-sitemap">展开</a>
			</div>
			<ul id="ActiontreeTree"></ul>
		</div>
		<div class="easyui-layout" data-options="region:'center',border:false">
			<div data-options="region:'north',border:false">
				<form id="searchActiontreeForm">
					<table class="editTable">
						<tr>
							<td class="title">
								名称:
							</td>
							<td>
								<input name="NAME:like" type="text">
							</td>
							<td class="title">
								备注:
							</td>
							<td>
								<input name="COMMENTS:like" type="text">
							</td>
							<td class="title">
								域:
							</td>
							<td>
								<select name="DOMAIN:=" id="DOMAIN_RULE" style="width: 120px;">
									<option value="">
									</option>
									<option value="alone">
										标准权限
									</option>
									<PF:OptionDictionary index="ALONE_MENU_DOMAIN" isTextValue="0" />
								</select>
							</td>
						</tr>
						<tr>
							<td class="title">
								上级节点:
							</td>
							<td>
								<input id="PARENTTITLE_RULE" type="text" readonly="readonly"
									style="background: #F3F3E8">
								<input id="PARENTID_RULE" name="PARENTID:=" type="hidden">
							</td>
							<td class="title">
								状态:
							</td>
							<td>
								<select name="STATE:=" style="width: 120px;">
									<option value="">
									</option>
									<option value="1">
										可用
									</option>
									<option value="0">
										禁用
									</option>
								</select>
							</td>
							<td class="title">
								类型:
							</td>
							<td>
								<select name="TYPE:=" style="width: 120px;">
									<option value="">
									</option>
									<option value="1">
										分类
									</option>
									<option value="2">
										菜单
									</option>
									<option value="3">
										权限
									</option>
								</select>
							</td>
						</tr>
						<tr style="text-align: center;">
							<td colspan="6">
								<a id="a_search" href="javascript:void(0)"
									class="easyui-linkbutton" iconCls="icon-search">查询</a>
								<a id="a_reset" href="javascript:void(0)"
									class="easyui-linkbutton" iconCls="icon-reload">清除条件</a>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div data-options="region:'center',border:false">
				<table id="dataActiontreeGrid">
					<thead>
						<tr>
							<th data-options="field:'ck',checkbox:true"></th>
							<th field="NAME" data-options="sortable:true" width="20">
								名称
							</th>
							<th field="TYPE" data-options="sortable:true" width="20">
								类型
							</th>
							<th field="SORT" data-options="sortable:true" width="20">
								排序
							</th>
							<th field="STATE" data-options="sortable:true" width="20">
								状态
							</th>
							<th field="ICON" data-options="sortable:true" width="20">
								样式
							</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</body>
	<script type="text/javascript">
	var url_delActionActiontree = "actiontree/del.do";//删除URL
	var url_formActionActiontree = "actiontree/form.do";//增加、修改、查看URL
	var url_searchActionActiontree = "actiontree/query.do";//查询URL
	var title_windowActiontree = "构造权限管理";//功能名称
	var gridActiontree;//数据表格对象
	var searchActiontree;//条件查询组件对象
	var currentType, currentTypeName;
	var toolBarActiontree = [ {
		id : 'view',
		text : '查看',
		iconCls : 'icon-tip',
		handler : viewDataActiontree
	}, {
		id : 'add',
		text : '新增',
		iconCls : 'icon-add',
		handler : addDataActiontree
	}, {
		id : 'edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : editDataActiontree
	}, {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDataActiontree
	}, {
		id : 'move',
		text : '移动',
		iconCls : 'icon-move_to_folder',
		handler : moveActiontree
	} ];
	$(function() {
		//初始化数据表格
		gridActiontree = $('#dataActiontreeGrid').datagrid( {
			url : url_searchActionActiontree,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarActiontree,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			border : false,
			ctrlSelect : true
		});
		//初始化条件查询
		searchActiontree = $('#searchActiontreeForm').searchForm( {
			gridObj : gridActiontree
		});
		$('#ActiontreeTreeReload').bind('click', function() {
			$('#ActiontreeTree').tree('reload');
		});
		$('#ActiontreeTreeOpenAll').bind('click', function() {
			$('#ActiontreeTree').tree('expandAll');
		});
		loadTree($('#menuDomainId').val());
		$('#menuDomainId').bind('change', function() {
			$('#PARENTID_RULE').val('');
			$('#PARENTTITLE_RULE').val('');
			loadTree($('#menuDomainId').val());
			$('#DOMAIN_RULE').val($('#menuDomainId').val());
			searchActiontree.dosearch( {
				'ruleText' : searchActiontree.arrayStr()
			});
		});
	});
	function loadTree(domain) {
		$('#ActiontreeTree').tree( {
			url : 'actiontree/loadtree.do?domain=' + domain,
			onSelect : function(node) {
				$('#PARENTID_RULE').val(node.id);
				$('#PARENTTITLE_RULE').val(node.text);
				searchActiontree.dosearch( {
					'ruleText' : searchActiontree.arrayStr()
				});
			}
		});
	}
	//查看
	function viewDataActiontree() {
		var selectedArray = $(gridActiontree).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionActiontree + '?operateType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow( {
				id : 'winActiontree',
				width : 600,
				height : 320,
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
	function addDataActiontree() {
		var selectedArray = $(gridActiontree).datagrid('getSelections');
		if (selectedArray.length != 0) {
			$.messager.alert(MESSAGE_PLAT.PROMPT, "如要增加子节点请选择左侧树形节点作为父节点!",
					'info');
			return false;
		}
		var url = url_formActionActiontree + '?parentid='
				+ $('#PARENTID_RULE').val() + '&operateType='
				+ PAGETYPE.ADD + '&domain=' + $('#menuDomainId').val();
		$.farm.openWindow( {
			id : 'winActiontree',
			width : 600,
			height : 320,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDataActiontree() {
		var selectedArray = $(gridActiontree).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionActiontree + '?operateType='
					+ PAGETYPE.EDIT + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow( {
				id : 'winActiontree',
				width : 600,
				height : 320,
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
	function delDataActiontree() {
		var selectedArray = $(gridActiontree).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridActiontree).datagrid('loading');
					$.post(url_delActionActiontree + '?ids='
							+ $.farm.getCheckedIds(gridActiontree, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridActiontree).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridActiontree).datagrid('reload');
									$.messager.confirm('确认对话框', '数据更新,是否重新加载左侧权限树？', function(r){
										if (r){
											$('#ActiontreeTree').tree('reload');
										}
									});
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
	function moveActiontree() {
		var selectedArray = $(gridActiontree).datagrid('getSelections');
		if (selectedArray.length > 0) {
			$.farm.openWindow( {
				id : 'ActionTreeNodeWin',
				width : 250,
				height : 300,
				modal : true,
				url : "actiontree/treePage.do?ids="
						+ $.farm.getCheckedIds(gridActiontree, 'ID'),
				title : '移动菜单'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
</script>
</html>