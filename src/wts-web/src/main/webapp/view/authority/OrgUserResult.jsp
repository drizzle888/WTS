<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<table id="dataPostUserGrid">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th field="USERNAME" data-options="sortable:true" width="30">名称
			</th>
			<th field="POSTS" data-options="sortable:false" width="30">岗位</th>
			<th field="USERSTATE" data-options="sortable:true" width="20">
				状态</th>
			<th field="LOGINTIME" data-options="sortable:true" width="30">
				登录时间</th>
		</tr>
	</thead>
</table>
<script type="text/javascript">
	var url_delActionPostUser = "organization/removeOrgUser.do";//删除URL
	var url_searchActionPostUser = "user/orgUserQuery.do?ids="
			+ $('#domTabsId').val();//查询URL
	var title_windowPostUser = "用户管理";//功能名称
	var gridPostUser;//数据表格对象
	var searchPostUser;//条件查询组件对象
	var pageType = '${pageset.operateType}';
	var toolBarPostUser = [ {
		id : 'adduser',
		text : '导入用户到机构',
		iconCls : 'icon-customers',
		handler : addUserByOrg
	}, {
		id : 'del',
		text : '从组织机构下移除用户',
		iconCls : 'icon-remove',
		handler : delDataPostUser
	}, {
		id : 'setpost',
		text : '设置用户岗位',
		iconCls : 'icon-client_account_template',
		handler : setUserPost
	} ];
	$(function() {
		/*  if (pageType == '0') {
				toolBarPostUser = [];
			}  */
		//初始化数据表格
		gridPostUser = $('#dataPostUserGrid').datagrid({
			url : url_searchActionPostUser,
			fit : true,
			fitColumns : true,
			'toolbar' : toolBarPostUser,
			pagination : true,
			closable : true,
			checkOnSelect : true,
			striped : true,
			border : true,
			rownumbers : true,
			ctrlSelect : true
		});
		//初始化条件查询
		searchPostUser = $('#searchPostUserForm').searchForm({
			gridObj : gridPostUser
		});
	});
	//导入用户
	function addUserByOrg() {
		var url = "user/chooseUser.do";
		$.farm.openWindow({
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
			$(gridPostUser).datagrid('loading');
			$.post("organization/addOrgUser.do", {
				'userids' : userids,
				id : '${ids}'
			},function(flag) {
				if (flag.STATE == 0) {
					$('#chooseUserWin').window('close');
					$(gridPostUser).datagrid('reload');
				} else {
					$.messager.alert(MESSAGE_PLAT.ERROR, flag.MESSAGE,
							'error');
				}
			}, 'json');
		};
	}
	//删除
	function delDataPostUser() {
		var selectedArray = $(gridPostUser).datagrid('getSelections');
		if (selectedArray.length >= 1) {
			// 有数据执行操作
			var str = "该操作将从组织机构中移除所选用户是否继续？";
			$.messager.confirm(MESSAGE_PLAT.PROMPT, str, function(flag) {
				if (flag) {
					$(gridPostUser).datagrid('loading');
					$.post(url_delActionPostUser + '?id=${ids}&ids='
							+ $.farm.getCheckedIds(gridPostUser, 'USERID'), {},
							function(flag) {
								$(gridPostUser).datagrid('loaded');
								var jsonObject = JSON.parse(flag, null);
								if (jsonObject.STATE == 0) {
									$(gridPostUser).datagrid('reload');
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
	//设置用户岗位
	function setUserPost() {
		var selectedArray = $(gridPostUser).datagrid('getSelections');
		if (selectedArray.length >= 1) {
			var url = "post/choosePostByOrg.do?orgid=${ids}";
			$.farm.openWindow({
				id : 'choosePostWin',
				width : 600,
				height : 400,
				modal : true,
				url : url,
				title : '设置用户岗位'
			});
			var userids= $.farm.getCheckedIds(gridPostUser, 'USERID');
			chooseWindowCallBackHandle = function(row) {
				var postids;
				$(row).each(function(i, obj) {
					if (postids) {
						postids = postids + ',' + obj.ID;
					} else {
						postids = obj.ID;
					}
				});
				$(gridPostUser).datagrid('loading');
				$.post("organization/setOrgUserPost.do", {
					'userids' : userids,
					'postids' :postids
				}, function(flag) {
					if (flag.STATE == 0) {
						$('#choosePostWin').window('close');
						$(gridPostUser).datagrid('reload');
					} else {
						$.messager.alert(MESSAGE_PLAT.ERROR, flag.MESSAGE,
								'error');
					}
				}, 'json');
			};
		} else {
			$.messager.alert(MESSAGE_PLAT.PROMPT, MESSAGE_PLAT.CHOOSE_ONE,
					'info');
		}
	}
</script>