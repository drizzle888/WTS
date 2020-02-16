<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<h1>免密登录执行登录</h1>
<p class="protocol">客户应用通过浏览器重定向访问EKS知识库管理系统，并在参数中添加LOGIN_CERTIFICATE进行免密登录</p>
<p class="lead">如：192.168.9.44:8080/wcp/home/Pubindex.html?LOGIN_CERTIFICATE=2b3756e4ac8440ad8fc437be12906613</p>
