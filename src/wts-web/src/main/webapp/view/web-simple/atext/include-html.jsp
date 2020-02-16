<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!-- 确认框和提示框组件，使用方法在最下方！confirm和alert的bootstrap实现 -->
<div id="ycf-alert" class="modal">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span><span class="sr-only">Close</span>
				</button>
				<h5 class="modal-title">
					<i class="fa fa-exclamation-circle"></i> [Title]
				</h5>
			</div>
			<div class="modal-body small">
				<p>[Message]</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary ok"
					data-dismiss="modal">[BtnOk]</button>
				<button type="button" class="btn btn-default cancel"
					data-dismiss="modal">[BtnCancel]</button>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	//Modal.alert({msg: '内容',title: '标题',btnok: '确定',btncl:'取消'});
	//Modal.confirm({msg : "是否删除角色？"}).on(function(e) {alert("返回结果：" + e);});
	$(function() {
		//控制内容中的图片，在缩放情况下不变形
		$('#docContentsId img').attr('height',''); 
		window.Modal = function() {
			var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
			var alr = $("#ycf-alert");
			var ahtml = alr.html();
			var _alert = function(options) {
				alr.html(ahtml); // 复原
				alr.find('.ok').removeClass('btn-primary').addClass(
						'btn-primary');
				alr.find('.cancel').hide();
				_dialog(options);

				return {
					on : function(callback) {
						if (callback && callback instanceof Function) {
							alr.find('.ok').click(function() {
								callback(true)
							});
						}
					}
				};
			};
			var _confirm = function(options) {
				alr.html(ahtml); // 复原
				alr.find('.ok').removeClass('btn-primary').addClass(
						'btn-primary');
				alr.find('.cancel').show();
				_dialog(options);
				return {
					on : function(callback) {
						if (callback && callback instanceof Function) {
							alr.find('.ok').click(function() {
								callback(true)
							});
							alr.find('.cancel').click(function() {
								callback(false)
							});
						}
					}
				};
			};
			var _dialog = function(options) {
				var ops = {
					msg : "提示内容",
					title : "操作提示",
					btnok : "确定",
					btncl : "取消"
				};
				$.extend(ops, options);
				console.log(alr);
				var html = alr.html().replace(reg, function(node, key) {
					return {
						Title : ops.title,
						Message : ops.msg,
						BtnOk : ops.btnok,
						BtnCancel : ops.btncl
					}[key];
				});

				alr.html(html);
				alr.modal({
					width : 500,
					backdrop : 'static'
				});
			}
			return {
				alert : _alert,
				confirm : _confirm
			}
		}();
	});
</script>
<!-- system modal end -->