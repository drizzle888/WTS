<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--设置阅读权限-->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false">
		<form id="dom_chooseSearchSetreadpop"></form>
	</div>
	<div data-options="region:'center',border:false">
		<table class="easyui-datagrid" id="dom_chooseGridSetSender">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="NAME" data-options="sortable:true" width="80">姓名</th>
					<th field="ORGNAME" data-options="sortable:true" width="80">组织机构</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	var chooseGridSetSender;
	var chooseSearchfarmSetreadpop;
	var toolbar_chooseSetreadpop = [ {
		text : '添加抄送人',
		iconCls : 'icon-hire-me',
		handler : function() {
			$.farm.openWindow({
				id : 'chooseUserWin',
				width : 600,
				height : 400,
				modal : true,
				url : 'user/chooseUser.do',
				title : '选择用户'
			});
		}
	}, {
		text : '删除抄送人',
		iconCls : 'icon-remove',
		handler : delPop
	} ];

	//删除权限
	function delPop() {
		var selectedArray = $(chooseGridSetSender).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$.messager.progress({
						text : '处理中...'
					});
					$(chooseGridSetSender).datagrid('loading');
					$.post('messagemodel/delUserToModel.do?ids='
							+ $.farm.getCheckedIds(chooseGridSetSender, 'ID'),
							{}, function(flag) {
								$.messager.progress('close');
								var jsonObject = JSON.parse(flag, null);
								$(chooseGridSetSender).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(chooseGridSetSender).datagrid('reload');
									$(gridMessagemodel).datagrid('reload');
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

	//添加用户到权限中
	function chooseWindowCallBackHandle(selectedArray) {
		$.messager.progress({
			text : '处理中...'
		});
		var userids;
		$(selectedArray).each(function(i, obj) {
			if (userids) {
				userids = userids + ',' + obj.ID;
			} else {
				userids = obj.ID;
			}
		});
		$.post('messagemodel/addUserToModel.do', {
			userids : userids,
			modelid : '${modelId}'
		}, function(flag) {
			$.messager.progress('close');
			$("#chooseUserWin").window('close');
			if (flag.STATE == '0') {
				$(chooseGridSetSender).datagrid('reload');
				$(gridMessagemodel).datagrid('reload');
			} else {
				$.messager.alert(MESSAGE_PLAT.PROMPT, flag.MESSAGE, 'error');
			}
		}, 'json');
	}

	$(function() {
		chooseGridSetSender = $('#dom_chooseGridSetSender').datagrid({
			url : 'messagemodel/querySenders.do?modelId=${modelId}',
			fit : true,
			'toolbar' : toolbar_chooseSetreadpop,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			rownumbers : true,
			ctrlSelect : true,
			fitColumns : true
		});
		chooseSearchfarmSetreadpop = $('#dom_chooseSearchSetreadpop')
				.searchForm({
					gridObj : chooseGridSetSender
				});
	});
</script>