<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--预加载题目缓存表单-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<span class="label label-primary"> 预加载题目缓存 </span>
	</div>
	<div data-options="region:'center'">
		<div style="text-align: center; padding: 20px;">
			<span id="loadProcessId">请启动加载任务</span>
		</div>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<a id="dom_do_load" href="javascript:void(0)" iconCls="icon-save"
				class="easyui-linkbutton">开始加载</a> <a id="dom_cancle"
				href="javascript:void(0)" iconCls="icon-cancel"
				class="easyui-linkbutton" style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		//关闭窗口
		$('#dom_cancle').bind('click', function() {
			$('#winLoadSubjects').window('close');
		});
		//提交新增数据
		$('#dom_do_load').bind('click', function() {
			//執行加载线程
			$.post('room/doLoadSubject.do?ids=${ids}', {}, function(flag) {
				if (flag.STATE == 0) {
					//啓動成功
					$('#loadProcessId').text('开始加载缓存');
					loadProcess();
				} else {
					var str = MESSAGE_PLAT.ERROR_SUBMIT + flag.MESSAGE;
					$.messager.alert(MESSAGE_PLAT.ERROR, str, 'error');
				}
			}, 'json');
			//读取加载进度
		});
	});
	//加載进度
	function loadProcess() {
		$.post('room/getLoadSubjectProcess.do', {}, function(pflag) {
			if (pflag.STATE == 0) {
				//啓動成功
				$('#loadProcessId').text(
						'加载缓存' + pflag.completes + "/" + pflag.alls);
				if (pflag.taskstate == 1) {
					setTimeout("loadProcess()", "1000");
				} else {
					$('#loadProcessId').text('完成缓存加载');
				}
			} else {
				$.messager.alert(MESSAGE_PLAT.ERROR, MESSAGE_PLAT.ERROR_SUBMIT
						+ pflag.MESSAGE, 'error');
			}
		}, 'json');
	}
//-->
</script>