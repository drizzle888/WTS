<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<PF:basePath/>">
<title>用户数据管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:include page="/view/conf/include.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',split:true,border:false"
		style="width: 250px;">
		<div class="TREE_COMMON_BOX_SPLIT_DIV">
			<a id="MessageconsoleTreeReload" href="javascript:void(0)"
				class="easyui-linkbutton" data-options="plain:true"
				iconCls="icon-reload">刷新</a> <a id="MessageconsoleTreeOpenAll"
				href="javascript:void(0)" class="easyui-linkbutton"
				data-options="plain:true" iconCls="icon-sitemap">展开</a>
		</div>
		<ul id="MessageconsoleTree"></ul>
	</div>
	<div class="easyui-layout" data-options="region:'center',border:false">
		<div data-options="region:'north',border:false">
			<form id="searchUserForm">
				<table class="editTable">
					<tr>
						<td class="title">组织机构:</td>
						<td><input id="PARENTTITLE_RULE" type="text"
							readonly="readonly" style="background: #F3F3E8"> <input
							id="PARENTID_RULE" name="PARENTID:=" type="hidden"></td>
						<td class="title">名称:</td>
						<td><input name="F.NAME:like" type="text"></td>
						<td class="title">登录名:</td>
						<td><input name="LOGINNAME:like" type="text"></td>
					</tr>
					<tr>
						<td class="title">类型:</td>
						<td><select name="TYPE:=" style="width: 120px;">
								<option value=""></option>
								<option value="1">系统用户</option>
								<option value="3">超级用户</option>
						</select></td>
						<td class="title">岗位:</td>
						<td><input name="D.NAME:like" type="text"></td>
						<td class="title">状态:</td>
						<td><select name="STATE:=" style="width: 120px;">
								<option value="1">可用</option>
								<option value="0">禁用</option>
								<option value="2">删除</option>
								<option value="3">待审核</option>
						</select></td>
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
			<table id="dataUserGrid">
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
						<th field="COMMENTS" data-options="sortable:true" width="40">
							备注</th>
						<th field="OUTUSER" data-options="sortable:true" width="40">
							外部账号数</th>
						<th field="CTIME" data-options="sortable:true" width="40">
							创建时间</th>
					</tr>
				</thead>
			</table>
		</div>
		<div id="userToolbar">
			<a class="easyui-linkbutton"
				data-options="iconCls:'icon-tip',plain:true,onClick:viewDataUser">查看
			</a>
			<PF:IfParameterNoEquals key="config.sso.state" val="true">
				<a class="easyui-linkbutton"
					data-options="iconCls:'icon-add',plain:true,onClick:addDataUser">新增
				</a>
			</PF:IfParameterNoEquals>
			<a class="easyui-linkbutton"
				data-options="iconCls:'icon-edit',plain:true,onClick:editDataUser">修改
			</a> <a href="javascript:void(0)" id="mb" class="easyui-menubutton"
				data-options="menu:'#mm6',iconCls:'icon-edit'">批量设置</a>
			<div id="mm6" style="width: 150px;">
				<div onclick="sysUser()">设置为系统用户</div>
				<div class="menu-sep"></div>
				<div onclick="setState('0')">设置为禁用</div>
				<div onclick="setState('1')">设置为启用</div>
				<div onclick="setState('3')">设置为待审核</div>
				<div class="menu-sep"></div>
				<div onclick="setUsersOrg()">设置组织机构</div>
				<div onclick="addUsersPost()">添加新岗位</div>
				<div onclick="delUsersPost()">删除原岗位</div>
			</div>
			<a class="easyui-linkbutton"
				data-options="iconCls:'icon-remove',plain:true,onClick:delDataUser">删除
			</a>
			<PF:IfParameterNoEquals key="config.sso.state" val="true">
				<a href="javascript:void(0)" id="passwordmng"
					class="easyui-menubutton"
					data-options="menu:'#passwords',iconCls:'icon-lock'">密码管理</a>
				<div id="passwords" style="width: 150px;">
					<div onclick="initPasswd()">初始化为默认密码</div>
					<div onclick="setingPassWd()">初始化为指定密码</div>
				</div>
			</PF:IfParameterNoEquals>
			<a href="javascript:void(0)" id="mb" class="easyui-menubutton"
				data-options="menu:'#mm5',iconCls:'icon-edit'">人员导入</a>
			<div id="mm5" style="width: 150px;">
				<div data-options="iconCls:'icon-cash_register_2'"
					onclick="toUserImport()">人员导入</div>
				<div data-options="iconCls:'icon-blogs'" onclick="userExport()">人员导出</div>
				<div class="menu-sep"></div>
				<div data-options="iconCls:'icon-cv'" onclick="userTempletDown()">模板下载</div>
			</div>

			<form method="post" action="user/loadUsers.do" id="reportForm">
				<input type="hidden" name="ruleText" id="ruleTextId" />
			</form>
		</div>
	</div>
