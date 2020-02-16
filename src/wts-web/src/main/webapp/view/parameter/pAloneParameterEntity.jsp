<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--系统参数-->
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
		<form id="dom_formparameter">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">名称:</td>
					<td colspan="3"><input type="text" class="easyui-validatebox"
						style="width: 360px;"
						data-options="required:true,validType:',maxLength[64]'"
						id="entity_name" name="name" value="${entity.name}"></td>
				</tr>
				<tr>
					<td class="title">键:</td>
					<td colspan="3"><input type="text" class="easyui-validatebox"
						style="width: 360px;"
						data-options="required:true,validType:',maxLength[32]'"
						id="entity_pkey" name="pkey" value="${entity.pkey}"></td>
				</tr>
				<tr>
					<td class="title">域:</td>
					<td><input type="text" class="easyui-validatebox" style="width: 138px;"
						data-options="required:false,validType:',maxLength[32]'"
						id="entity_domain" name="domain" value="${entity.domain}">
					</td>
					<td class="title">类型:</td>
					<td><select name="vtype" id="entity_vtype"
						val="${entity.vtype}">
							<option value="1">字符串</option>
							<option value="2">数值</option>
							<option value="3">枚举</option>
							<option value="4">布尔</option>
					</select></td>
				</tr>
				<tr>
					<td class="title">默认值:</td>
					<td><input type="text" class="easyui-validatebox" style="width: 138px;"
						data-options="required:true,validType:',maxLength[1024]'"
						id="entity_pvalue" name="pvalue" value="${entity.pvalue}">
					</td>
					<td class="title">用户个性化:</td>
					<td><select name="userable" id="entity_userable"
						val="${entity.userable}">
							<option value="0">否</option>
							<option value="1">是</option>
					</select></td>
				</tr>
				<tr>
					<td class="title">枚举值: <br />
					</td>
					<td colspan="3"> (枚举类型时有效)如:1,2,3 <br /> <input type="text"
						class="easyui-validatebox" style="width: 360px;"
						data-options="required:false,validType:',maxLength[1024]'"
						id="entity_rules" name="rules" value="${entity.rules}">
					</td>
				</tr>
				<tr>
					<td class="title">备注:</td>
					<td colspan="3"><textarea rows="1" cols="32"
							class="easyui-validatebox"
							data-options="required:false,validType:',maxLength[128]'"
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
				<a id="dom_add_entityparameter" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityparameter" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formparameter" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionparameter = 'parameter/add.do';
	var submitEditActionparameter = 'parameter/edit.do';
	var currentPageTypeparameter = '${pageset.operateType}';
	var submitFormparameter;
	$(function() {
		//表单组件对象
		submitFormparameter = $('#dom_formparameter').SubmitForm({
			pageType : currentPageTypeparameter,
			grid : gridparameter,
			currentWindowId : 'winparameter'
		});
		//关闭窗口
		$('#dom_cancle_formparameter').bind('click', function() {
			$('#winparameter').window('close');
		});
		//提交新增数据
		$('#dom_add_entityparameter').bind('click', function() {
			submitFormparameter.postSubmit(submitAddActionparameter);
		});
		//提交修改数据
		$('#dom_edit_entityparameter').bind('click', function() {
			submitFormparameter.postSubmit(submitEditActionparameter);
		});
	});
//-->
</script>