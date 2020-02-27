<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div class="super_content">
	<div id="showHomeMessageId" class="new-userMessage" ></div>
</div>
<c:if test="${USEROBJ!=null}">
<script>
	var url = "webusermessage/showHomeMessage.do";
	$.post(url, {}, function(obj){
		var message = "";
		if(obj.newMessage){
			message += "最新消息："+obj.newMessage + "；";
		}
		var href = "<span class='glyphicon glyphicon-envelope'></span> " + obj.unReadCount + " <a href='webuser/Home.do?type=usermessage&userid=${USEROBJ.id}'>"+message+"</a>";
		if(obj.unReadCount>0){
			$("#showHomeMessageId").html(href);
		}
	},"json");
</script>
</c:if>