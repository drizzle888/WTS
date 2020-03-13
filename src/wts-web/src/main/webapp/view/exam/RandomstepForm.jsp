<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--答卷随机步骤表单-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<span class="label label-primary"> <c:if
				test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
		</span>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formRandomstep">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<input type="hidden" id="itemid_id" name="itemid"
				value="${itemid}${entity.itemid}">
			<table class="editTable">
				<tr>
					<td class="title">名称:</td>
					<td colspan="3"><input type="text" style="width: 420px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[32]']"
						id="entity_name" name="name" value="${entity.name}"></td>
				</tr>
				<tr>
					<td class="title">题库分类:</td>
					<td colspan="3"><input type="text"
						style="width: 420px; height: 26px;" id="entity_typeid"
						name="typeid" value="${entity.typeid}"></td>
				</tr>
				<tr>
					<td class="title">题型:</td>
					<td><select name="tiptype" id="entity_tiptype"
						style="width: 80px;" val="${entity.tiptype}">
							<!-- 1.填空，2.单选，3.多选，4判断，5问答,6附件 -->
							<option value="2">单选</option>
							<option value="3">多选</option>
							<option value="1">填空</option>
							<option value="4">判断</option>
							<option value="5">问答</option>
							<option value="6">附件</option>
					</select></td>
					<td class="title">排序:</td>
					<td><input type="text" style="width: 120px;"
						class="easyui-validatebox"
						data-options="required:true,validType:['integer','maxLength[5]']"
						id="entity_sort" name="sort" value="${entity.sort}"></td>
				</tr>
				<tr>
					<td class="title">每题得分:</td>
					<td><input type="text" style="width: 120px;"
						class="easyui-validatebox"
						data-options="required:true,validType:['integer','maxLength[5]']"
						id="entity_subpoint" name="subpoint" value="${entity.subpoint}">
					</td>
					<td class="title">题目数量:</td>
					<td><input type="text" style="width: 120px;"
						class="easyui-validatebox"
						data-options="required:true,validType:['integer','maxLength[5]']"
						id="entity_subnum" name="subnum" value="${entity.subnum}">
					</td>
				</tr>
				<tr>
					<td class="title">备注:</td>
					<td colspan="3"><textarea style="width: 420px;"
							class="easyui-validatebox"
							data-options="validType:[,'maxLength[64]']" id="entity_pcontent"
							name="pcontent"> ${entity.pcontent}</textarea></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entityRandomstep" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityRandomstep" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formRandomstep" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionRandomstep = 'randomstep/add.do';
	var submitEditActionRandomstep = 'randomstep/edit.do';
	var currentPageTypeRandomstep = '${pageset.operateType}';
	var submitFormRandomstep;
	$(function() {
		//表单组件对象
		submitFormRandomstep = $('#dom_formRandomstep').SubmitForm({
			pageType : currentPageTypeRandomstep,
			grid : gridRandomstep,
			currentWindowId : 'winRandomstep'
		});
		//加载题库分类
		$('#entity_typeid').combotree({
			url : 'subjecttype/subjecttypeTree.do?funtype=1',
			required : true,
			textFiled : 'name',
			parentField : 'parentid',
			onSelect : function(node) {
				//选中
				$('#entity_typeid').val(node.id);
			},
			onLoadSuccess : function(node, data) {
				$('#entity_typeid').combotree('setText',"${typename}");
			}
		});
		//关闭窗口
		$('#dom_cancle_formRandomstep').bind('click', function() {
			$('#winRandomstep').window('close');
		});
		//提交新增数据
		$('#dom_add_entityRandomstep').bind('click', function() {
			submitFormRandomstep.postSubmit(submitAddActionRandomstep);
		});
		//提交修改数据
		$('#dom_edit_entityRandomstep').bind('click', function() {
			submitFormRandomstep.postSubmit(submitEditActionRandomstep);
		});
	});
//-->
</script>