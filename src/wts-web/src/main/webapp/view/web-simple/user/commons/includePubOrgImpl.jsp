<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<style>
<!--
.docOrgUl .orgLableType {
	cursor: pointer;
}

.farm_tree_flag {
	color: #c3c3c3;
	text-decoration: none;
}

.docOrgUl .hr1 {
	margin: 4px;
}

.docOrgUl .hr2 {
	border-style: dotted;
	margin: 4px;
}

.docOrgUl h1 {
	font-size: 16px;
	margin: 2px;
	margin-left: 30px;
	font-weight: bold;
	margin-left: 30px;
}

.docOrgUl h2 {
	font-size: 14px;
	margin: 2px;
	margin-left: 30px;
	font-weight: bold;
}

.docOrgUl h3 {
	font-size: 12px;
	margin: 2px;
	margin-left: 30px;
	font-weight: bold;
}

.docOrgUl h4 {
	font-size: 10px;
	margin: 2px;
	margin-left: 30px;
	font-weight: bold;
}
-->
</style>
<div>
	<ul class="docOrgUl">
		<c:forEach items="${result.resultList}" var="node1">
			<c:if test="${node1.PARENTID=='NONE'}">
				<li>
					<h1>
						<span class="orgLableType" title="${node1.ID}">${node1.NAME}</span>
					</h1>
					<hr class="hr1" />
					<ul>
						<c:forEach items="${result.resultList}" var="node2">
							<c:if test="${node2.PARENTID==node1.ID}">
								<li>
									<h2>
										<span class="orgLableType" title="${node2.ID}">${node2.NAME}</span>
										<span class="glyphicon glyphicon-chevron-down farm_tree_flag"></span>
									</h2>
									<hr class="hr2" />
									<ul>
										<c:forEach items="${result.resultList}" var="node3">
											<c:if test="${node3.PARENTID==node2.ID}">
												<li>
													<h3>
														<span class="orgLableType" title="${node3.ID}">${node3.NAME}</span>
														<span
															class="glyphicon glyphicon-chevron-down farm_tree_flag"></span>
													</h3>
													<hr class="hr2" />
													<ul>
														<c:forEach items="${result.resultList}" var="node4">
															<c:if test="${node4.PARENTID==node3.ID}">
																<li>
																	<h4 class="orgLableType" title="${node4.ID}">${node4.NAME}</h4>
																	<hr class="hr2" />
																	<ul>
																		<c:forEach items="${result.resultList}" var="node5">
																			<c:if test="${node5.PARENTID==node4.ID}">
																				<li>
																					<h5 class="orgLableType" title="${node5.ID}">${node5.NAME}</h5>
																				</li>
																			</c:if>
																		</c:forEach>
																	</ul>
																</li>
															</c:if>
														</c:forEach>
													</ul>
												</li>
											</c:if>
										</c:forEach>
									</ul>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</li>
			</c:if>
		</c:forEach>
	</ul>
</div>
<script type="text/javascript">
	$(function() {
		$('.orgLableType').click(function() {
			try {
				chooseOrgHandle($(this).attr("title"), $(this).text());
			} catch (e) {
				alert("请实现或检查chooseOrgHandle函数!");
			}
		});
		initTypesStyle();
	});
	function initTypesStyle() {
		$('.farm_tree_flag').each(function(i, o) {
			if ($(this).parent().nextAll('ul').contents().length <= 1) {
				$(this).removeClass("farm_tree_flag");
				$(this).removeClass("glyphicon");
				$(this).removeClass("glyphicon-chevron-down");
			} else {
				$(this).parent().nextAll('ul').hide();
			}
		});
		$('.farm_tree_flag').bind('click', function() {
			var ul = $(this).parent().nextAll('ul');
			if ($(ul).is(':hidden')) {
				$(this).parent().nextAll('ul').show();
				$(this).removeClass("farm_tree_s");
				$(this).addClass("farm_tree_h");
				$(this).removeClass("glyphicon-chevron-down");
				$(this).addClass("glyphicon-chevron-up");
			} else {
				$(this).parent().nextAll('ul').hide();
				$(this).removeClass("farm_tree_h");
				$(this).addClass("farm_tree_s");
				$(this).removeClass("glyphicon-chevron-up");
				$(this).addClass("glyphicon-chevron-down");
			}
		});
	}
</script>
