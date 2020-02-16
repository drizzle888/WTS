<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<style>
<!--
.has-feedback .form-control {
	padding-right: 8px;
}
-->
</style>
<!--考题表单-->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center'" class="wts-paper-forms">
		<c:if test="${!empty material}">
			<!-- 如果有引用材料的话 -->
			<div
				style="padding: 20px; padding-top: 4px; padding-bottom: 4px; line-height: 2em;"
				class="ke-content ke-content-borderbox">
				<div style="text-align: center; font-size: 18px; font-weight: 700;">
					<code>引用材料:</code>${material.title}</div>
				<div>${material.text}</div>
			</div>
		</c:if>
		<div id="subject_view_load_id"
			style="padding: 20px; padding-top: 4px; padding-bottom: 4px;">
			<div style="text-align: center; color: #999;">load...</div>
		</div>
		<c:if test="${!empty analysis}">
			<!-- 如果有解析的話 -->
			<c:forEach items="${analysis}" var="node">
				<div
					style="padding: 20px; padding-top: 4px; padding-bottom: 4px; line-height: 2em; border: 1px solid green; margin: 20px;"
					class="ke-content ke-content-borderbox">
					<div style="text-align: left; font-size: 18px; font-weight: 700;">
						<code>解析:${node.pcontent}</code>
					</div>
					<div>${node.text}</div>
				</div>
			</c:forEach>
		</c:if>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<a id="dom_add_entitySubjectView" href="javascript:void(0)"
				iconCls="icon-application_osx_terminal" class="easyui-linkbutton">计算得分</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		$('#subject_view_load_id').load('subject/view.do', {
			versionid : '${subjectu.version.id}'
		});
		//关闭窗口
		$('#dom_add_entitySubjectView').bind('click', function() {
			$('#dom_add_entitySubjectView').linkbutton('disable');
			var jsonStr = enCodePaperForm();
			$.post('subject/countPoint.do', {
				'jsons' : jsonStr
			}, function(flag) {
				$('#dom_add_entitySubjectView').linkbutton('enable');
				if (flag.STATE == '0') {
					$.messager.alert('信息', flag.point, 'info');
				} else {
					$.messager.alert('错误', flag.MESSAGE, 'error');
				}
			}, 'json');
		});
	});
	// 编码所有表单
	function enCodePaperForm() {
		var json;
		$(".wts-paper-forms input[type='text']").each(function(i, obj) {
			if (json) {
				json = json + ',' + enCodeFormInput(obj);
			} else {
				json = enCodeFormInput(obj);
			}
		});
		$(".wts-paper-forms input[type='radio']").each(function(i, obj) {
			if (json) {
				json = json + ',' + enCodeFormInput(obj);
			} else {
				json = enCodeFormInput(obj);
			}
		});
		$(".wts-paper-forms input[type='checkbox']").each(function(i, obj) {
			if (json) {
				json = json + ',' + enCodeFormInput(obj);
			} else {
				json = enCodeFormInput(obj);
			}
		});
		$(".wts-paper-forms textarea").each(function(i, obj) {
			if (json) {
				json = json + ',' + enCodeFormInput(obj);
			} else {
				json = enCodeFormInput(obj);
			}
		});
		return "[" + json + "]";
	}

	// 编码一个表单
	function enCodeFormInput(docObj) {
		var subjectAnswer = new Object();
		subjectAnswer.versionid = $(docObj).attr('name');
		var answerid = $(docObj).attr('id');
		if (answerid) {
			subjectAnswer.answerid = answerid.replace("-INPUT", "");
		} else {
			subjectAnswer.answerid = "NONE";
		}
		if ($(docObj).attr('type') == 'radio'
				|| $(docObj).attr('type') == 'checkbox') {
			var val = $("input[value='" + $(docObj).val() + "']")
					.is(':checked') == true;
			subjectAnswer.value = val;
		} else {
			subjectAnswer.value = $(docObj).val();
		}
		var jsonStr = JSON.stringify(subjectAnswer);
		return jsonStr;
	}
</script>