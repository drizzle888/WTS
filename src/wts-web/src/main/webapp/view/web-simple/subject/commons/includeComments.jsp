<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fun"%>
<style>
<!--
.list-group {
	margin-top: 8px;
}

.list-group .list-group-item {
	background-color: #ffffee;
}
-->
</style>
<div class="row">
	<div class="col-sm-12">
		<c:forEach items="${comments}" var="node">
			<div class="col-sm-12 doc_node">
				<div class="media message_boxunit">
					<a class="pull-left"
						href="webuser/PubHome.do?userid=${node.cuser }"
						style="max-width: 200px; text-align: center;"
						title="${node.cusername }"><i class="glyphicon glyphicon-user"></i>
					</a>
					<div class="media-body">
						<div class="side_unit_info">
							<div class="side_unit_info">
								<span class="side_unit_info"> <a
									href="webuser/PubHome.do?userid=${node.cuser }">${node.cusername }</a>
									<PF:FormatTime date="${node.ctime }"
										yyyyMMddHHmmss="yyyy-MM-dd HH:mm" />&nbsp;&nbsp; <PF:UserIsAdmin
										userid="${USEROBJ.id}">
										<a style="color: red;" onclick="delComment('${node.id}')">删除</a>
									</PF:UserIsAdmin>
								</span>
							</div>
							<div class="doc_node_content" style="margin: 4px;">
								<pre>${node.text}</pre>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</div>