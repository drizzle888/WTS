<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>文档附件数据管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<form id="searchDocfileForm">
			<table class="editTable">
				<tr>
					<td class="title">类型:</td>
					<td><select name="TYPE:=">
							<option value="">~全部~</option>
							<option value="1">图片</option>
							<option value="2">资源</option>
							<option value="3">压缩</option>
							<option value="0">其他</option>
					</select></td>
					<td class="title">显示名称:</td>
					<td><input name="NAME:like" type="text"></td>
					<td class="title">扩展名:</td>
					<td><input name="EXNAME:like" type="text"></td>
				</tr>
				<tr>
					<td class="title">文件名:</td>
					<td><input name="FILENAME:like" type="text"></td>
					<td class="title">状态:</td>
					<td><select name="PSTATE:=">
							<option value="1">使用</option>
							<option value="0">临时</option>
							<option value="3">永久</option>
							<option value="all">全部</option>
					</select></td>
					<td class="title">ID:</td>
					<td><input name="A.ID:like" type="text"></td>
				</tr>
				<tr>
					<td class="title">发布时间起:</td>
					<td><input name="STARTTIME:like" type="text" class="easyui-datebox"></td>
					<td class="title">发布时间止:</td>
					<td colspan="3"><input name="ENDTIME:like" type="text" class="easyui-datebox"></td>
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
		<table id="dataDocfileGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="NAME" data-options="sortable:true" width="100">
						显示名称</th>
					<th field="TYPE" data-options="sortable:true" width="20">类型</th>
					<th field="EXNAME" data-options="sortable:true" width="20">
						扩展名</th>
						<th field="CTIME" data-options="sortable:true" width="40">
						创建时间</th>
					<th field="LEN" data-options="sortable:true" width="40">
						文件大小(b)</th>
					<!-- <th field="FILENAME" data-options="sortable:true" width="160">
							文件名
						</th> -->
					<th field="PSTATE" data-options="sortable:true" width="20">状态
					</th>
					<th field="DOWNUM" data-options="sortable:true" width="20">下载量
					</th>
					<th field="PCONTENT" data-options="sortable:true" width="50">备注
					</th>
					<th field="ID" data-options="sortable:true" width="50">
						附件ID</th>
					<th field="DESCRIBESMIN" data-options="sortable:true" width="50">
						内容预览</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
<script type="text/javascript">
	var url_delActionDocfile = "docfile/del.do";//删除URL
	var url_formActionDocfile = "docfile/form.do";//增加、修改、查看URL
	var url_searchActionDocfile = "docfile/query.do";//查询URL
	var title_windowDocfile = "文档附件管理";//功能名称
	var gridDocfile,gridfarmdoc;//数据表格对象
	var searchDocfile;//条件查询组件对象
	var toolBarDocfile = [ {
		id : 'view',
		text : '查看',
		iconCls : 'icon-tip',
		handler : viewDataDocfile
	}, {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDataDocfile
	}];
	$(function() {
		//初始化数据表格
		gridDocfile = $('#dataDocfileGrid').datagrid({
			url : url_searchActionDocfile,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarDocfile,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			border : false,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchDocfile = $('#searchDocfileForm').searchForm({
			gridObj : gridDocfile
		});
	});
	//查看
	function viewDataDocfile() {
		var selectedArray = $(gridDocfile).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionDocfile + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winDocfile',
				width : 600,
				height : 500,
				modal : true,
				url : url,
				title : '浏览'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}
	//删除
	function delDataDocfile() {
		var selectedArray = $(gridDocfile).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridDocfile).datagrid('loading');
					$.post(url_delActionDocfile + '?ids='
							+ $.farm.getCheckedIds(gridDocfile, 'ID'), {},
							function(flag) {
								$(gridDocfile).datagrid('loaded');
								if (flag.STATE == 0) {
									$(gridDocfile).datagrid('reload');
								} else {
									var str = MESSAGE_PLAT.ERROR_SUBMIT
											+ flag.MESSAGE;
									$.messager.alert(MESSAGE_PLAT.ERROR, str,
											'error');
								}
							}, 'json');
				}
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
</script>
</html>