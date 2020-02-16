<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--答卷章节表单-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<span class="label label-primary"> <c:if
				test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
		</span>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formPaperchapter">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">上级章节:</td>
					<td colspan="3">${parentName}<input type="hidden"
						name="parentid" value="${entity.parentid}"> <input
						type="hidden" name="paperid" value="${entity.paperid}">
					</td>
				</tr>
				<tr>
					<td class="title">章节名称:</td>
					<td colspan="3"><input type="text" style="width: 420px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[32]']"
						id="entity_name" name="name" value="${entity.name}"></td>
				</tr>
				<tr>
					<td class="title">章节说明:</td>
					<td colspan="3"><textarea id="entity_textnote"
							style="display: none;" name="textnote">${entity.textnote}
						</textarea>
						<div style="width: 425px;">
							<jsp:include page="comment/IncludeEditor.jsp">
								<jsp:param value="entity_textnote" name="fieldId" />
								<jsp:param value="${pageset.operateType}" name="type" />
								<jsp:param value="章节说明" name="fieldTitle" />
							</jsp:include></div></td>
				</tr>
				<tr>
					<td class="title">章节类型:</td>
					<td><select name="ptype" id="entity_ptype"
						val="${entity.ptype}"><option value="2">内容</option>
							<option value="1">结构</option></select><td class="title">排序:</td>
					<td><input type="text" style="width: 120px;"
						class="easyui-validatebox"
						data-options="required:true,validType:['integer','maxLength[5]']"
						id="entity_sort" name="sort" value="${entity.sort}"></td></td>


				</tr>
				<tr style="display: none;">
					<td class="title">初始得分:</td>
					<td><input type="text" style="width: 120px;"
						class="easyui-validatebox"
						data-options="required:true,validType:['intVal','maxLength[5]']"
						id="entity_initpoint" name="initpoint" value="${entity.initpoint}">
					</td>
					<td class="title">出题类型:</td>
					<td><select name="stype" id="entity_stype"
						val="${entity.stype}"><option value="1">手动出题</option>
							<!-- <option value="2">随机出题</option> -->
					</select></td>
				</tr>
				<!-- <tr>
					<td class="title">章节描述:</td>
					<td colspan="3"><input type="text" style="width: 420px;"
						class="easyui-validatebox"
						data-options="validType:[,'maxLength[32,767.5]']"
						id="entity_textnote" name="textnote" value="${entity.textnote}">
					</td>
				</tr><tr>
					<td class="title">随机题目得分:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="validType:[,'maxLength[5]']"
						id="entity_subjectpoint" name="subjectpoint"
						value="${entity.subjectpoint}"></td>
				</tr>
				<tr>
					<td class="title">随机题目数量:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="validType:[,'maxLength[5]']" id="entity_subjectnum"
						name="subjectnum" value="${entity.subjectnum}"></td>
				</tr>
				<tr>
					<td class="title">随机题库分类:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="validType:[,'maxLength[16]']"
						id="entity_subjecttypeid" name="subjecttypeid"
						value="${entity.subjecttypeid}"></td>
				</tr> -->
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entityPaperchapter" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityPaperchapter" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formPaperchapter" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionPaperchapter = 'paperchapter/add.do';
	var submitEditActionPaperchapter = 'paperchapter/edit.do';
	var currentPageTypePaperchapter = '${pageset.operateType}';
	var submitFormPaperchapter;
	$(function() {
		//表单组件对象
		submitFormPaperchapter = $('#dom_formPaperchapter').SubmitForm({
			pageType : currentPageTypePaperchapter,
			grid : null,
			currentWindowId : 'winPaperchapter'
		});
		//关闭窗口
		$('#dom_cancle_formPaperchapter').bind('click', function() {
			$('#winPaperchapter').window('close');
		});
		//提交新增数据
		$('#dom_add_entityPaperchapter').bind(
				'click',
				function() {
					submitFormPaperchapter.postSubmit(
							submitAddActionPaperchapter, function() {
								$('#chapterTypeTree').tree('reload');
								return true;
							});
				});
		//提交修改数据
		$('#dom_edit_entityPaperchapter').bind(
				'click',
				function() {
					submitFormPaperchapter.postSubmit(
							submitEditActionPaperchapter, function() {
								$('#chapterTypeTree').tree('reload');
								return true;
							});
				});
	});
//-->
</script>