<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--构造权限表单-->
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
		<form id="dom_formActiontree">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<c:if test="${pageset.operateType==1}">
				<input type="hidden" id="entity_domain" name="domain"
					value="${domain}">
			</c:if>
			<table class="editTable">
				<c:if test="${pageset.operateType==0}">
					<tr>
						<td class="title">节点ID:</td>
						<td colspan="3">${entity.id}</td>
					</tr>
				</c:if>
				<tr>
					<td class="title">上级节点:</td>
					<td colspan="3">
						<c:if test="${parent!=null&&parent.id!=null}">
				       	 	${parent.name}
				        	<input type="hidden" name='parentid' value="${parent.id}" />
						</c:if> 
						<c:if test="${parent==null||parent.id==null}">
				       		 无
				      	</c:if>
				    </td>
				</tr>
				<tr>
					<td class="title">图标样式:</td>
					<td colspan="3">
						<input type="hidden" id="entity_img_id" name="icon" value="${entity.icon}"> 
						<a id="chooseImg" class="easyui-linkbutton" data-options="iconCls:'${entity.icon}'">选择</a>
						<span id="iconTextSpanId">${entity.icon}</span>
					</td>
				</tr>
				<tr>
					<td class="title">名称:</td>
					<td><input type="text" style="width: 120px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[32]']"
						id="entity_name" name="name" value="${entity.name}"></td>
					<td class="title">排序:</td>
					<td><input type="text" style="width: 120px;"
						class="easyui-validatebox"
						data-options="required:true,validType:['integer','maxLength[5]']"
						id="entity_sort" name="sort" value="${entity.sort}"></td>
				</tr>
				<tr>
					<td class="title">类型:</td>
					<td><select name="type" id="entity_type" val="${entity.type}"
						style="width: 120px;">
							<option value="1">分类</option>
							<option value="2">菜单</option>
							<option value="3">权限</option>
					</select></td>
					<td class="title">状态:</td>
					<td><select name="state" id="entity_state"
						val="${entity.state}" style="width: 120px;">
							<option value="1">可用</option>
							<option value="0">禁用</option>
					</select></td>
				</tr>
				<tr>
					<td class="title">备注:</td>
					<td colspan="3"><textarea rows="2" style="width: 360px;"
							class="easyui-validatebox"
							data-options="validType:[,'maxLength[64]']" id="entity_comments"
							name="comments">${entity.comments}</textarea></td>
				</tr>
				<tr id="URL_TR_DOM">
					<td class="title">资源:</td>
					<td colspan="3"><input type="text" class="easyui-validatebox"
						style="width: 380px;"
						data-options="required:false,validType:['key','maxLength[64]']"
						id="entity_action_title" name="authkey"
						value="${treeAction.authkey}"> <input type="hidden"
						value="${entity.actionid}" id="entity_actionid" name="actionid">
						<c:if test="${pageset.operateType!=0}">
							<!--非浏览-->
							<a id="form_AloneAction_a_ChoosePop" href="javascript:void(0)"
								class="easyui-linkbutton" style="color: #000000;">选择</a>
						</c:if></td>
				</tr>
				<tr id="PARAMS_TR_DOM">
					<td class="title">参数:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="validType:[,'maxLength[64]']" id="entity_params"
						name="params" value="${entity.params}"></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entityActiontree" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityActiontree" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formActiontree" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionActiontree = 'actiontree/add.do';
	var submitEditActionActiontree = 'actiontree/edit.do';
	var currentPageTypeActiontree = '${pageset.operateType}';
	var submitFormActiontree;
	$(function() {
		//表单组件对象
		submitFormActiontree = $('#dom_formActiontree').SubmitForm({
			pageType : currentPageTypeActiontree,
			grid : gridActiontree,
			currentWindowId : 'winActiontree'
		});
		//关闭窗口
		$('#dom_cancle_formActiontree').bind('click', function() {
			$('#winActiontree').window('close');
		});
		//提交新增数据
		$('#dom_add_entityActiontree').bind('click', function() {
			submitFormActiontree.postSubmit(submitAddActionActiontree,function(){
				$.messager.confirm('确认对话框', '数据更新,是否重新加载左侧权限树？', function(r){
					if (r){
						$('#ActiontreeTree').tree('reload');
					}
				});
				return true;
			});
		});
		//提交修改数据
		$('#dom_edit_entityActiontree').bind('click', function() {
			submitFormActiontree.postSubmit(submitEditActionActiontree,function(){
				$.messager.confirm('确认对话框', '数据更新,是否重新加载左侧权限树？', function(r){
					if (r){
						$('#ActiontreeTree').tree('reload');
					}
				});
				return true;
			});
		});
		$('#chooseImg').bind('click', function() {
			$.farm.openWindow({
				id : 'windowChooseImg',
				width : 600,
				height : 300,
				modal : true,
				url : "actiontree/icons.do",
				title : '选择图标'
			});
		});
		$('#form_AloneAction_a_ChoosePop').bind('click', function() {
			$.farm.openWindow({
				id : 'windowChooseAction',
				width : 800,
				height : 400,
				modal : true,
				url : "actiontree/actionsPage.do",
				title : '选择权限'
			});
		});
		initType($('#entity_type').val());
		$('#entity_type').bind("change", function() {
			initType($(this).val());
		});
		chooseWindowCallBackHandle = function(row) {
			$("#entity_action_title").val(row[0].AUTHKEY);
			$("#entity_actionid").val(row[0].ID);
			$("#windowChooseAction").window('close');
		};
	});
	// 依据类型设置URL的显示和隐藏
	function initType(type) {
		if (type == '1') {
			$('#URL_TR_DOM').hide();
			$('#PARAMS_TR_DOM').hide();
		}
		if (type == '2') {
			$('#URL_TR_DOM').show();
			$('#PARAMS_TR_DOM').show();
		}
		if (type == '3') {
			$('#URL_TR_DOM').show();
			$('#PARAMS_TR_DOM').hide();
		}
	}
//-->
</script>