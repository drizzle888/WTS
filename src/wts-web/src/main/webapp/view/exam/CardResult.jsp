<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false">
		<form id="searchCardForm">
			<table class="editTable">
				<tr>
					<td class="title">答题人姓名:</td>
					<td><input name="b.NAME:like" style="width: 100%;" type="text"></td>
					<td class="title">答题卡状态:</td>
					<td><select name="a.PSTATE:like">
							<option value="">全部</option>
							<option value="1">开始答题</option>
							<option value="2">手动交卷</option>
							<option value="3">超时未交卷</option>
							<option value="4">超时自动交卷</option>
							<option value="5">已自动阅卷</option>
							<option value="6">已完成阅卷</option>
							<option value="7">发布成绩</option>
					</select></td>
					<td><a id="a_search" href="javascript:void(0)"
						class="easyui-linkbutton" iconCls="icon-search">查询</a> <a
						id="a_reset" href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-reload">清除条件</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataCardGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="USERNAME" data-options="sortable:true" width="30">答题人</th>
					<th field="STARTTIME" data-options="sortable:true" width="50">开始时间</th>
					<th field="ENDTIME" data-options="sortable:true" width="30">交卷时间</th>
					<th field="COMPLETENUM" data-options="sortable:true" width="15">完成</th>
					<th field="ALLNUM" data-options="sortable:true" width="15">总量</th>
					<th field="ADJUDGETIME" data-options="sortable:true" width="50">阅卷时间</th>
					<th field="ADJUDGEUSERNAME" data-options="sortable:true" width="30">阅卷人</th>
					<th field="PSTATE" data-options="sortable:true" width="40">状态</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	var url_delActionCard = "card/del.do";//删除URL
	var url_formActionCard = "card/form.do";//增加、修改、查看URL
	var url_searchActionCard = "card/query.do?roompaperIds=${roompaperIds}";//查询URL
	var title_windowCard = "答题卡管理";//功能名称
	var gridCard;//数据表格对象
	var searchCard;//条件查询组件对象
	var toolBarCard = [ /**{
	                 		id : 'view',
	                 		text : '查看',
	                 		iconCls : 'icon-tip',
	                 		handler : viewDataCard
	                 	}, {
	                 		id : 'add',
	                 		text : '新增',
	                 		iconCls : 'icon-add',
	                 		handler : addDataCard
	                 	}, {
	                 		id : 'edit',
	                 		text : '修改',
	                 		iconCls : 'icon-edit',
	                 		handler : editDataCard
	                 	}, **/
	{
		id : 'del',
		text : '删除答题卡',
		iconCls : 'icon-remove',
		handler : delDataCard
	},{
		id : 'reAdjudge',
		text : '重置阅卷状态',
		iconCls : 'icon-group_green_new',
		handler : reAdjudge
	},{
		id : 'pubPoint',
		text : '发布得分',
		iconCls : 'icon-blog--arrow',
		handler : pubPoint
	}  ];
	$(function() {
		//初始化数据表格
		gridCard = $('#dataCardGrid').datagrid({
			url : url_searchActionCard,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarCard,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchCard = $('#searchCardForm').searchForm({
			gridObj : gridCard
		});
	});
	//查看
	function viewDataCard() {
		var selectedArray = $(gridCard).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionCard + '?pageset.pageType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winCard',
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
	function addDataCard() {
		var url = url_formActionCard + '?operateType=' + PAGETYPE.ADD;
		$.farm.openWindow({
			id : 'winCard',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDataCard() {
		var selectedArray = $(gridCard).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionCard + '?operateType=' + PAGETYPE.EDIT
					+ '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winCard',
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
	
	//重置阅卷状态
	function reAdjudge() {
		var selectedArray = $(gridCard).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			$.messager.confirm(MESSAGE_PLAT.PROMPT,"重置后阅卷人员需要重新手工阅卷，是否继续？", function(flag) {
				if (flag) {
					$(gridCard).datagrid('loading');
					$.post('card/reAdjudge.do?ids='
							+ $.farm.getCheckedIds(gridCard, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridCard).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridCard).datagrid('reload');
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
	//发布得分
	function pubPoint() {
		var selectedArray = $(gridCard).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			$.messager.confirm(MESSAGE_PLAT.PROMPT,"完成自动阅卷的得分将被发布，发布后不可撤回，是否继续？", function(flag) {
				if (flag) {
					$(gridCard).datagrid('loading');
					$.post('card/pubPoint.do?ids='
							+ $.farm.getCheckedIds(gridCard, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridCard).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridCard).datagrid('reload');
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
	//删除
	function delDataCard() {
		var selectedArray = $(gridCard).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridCard).datagrid('loading');
					$.post(url_delActionCard + '?ids='
							+ $.farm.getCheckedIds(gridCard, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridCard).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridCard).datagrid('reload');
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