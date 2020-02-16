<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>计划任务管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<table class="easyui-datagrid" id="dom_datagridfarmqzscheduler">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="AUTOIS" data-options="sortable:true" width="80">
						启动类型</th>
					<th field="TASKNAME" data-options="sortable:true" width="80">
						任务</th>
					<th field="CTRIGGERNAME" data-options="sortable:true" width="80">
						触发计划</th>
					<th field="RUN" data-options="sortable:false" width="80">是否启动
					</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
<script type="text/javascript">
	var url_delActionfarmqzscheduler = "qzScheduler/del.do";//删除URL
	var url_formActionfarmqzscheduler = "qzScheduler/form.do";//增加、修改、查看URL
	var url_searchActionfarmqzscheduler = "qzScheduler/query.do";//查询URL
	var title_windowfarmqzscheduler = "计划任务管理";//功能名称
	var gridfarmqzscheduler;//数据表格对象
	var searchfarmqzscheduler;//条件查询组件对象
	var TOOL_BARfarmqzscheduler = [ {
		id : 'view',
		text : '查看',
		iconCls : 'icon-tip',
		handler : viewDatafarmqzscheduler
	}, {
		id : 'add',
		text : '新增',
		iconCls : 'icon-add',
		handler : addDatafarmqzscheduler
	}, {
		id : 'del',
		text : '删除',
		iconCls : 'icon-remove',
		handler : delDatafarmqzscheduler
	}, {
		id : 'edit',
		text : '启动',
		iconCls : 'icon-ok',
		handler : startDatafarmqzscheduler
	}, {
		id : 'edit',
		text : '暂停',
		iconCls : 'icon-busy',
		handler : stopDatafarmqzscheduler
	}, {
		id : 'runolone',
		text : '执行一次',
		iconCls : 'icon-showreel',
		handler : runDatafarmqzscheduler
	} ];
	$(function() {
		//初始化数据表格
		gridfarmqzscheduler = $('#dom_datagridfarmqzscheduler').datagrid({
			url : url_searchActionfarmqzscheduler,
			fit : true,
			fitColumns : true,
			'toolbar' : TOOL_BARfarmqzscheduler,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true,
			rowStyler : function(index, row) {
				if (row.RUNTYPE) {
					return 'color:green;';
				} else {
					return 'color:red;';
				}
			},
			fitColumns : true
		});
	});
	//查看
	function viewDatafarmqzscheduler() {
		var selectedArray = $(gridfarmqzscheduler).datagrid('getSelections');
		if (selectedArray.length > 0) {
			var url = url_formActionfarmqzscheduler + '?operateType='
					+ PAGETYPE.VIEW + '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winfarmqzscheduler',
				width : 600,
				height : 300,
				modal : true,
				url : url,
				title : '浏览'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
	//新增
	function addDatafarmqzscheduler() {
		var url = url_formActionfarmqzscheduler + '?operateType='
				+ PAGETYPE.ADD;
		$.farm.openWindow({
			id : 'winfarmqzscheduler',
			width : 600,
			height : 300,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//修改
	function editDatafarmqzscheduler() {
		var selectedArray = $(gridfarmqzscheduler).datagrid('getSelections');
		if (selectedArray.length > 0) {
			var url = url_formActionfarmqzscheduler + '?operateType='
					+ PAGETYPE.EDIT + '&ids=' + selectedArray[0].ID;
			;
			$.farm.openWindow({
				id : 'winfarmqzscheduler',
				width : 600,
				height : 300,
				modal : true,
				url : url,
				title : '修改'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
	//执行一次所选任务
	function runDatafarmqzscheduler() {
		var selectedArray = $(gridfarmqzscheduler).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + "条任务将被执行，确定继续？";
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$.post("qzScheduler/runOnec.do" + '?ids='
							+ $.farm.getCheckedIds(gridfarmqzscheduler), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								if (jsonObject.STATE == 0) {
									$(gridfarmqzscheduler).datagrid('reload');
									alert(jsonObject.INFO);
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
	function delDatafarmqzscheduler() {
		var selectedArray = $(gridfarmqzscheduler).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$.post(url_delActionfarmqzscheduler + '?ids='
							+ $.farm.getCheckedIds(gridfarmqzscheduler), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								if (jsonObject.STATE == 0) {
									$(gridfarmqzscheduler).datagrid('reload');
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
	//启动任务
	function startDatafarmqzscheduler() {
		var selectedArray = $(gridfarmqzscheduler).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + '项计划将被启动，是否继续?';
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$.post('qzScheduler/start.do' + '?ids='
							+ $.farm.getCheckedIds(gridfarmqzscheduler), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								if (jsonObject.STATE == 0) {
									$(gridfarmqzscheduler).datagrid('reload');
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
	//停止任务
	function stopDatafarmqzscheduler() {
		var selectedArray = $(gridfarmqzscheduler).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + '项计划将被停止，是否继续?';
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$.post('qzScheduler/stop.do' + '?ids='
							+ $.farm.getCheckedIds(gridfarmqzscheduler), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								if (jsonObject.STATE == 0) {
									$(gridfarmqzscheduler).datagrid('reload');
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




