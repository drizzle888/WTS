var wcp_latex_editor;
var wcplatexKnowdialog;
KindEditor.plugin('wcplatex', function(K) {
	wcp_latex_editor = this, name = 'wcplatex';
	// 点击图标时执行
	wcp_latex_editor.clickToolbar(name, function() {
		wcplatexKnowdialog = K
		.dialog( {
			width : 400,
			title : '插入LaTeX公式',
			body : '<div style="margin:10px;height:250px;text-align: center;overflow:auto;">'+
			       '<div id="kindEditorLatexBoxId">loading...</div>'+
				   '</div>',
			closeBtn : {
				name : '关闭',
				click : function(e) {
					wcplatexKnowdialog.remove();
				}
			}
		});
		$('#kindEditorLatexBoxId').load('latex/inputcom.do',{},function(){
			$('#kindEditorLatexBoxId #insertLatexImg').click(function(){
				if ($('#codeId').val()) {
					$('#latexloadTitleId').text('公式解析中......');
					$.post('latex/latexpng.do', {
						latex : $('#codeId').val()
					}, function(flag) {
						if(flag.error==0){
							//拼装img
							var tag='<img height="60"  src="'+flag.url+'" alt="" />';
							//插入img
							wcp_latex_editor.insertHtml(tag);
							wcplatexKnowdialog.remove();
						}else{
							$('#latexloadTitleId').text(flag.message);
						}
					}, 'json');
				} else {
					$('#latexloadTitleId').text('请输入公式脚本...');
				}
			});
		});
	});
});
KindEditor.lang({
	wcplatex : '插入公式'
});