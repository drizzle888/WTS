<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false">
		<form id="searchChoosePostForm">
			<table class="editTable">
				<tr>
					<td class="title">
						岗位名称:
					</td>
					<td>
						<input name="post.NAME:like" type="text">
					</td>
					<td class="title">
						组织机构名称:
					</td>
					<td>
						<input name="org.NAME:like" type="text">
					</td>
				</tr>
				<tr style="text-align: center;">
					<td colspan="4">
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
		<table id="dataChoosePostGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="NAME" data-options="sortable:true" width="30">
						岗位名称
					</th>
					<th field="ORGNAME" data-options="sortable:true" width="30">
						组织机构
					</th>
					<th field="EXTENDIS" data-options="sortable:true" width="20">
						子机构可用
					</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	var url_searchActionChoosePost = "post/query.do";//查询URL  admin/FarmAuthorityUserQuery.do
	var title_windowChoosePost = "用户管理";//功能名称
	var gridChoosePost;//数据表格对象
	var searchChoosePost;//条件查询组件对象
	var toolBarChoosePost = [ {
		id : 'choose',
		text : '选中',
		iconCls : 'icon-ok',
		handler : chooseChoosePostFun
	} ];
	$(function() {
		//初始化数据表格
		gridChoosePost = $('#dataChoosePostGrid').datagrid( {
			url : url_searchActionChoosePost,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarChoosePost,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			border : false,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchChoosePost = $('#searchChoosePostForm').searchForm( {
			gridObj : gridChoosePost
		});
	});
	function chooseChoosePostFun() {
		var selectedArray = $(gridChoosePost).datagrid('getSelections');
		if (selectedArray.length > 0) {
			chooseWindowCallBackHandleForPost(selectedArray);
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
</script>
