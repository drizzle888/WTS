<%@page import="org.apache.commons.lang.math.RandomUtils"%>
<%@page import="java.util.RandomAccess"%>
<%@page import="java.util.Random"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!-- random是为了解决同一个页面多次调用用户选择窗口，未经过严格测试，注意bug的发生 -->
<% int random=RandomUtils.nextInt(); %>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false">
		<form id="search<%=random %>ChooseUserForm">
			<table class="editTable">
				<tr>
					<td class="title">名称:</td>
					<td><input name="F.NAME:like" type="text" style="width: 90px;"></td>
					<td class="title">登录名:</td>
					<td><input name="F.LOGINNAME:like" type="text" style="width: 90px;"></td>
					<td class="title">状态:</td>
					<td><select name="F.STATE:=" style="width: 90px;">
							<option value="1">可用</option>
							<option value="0">禁用</option>
							<option value="2">删除</option>
					</select></td>
				</tr>
				<tr>
					<td class="title">类型:</td>
					<td><select name="F.TYPE:=" style="width: 90px;">
							<option value=""></option>
							<option value="1">系统用户</option>
							<option value="2">其他</option>
							<option value="3">超级用户</option>
					</select></td>
					<td class="title">组织机构:</td>
					<td><input name="F.ORGNAME:like" type="text" style="width: 90px;"></td>
					<td class="title"></td>
					<td></td>
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
		<table id="data<%=random %>ChooseUserGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="NAME" data-options="sortable:true" width="30">名称</th>
					<th field="LOGINNAME" data-options="sortable:true" width="30">
						登录名</th>
					<th field="TYPE" data-options="sortable:true" width="20">类型</th>
					<th field="STATE" data-options="sortable:true" width="20">状态</th>
					<th field="ORGNAME" data-options="sortable:true" width="30">
						组织机构</th>
					<th field="LOGINTIME" data-options="sortable:true" width="30">
						登录时间</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	var url_searchAction<%=random %>ChooseUser = "user/query.do";//查询URL  admin/FarmAuthorityUserQuery.do
	var title_window<%=random %>ChooseUser = "用户管理";//功能名称
	var grid<%=random %>ChooseUser;//数据表格对象
	var search<%=random %>ChooseUser;//条件查询组件对象
	var toolBar<%=random %>ChooseUser = [ {
		id : 'choose',
		text : '选中',
		iconCls : 'icon-ok',
		handler : choose<%=random %>ChooseUserFun
	} ];
	$(function() {
		//初始化数据表格
		grid<%=random %>ChooseUser = $('#data<%=random %>ChooseUserGrid').datagrid({
			url : url_searchAction<%=random %>ChooseUser,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBar<%=random %>ChooseUser,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			border : false,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		search<%=random %>ChooseUser = $('#search<%=random %>ChooseUserForm').searchForm({
			gridObj : grid<%=random %>ChooseUser
		});
	});
	function choose<%=random %>ChooseUserFun() {
		var selectedArray = $(grid<%=random %>ChooseUser).datagrid('getSelections');
		if (selectedArray.length > 0) {
			chooseWindowCallBackHandle(selectedArray);
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
</script>
