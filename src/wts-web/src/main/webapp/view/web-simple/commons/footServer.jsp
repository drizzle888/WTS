<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<style>
<!--
.footServerUl {
	float: left;
}

.footServerUl img {
	float: left;
	width: 24px;
	height: 24px;
	background-color: #ffffff;
}

.footServerUl a {
	color: #b0b6bb;
}

.footServerUl a:HOVER {
	color: #ffffff;
}
</style>
<div class="super_contentfoot  hidden-xs" style="margin: 0px;">
	<div class="container ">
		<div class="row">
			<div class="col-sm-1"><jsp:include
					page="../commons/includeTowDCode.jsp"></jsp:include></div>
			<div class="col-sm-11 putServerBox">
				<ul id="recommendServiceList" class="footServerUl">
				</ul>
			</div>
		</div>
	</div>
</div>