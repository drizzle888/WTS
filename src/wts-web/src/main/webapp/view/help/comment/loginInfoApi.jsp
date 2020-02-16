<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<h1>免密登录</h1>
<img src="view/help/comment/loginTip.jpg" />
<h2>流程</h2>
<h3>1.应用系统通过restful接口进行授权注册，换取权限码</h3>
<h3>2.浏览器重定向到目标地址，并将权限码添加LOGIN_CERTIFICATE参数中</h3>
