<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--设置阅读权限-->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false">
		<form id="dom_chooseSearchSetreadpop">
			<table class="editTable">
				<tr>
					<td class="title">应用类型:</td>
					<td>${targetNames}</td>
					<td class="title">对象类型:</td>
					<td><select name="POPTYPE:=">
								<option value=""></option>
								<option value="1">用户</option>
								<option value="2">组织机构</option>
								<option value="3">岗位</option>
						</select></td>
				</tr>
				<tr style="text-align: center;">
						<td colspan="4"><a id="a_search" href="javascript:void(0)"
							class="easyui-linkbutton" iconCls="icon-search">查询</a> <a
							id="a_reset" href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-reload">清除条件</a></td>
				</tr>
				<tr style="text-align: center;">
						<td colspan="4" style="color: red;">添加完成授权后需要用户重新登录即可启用权限</td> 
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table class="easyui-datagrid" id="dom_chooseGridSetreadpop">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="POPTYPE" data-options="sortable:true" width="80">对象类型</th>
					<th field="ONAME" data-options="sortable:true" width="80">所有者名称</th>
					<th field="TARGETTYPE" data-options="sortable:true" width="80">应用类型</th>
					<th field="TARGETNAME" data-options="sortable:true" width="80">授权应用名称</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	var chooseGridSetreadpop;
	var chooseSearchfarmSetreadpop;
	var toolbar_chooseSetreadpop = [ {
		text : '添加用户',
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
		text : '添加组织机构',
		iconCls : 'icon-customers',
		handler : function() {
			$.farm.openWindow({
				id : 'chooseOrgWin',
				width : 300,
				height : 400,
				modal : true,
				url : 'organization/chooseOrg.do',
				title : '选择组织机构'
			});
		}
	}, {
		text : '添加岗位',
		iconCls : 'icon-credit-card',
		handler : function() {
			$.farm.openWindow({
				id : 'choosePostWin',
				width : 600,
				height : 400,
				modal : true,
				url : 'post/choosePost.do',
				title : '选择岗位'
			});
		}
	}, {
		text : '删除',
		iconCls : 'icon-remove',
		handler : delPop
	} ];
	//删除权限
	function delPop() {
		var selectedArray = $(chooseGridSetreadpop).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(chooseGridSetreadpop).datagrid('loading');
					$.post('popcom/delPop.do?ids='
							+ $.farm.getCheckedIds(chooseGridSetreadpop, 'ID'),
							{}, function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(chooseGridSetreadpop).datagrid('loaded');
								if (jsonObject.STATE == 0) {
									$(chooseGridSetreadpop).datagrid('reload');
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
	//添加组织机构到权限中
	function chooseOrgWindowCallBackHandle(node) {
		$.post('popcom/addOrgToPop.do', {
			orgids : node.id,
			targetIds : '${targetIds}',
			targetType : '${targetType}'
		}, function(flag) {
			if (flag.STATE == '0') {
				$.messager.alert(MESSAGE_PLAT.PROMPT, "组织机构添加成功!", 'info');
				$(chooseGridSetreadpop).datagrid('reload');
			} else {
				$.messager.alert(MESSAGE_PLAT.PROMPT, flag.MESSAGE, 'error');
			}
		}, 'json');
	}

	//添加用户到权限中
	function chooseWindowCallBackHandle(selectedArray) {
		var useridsvar;
		$(selectedArray).each(function(i, obj) {
			if (useridsvar) {
				useridsvar = useridsvar + ',' + obj.ID;
			} else {
				useridsvar = obj.ID;
			}
		});
		$.post('popcom/addUserToPop.do', {
			userids : useridsvar,
			targetIds : '${targetIds}',
			targetType : '${targetType}'
		}, function(flag) {
			$("#chooseUserWin").window('close');
			if (flag.STATE == '0') {
				$(chooseGridSetreadpop).datagrid('reload');
			} else {
				$.messager.alert(MESSAGE_PLAT.PROMPT, flag.MESSAGE, 'error');
			}
		}, 'json');
	}
	//添加岗位到权限中
	function chooseWindowCallBackHandleForPost(selectedArray) {
		var postidsvar;
		$(selectedArray).each(function(i, obj) {
			if (postidsvar) {
				postidsvar = postidsvar + ',' + obj.ID;
			} else {
				postidsvar = obj.ID;
			}
		});
		$.post('popcom/addPostToPop.do', {
			postids : postidsvar,
			targetIds : '${targetIds}',
			targetType : '${targetType}'
		}, function(flag) {
			$("#choosePostWin").window('close');
			if (flag.STATE == '0') {
				$(chooseGridSetreadpop).datagrid('reload');
			} else {
				$.messager.alert(MESSAGE_PLAT.PROMPT, flag.MESSAGE, 'error');
			}
		}, 'json');
	}

	$(function() {
		chooseGridSetreadpop = $('#dom_chooseGridSetreadpop')
				.datagrid(
						{
							url : 'popcom/query.do?targetIds=${targetIds}&targetType=${targetType}',
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
					gridObj : chooseGridSetreadpop
				});
	});
//-->
</script>