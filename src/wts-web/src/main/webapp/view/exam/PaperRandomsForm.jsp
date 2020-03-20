<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--批量创建随机答卷-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<span class="label label-primary"> 批量创建随机答卷 </span>
	</div>
	<div data-options="region:'center'" style="overflow-x: hidden;">
		<form id="dom_formRandom">
			<input type="hidden" id="entity_id" name="examtypeid"
				value="${type.id}">
			<table class="editTable">
				<tr>
					<td class="title">答卷名称:</td>
					<td colspan="3"><input type="text" style="width: 420px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[64]']"
						id="entity_name" name="name"></td>
				</tr>
				<tr>
					<td class="title">答卷分类:</td>
					<td colspan="3">${type.name}</td>
				</tr>
				<tr>
					<td class="title">生成数量:</td>
					<td><select name="num" id="entity_num">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
					</select></td>
					<td class="title">建议答题时间:</td>
					<td><input type="text" style="width: 45px;"
						class="easyui-validatebox"
						data-options="required:true,validType:['integer','maxLength[5]']"
						id="entity_advicetime" name="advicetime">（分）</td>
				</tr>
				<tr>
					<td class="title">随机规则:</td>
					<td colspan="3"><select name="itemid" id="entity_counttype"
						style="width: 420px;">
							<c:forEach items="${items}" var="node">
								<option value="${node.ID}">${node.NAME}[
									含${node.NUM}条规则 ]</option>
							</c:forEach>
					</select><br /> <span
						style="font-size: 12px; color: green; text-align: center;">#随机规则请在"规则管理/随机卷规则配置"菜单下进行维护</span></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<a id="dom_add_entityRandom" href="javascript:void(0)"
				iconCls="icon-save" class="easyui-linkbutton">创建</a> <a
				id="dom_cancle_formRandom" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		//表单组件对象
		var submitFormRandom = $('#dom_formRandom').SubmitForm({
			pageType : 1,
			grid : gridPaper,
			currentWindowId : 'winRandom'
		});
		//关闭窗口
		$('#dom_cancle_formRandom').bind('click', function() {
			$('#winRandom').window('close');
		});
		//提交新增数据
		$('#dom_add_entityRandom').bind(
				'click',
				function() {
					$.messager.alert('请等待',
							'当前操作将消耗一定时间，请等待直至系统提示执行完毕后手动关闭此对话框...', 'info');
					submitFormRandom.postSubmit("paper/runRandomPapers.do");
				});
	});
//-->
</script>