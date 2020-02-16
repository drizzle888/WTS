<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false">
		<form id="searchChooseOrgPostForm">
			<table class="editTable">
				<tr>
					<td class="title">岗位名称:</td>
					<td><input name="post.NAME:like" type="text"></td>
					<td class="title">组织机构名称:</td>
					<td><input name="org.NAME:like" type="text"></td>
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
		<table id="dataChooseOrgPostGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="NAME" data-options="sortable:true" width="30">岗位名称
					</th>
					<th field="ORGNAME" data-options="sortable:true" width="30">
						组织机构</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	var url_searchActionChooseOrgPost = "post/queryOrgPost.do?orgid=${orgid}";//查询URL  admin/FarmAuthorityUserQuery.do
	var title_windowChooseOrgPost = "用户管理";//功能名称
	var gridChooseOrgPost;//数据表格对象
	var searchChooseOrgPost;//条件查询组件对象
	var toolBarChooseOrgPost = [ {
		id : 'choose',
		text : '选中',
		iconCls : 'icon-ok',
		handler : chooseChooseOrgPostFun
	} ];
	$(function() {
		//初始化数据表格
		gridChooseOrgPost = $('#dataChooseOrgPostGrid').datagrid({
			url : url_searchActionChooseOrgPost,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarChooseOrgPost,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			border : false,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchChooseOrgPost = $('#searchChooseOrgPostForm').searchForm({
			gridObj : gridChooseOrgPost
		});
	});
	function chooseChooseOrgPostFun() {
		var selectedArray = $(gridChooseOrgPost).datagrid('getSelections');
		if (selectedArray.length > 0) {
			chooseWindowCallBackHandle(selectedArray);
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
</script>
