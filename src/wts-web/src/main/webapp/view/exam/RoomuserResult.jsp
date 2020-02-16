<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false">
		<form id="searchRoomuserForm">
			<table class="editTable">
				<tr>
					<td class="title">人员姓名:</td>
					<td><input name="B.NAME:like" type="text"></td>
					<td class="title">机构姓名:</td>
					<td><input name="E.NAME:like" type="text"></td>
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
		<table id="dataRoomuserGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="USERNAME" data-options="sortable:true" width="60">姓名</th>
					<th field="ORGNAME" data-options="sortable:true" width="60">组织机构</th>
					<th field="ROOMNAME" data-options="sortable:true" width="60">答题室名称</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	var url_delActionRoomuser = "roomuser/del.do";//删除URL
	var url_formActionRoomuser = "roomuser/form.do";//增加、修改、查看URL
	var url_searchActionRoomuser = "roomuser/query.do?roomid=${roomid}";//查询URL
	var title_windowRoomuser = "参考人管理";//功能名称
	var gridRoomuser;//数据表格对象
	var searchRoomuser;//条件查询组件对象
	var toolBarRoomuser = [ {
		id : 'add',
		text : '添加',
		iconCls : 'icon-add',
		handler : addRoomUser
	}, {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDataRoomuser
	} ];
	$(function() {
		//初始化数据表格
		gridRoomuser = $('#dataRoomuserGrid').datagrid({
			url : url_searchActionRoomuser,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarRoomuser,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchRoomuser = $('#searchRoomuserForm').searchForm({
			gridObj : gridRoomuser
		});
	});
	//导入
	function addRoomUser() {
		var url = "user/chooseUser.do";
		$.farm.openWindow( {
			id : 'chooseUserWin',
			width : 600,
			height : 400,
			modal : true,
			url : url,
			title : '导入用户'
		});
		chooseWindowCallBackHandle = function(row) {
			var userids;
			$(row).each(function(i, obj) {
				if (userids) {
					userids = userids + ',' + obj.ID;
				} else {
					userids = obj.ID;
				}
			});
			$.post("roomuser/add.do", {
				'userids' : userids,
				roomid: '${roomid}'
			}, function(flag) {
				if (flag.STATE == 0) {
					$('#chooseUserWin').window('close');
					$(gridRoomuser).datagrid('reload');
				} else {
					$.messager.alert(MESSAGE_PLAT.ERROR, flag.MESSAGE,
							'error');
				}
			}, 'json');
		};
	}
	 
	//删除
	function delDataRoomuser() {
		var selectedArray = $(gridRoomuser).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridRoomuser).datagrid('loading');
					$.post(url_delActionRoomuser + '?ids='
							+ $.farm.getCheckedIds(gridRoomuser, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridRoomuser).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridRoomuser).datagrid('reload');
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
</html>