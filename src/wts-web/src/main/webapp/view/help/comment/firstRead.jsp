<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!-- 机构接口 -->
<h1>准备</h1>
<h2>API访问地址的根路径为：</h2>
<p class="protocol">${CURL}</p>
<p class="lead">本接口采用http协议通过json传参方式进行调用</p>
<h2>接口启用状态为：</h2>
<p class="protocol"><PF:ParameterValue key="config.restful.state"/></p>
<h2>接口调试状态为：</h2>
<p class="protocol"><PF:ParameterValue key="config.restful.debug"/></p>