</body>
<script type="text/javascript">
	var url_delActionUser = "user/del.do";//删除URL
	var url_formActionUser = "user/form.do";//增加、修改、查看URL
	var url_searchActionUser = "user/query.do";//查询URL 
	var title_windowUser = "用户管理";//功能名称
	var gridUser;//数据表格对象
	var searchUser;//条件查询组件对象
	$(function() {
		//初始化数据表格
		gridUser = $('#dataUserGrid').datagrid({
			url : url_searchActionUser,
			fit : true,
			fitColumns : true,
			'toolbar' : '#userToolbar',
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			queryParams : {
				'a.STATE:=' : 'easyui',
			},
			border : false,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchUser = $('#searchUserForm').searchForm({
			gridObj : gridUser
		});
		$('#MessageconsoleTree').tree({
			url : 'organization/organizationTree.do',
			onSelect : function(node) {
				$('#PARENTID_RULE').val(node.id);
				$('#PARENTTITLE_RULE').val(node.text);
				searchUser.dosearch({
					'ruleText' : searchUser.arrayStr()
				});
			}
		});
		$('#MessageconsoleTreeReload').bind('click', function() {
			$('#MessageconsoleTree').tree('reload');
		});
		$('#MessageconsoleTreeOpenAll').bind('click', function() {
			$('#MessageconsoleTree').tree('expandAll');
		});
	});
	//查看
	function viewDataUser() {
		var selectedArray = $(gridUser).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionUser + '?operateType=' + PAGETYPE.VIEW
					+ '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winUser',
				width : 600,
				height : 400,
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
	function addDataUser() {
		var url = url_formActionUser + '?ids=123123132&operateType='
				+ PAGETYPE.ADD;
		$.farm.openWindow({
			id : 'winUser',
			width : 600,
			height : 400,
			modal : true,
			url : url,
			title : '新增'
		});
	}
	//初始化为指定密码
	function setingPassWd() {
		var selectedArray = $(gridUser).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = 'user/setingPassWordForm.do' + '?operateType='
					+ PAGETYPE.EDIT + '&userid=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winPasswordUser',
				width : 400,
				height : 300,
				modal : true,
				url : url,
				title : '初始化用户密码'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}
	//修改
	function editDataUser() {
		var selectedArray = $(gridUser).datagrid('getSelections');
		if (selectedArray.length == 1) {
			var url = url_formActionUser + '?operateType=' + PAGETYPE.EDIT
					+ '&ids=' + selectedArray[0].ID;
			$.farm.openWindow({
				id : 'winUser',
				width : 600,
				height : 400,
				modal : true,
				url : url,
				title : '修改'
			});
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE_ONLY,
					'info');
		}
	}

	//密码初始化
	function initPasswd() {
		var selectedArray = $(gridUser).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			$.messager
					.confirm(
							MESSAGE_PLAT.PROMPT,
							"该操作将无法返回，是否继续执行密码初始化?",
							function(flag) {
								if (flag) {
									$
											.post(
													"user/init.do"
															+ '?ids='
															+ $.farm
																	.getCheckedIds(
																			gridUser,
																			'ID'),
													{},
													function(flag) {
														var jsonObject = JSON
																.parse(flag,
																		null);
														if (jsonObject.STATE == 0) {
															$.messager
																	.alert(
																			MESSAGE_PLAT.PROMPT,
																			MESSAGE_PLAT.SUCCESS,
																			'info');
														} else {
															var str = MESSAGE_PLAT.ERROR_SUBMIT
																	+ jsonObject.MESSAGE;
															$.messager
																	.alert(
																			MESSAGE_PLAT.ERROR,
																			str,
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

	//批量设置为只读用户
	function setUserType(flag) {
		var selectedArray = $(gridUser).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var name = '';
			var urlname = '';
			if (flag == 'read') {
				name = '只读';
				urlname = 'readUser';
			}
			if (flag == 'question') {
				name = '问答';
				urlname = 'questionUser';
			}
			if (flag == 'know') {
				name = '知识';
				urlname = 'knowUser';
			}
			var str = selectedArray.length + "用户将被设置为" + name + "用户，是否继续?";
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridUser).datagrid('loading');
					$.post('user/' + urlname + '.do?ids='
							+ $.farm.getCheckedIds(gridUser, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridUser).datagrid('loaded');
								if (jsonObject.model.STATE == 0) {
									$(gridUser).datagrid('reload');
								} else {
									var str = MESSAGE_PLAT.ERROR_SUBMIT
											+ flag.pageset.message;
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
	//批量设置为系统用户
	function sysUser() {
		var selectedArray = $(gridUser).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + "用户将被设置为系统用户，是否继续?";
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridUser).datagrid('loading');
					$.post('user/sysUser.do?ids='
							+ $.farm.getCheckedIds(gridUser, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridUser).datagrid('loaded');
								if (jsonObject.model.STATE == 0) {
									$(gridUser).datagrid('reload');
								} else {
									var str = MESSAGE_PLAT.ERROR_SUBMIT
											+ flag.pageset.message;
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
	function delDataUser() {
		var selectedArray = $(gridUser).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridUser).datagrid('loading');
					$.post(url_delActionUser + '?ids='
							+ $.farm.getCheckedIds(gridUser, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridUser).datagrid('loaded');
								if (jsonObject.model.STATE == 0) {
									$(gridUser).datagrid('reload');
								} else {
									var str = MESSAGE_PLAT.ERROR_SUBMIT
											+ flag.pageset.message;
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

	//设置用户状态
	function setState(code) {
		var flagTitle;
		if (code == '1') {
			flagTitle = '可用';
		}
		if (code == '0') {
			flagTitle = '禁用';
		}
		if (code == '3') {
			flagTitle = '待审核';
		}
		if (!flagTitle) {
			return;
		}
		var selectedArray = $(gridUser).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var str = selectedArray.length + MESSAGE_PLAT.SUCCESS_DEL_NEXT_IS;
			$.messager.confirm(MESSAGE_PLAT.PROMPT, "确认修改用户状态为'" + flagTitle
					+ "'?", function(flag) {
				if (flag) {
					$(gridUser).datagrid('loading');
					$.post("user/stateUser.do" + '?statecode=' + code + '&ids='
							+ $.farm.getCheckedIds(gridUser, 'ID'), {},
							function(flag) {
								var jsonObject = JSON.parse(flag, null);
								$(gridUser).datagrid('loaded');
								if (jsonObject.model.STATE == 0) {
									$(gridUser).datagrid('reload');
								} else {
									var str = MESSAGE_PLAT.ERROR_SUBMIT
											+ flag.pageset.message;
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

	//达到人员导入页面
	function toUserImport() {
		$.farm.openWindow({
			id : 'toUserImport',
			width : 400,
			height : 250,
			modal : true,
			url : "user/toUserImport.do",
			title : '人员导入'
		});
	}

	//人员模板下载
	function userTempletDown() {
		window.location.href = basePath + "user/userTempletDown.do"
	}

	//人员导出
	function userExport() {
		$('#ruleTextId').val(searchUser.arrayStr());
		$('#reportForm').submit();
	}
	
	//批量设置组织机构
	function setUsersOrg() {
		var selectedArray = $(gridUser).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var userids = $.farm.getCheckedIds(gridUser, 'ID');
			$.farm.openWindow({
				id : 'chooseOrgWin',
				width : 300,
				height : 400,
				modal : true,
				url : 'organization/chooseOrg.do',
				title : '选择组织机构'
			});
			chooseOrgWindowCallBackHandle = function(node) {
				$.messager.confirm(MESSAGE_PLAT.PROMPT, "确认执行批量更新操作？",
						function(confirmFlag) {
							if (confirmFlag) {
								$.post('user/editUserOrg.do', {
									"ids" : userids,
									"orgid" : node.id
								}, function(flag) {
									var jsonObject = JSON.parse(flag, null);
									$('#chooseOrgWin').window('close');
									$(gridUser).datagrid('loaded');
									if (jsonObject.STATE == 0) {
										$(gridUser).datagrid('reload');
									} else {
										var str = MESSAGE_PLAT.ERROR_SUBMIT
												+ jsonObject.MESSAGE;
										$.messager.alert(MESSAGE_PLAT.ERROR,
												str, 'error');
									}
								});
							}
						});
			}
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
	//批量添加岗位
	function addUsersPost() {
		var selectedArray = $(gridUser).datagrid('getSelections');
		if (selectedArray.length > 0) {
			if(!$('#PARENTID_RULE').val()){
				alert("注意：必须点击左侧组织机构树后才能选择组织机构下相应岗位,否则只显示全局岗位！"); 
			}
			// 有数据执行操作
			var userids = $.farm.getCheckedIds(gridUser, 'ID');
			$.farm.openWindow({
				id : 'choosePostWin',
				width : 600,
				height : 400,
				modal : true,
				url : 'post/choosePostByOrg.do?orgid='
						+ $('#PARENTID_RULE').val(),
				title : '添加用户岗位'
			});
			chooseWindowCallBackHandle = function(selectedArray) {
				$.messager.confirm(MESSAGE_PLAT.PROMPT, "确认执行批量添加操作,原有岗位将保留，如果用户无权使用该岗位则不会被添加？",
						function(confirmFlag) {
							if (confirmFlag) {
								var postidsvar;
								$(selectedArray).each(function(i, obj) {
									if (postidsvar) {
										postidsvar = postidsvar + ',' + obj.ID;
									} else {
										postidsvar = obj.ID;
									}
								});
								$.post('user/addUserPost.do', {
									"ids" : userids,
									"postids" : postidsvar
								}, function(flag) {
									var jsonObject = JSON.parse(flag, null);
									$('#choosePostWin').window('close');
									$(gridUser).datagrid('loaded');
									if (jsonObject.STATE == 0) {
										$(gridUser).datagrid('reload');
									} else {
										var str = MESSAGE_PLAT.ERROR_SUBMIT
												+ jsonObject.MESSAGE;
										$.messager.alert(MESSAGE_PLAT.ERROR,
												str, 'error');
									}
								});
							}
						});
			}
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
	//批量删除岗位
	function delUsersPost() {
		var selectedArray = $(gridUser).datagrid('getSelections');
		if (selectedArray.length > 0) {
			// 有数据执行操作
			var userids = $.farm.getCheckedIds(gridUser, 'ID');
			$.farm.openWindow({
				id : 'choosePostWin',
				width : 600,
				height : 400,
				modal : true,
				url : 'post/choosePostByOrg.do?orgid='
						+ $('#PARENTID_RULE').val(),
				title : '删除用户岗位'
			});
			chooseWindowCallBackHandle = function(selectedArray) {
				$.messager.confirm(MESSAGE_PLAT.PROMPT, "确认执行批量删除操作？",
						function(confirmFlag) {
							if (confirmFlag) {
								var postidsvar;
								$(selectedArray).each(function(i, obj) {
									if (postidsvar) {
										postidsvar = postidsvar + ',' + obj.ID;
									} else {
										postidsvar = obj.ID;
									}
								});
								$.post('user/delUserPost.do', {
									"ids" : userids,
									"postids" : postidsvar
								}, function(flag) {
									var jsonObject = JSON.parse(flag, null);
									$('#choosePostWin').window('close');
									$(gridUser).datagrid('loaded');
									if (jsonObject.STATE == 0) {
										$(gridUser).datagrid('reload');
									} else {
										var str = MESSAGE_PLAT.ERROR_SUBMIT
												+ jsonObject.MESSAGE;
										$.messager.alert(MESSAGE_PLAT.ERROR,
												str, 'error');
									}
								});
							}
						});
			}
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
</script>

</html>