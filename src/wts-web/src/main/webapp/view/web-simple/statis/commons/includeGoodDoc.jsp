<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="java.net.InetAddress"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="panel panel-default" style="background-color: #FCFCFA;min-height: 460px;">
	<div class="panel-body">
		<p>
			<span class="glyphicon glyphicon-thumbs-up  ">好评知识排名 </span>
		</p>
		<table class="table" style="font-size: 12px;">
			<tr>
				<th width="60">
					排名
				</th>
				<th >
					标题
				</th>
				<th width="150">
					所有者
				</th>
			</tr>
			<c:forEach items="${goodDocs.resultList}" varStatus="status" var="node">
				<tr>
					<td>
						${status.index+1}
					</td>
					<td style="overflow: hidden;max-width: 200px;">
						<a  target="${config_sys_link_newwindow_target}"  href="webdoc/view/Pub${node.ID}.html"> ${node.TITLE}</a>&nbsp;(&nbsp;${node.EVALUATE}&nbsp;)&nbsp;
					</td>
					<td>
						<c:if test="${node.GROUPID==null}">
							<span class="glyphicon glyphicon-user"> ${node.USERNAME} </span>
						</c:if>
						<c:if test="${node.GROUPID!=null}">
							<c:if test="${node.JOINCHECK=='1'}">
								<span class="glyphicon glyphicon-leaf"> ${node.GROUPNAME}</span>
							</c:if>
							<c:if test="${node.JOINCHECK=='0'}">
								<a href="webgroup/Pubshow.do?groupid=?id=${node.GROUPID}"> <span
									class="glyphicon glyphicon-leaf">&nbsp;${node.GROUPNAME}</span>
								</a>
							</c:if>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>