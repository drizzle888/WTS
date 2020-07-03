<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<%@ taglib uri="/view/conf/tip.tld" prefix="TIP"%>
<link rel="stylesheet" type="text/css"
	href="<PF:basePath/>view/exam/subject/subject.css">
<script src="text/lib/fileUpload/jquery.ui.widget.js"></script>
<script src="text/lib/fileUpload/jquery.iframe-transport.js"></script>
<script src="text/lib/fileUpload/jquery.fileupload.js"></script>
<link href="text/lib/fileUpload/jquery.fileupload.css" rel="stylesheet">
<!--附件题 flag=(adjudge:判卷，answer：答卷，checkup：检查)-->
<div>
	<div class="subjectUnitViewBox">
		<div class="subjectOrder">${status.index + 1}</div>
		<div>
			<c:if test="${flag!='adjudge'}">
				<div><TIP:HtmlEscape text="${subjectu.version.tipstr}" /><span class="subjectPoint">本题${subjectu.point}分</span>
				</div>
				<c:if test="${!empty subjectu.version.tipnote}">
					<div class="ke-content ke-content-borderbox">
						<TIP:InitHtmlContentTag html="${subjectu.version.tipnote}"></TIP:InitHtmlContentTag>
					</div>
				</c:if>
			</c:if>
			<c:if test="${flag=='answer'||flag==null}">
				<!--回答组件 -->
				<div class="answerVacancyViewBox">
					<div class="row" style="padding-top: 8px;">
						<div class="col-md-6">
							<div class="form-group has-warning has-feedback">
								<div class="input-group">
									<label id="lable-${subjectu.version.id}" class="form-control"
										style="border: 1px dashed #999999;"><c:if
											test="${empty subjectu.valtitle}">请上传附件...</c:if> <c:if
											test="${not empty subjectu.valtitle}">${subjectu.valtitle}</c:if>
									</label> <span class="input-group-btn"> <span
										class="btn btn-success fileinput-button"> <i
											class="glyphicon glyphicon-cloud"></i> <span>上传文件</span> <input
											id="fileupload-${subjectu.version.id}" type="file"
											name="imgFile">
									</span>
									</span>
								</div>
								<div style="display: none;" id="hidenbox-${subjectu.version.id}">
									<textarea name="${subjectu.version.id}" class="form-control"
										rows="3">${subjectu.val}</textarea>
								</div>
							</div>
						</div>
					</div>
				</div>
				<script>
					$(function() {
						$('#fileupload-${subjectu.version.id}')
								.fileupload(
										{
											url : "actionImg/PubFPupload.do",
											dataType : 'json',
											done : function(e, data) {
												if (data.result.error != 1) {
													$(
															'#lable-${subjectu.version.id}')
															.text(
																	decodeURI(data.result.fileName));
													$(
															'#hidenbox-${subjectu.version.id} textarea')
															.val(data.result.id);
													$(
															"#hidenbox-${subjectu.version.id} textarea")
															.change();
												} else {
													$(
															'#lable-${subjectu.version.id}')
															.text("请上传附件...");
													$(
															'#hidenbox-${subjectu.version.id} textarea')
															.val("");
													$(
															"#hidenbox-${subjectu.version.id} textarea")
															.change();
													alert(data.result.message);
												}
											},
											progressall : function(e, data) {
												var progress = parseInt(
														data.loaded
																/ data.total
																* 100, 10);
												$(
														'#lable-${subjectu.version.id}')
														.text(progress + '%');
											}
										});
					});
				</script>
			</c:if>
			<c:if test="${flag=='checkup'||flag=='adjudge'}">
				<!--回答回显-->
				<div class="answerVacancyViewBox"
					style="height: auto;">
					<div>
						<label>附件下载：</label> <a
							href="actionImg/Publoadfile.do?id=${subjectu.val}">${subjectu.valtitle}</a>
					</div>
				</div>
			</c:if>
			<%@ include file="../includePointInput.jsp"%>
		</div>
	</div>
</div>

