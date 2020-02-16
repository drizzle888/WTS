<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%><%@taglib
	uri="http://java.sun.com/jsp/jstl/functions" prefix="fun"%>
<script charset="utf-8"
	src="<PF:basePath/>text/javascript/bootstrap-treeview.js"></script>
<script
	src="<PF:basePath/>view/web-simple/user/commons/text/chooseUsersWindow.js"></script>
<link
	href="<PF:basePath/>view/web-simple/user/commons/text/chooseUsersWindow.css"
	rel="stylesheet">
<div class="modal fade" id="chooseUserAndOrgConfirm" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h3 class="modal-title" id="myModalLabel">
					<i class="glyphicon glyphicon-user"></i> 选择用户
				</h3>
			</div>
			<div class="modal-body" style="padding: 0px;">
				<div class="row modal-rows">
					<div class="col-sm-5 modal-col-right"
						style="height: 350px; overflow: auto; padding: 4px; background-color: #f8f5ef;">
						<div id="treeview12"></div>
					</div>
					<div class="col-sm-7"
						style="margin-left: 0px; padding: 0px; background-color: #fff;">
						<div style="padding: 4px;">
							<div class="input-group  input-group-sm" style="margin: 4px;">
								<input type="text" class="form-control" id="sendUserNameInput"
									placeholder="人员姓名或组织机构名称"> <span
									class="input-group-btn">
									<button class="btn btn-default" id="sendUserNameSearch"
										type="button">
										<i class="glyphicon glyphicon-search"></i>查询用戶
									</button>
								</span>
							</div>
						</div>
						<div
							style="background-color: #fff; height: 260px; overflow: auto; margin: auto; text-align: center;">
							<ul id="ableChooseNodes" class="usernode-box">
							</ul>
						</div>
						<nav aria-label="Page navigation"
							style="text-align: center; background-color: #fff;">
							<ul class="pagination pagination-sm usernode-page"
								style="margin: 4px; margin: auto; margin-top: 4px;">
								<li><a href="#" aria-label="Previous"> <span
										aria-hidden="true">&laquo;</span>
								</a></li>
								<li><a href="#">1</a></li>
								<li><a href="#">2</a></li>
								<li><a href="#">3</a></li>
								<li><a href="#">4</a></li>
								<li><a href="#">5</a></li>
								<li><a href="#" aria-label="Next"> <span
										aria-hidden="true">&raquo;</span>
								</a></li>
							</ul>
						</nav>
					</div>
				</div>
				<div class="row modal-rows">
					<div class="col-sm-12" id="chooseNodeShowbox"
						style="padding: 10px; background-color: #fff; max-height: 135px; overflow: auto;">
					</div>
				</div>
			</div>
			<div class="modal-footer modal-rows"
				style="border-top: 1px; margin-top: 0px; padding: 12px;">
				<button type="button" class="btn btn-primary"
					id="chooseUserAndOrgedButtonId">确定选择</button>
				<button type="button" id="clearChooseUsersId"
					class="btn btn-default">清空</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">
					取消</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>

<script type="text/javascript">
	$(function() {
		initTreeConntent('#treeview12');
		$('#sendUserNameSearch').click(function() {
			defOrgid = null;
			dataLoadSearch(null, $('#sendUserNameInput').val(), 1);
			clearSelected();
		});
		$('#chooseUserAndOrgedButtonId').click(function() {
			chooseUserAndOrgsHandle();
		});
		$('#clearChooseUsersId').click(function() {
			delAllSendNode();
		});
		dataLoadSearch(null, null, 1);
		showChooseNodeByChooseBox();
	});
	/**
	使用説明：
	 **/
	/**
	1：如下面方法可以在父级构造选中节点的回显
	//在弹出框中选择完用户和机构后的回显方法
	//function chooseUserAndOrgsHandle() {
	//	var num = showChooseNodeByOutBox('chooseUserAndOrgbox');
	//	if (num > 0) {
	//		$('#showNoSenderMessageBoxId').hide();
	//	} else {
	//		$('#showNoSenderMessageBoxId').show();
	//	}
	//	$('#chooseUserAndOrgConfirm').modal('hide');
	//}
	 **/
	/**
	2:初始化模态窗口
	$(function() {
		$('.chooseUsersButton').click(function() {
			$('#chooseUserAndOrgConfirm').modal({
				keyboard : false
			});
		});
		$('#sendMessageButtonId').click(function() {
			sendUserAndOrgsMessage();
		});

	});
	 **/
	/**
	3:父页面引入本jsp
	<-jsp-:-include page="commons/includeChooseSenderBox.jsp"></-jsp-:-include>
	 **/
</script>