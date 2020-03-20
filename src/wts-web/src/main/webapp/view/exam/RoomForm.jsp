<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<link rel="stylesheet" type="text/css"
	href="text/lib/kindeditor/themes/default/default.css">
<script type="text/javascript"
	src="text/lib/kindeditor/kindeditor-all-min.js"></script>
<script type="text/javascript" src="text/lib/kindeditor/zh-CN.js"></script>
<!--答题表单-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<span class="label label-primary"> <c:if
				test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
		</span>
	</div>
	<div data-options="region:'center'" style="overflow-x: hidden;">
		<form id="dom_formRoom">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<table class="editTable">
				<c:if test="${pageset.operateType==0}">
					<tr>
						<td class="title">答题室ID:</td>
						<td colspan="3" ><code>
								${entity.id}</code></td>
					</tr>
				</c:if>
				<tr>
					<td class="title">名称:</td>
					<td colspan="2"><input type="text" style="width: 240px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[64]']"
						id="entity_name" name="name" value="${entity.name}"></td>
					<td rowspan="3"
						style="border-left: 1px solid #ccc; text-align: center;"><c:if
							test="${empty entity.imgid }">
							<img id="iconImgId" style="width: 64px; height: 64px;" alt=""
								src="text/img/exam.png">
						</c:if> <c:if test="${!empty entity.imgid }">
							<img id="iconImgId" style="width: 64px; height: 64px;" alt=""
								src="actionImg/Publoadimg.do?id=${entity.imgid}">
						</c:if></td>
				</tr>
				<tr>
					<td class="title">答卷模式:</td>
					<td colspan="2"><select name="pshowtype" id="entity_pshowtype"
						style="width: 120px;" val="${entity.pshowtype}">
							<option value="1">标准答题模式</option>
							<option value="2">抽取单套答题</option>
							<option value="3">习题练习模式</option>
					</select></td>
				</tr>
				<tr>
					<td class="title">考场图标:</td>
					<td colspan="2"><c:if test="${pageset.operateType!=0}">
							<input type="hidden" id="entity_imgid" name="imgid"
								value="${entity.imgid}">
							<input type="button" id="uploadButton" value="上传图标" />&nbsp;&nbsp;<a
								style="cursor: pointer;" id="del_Backimgid">删除图标</a>
							<script type="text/javascript">
								$(function() {
									var uploadbutton;
									uploadbutton = KindEditor
											.uploadbutton({
												button : KindEditor('#uploadButton')[0],
												fieldName : 'imgFile',
												url : 'actionImg/PubFPuploadImg.do',
												afterUpload : function(data) {
													if (data.error === 0) {
														$("#entity_imgid").val(
																data.id);
														$("#iconImgId")
																.attr(
																		'src',
																		data.url);
													} else {
														if (data.message) {
															alert(data.message);
														} else {
															alert('请检查文件格式');
														}
													}
												},
												afterError : function(str) {
													alert('自定义错误信息: ' + str);
												}
											});
									uploadbutton.fileBox.change(function(e) {
										uploadbutton.submit();
									});
									$('#del_Backimgid')
											.click(
													function() {
														if (confirm("是否删除预览图?")) {
															$("#entity_imgid")
																	.val("");
															$("#iconImgId")
																	.attr(
																			'src',
																			'text/img/exam.png');
														}
													});
								});
							</script>
						</c:if></td>
				</tr>
				<tr>
					<td class="title">有效时间:</td>
					<td><select name="timetype" id="entity_timetype"
						style="width: 120px;" val="${entity.timetype}"><option
								value="1">永久</option>
							<option value="2">限时</option>
					</select></td>
					<td class="title">答题时长:</td>
					<td><input type="text" style="width: 120px;"
						class="easyui-validatebox"
						data-options="required:true,validType:['integer','maxLength[5]']"
						id="entity_timelen" name="timelen" value="${entity.timelen}">
					</td>
				</tr>
				<tr id="tr_time" style="display: none;">
					<td class="title">开始时间:</td>
					<td><input id="entity_starttime" name="starttime"
						style="width: 140px;" value="${entity.starttime}" type="text"
						class="easyui-datetimebox"></input></td>
					<td class="title">结束时间:</td>
					<td><input id="entity_endtime" name="endtime"
						style="width: 140px;" value="${entity.endtime}" type="text"
						class="easyui-datetimebox"></input></td>
				</tr>
				<tr>
					<td class="title">答题人员:</td>
					<td><select name="writetype" id="entity_writetype"
						style="width: 120px;" val="${entity.writetype}"><option
								value="1">指定人员</option>
							<option value="0">任何人员</option>
							<option value="2">匿名答题</option></select></td>
					<td class="title">业务分类:</td>
					<td><input type="hidden" style="width: 120px;"
						id="entity_examtypeid" name="examtypeid"
						value="${entity.examtypeid}"><span id="lable_examtypeid">${examType.name}</span></td>
				</tr>
				<tr>
					<!--  <td class="title">状态:</td>
					<td><select name="pstate" id="entity_pstate"
						val="${entity.pstate}"><option value="1">新建</option>
							<option value="2">发布</option>
							<option value="0">禁用</option></select></td> -->
					<td class="title">答题次数:</td>
					<td><select name="restarttype" id="entity_restarttype"
						style="width: 120px;" val="${entity.restarttype}">
							<option value="1">每人一次</option>
							<option value="2">重复答题</option>
					</select></td>
					<td class="title">阅卷类型:</td>
					<td><select name="counttype" id="entity_counttype"
						style="width: 120px;" val="${entity.counttype}">
							<option value="2">自动</option>
							<option value="1">自动/人工</option>
							<!-- <option value="3">人工</option> -->
					</select></td>
				</tr>
				<tr>
					<td class="title">题目排序:</td>
					<td><select name="ssorttype" id="entity_ssorttype"
						style="width: 120px;" val="${entity.ssorttype}">
							<option value="1">固定</option>
							<option value="2">随机</option>
					</select></td>
					<td class="title">选项排序:</td>
					<td><select name="osorttype" id="entity_osorttype"
						style="width: 120px;" val="${entity.osorttype}">
							<option value="1">固定</option>
							<option value="2">随机</option>
					</select></td>
				</tr>
				<tr>
					<td class="title">考场备注:</td>
					<td colspan="3"><textarea rows="2" style="width: 420px;"
							class="easyui-validatebox"
							data-options="validType:[,'maxLength[64]']" id="entity_pcontent"
							name="pcontent">${entity.pcontent}</textarea></td>
				</tr>
				<tr>
					<td class="title">考场说明:</td>
					<td colspan="3"><textarea id="entity_roomnote"
							style="display: none;" name="roomnote">${entity.roomnote}
						</textarea>
						<div style="width: 425px;"><jsp:include
								page="comment/IncludeEditor.jsp">
								<jsp:param value="entity_roomnote" name="fieldId" />
								<jsp:param value="${pageset.operateType}" name="type" />
								<jsp:param value="考场说明" name="fieldTitle" />
							</jsp:include></div></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entityRoom" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityRoom" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formRoom" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionRoom = 'room/add.do';
	var submitEditActionRoom = 'room/edit.do';
	var currentPageTypeRoom = '${pageset.operateType}';
	var submitFormRoom;
	$(function() {
		//表单组件对象
		submitFormRoom = $('#dom_formRoom').SubmitForm({
			pageType : currentPageTypeRoom,
			grid : gridRoom,
			currentWindowId : 'winRoom'
		});
		//关闭窗口
		$('#dom_cancle_formRoom').bind('click', function() {
			$('#winRoom').window('close');
		});
		//提交新增数据
		$('#dom_add_entityRoom').bind('click', function() {
			submitFormRoom.postSubmit(submitAddActionRoom);
		});
		//提交修改数据
		$('#dom_edit_entityRoom').bind('click', function() {
			submitFormRoom.postSubmit(submitEditActionRoom);
		});
		initTimetype();
		$('#entity_timetype').change(function(e) {
			initTimetype();
		});
		//新增表单
		if (currentPageTypeRoom == '1') {
			$('#lable_examtypeid').text($('#PARENTTITLE_RULE').val());
			$('#entity_examtypeid').val($('#PARENTID_RULE').val());
		}
	});

	//初始化时间类型
	function initTimetype() {
		var type = $('#entity_timetype').val();
		//1永久/2限时
		if (type == '1') {
			//隐藏时间表单
			$('#tr_time').hide();
			$('#entity_starttime,#entity_endtime').datetimebox({
				required : false,
				showSeconds : false
			});
		}
		if (type == '2') {
			//显示时间表单
			$('#tr_time').show();
			$('#entity_starttime,#entity_endtime').datetimebox({
				required : true,
				showSeconds : false
			});
		}
	}
//-->
</script>