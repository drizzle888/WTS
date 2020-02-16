<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--数据实体-->
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
		<form id="dom_formDictinary">
			<input type="hidden" id="entity_id" name="id"
				value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">
						名称:
					</td>
					<td colspan="3">
						<textarea rows="1" cols="32" class="easyui-validatebox"
							data-options="required:true,validType:',maxLength[64]'"
							id="entity_name" name="name">${entity.name}</textarea>
					</td>
				</tr>
				<tr>
					<td class="title">
						键:
					</td>
					<td colspan="3">
						<c:if test="${pageset.operateType!=2}">
							<textarea rows="1" cols="32" class="easyui-validatebox"
								data-options="required:true,validType:'ajaxValidate[\'\']'"
								id="entity_entityindex" name="entityindex">${entity.entityindex}</textarea>
						</c:if>
						<c:if test="${pageset.operateType==2}">
							<textarea rows="1" cols="32" class="easyui-validatebox"
								data-options="required:true,validType:'ajaxValidate[\'${ids}\']'"
								id="entity_entityindex" name="entityindex">${entity.entityindex}</textarea>
						</c:if>
					</td>
				</tr>
				<tr>
					<td class="title">
						类型:
					</td>
					<td colspan="3">
						<select id="entity_type" name="type" val="${entity.type }">
							<option value="1">
								序列
							</option>
							<option value="0">
								树
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="title">
						备注:
					</td>
					<td colspan="3">
						<textarea rows="2" cols="32" class="easyui-validatebox"
							data-options="required:false,validType:',maxLength[120]'"
							id="entity_comments" name="comments">${entity.comments}</textarea>
					</td>
				</tr>
				<c:if test="${pageset.operateType==1}">
					<!--新增-->
				</c:if>
				<c:if test="${pageset.operateType==2}">
					<!--修改-->
				</c:if>
				<c:if test="${pageset.operateType==0}">
					<!--浏览-->
				</c:if>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entityDictinary" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityDictinary" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formDictinary" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionDictinary = 'dictionary/add.do';
	var submitEditActionDictinary = 'dictionary/edit.do';
	var currentPageTypeDictinary = '${pageset.operateType}';
	var submitFormDictinary;
	$(function() {
		//表单组件对象
		submitFormDictinary = $('#dom_formDictinary').SubmitForm( {
			pageType : currentPageTypeDictinary,
			grid : gridDictinary,
			currentWindowId : 'winDictinary'
		});
		//关闭窗口
		$('#dom_cancle_formDictinary').bind('click', function() {
			$('#winDictinary').window('close');
		});
		//提交新增数据
		$('#dom_add_entityDictinary').bind('click', function() {
			submitFormDictinary.postSubmit(submitAddActionDictinary);
		});
		//提交修改数据
		$('#dom_edit_entityDictinary').bind('click', function() {
			submitFormDictinary.postSubmit(submitEditActionDictinary);
		});
		 $.extend($.fn.validatebox.defaults.rules, {
			ajaxValidate : {
				validator : function(value, para) {
					var resultVar = $.ajax( {
						url : 'dictionary/ALONEDictionary_validateIsRepeatKey.do',
						dataType : "json",
						data : {
							key : value,
							ids : para[0]
						},
						async : false,
						cache : false,
						type : "post"
					}).responseText;
					return $.parseJSON(resultVar).isRepeatKey == false;
				},
				message : "该KEY已经存在"
			}
		});  
	});
	//-->
</script>