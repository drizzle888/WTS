<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<div id="orgBoxId" style="height: 300px; overflow: auto;"></div>
<script type="text/javascript">
	$(function() {
		$("#orgBoxId").load("home/PubFPloadOrgs.do");
	});
	function chooseOrgHandle(id, name) {
		$('#orgnameId').val(name);
		$('#orgid').val(id);
		$('#myModal').modal('hide');
	}
</script>
