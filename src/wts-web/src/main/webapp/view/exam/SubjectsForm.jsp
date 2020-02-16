<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--考卷表单-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<span class="label label-primary"> 批量创建题 </span>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formSubjects">
			<input type="hidden" id="entity_typeid" name="typeid"
				value="${typeid}">
			<table class="editTable">
				<tr>
					<td class="title">粘贴全部考题:</td>
					<td colspan="3"><textarea rows="10" style="width: 100%;"
							class="easyui-validatebox" data-options="required:true"
							id="entity_texts" name="texts"></textarea></td>
				</tr>
				<tr>
					<td class="title">格式説明:</td>
					<td colspan="3">
						<ul>
							<li>每道题目前要加标记<span style="color: red;">[SUB:填空题]</span>/<span
								style="color: red;">[SUB:单选题]</span>/<span style="color: red;">[SUB:多选题]</span>/<span
								style="color: red;">[SUB:判断题]</span>/<span style="color: red;">[SUB:问答题]</span>/
							</li>
							<li>选择题答案由<span style="color: red;">[ANS:答案:正确]</span>进行标记
							</li>
							<li>判断题答案由题干后的<span style="color: red;">[ANS:答案:正确]</span>或<span
								style="color: red;">[ANS:答案:错误]</span>进行标记
							</li>
							<li>填空题答案由在答案位置的多组<span style="color: red;">[ANS:答案:...]</span>进行标记，<span
								style="color: red;">...</span>代表具体答案
							</li>
							<li>问答题答案由题干后的多组<span style="color: red;">[ANS:关键字:...]</span>进行标记，<span
								style="color: red;">...</span>代表问答题答案的关键字
							</li>
							<li>试题解析通过标签<span style="color: red;">[ANALYSIS]</span>进行标记，单独起一行跟在试题最后部分
							</li>
							<li>选择题时每个选项要都换行</li>
						</ul>
					</td>
				</tr>
				<tr>
					<td class="title">格式示例:</td>
					<td colspan="3">
						<div
							style="padding: 10px; border: dashed 1px; color: #666; background-color: #fff; margin: 4px;">
							<span style="color: red;">[SUB:填空题]</span>1.完成下列诗句:白日<span
								style="color: red;">[ANS:答案:依]</span>山尽,黄河<span
								style="color: red;">[ANS:答案:入]</span>海流。 <br /> <span
								style="color: red;">[ANALYSIS]</span>试题解析...
						</div>
						<div
							style="padding: 10px; border: dashed 1px; color: #666; background-color: #fff; margin: 4px;">
							<span style="color: red;">[SUB:单选题]</span>1.一加一等于( )<br /> A.一<br />
							B.二<span style="color: red;">[ANS:答案:正确]</span><br /> C.三 <br />
							D.四<br /> <span
								style="color: red;">[ANALYSIS]</span>试题解析...
						</div>
						<div
							style="padding: 10px; border: dashed 1px; color: #666; background-color: #fff; margin: 4px;">
							<span style="color: red;">[SUB:多选题]</span>2.食品标签中的配料包括( )<br />
							A.原料<span style="color: red;">[ANS:答案:正确]</span> <br /> B原料、辅料 <span
								style="color: red;">[ANS:答案:正确]</span><br /> C食品添加剂<span
								style="color: red;">[ANS:答案:正确]</span> <br /> D原料、辅料、食品添加剂<br /> <span
								style="color: red;">[ANALYSIS]</span>试题解析...
						</div>
						<div
							style="padding: 10px; border: dashed 1px; color: #666; background-color: #fff; margin: 4px;">
							<span style="color: red;">[SUB:判断题]</span>.保质期是指预包装食品在标签指明的贮存条件下保持品质的期限。（
							）<span style="color: red;">[ANS:答案:正确]</span> <br /> <span
								style="color: red;">[ANALYSIS]</span>试题解析...
						</div>
						<div
							style="padding: 10px; border: dashed 1px; color: #666; background-color: #fff; margin: 4px;">
							<span style="color: red;">[SUB:问答题]</span>食品质量安全主体责任规定企业应建立哪些制度？<span
								style="color: red;">[ANS:关键字:安全制度]</span><br /> <span
								style="color: red;">[ANALYSIS]</span>试题解析...
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<a id="dom_add_entitySubjects" href="javascript:void(0)"
				iconCls="icon-save" class="easyui-linkbutton">提交</a>
			<!--  -->
			<a id="dom_cancle_formSubjects" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionSubjects = 'subject/addTextSubjects.do';
	var currentPageTypeSubjects = '${pageset.operateType}';
	var submitFormSubjects;
	$(function() {
		//表单组件对象
		submitFormSubjects = $('#dom_formSubjects').SubmitForm({
			pageType : currentPageTypeSubjects,
			grid : gridSubject,
			currentWindowId : 'winSubjects'
		});
		//关闭窗口
		$('#dom_cancle_formSubjects').bind('click', function() {
			$('#winSubjects').window('close');
		});
		//提交新增数据
		$('#dom_add_entitySubjects').bind('click', function() {
			submitFormSubjects.postSubmit(submitAddActionSubjects);
		});
	});
//-->
</script>