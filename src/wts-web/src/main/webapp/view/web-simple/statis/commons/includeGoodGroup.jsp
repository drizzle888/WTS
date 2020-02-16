<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="java.net.InetAddress"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="panel panel-default" style="background-color: #FCFCFA;min-height: 460px;">
	<div class="panel-body">
		<p>
			<span class="glyphicon glyphicon-leaf  ">好评小组排名</span>
		</p>
		<table class="table" style="font-size: 12px;">
			<tr>
				<th>
					排名
				</th>
				<th>
					小组
				</th>
				<th>
					好评
				</th>
			</tr>
			<c:forEach items="${goodGroups.resultList}" varStatus="status" var="node">
				<tr>
					<td>
						${status.index+1}
					</td>
					<td>
						<c:if test="${node.JOINCHECK=='1'}">
							<span class="glyphicon glyphicon-leaf"> ${node.NAME}</span>
						</c:if>
						<c:if test="${node.JOINCHECK=='0'}">
							<a href="webgroup/Pubshow.do?groupid=${node.ID}"> <span
								class="glyphicon glyphicon-leaf">&nbsp;${node.NAME}</span>
							</a>
						</c:if>
					</td>
					<td>
						${node.SUMYES}
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>