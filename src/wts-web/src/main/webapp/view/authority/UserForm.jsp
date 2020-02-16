<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--用户表单-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<div class="tableTitle_msg">${MESSAGE}</div>
		<div class="tableTitle_tag">
			<c:if test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if>
			<c:if test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if>
			<c:if test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
		</div>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formUser">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<table class="editTable">
				<c:if test="${pageset.operateType==0}">
					<tr>
						<td class="title">ID:</td>
						<td colspan="3">${entity.id}</td>
					</tr>
				</c:if>
				<tr>
					<td class="title">名称:</td>
					<td><input type="text" style="width: 120px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[32]']"
						id="entity_name" name="name" value="${entity.name}"></td>
					<td class="title">登录名:</td>
					<td><input type="text" style="width: 120px;"
						class="easyui-validatebox"
						data-options="required:true,validType:['minLength[4]','maxLength[32]']"
						id="entity_loginname" name="loginname" value="${entity.loginname}">
					</td>
				</tr>
				<tr>
					<td class="title">类型:</td>
					<td>
						<select name="type" id="entity_type" val="${entity.type}"
							style="width: 120px;">
							<option value="1">系统用户</option>
							<!-- <option value="2">其他</option> -->
							<option value="3">超级用户</option>
						</select>
					</td>
					<td class="title">状态:</td>
					<td><select name="state" id="entity_state"
						val="${entity.state}" style="width: 120px;">
							<option value="1">可用</option>
							<option value="0">禁用</option>
							<option value="3">待审核</option>
					</select></td>
				</tr>
				<tr>
					<td class="title">备注:</td>
					<td colspan="3"><textarea rows="2" style="width: 360px;"
							class="easyui-validatebox"
							data-options="validType:[,'maxLength[64]']" id="entity_comments"
							name="comments">${entity.comments}</textarea></td>
				</tr>
				<tr>
					<td class="title">组织机构</td>
					<td colspan="3"><input id="entity_orgId" name="orgId"
						value="${orgId }"> <c:if test="${pageset.operateType==0}">${organization.name}</c:if>
					</td>
				</tr>
				<tr>
					<td class="title">所属岗位</td>
					<td colspan="3"><c:if test="${pageset.operateType==0}">
						<c:forEach items="${posts}" var="node">
						${node.name}&nbsp;|&nbsp;
						</c:forEach>
						</c:if> <input type="hidden" id="entity_postIds" name="postIds" /> <input
						id="entity_postId" name="postId" value="${postIds}"
						style="width: 420px;" /></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entityUser" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityUser" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formUser" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionUser = 'user/add.do';
	var submitEditActionUser = 'user/edit.do';
	var currentPageTypeUser = '${pageset.operateType}';
	var submitFormUser;
	$(function() {
		//表单组件对象
		submitFormUser = $('#dom_formUser').SubmitForm({
			pageType : currentPageTypeUser,
			grid : gridUser,
			currentWindowId : 'winUser'
		});
		//关闭窗口
		$('#dom_cancle_formUser').bind('click', function() {
			$('#winUser').window('close');
		});
		//提交新增数据
		$('#dom_add_entityUser').bind(
				'click',
				function() {
					$('#entity_postIds').val(
							$('#entity_postId').combotree('getValues'));
					submitFormUser.postSubmit(submitAddActionUser);
				});
		//提交修改数据
		$('#dom_edit_entityUser').bind(
				'click',
				function() {
					$('#entity_postIds').val(
							$('#entity_postId').combotree('getValues'));
					submitFormUser.postSubmit(submitEditActionUser);
				});
		$('#entity_postId')
				.combotree(
						{
							url : 'organization/loadPostWithPOrgPost.do?orgId=${orgId}',
							required : false,
							idFiled : 'POSTID',
							textFiled : 'POSTNAME',
							parentField : 'NONE',
							multiple : true,
							loadFilter : function(data, parent) {
								var opt = $(this).data().tree.options;
								var idFiled, textFiled, parentField;
								if (opt.parentField) {
									idFiled = opt.idFiled || 'id';
									textFiled = opt.textFiled || 'text';
									parentField = opt.parentField;

									var i, l, treeData = [], tmpMap = [];

									for (i = 0, l = data.length; i < l; i++) {
										tmpMap[data[i][idFiled]] = data[i];
									}

									for (i = 0, l = data.length; i < l; i++) {
										if (tmpMap[data[i][parentField]]
												&& data[i][idFiled] != data[i][parentField]) {
											if (!tmpMap[data[i][parentField]]['children'])
												tmpMap[data[i][parentField]]['children'] = [];
											data[i]['id'] = data[i][idFiled];
											data[i]['text'] = data[i][textFiled];
											tmpMap[data[i][parentField]]['children']
													.push(data[i]);
										} else {
											data[i]['id'] = data[i][idFiled];
											data[i]['text'] = data[i][textFiled];
											treeData.push(data[i]);
										}
									}
									return treeData;
								}
								return data;
							},
							onSelect : function(node) {

							}
						});

		//加载机构树（放在岗位列表后面）
		$('#entity_orgId')
				.combotree(
						{
							url : 'organization/loadTree.do',
							required : true,
							textFiled : 'name',
							parentField : 'parentid',
							loadFilter : function(data, parent) {
								var opt = $(this).data().tree.options;
								var idFiled, textFiled, parentField;
								if (opt.parentField) {
									idFiled = opt.idFiled || 'id';
									textFiled = opt.textFiled || 'text';
									parentField = opt.parentField;

									var i, l, treeData = [], tmpMap = [];

									for (i = 0, l = data.length; i < l; i++) {
										tmpMap[data[i][idFiled]] = data[i];
									}

									for (i = 0, l = data.length; i < l; i++) {
										if (tmpMap[data[i][parentField]]
												&& data[i][idFiled] != data[i][parentField]) {
											if (!tmpMap[data[i][parentField]]['children'])
												tmpMap[data[i][parentField]]['children'] = [];
											data[i]['text'] = data[i][textFiled];
											tmpMap[data[i][parentField]]['children']
													.push(data[i]);
										} else {
											data[i]['text'] = data[i][textFiled];
											treeData.push(data[i]);
										}
									}
									return treeData;
								}
								return data;
							},
							onSelect : function(node) {
								loadPost(node.id);
							},
							onLoadSuccess : function(node, data) {

							}
						});
	});

	//加载岗位
	function loadPost(orgId) {
		$('#entity_postId').combotree('clear');
		$('#entity_postId').combotree('reload',
				'organization/loadPostWithPOrgPost.do?orgId=' + orgId);
	}
//-->
</script>