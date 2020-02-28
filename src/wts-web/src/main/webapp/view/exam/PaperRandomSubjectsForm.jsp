<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<style>
<!--
.paperUnit {
	border: 1px dashed #fffff;
	background-color: #6f6f6f;
	margin: 4px;
	padding: 4px;
}

.paperWarnTip {
	border: 1px dashed #fffff;
	background-color: red;
	margin: 4px;
	padding: 4px;
}

.consoleTip {
	color: #000000;
}
-->
</style>
<!--考卷表单-->
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false"
		style="background-color: #FFFEE6;">
		<div style="padding: 8px; color: green;">
			1.设置下面抽题参数；2.点击最下方添加按钮将题目插入答卷；3.系统将自动按照题型创建大题类型;</div>
	</div>
	<div data-options="region:'center'" style="padding-top: 20px;">
		<form id="dom_formPaper_random">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">来源题库分类:</td>
					<td colspan="5"><input id="subjecttypeId" name="subjecttype"
						style="width: 465px;"></td>

				</tr>
				<tr>
					<td class="title" style="border-bottom: 0px;">题型:</td>
					<td style="border-bottom: 0px;"><select name="modeltype"
						id="modeltypeId" style="width: 80px;" val="${entity.modeltype}">
							<!-- 1.填空，2.单选，3.多选，4判断，5问答,6附件 -->
							<option value="1">填空</option>
							<option value="2">单选</option>
							<option value="3">多选</option>
							<option value="4">判断</option>
							<option value="5">问答</option>
							<option value="6">附件</option>
					</select></td>
					<td class="title" style="border-bottom: 0px;">抽题数量:</td>
					<td style="border-bottom: 0px;"><select id="subnumId"
						class="easyui-combobox" name="dept"
						data-options="required:true,validType:['integer','maxLength[5]']"
						style="width: 80px;">
							<option>1</option>
							<option>2</option>
							<option>3</option>
							<option>4</option>
							<option>5</option>
							<option>10</option>
							<option>15</option>
							<option>20</option>
					</select></td>
					<td class="title" style="border-bottom: 0px;">每题得分:</td>
					<td style="border-bottom: 0px;"><select id="pointId"
						class="easyui-combobox" name="dept"
						data-options="required:true,validType:['integer','maxLength[5]']"
						style="width: 80px;">
							<option>1</option>
							<option>2</option>
							<option>3</option>
							<option>4</option>
							<option>5</option>
							<option>10</option>
							<option>15</option>
							<option>20</option>
					</select></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false"
		style="background-color: #FFFEE6;">
		<div class="div_button"
			style="text-align: center; padding: 4px; padding-top: 10px;">
			<a id="dom_add_entityPaper" href="javascript:void(0)"
				iconCls="icon-add" class="easyui-linkbutton">添加题目到当前${fn:length(papers)}套答卷中</a>
			<!--  -->
			<a id="dom_clear_entityPaper" href="javascript:void(0)"
				iconCls="icon-remove" class="easyui-linkbutton">清空当前${fn:length(papers)}套答卷中所有题目</a>
			<!--  -->
			<a id="dom_cancle_formPaper" href="javascript:void(0)"
				iconCls="icon-sign-out" class="easyui-linkbutton"
				style="color: #000000;">关闭窗口</a>
		</div>
		<div
			style="height: 115px; color: #ffffff; padding: 10px; overflow: auto; margin-top: 10px;">
			<div id="subjectsInfoBoxId"></div>
		</div>
	</div>
</div>
<script type="text/javascript">
	var currentPageTypePaper;
	$(function() {
		//加載答卷题量和总分
		loadPapersInfo();
		//关闭窗口
		$('#dom_cancle_formPaper').bind('click', function() {
			$('#winPaper').window('close');
		});
		//提交新增数据
		$('#dom_add_entityPaper').bind('click', function() {
			submitRandomForm();
		});
		//清空答卷
		$('#dom_clear_entityPaper').bind('click', function() {
			submitClearPapers();
		});
		//新增表单
		if (currentPageTypePaper == '1') {
			$('#lable_examtypeid').text($('#PARENTTITLE_RULE').val());
			$('#entity_examtypeid').val($('#PARENTID_RULE').val());
		}
		//加载题库分类
		$('#subjecttypeId').combotree({
			url : 'subjecttype/subjecttypeTree.do?funtype=1',
			required : true,
			textFiled : 'name',
			parentField : 'parentid',
			onSelect : function(node) {
				//选中
				$('#subjecttypeId').val(node.id);
			},
			onLoadSuccess : function(node, data) {

			}
		});
	});
	//加載答卷题量和总分
	function loadPapersInfo(tip) {
		$('#subjectsInfoBoxId').html(
				'<span class="consoleTip">loading...</span>');
		$.post("paper/loadPapersInfo.do", {
			ids : '${paperids}'
		}, function(flag) {
			$('#subjectsInfoBoxId').html('');
			$(flag.papers).each(
					function(i, obj) {
						var paperUnit = "<b>" + obj.info.name + "</b>:总分"
								+ obj.allPoint + ",共包含" + obj.rootChapterNum
								+ "大题，" + obj.subjectNum + "小题";
						$('#subjectsInfoBoxId').append(
								'<div class="paperUnit">' + paperUnit
										+ '</div>');
						if (tip) {
							$('#subjectsInfoBoxId').append(
									'<div class="paperWarnTip">' + tip
											+ '</div>');
						}
					});
		}, 'json');
	}

	//提交清理答卷
	function submitClearPapers() {
		if (confirm("该操作将刪除答卷中的章节和题目，删除后不可恢复，是否继续?")) {
			$('#subjectsInfoBoxId').html(
					'<span class="consoleTip">loading...</span>');
			$.post('paper/doClearSubjects.do', {
				"ids" : '${paperids}'
			}, function(flag) {
				$(gridPaper).datagrid('reload');
				loadPapersInfo();
			}, 'json');
		}
	}

	//提交随机题创建表单
	function submitRandomForm() {
		var typeid = $('#subjecttypeId').val();
		var tiptype = $('#modeltypeId').val();
		var subnum = $('#subnumId').combo('getText');
		var point = $('#pointId').combo('getText');
		var ids = '${paperids}';
		if ($('#dom_formPaper_random').form('validate')) {
			if (confirm("该操作将向答卷中插入题，是否提交请求?")) {
				$('#subjectsInfoBoxId').html(
						'<span class="consoleTip">loading...</span>');
				$.post('paper/doAddRandomSubjects.do', {
					"typeid" : typeid,
					"tiptype" : tiptype,
					"subnum" : subnum,
					"point" : point,
					"ids" : ids
				}, function(flag) {
					$(gridPaper).datagrid('reload');
					loadPapersInfo(flag.warn);
				}, 'json');
			}
		}
	}
//-->
</script>