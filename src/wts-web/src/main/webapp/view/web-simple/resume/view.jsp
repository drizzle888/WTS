<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<PF:basePath/>" />
<title>${USEROBJ.name}-个人简历-<PF:ParameterValue
		key="config.sys.title" /></title>
<meta name="description"
	content='<PF:ParameterValue key="config.sys.mate.description"/>'>
<meta name="keywords"
	content='<PF:ParameterValue key="config.sys.mate.keywords"/>'>
<meta name="author"
	content='<PF:ParameterValue key="config.sys.mate.author"/>'>
<meta name="robots" content="noindex,nofllow">
<jsp:include page="../atext/include-web.jsp"></jsp:include>
<link href="view/web-simple/atext/style/resume.css" rel="stylesheet">
</head>
<body>

	<jsp:include page="../commons/head.jsp"></jsp:include>
	<jsp:include page="../commons/superContent.jsp"></jsp:include>
	<div class="containerbox">
		<div class="container " style="font-size: 14px;">
			<div class="row  column_box">
				<div class="col-md-12">
					<h3>${user.name}-个人档案</h3>
				</div>
			</div>
			<div class="row ">
				<div class="widget-box shadow-box">
					<div class="title">
						<h3>
							<i class="glyphicon glyphicon-star"></i>基本信息
							<c:if test="${user.id==USEROBJ.id }">
								<a class="resum_button btn btn-info btn-sm"
									href="resume/baseEdit.do?userid=${USEROBJ.id }">编辑</a>
							</c:if>
						</h3>
					</div>
					<div class="stream-list p-stream" style="padding: 4px;">
						<table class="table table-striped resum_unit">
							<tr>
								<td class="resum_title">姓名</td>
								<td>${base.name}</td>
								<td class="resum_title">性别</td>
								<td>${base.sex}</td>
								<td rowspan="7"
									style="width: 220px; border-left: 1px solid #eeeeee;"><c:if
										test="${base.photoid!=null&&base.photoid!=''}">
										<img src="${photourl}" alt="照片" class="img-thumbnail phoneimg">
									</c:if> <c:if test="${base.photoid==null||base.photoid==''}">
										<img src="text/img/photo.png" alt="..." class="phoneimg">
									</c:if></td>
							</tr>
							<tr>
								<td class="resum_title">参加工作年份</td>
								<td>${base.dateyear}</td>
								<td class="resum_title">出生日期</td>
								<td>${base.birthday}</td>
							</tr>
							<tr>
								<td class="resum_title">最高学历</td>
								<td>${base.degreemax}</td>
								<td class="resum_title">户口所在地</td>
								<td>${base.registered}</td>
							</tr>
							<tr>
								<td class="resum_title">居住所在地</td>
								<td>${base.livestr}</td>
								<td class="resum_title">政治面貌</td>
								<td>${base.zzmm}</td>
							</tr>
							<tr>
								<td class="resum_title">身份证号</td>
								<td>${base.idcode}</td>
								<td class="resum_title">海外工作/学习经历</td>
								<td>${base.studyabroad}</td>
							</tr>
							<tr>
								<td class="resum_title">其他证件类型</td>
								<td>${base.othertype}</td>
								<td class="resum_title">其他证件编号</td>
								<td>${base.otherid}</td>
							</tr>
							<tr>
								<td class="resum_title">婚姻状况</td>
								<td>${base.marriagesta}</td>
								<td class="resum_title">国籍</td>
								<td>${base.nationality}</td>
							</tr>
							<tr>
								<td class="resum_title">备注</td>
								<td>${base.pcontent}</td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="row ">
				<div class="widget-box shadow-box">
					<div class="title">
						<h3>
							<i class="glyphicon glyphicon-star"></i>通讯方式
							<c:if test="${user.id==USEROBJ.id }">
								<a class="resum_button btn btn-info btn-sm"
									href="resume/addressEdit.do?userid=${USEROBJ.id }">编辑</a>
							</c:if>
						</h3>
					</div>
					<div class="stream-list p-stream" style="padding: 4px;">
						<table class="table table-striped resum_unit">
							<tr>
								<td class="resum_title">电子邮箱</td>
								<td width="25%">${base.emailcode}</td>
								<td class="resum_title">手机</td>
								<td>${base.mobilecode}</td>
							</tr>
							<tr>
								<td class="resum_title">qq</td>
								<td>${base.qqcode}</td>
								<td class="resum_title">微信</td>
								<td>${base.wxcode}</td>
							</tr>
							<tr>
								<td class="resum_title">电话</td>
								<td colspan="3">${base.phonecode}</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="row ">
				<div class="widget-box shadow-box">
					<div class="title">
						<h3>
							<i class="glyphicon glyphicon-star"></i>自我评价
							<c:if test="${user.id==USEROBJ.id }">
								<a class="resum_button btn btn-info btn-sm"
									href="resume/appraisalEdit.do?userid=${USEROBJ.id }">编辑</a>
							</c:if>
						</h3>
					</div>
					<div class="stream-list p-stream" style="padding: 4px;">
						<table class="table table-striped resum_unit">
							<tr>
								<td class="resum_title">自我评价</td>
								<td>${resumeappraisal.appraisal }</td>
							</tr>
							<tr>
								<td class="resum_title">职业目标</td>
								<td>${resumeappraisal.careergoals }</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="row ">
				<div class="widget-box shadow-box">
					<div class="title">
						<h3>
							<i class="glyphicon glyphicon-star"></i>教育经历
							<c:if test="${user.id==USEROBJ.id }">
								<a class="resum_button btn btn-info btn-sm"
									href="resume/educateEdit.do?userid=${USEROBJ.id}&pagetype=0">编辑</a>
							</c:if>
						</h3>
					</div>
					<c:forEach items="${educates}" var="none">
						<div class="stream-list p-stream" style="padding: 4px;">
							<table class="table table-striped resum_unit">
								<tr>
									<th class="resum_title">${none.datetime}&nbsp;至&nbsp;${none.dateend}</th>
									<th>${none.degree}&nbsp;&nbsp;|&nbsp;&nbsp;${none.schoolname}&nbsp;&nbsp;|&nbsp;&nbsp;${none.specialityname}&nbsp;&nbsp;|&nbsp;&nbsp;是否统招(${none.unitaryis})</th>
								</tr>
								<c:if test="${none.pcontent!=null&&none.pcontent!=''}">
									<tr>
										<td class="resum_title">备注</td>
										<td>${none.pcontent}</td>
									</tr>
								</c:if>
							</table>
						</div>
					</c:forEach>
				</div>
			</div>
			<div class="row ">
				<div class="widget-box shadow-box">
					<div class="title">
						<h3>
							<i class="glyphicon glyphicon-star"></i>工作经历
							<c:if test="${user.id==USEROBJ.id }">
								<a class="resum_button btn btn-info btn-sm"
									href="resume/workEdit.do?userid=${USEROBJ.id}&pagetype=0">编辑</a>
							</c:if>
						</h3>
					</div>
					<c:forEach items="${works}" var="none">
						<div class="stream-list p-stream" style="padding: 4px;">
							<table class="table table-striped resum_unit">
								<tr>
									<th class="resum_title">${none.datestart}&nbsp;至&nbsp;${none.dateend}</th>
									<th>${none.name}&nbsp;&nbsp;
										|&nbsp;&nbsp;${none.postname}&nbsp;&nbsp;
										|&nbsp;&nbsp;${none.type}&nbsp;&nbsp;
										|&nbsp;&nbsp;${none.eproperty}&nbsp;&nbsp;
										|&nbsp;&nbsp;${none.escale}&nbsp;&nbsp;
										|&nbsp;&nbsp;职位税前月薪(${none.salary})</th>
								</tr>
								<tr>
									<td class="resum_title">工作描述</td>
									<td>${none.worknote}</td>
								</tr>
							</table>
						</div>
					</c:forEach>
				</div>
			</div>
			<div class="row ">
				<div class="widget-box shadow-box">
					<div class="title">
						<h3>
							<i class="glyphicon glyphicon-star"></i>家庭成员/联系人
							<c:if test="${user.id==USEROBJ.id }">
								<a class="resum_button btn btn-info btn-sm"
									href="resume/familyEdit.do?userid=${USEROBJ.id}&pagetype=0">编辑</a>
							</c:if>
						</h3>
					</div>
					<c:forEach items="${familys}" var="none">
						<div class="stream-list p-stream" style="padding: 4px;">
							<table class="table table-striped resum_unit">
								<tr>
									<th class="resum_title">${none.relationtype}</th>
									<th>${none.name}&nbsp;&nbsp;|&nbsp;&nbsp;${none.birth}
										&nbsp;&nbsp;|&nbsp;&nbsp;${none.phone}
										&nbsp;&nbsp;|&nbsp;&nbsp;${none.zzmm}
										&nbsp;&nbsp;|&nbsp;&nbsp;${none.wordaddr}</th>
								</tr>
								<tr>
									<td class="resum_title">住址/备注</td>
									<td>${none.addr}&nbsp;&nbsp;|&nbsp;&nbsp;${none.pcontent}</td>
								</tr>
							</table>
						</div>
					</c:forEach>
				</div>
			</div>
			<div class="row ">
				<div class="widget-box shadow-box">
					<div class="title">
						<h3>
							<i class="glyphicon glyphicon-star"></i>职业规划
							<c:if test="${user.id==USEROBJ.id }">
								<a class="resum_button btn btn-info btn-sm"
									href="resume/intensionEdit.do?userid=${USEROBJ.id}&pagetype=0">编辑</a>
							</c:if>
						</h3>
					</div>
					<div class="stream-list p-stream" style="padding: 4px;">
						<table class="table table-striped resum_unit">
							<tr>
								<td class="resum_title">期望工作性质</td>
								<td>${resumeintension.worknature }</td>
								<td class="resum_title">期望工作地点</td>
								<td>${resumeintension.workaddr }</td>
								<td class="resum_title">期望从事职业</td>
								<td>${resumeintension.workoccupation }</td>
							</tr>
							<tr>
								<td class="resum_title">期望从事行业</td>
								<td>${resumeintension.workindustry }</td>
								<td class="resum_title">期望月薪(税前)</td>
								<td>${resumeintension.workpay }</td>
								<td class="resum_title">工作状态</td>
								<td>${resumeintension.workstat }</td>
							</tr>
							<tr>
								<td class="resum_title">备注</td>
								<td colspan="5">${resumeintension.pcontent }</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="row ">
				<div class="widget-box shadow-box">
					<div class="title">
						<h3>
							<i class="glyphicon glyphicon-star"></i>附件
							<c:if test="${user.id==USEROBJ.id }">
								<a class="resum_button btn btn-info btn-sm"
									href="resume/filesEdit.do?userid=${USEROBJ.id}&pagetype=0">编辑</a>
							</c:if>
						</h3>
					</div>
					<div class="stream-list p-stream" style="padding: 4px;">
						<table class="table table-striped resum_unit">
							<tr>
								<td><c:forEach var="farmDocfile"
										items="${farmDocfileList }">
										<div id="file_${farmDocfile.id }">
											<span>${farmDocfile.name }</span> &nbsp; <a
												href="${farmDocfile.url}" style="color: green;">下载</a>
											&nbsp;&nbsp;
										</div>
									</c:forEach></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../commons/footServer.jsp"></jsp:include>
	<jsp:include page="../commons/foot.jsp"></jsp:include>
</body>
</html>