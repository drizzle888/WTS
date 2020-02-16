var wcpKnowdialog;
var wcpKnowEditor;
KindEditor.plugin('wcpknow',function(K) {
	wcpKnowEditor = this, name = 'wcpknow';
	wcpKnowEditor.clickToolbar(name,function() {
		wcpKnowdialog = K
			.dialog( {
				width : 600,
				title : '选择知识',
				body : '<div style="margin:10px;text-align: center;"><div class="wcpknow_search_limit_box">'
						+'<input id="wcpknow_input_id" type="text"/>'
						+'<span class="ke-button-common ke-button-outer ke-dialog-yes" title="查找">'
						+'<input class="ke-button-common ke-button" id="wcpknow_button_id" value="查找" type="button">'
						+'</span>'
						+'</div>'
						+'<div class="wcpknow_search_rbox_c" >'
						+'<table id="wcpknow_search_rbox" class="kindeditor_serch_table table table-striped">'
						+'</table>'
						+'</div>'
						+'</div>',
				closeBtn : {
					name : '关闭',
					click : function(e) {
						wcpKnowdialog.remove();
					}
				}
			});
		loadKnow_wcpKnow();
		$('#wcpknow_button_id').bind('click',function() {
			loadKnow_wcpKnow($('#wcpknow_input_id').val());
		});
		$('#wcpknow_input_id').keydown(function(e){
			if(e.keyCode==13){
				loadKnow_wcpKnow($(
				'#wcpknow_input_id')
				.val());
			}
		});
	});
});
function loadKnow_wcpKnow(knowtitle) {
	$('#wcpknow_search_rbox').html('loading...');
	$.post('home/FPsearchKnow.do', {'knowtitle' : knowtitle}, function(flag) {
		if (flag.size > 0) {
			$('#wcpknow_search_rbox').html('<tr><th width="280">知识名称</th><th width="80">知识类型</th><th width="80">分类</th><th width="80">操作</th></tr>');
			$(flag.list).each(
					function(i, obj) {
						$('#wcpknow_search_rbox').append('<tr><td>'+obj.TITLE+'</td><td>'+obj.DOMTYPE+'</td><td>'+obj.TYPENAME+'</td><td>'+'<a title="'+obj.TITLE+'" onClick="clickLink_wcpKnow(this)" id="'
								+ obj.ID + '" wcptype="'
								+ obj.DOMTYPE + '">选择</a>'+'</td></tr>'
								);
					});
		} else {
			alert('未匹配到相关知识!');
		}
	}, 'json');
}
function clickLink_wcpKnow(flag) {
	var id = $(flag).attr('id');
	var title = $(flag).attr('title');
	var type = $(flag).attr('wcptype');
	wcpKnowEditor.insertHtml('<a href="webdoc/view/Pub'+id+'.html">' + title + '</a>');
	wcpKnowdialog.remove();
}

KindEditor.lang({ wcpknow : '插入知识' });