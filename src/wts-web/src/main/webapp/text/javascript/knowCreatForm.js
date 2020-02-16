//屏蔽知识创建表单中的回车事件
$(function() {
	$(".wcp_noEnterSubmit").keypress(function(event) {
		var target, code, tag;
		if (!event) {
			event = window.event; //针对ie浏览器    
			target = event.srcElement;
			code = event.keyCode;
			if (code == 13) {
				tag = target.tagName;
				if (tag == "TEXTAREA") {
					return true;
				} else {
					return false;
				}
			}
		} else {
			target = event.target; //针对遵循w3c标准的浏览器，如Firefox    
			code = event.keyCode;
			if (code == 13) {
				tag = target.tagName;
				if (tag == "INPUT") {
					return false;
				} else {
					return true;
				}
			}
		}
	});
});


//处理分类tags=[[NONE, 办公], [类型, 住宅, 学校], [风格, 威尼斯, 意大利, 日式, 欧洲]]
/**加载分类标签，在知识表单中
 * @param knowtypeid 知识分类id
 * @param taglistboxId 用来展示分类标签列表的容器id
 */
function loadTypeTags(knowtypeid,taglistboxId,knowtagId) {
	$.post('doctypetag/findTypeTags.do', {
		typeid :knowtypeid
	}, function(flag) {
		$('#'+taglistboxId).html("");
		if(!flag.tags){
			return;
		}
		if(flag.tags.length<=0){
			return;
		}
		var htmlscript = '<div class="form-group taglistBox">';
		$(flag.tags).each(function(i, obj) {
			if (i > 0) {
				htmlscript = htmlscript + '<hr style="margin: 4px;" />';
			}
			htmlscript = htmlscript + '<div>';
			$(obj).each(function(i, obj2) {
				if (i == 0) {
					var text=obj2;
					if(obj2!='NONE'){
						htmlscript = htmlscript + '<span style="font-weight: bold;">'+obj2+'：</span> ';
					}
				} else {
					htmlscript = htmlscript + '<span class="label label-default wcp-tag">'+obj2+'</span> ';
				}
			});
			htmlscript = htmlscript + '</div>';
		});
		htmlscript = htmlscript + "</div>";
		$('#'+taglistboxId).html(htmlscript);
		$('.wcp-tag').click(function(obj) {
			var texttag = $('#'+knowtagId).val();
			if (texttag) {
				$('#'+knowtagId).val(texttag + ',' + $(this).text());
			} else {
				$('#'+knowtagId).val($(this).text());
			}
		});
	}, 'json')
}

/** 用post方式打开一个新的页面
 * openBlank('/member/succeed.html',{id:'6',describe:'添加控制器, 包括前台与后台',money:$('.money:first').text()});
 * @param action
 * @param data
 * @param isNewWin
 */
function openNewPageByPost(action, data, isNewWin) {
	var form = $("<form/>").attr('action', action).attr('method', 'post');
	if (isNewWin) {
		form.attr('target', '_blank');
	}
	var input = '';
	$.each(data, function(i, n) {
		input += '<input type="hidden" name="' + i + '" value="' + n + '" />';
	});
	form.append(input).appendTo("body").css('display', 'none').submit();
}

//创建一条关联资源文件
function creatRelationWebFile() {
	var type = $('#knowtypeId').val();
	var group = $('#docgroupId').val();
	var title = "资源知识:"+$('#knowtitleId').val();
	var tag = $('#knowtagId').val();
	var reTag = /<(?:.|\s)*?>/g;
	var text = getEditorcontentText().replace(reTag,"");
	//alert("type"+type);alert("group"+group);alert("title"+title);alert("tag"+tag);alert("text"+text);
	//标题、分类、标签、摘要带过来String typeid, String groupid, String knowtitle, String knowtag, String text
	//需要审核的分类不能创建关联资源&&关联资源不能由一个创建页面创建多个资源知识
	openNewPageByPost("webfile/add.do", {
		typeid : type,
		groupid : group,
		knowtitle : title,
		knowtag : tag,
		'text' : text,
		flag : 'RELATION'
	}, true);
	loadRemoteRelationdoc();
}
//抓取远程已经创建的关联资源知识
function loadRemoteRelationdoc() {
	$.post('home/carrySession.do', {
		attrName : 'RELATION_DOCID'
	}, function(flag) {
		if(flag.val){
			userRelation(flag.val.id, flag.val.title);
		}else{
			window.setTimeout(loadRemoteRelationdoc, 3000);
		}
	}, "json");
}
