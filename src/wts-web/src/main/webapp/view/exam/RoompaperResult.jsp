<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
		<table id="dataRoompaperGrid">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="PAPERNAME" data-options="sortable:true" width="60">考卷名称</th>
					<th field="NAME" data-options="sortable:true" width="90">别名</th>
					<th field="PAPERSTATE" data-options="sortable:true" width="30">考卷状态</th>
					<th field="CURRENTUSERNUM" data-options="sortable:true" width="30">参加考试</th>
					<th field="ADJUDGEUSERNUM" data-options="sortable:true" width="30">完成阅卷</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	var url_delActionRoompaper = "roompaper/del.do";//删除URL
	var url_CardMng = "card/list.do";//清空用户答题卡URL
	var url_clearUserCard = "roompaper/clearUserCard.do";//清空用户答题卡URL
	var url_formActionRoompaper = "roompaper/form.do";//增加、修改、查看URL
	var url_searchActionRoompaper = "roompaper/query.do?roomid=${roomid}";//查询URL
	var title_windowRoompaper = "答卷管理";//功能名称
	var gridRoompaper;//数据表格对象
	var searchRoompaper;//条件查询组件对象
	var toolBarRoompaper = [ {
		id : 'add',
		text : '添加答卷',
		iconCls : 'icon-add',
		handler : addDataRoompaper
	}, {
		id : 'setoname',
		text : '设置别名',
		iconCls : 'icon-invoice',
		handler : setOtherName
	}, {
		id : 'setoname',
		text : '清除别名',
		iconCls : 'icon-order',
		handler : clearOtherName
	}, {
		id : 'del',
		text : '移除答卷',
		iconCls : 'icon-remove',
		handler : delDataRoompaper
	}, {
		id : 'clearCard',
		text : '清空答题卡',
		iconCls : 'icon-exclamation-red-frame',
		handler : clearUsercard
	}, {
		id : 'userCards',
		text : '答题卡管理',
		iconCls : 'icon-client_account_template',
		handler : UsercardMng
	} ];
	$(function() {
		//初始化数据表格
		gridRoompaper = $('#dataRoompaperGrid').datagrid({
			url : url_searchActionRoompaper,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarRoompaper,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			border : false,
			striped : true,
			rownumbers : true,
			ctrlSelect : true
		});
	});
	//新增
	function addDataRoompaper() {
		var url = "paper/chooselist.do" + '?operateType=' + PAGETYPE.ADD;
		$.farm.openWindow({
			id : 'winRoompaper',
			width : 700,
			height : 400,
			modal : true,
			url : url,
			title : '添加答卷'
		});
	}

	//设置答卷在答题室中的别名
	function setOtherName() {
		var selectedArray = $(gridRoompaper).datagrid('getSelections');
		if (selectedArray.length > 0) {
			$.messager.prompt('设置别名', '输入别名：', function(r) {
				if (r) {
					$.post('roompaper/editOtherName.do', {
						ids : $.farm.getCheckedIds(gridRoompaper, 'ID'),
						name : r
					}, function(flag) {
						if (flag.STATE == 0) {
							$(gridRoompaper).datagrid('reload');
						} else {
							$.messager.alert(MESSAGE_PLAT.ERROR, flag.MESSAGE,
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
	//清除別名
	function clearOtherName() {
		var selectedArray = $(gridRoompaper).datagrid('getSelections');
		if (selectedArray.length > 0) {
			$.post('roompaper/clearOtherName.do', {
				ids : $.farm.getCheckedIds(gridRoompaper, 'ID')
			},
					function(flag) {
						if (flag.STATE == 0) {
							$(gridRoompaper).datagrid('reload');
						} else {
							$.messager.alert(MESSAGE_PLAT.ERROR, flag.MESSAGE,
									'error');
						}
					}, 'json');
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}

	//用户答题卡管理
	function UsercardMng() {
		var selectedArray = $(gridRoompaper).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_CardMng + '?operateType=' + PAGETYPE.EDIT
					+ '&roompaperId=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winUsercardMng',
				width : 750,
				height : 350,
				modal : true,
				url : url,
				title : '用户答题卡'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}

	//添加答卷,回调函数
	function chooseThePaperBackFunction(paperids) {
		$.post("roompaper/addPaperSubmit.do", {
			roomid : '${roomid}',
			"paperids" : paperids
		}, function(flag) {
			var jsonObject = JSON.parse(flag, null);
			if (jsonObject.STATE == 0) {
				$(gridRoompaper).datagrid('reload');
				$('#winRoompaper').window('close');
			} else {
				$.messager.alert(MESSAGE_PLAT.ERROR, jsonObject.MESSAGE,
						'error');
			}
		});
	}
	//清空用户答题卡
	function clearUsercard() {
		var selectedArray = $(gridRoompaper).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + "张答卷下的用户答题记录将被全部删除，该操作不可恢复，确认执行?";
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridRoompaper).datagrid('loading');
					$.post(url_clearUserCard + '?ids='
							+ $.farm.getCheckedIds(gridRoompaper, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridRoompaper).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridRoompaper).datagrid('reload');
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
	function delDataRoompaper() {
		var selectedArray = $(gridRoompaper).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridRoompaper).datagrid('loading');
					$.post(url_delActionRoompaper + '?ids='
							+ $.farm.getCheckedIds(gridRoompaper, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridRoompaper).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(gridRoompaper).datagrid('reload');
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