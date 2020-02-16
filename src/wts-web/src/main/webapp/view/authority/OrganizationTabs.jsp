<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<c:if test="${pageset.operateType==1}">
	<!--新增${JSP_Messager_Title}记录-->
</c:if>
<c:if test="${pageset.operateType==2}">
	<!--修改${JSP_Messager_Title}记录-->
</c:if>
<c:if test="${pageset.operateType==0}">
	<!--浏览${JSP_Messager_Title}记录-->
</c:if>
<div id="orgtabsId">
	<div title="机构信息" style="padding: 10px"
		data-options="href:'organization/form.do?operateType=${pageset.operateType}&ids=${ids}&parentId=${parentId}'">
	</div>
	<div title="机构岗位" style="padding: 10px"
		data-options="href:'organization/postConsoleTabs.do?pageset.pageType=${pageset.operateType}&ids=${ids}'">
	</div>
<%-- 	<div title="机构用户" style="padding: 10px"
		data-options="href:'organization/postActionsTabs.do?pageset.pageType=${pageset.operateType}&ids=${ids}'">
	</div> --%>
	<div title="机构用户" style="padding: 10px"
		data-options="href:'organization/postActionsTabs.do?pageset.pageType=${pageset.operateType}&ids=${ids}'">
	</div>
</div>
<!-- 唯一保证正确的用户ID -->
<input type="hidden" id="domTabsId" value="${ids}" />
<script type="text/javascript">
	$(function() {
		$('#orgtabsId').tabs( {
			fit : true,
			border : false
		});
		$('#orgtabsId').tabs('disableTab', 1);
		$('#orgtabsId').tabs('disableTab', 2);
		//$('#orgtabsId').tabs('enableTab', 1);
	});
	$(function() {
		$('#orgtabsId').tabs( {
			onSelect : function(title, index) {

			}
		});
	})
</script>
<c:if test="${ids!=null&&ids!=''}">
	<script type="text/javascript">
	$(function() {
		$('#orgtabsId').tabs('enableTab', 1);
		$('#orgtabsId').tabs('enableTab', 2);
	});
</script>
</c:if>