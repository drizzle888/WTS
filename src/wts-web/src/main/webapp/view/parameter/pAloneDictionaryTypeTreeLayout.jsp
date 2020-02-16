<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'west',split:true,border:false"
		style="width: 250px;">
		<div class="TREE_COMMON_BOX_SPLIT_DIV">
			<a id="a_tree_reload" href="javascript:void(0)"
				class="easyui-linkbutton" data-options="plain:true"
				iconCls="icon-reload">刷新</a>
			<a id="a_tree_openAll" href="javascript:void(0)"
				class="easyui-linkbutton" data-options="plain:true"
				iconCls="icon-sitemap">展开</a>
		</div>
		<ul id="tt"></ul>
	</div>
	<div class="easyui-layout" data-options="region:'center',border:false">
		<div data-options="region:'north',border:false">
			<form id="searchtreeTypeForm">
				<table class="editTable">
					<tr>
						<td class="title">
							名称:
						</td>
						<td>
							<input id="NAME_like" name="a.NAME:like" type="text"
								readonly="readonly" style="background: #F3F3E8">
						</td>
						<td>
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
			<table id="dom_dT_var_grid">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="NAME" data-options="sortable:true" width="80">
							名称
						</th>
						<th field="PNAME" data-options="sortable:true" width="80">
							上级节点
						</th>
						<th field="ENTITYTYPE" data-options="sortable:true" width="80">
							类型
						</th>
						<th field="SORT" data-options="sortable:true" width="80">
							排序号
						</th>
						<th field="STATE" data-options="sortable:true" width="80">
							状态
						</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>

<script type="text/javascript">
	var url_delActiontreeType = "dictionaryType/del.do";//删除URL
	var url_formActiontreeType =  "dictionaryType/viewTreeform.do";//增加、修改、查看URL
	var url_searchActiontreeType = "dictionaryType/query.do?ids=${ids}";//查询URL
	var title_windowtreeType = "系统参数";//功能名称
	var gridtreeType;//数据表格对象
	var searchtreeType;//条件查询组件对象
	var toolBartreeType = [ {
		id : 'view',
		text : '查看',
		iconCls : 'icon-tip',
		handler : viewDatatreeType
	}, {
		id : 'add',
		text : '新增根分类',
		iconCls : 'icon-add',
		handler : addDatatreeType
	}, {
		id : 'add',
		text : '新增子分类',
		iconCls : 'icon-add',
		handler : addSubDatatreeType
	}, {
		id : 'edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : editDatatreeType
	}, {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDatatreeType
	} ];
	$(function() {
		$('#tt').tree( {
			url : 'dictionaryType/dictionaryTreeNote.do?ids=${ids}',
			onSelect : function(node) {
				$('#NAME_like').attr('value', node.text);
				searchtreeType.dosearch( {
					'ruleText' : searchtreeType.arrayStr()
				});
			}
		});
		$('#a_tree_reload').bind('click', function() {
			$('#tt').tree('reload');
		});
		$('#a_tree_openAll').bind('click', function() {
			$('#tt').tree('expandAll');
		});
		//初始化数据表格
		gridtreeType = $('#dom_dT_var_grid').datagrid( {
			url : url_searchActiontreeType,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBartreeType,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchtreeType = $('#searchtreeTypeForm').searchForm( {
			gridObj : gridtreeType
		});
	});
	//查看
	function viewDatatreeType() {
		var selectedArray = $(gridtreeType).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActiontreeType + '?operateType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow( {
				id : 'wintreeType',
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
	function addDatatreeType() {
		var url = url_formActiontreeType + '?dicId=${ids}&operateType='
				+ PAGETYPE.ADD;
		$.farm.openWindow( {
			id : 'wintreeType',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//新增子分类
	function addSubDatatreeType() {
		var selectedArray = $(gridtreeType).datagrid('getSelections');
		if (selectedArray.length > 0) {
			var url = url_formActiontreeType + '?dicId=${ids}&parentId='
					+ selectedArray[0].ID + '&operateType=' + PAGETYPE.ADD;
			$.farm.openWindow( {
				id : 'wintreeType',
				width : 600,
				height : 300,
				modal : true,
				url : url,
				title : '新增'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
	//修改
	function editDatatreeType() {
		var selectedArray = $(gridtreeType).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActiontreeType + '?operateType='
					+ PAGETYPE.EDIT + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow( {
				id : 'wintreeType',
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
	function delDatatreeType() {
		var selectedArray = $(gridtreeType).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridtreeType).datagrid('loading');
					$.post(url_delActiontreeType + '?ids='
							+ $.farm.getCheckedIds(gridtreeType, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridtreeType).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridtreeType).datagrid('reload');
								} else {
									var str = MESSAGE_PLAT.ERROR_SUBMIT
											+ flag.pageset.message;
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
