<%@ page language="java" pageEncoding="utf-8"%>
<%@page import="com.farm.web.constant.FarmConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div style="position: relative; top: -2px;"
	id="book-${paper.info.id}-box">
	<a class="btn  btn-xs"
		href="javascript:bookPaper('book-${paper.info.id}-box','${room.room.id}','${paper.info.id}',true);"
		role="button" title="收藏"><span id="bookTitleId"></span>收藏&nbsp;<i
		id="book-y" class="glyphicon glyphicon-star" style="display: none;"></i>
		<i id="book-n" class="glyphicon glyphicon-star-empty"></i>(<span
		id="bookNumId">0</span>) </a>
</div>
<script type="text/javascript">
	$(function() {
		// 加载收藏信息
		bookPaper('book-${paper.info.id}-box', '${room.room.id}',
				'${paper.info.id}', false);
	}); 
</script>