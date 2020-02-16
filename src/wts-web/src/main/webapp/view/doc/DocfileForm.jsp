<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--文档附件表单-->
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
		<form id="dom_formDocfile">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">文件ID:</td>
					<td colspan="3">${entity.id}</td>
				</tr>
				<tr>
					<td class="title">文件名:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[32]']"
						id="entity_filename" name="filename" value="${entity.filename}">
					</td>
				</tr>
				<tr>
					<td class="title">附件名称:</td>
					<td colspan="2"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[32]']"
						id="entity_name" name="name" value="${entity.name}"></td>
					<td><c:if test="${pageset.operateType==0}">
							<a target="_blank" href="${entity.url}">下载</a>
						</c:if></td>
				</tr>
				<tr>
					<td class="title">文件大小:</td>
					<td><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[6]']"
						id="entity_len" name="len" value="${entity.len}"></td>
					<td class="title">扩展名:</td>
					<td><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[8]']"
						id="entity_exname" name="exname" value="${entity.exname}">
					</td>
				</tr>
				<tr>
					<td class="title">类型:</td>
					<td><select id="entity_type" name="type" val="${entity.type}">
							<option value="1">图片</option>
							<option value="0">其他</option>
							<option value="2">资源</option>
							<option value="3">压缩</option>
					</select></td>
					<td class="title">状态:</td>
					<td><select id="entity_pstate" name="pstate"
						val="${entity.pstate}">
							<option value="1">使用</option>
							<option value="0">临时</option>
							<option value="3">永久</option>
					</select></td>
				</tr>
				<tr>
					<td class="title">相对路径:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[128]']"
						id="entity_dir" name="dir" value="${entity.dir}"></td>
				</tr>
				<tr>
					<td class="title">物理名称:</td>
					<td colspan="3">${entity.filename }</td>
				</tr>
				<tr>
					<td class="title">物理路径:</td>
					<td colspan="3">${realPath}</td>
				</tr>
				<c:if test="${DOC!=null}">
					<tr>
						<td class="title">所属知识:</td>
						<td colspan="3">${DOC.title }</td>
					</tr>
				</c:if>
				<c:if test="${entity.type=='1'}">
					<tr>
						<td colspan="4" align="center"><img src="${entity.minurl}"
							style="max-width: 30%;max-height: 600px;text-align: center;" /></td>
					</tr>
				</c:if>
				<c:if test="${text!=null}">
					<tr>
						<td colspan="4"><div class="demo-info">
								<div>${text}</div>
							</div></td>
					</tr>
				</c:if>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entityDocfile" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityDocfile" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formDocfile" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionDocfile = 'doc/FarmDocDocfileaddCommit.do';
	var submitEditActionDocfile = 'doc/FarmDocDocfileeditCommit.do';
	var currentPageTypeDocfile = '${pageset.operateType}';
	var submitFormDocfile;
	$(function() {
		//表单组件对象
		submitFormDocfile = $('#dom_formDocfile').SubmitForm({
			pageType : currentPageTypeDocfile,
			grid : gridDocfile,
			currentWindowId : 'winDocfile'
		});
		//关闭窗口
		$('#dom_cancle_formDocfile').bind('click', function() {
			$('#winDocfile').window('close');
		});
		//提交新增数据
		$('#dom_add_entityDocfile').bind('click', function() {
			submitFormDocfile.postSubmit(submitAddActionDocfile);
		});
		//提交修改数据
		$('#dom_edit_entityDocfile').bind('click', function() {
			submitFormDocfile.postSubmit(submitEditActionDocfile);
		});
	});
//-->
</script>