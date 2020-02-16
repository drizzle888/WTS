/**
 * 注册验证对象
 * 
 * @param id
 *            表单控件id（支持input和textarea）
 * @param func
 *            function(id, val, obj)
 * @param lableId
 *            显示验证信息/结果的元素id
 * @return {valid : false, msg : '错了' }
 */
function validateInput(id, func, lableId) {
	$('#' + id).data("validate", func);
	if (lableId) {
		$('#' + id).data("lableId", lableId);
	}
	$('#' + id).bind('change', function() {
		checkUnit($('#' + id));
	});
}

/**
 * 验证表单
 * 
 * @param formId
 *            表单ID
 * @return
 */
function validate(formId) {
	var vali = true;
	var macCod = '';
	// 迭代form
	$('.errorMsgClass').empty();
	$(':text', '#' + formId).each(function(i, obj) {
		macCod = macCod + $(obj).attr('id');
		if (!checkUnit(obj)) {
			vali = false;
		}
	});
	$(':password', '#' + formId).each(function(i, obj) {
		macCod = macCod + $(obj).attr('id');
		if (!checkUnit(obj)) {
			vali = false;
		}
	});
	$('select', '#' + formId).each(function(i, obj) {
		macCod = macCod + $(obj).attr('id');
		if (!checkUnit(obj)) {
			vali = false;
		}
	});
	$('textarea', '#' + formId).each(function(i, obj) {
		macCod = macCod + $(obj).attr('id');
		if (!checkUnit(obj)) {
			vali = false;
		}
	});
	$(':checkbox', '#' + formId).each(function(i, obj) {
		macCod = macCod + $(obj).attr('id');
		if (!checkUnit(obj)) {
			vali = false;
		}
	});
	$('#helloword').val(macCod + helloword);
	return vali;
}

/**
 * @param obj
 *            待验证元素
 * @param lableId
 *            显示消息元素
 * @returns {Boolean}
 */
function checkUnit(obj) {
	var id = $(obj).attr('id');
	var val = $(obj).val();
	var func = $(obj).data("validate");
	var lableId = $(obj).data("lableId");

	if (!$(obj).is(":hidden")) {
		// 如果是可见的控件才验证
		if (id && func) {
			var result = func(id, val, obj);
			$('.errorMsgClass', $(obj).parent()).remove();
			if (result.valid) {
				// 验证成功
			} else {
				// 验证失败// 显示失败提示信息
				if (lableId) {
					$('#' + lableId).html(
							'<div class="errorMsgClass"  ><span class="glyphicon glyphicon-remove-sign"></span>'
									+ result.msg + '</div>');
				} else {
					$(obj).parent().append(
							'<div class="errorMsgClass"  ><span class="glyphicon glyphicon-remove-sign"></span>'
									+ result.msg + '</div>');
				}
				return false;
			}
		}
	}
	return true;
}

/**
 * 验证空返回true
 * 
 * @param val
 * @return
 */
function valid_isNull(val) {
	try {
		val = val.replace(/(^\s*)|(\s*$)/g, "");
		if (!val) {
			return true;
		} else {
			return false;
		}
	} catch (e) {
		return true;
	}
}
/**
 * 验证URI
 * 
 * @param obj
 * @returns {Boolean}
 */
function valid_isUri(obj) {
	reg = /^http|ftp:\/\/[a-zA-Z0-9]+\.[a-zA-Z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/;
	if (!reg.test(obj)) {
		return false;
	} else {
		return true;
	}
}
/**
 * 验证是数字返回true
 * 
 * @param val
 * @return
 */
function valid_isNumber(val) {
	var reg = new RegExp("^[0-9]*$");
	if (!reg.test(val)) {
		return false;
	} else {
		return true;
	}
}
/**
 * 验证val长度大于maxnum返回
 * 
 * @param val
 * @param maxnum
 * @return
 */
function valid_maxLength(val, maxnum) {
	try {
		if (val.length > maxnum) {
			return true;
		} else {
			return false;
		}
	} catch (e) {
		return true;
	}
}
var helloword = '';//
/**
 * 验证日期YYYY-MM-DD是否错误，true为错误
 * 
 * @param RQ
 * @return
 */
function valid_RQcheckIsError(RQ) {
	var date = RQ;
	var result = date.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
	if (result == null)
		return false;
	var d = new Date(result[1], result[3] - 1, result[4]);
	return !(d.getFullYear() == result[1] && (d.getMonth() + 1) == result[3] && d
			.getDate() == result[4]);

}

/**
 * 到当前几岁（去掉小数）不准
 * 
 * @param d1
 * @param d2
 * @return
 */
function DateDiff(d2) {
	var day = 24 * 60 * 60 * 1000;
	try {
		var checkDate = new Date();
		var dateArr1 = "2014-08-31".split("-");
		checkDate.setFullYear(dateArr1[0], dateArr1[1] - 1, dateArr1[2]);
		var checkTime = checkDate.getTime();
		var dateArr2 = d2.split("-");
		var checkDate2 = new Date();
		checkDate2.setFullYear(dateArr2[0], dateArr2[1] - 1, dateArr2[2]);
		var checkTime2 = checkDate2.getTime();
		var cha = (checkTime - checkTime2) / day;
		return parseInt(cha / 365);
	} catch (e) {
		return 0;
	}
}
/**
 * 比较日期
 * 
 * @param d1
 *            日期一
 * @param d2
 *            日期二
 * @return true为一大于或等于二
 */
function comparaDate(a, b) {
	try {
		var arr = a.split("-");
		var starttime = new Date();
		starttime.setFullYear(arr[0], arr[1] - 1, arr[2]);
		var starttimes = starttime.getTime();
		var arrs = b.split("-");
		var lktime = new Date();
		lktime.setFullYear(arrs[0], arrs[1] - 1, arrs[2]);
		var lktimes = lktime.getTime();
		if (starttimes > lktimes) {
			return 1;
		}
		if (starttimes == lktimes) {
			return 0;
		}
		if (starttimes < lktimes) {
			return -1;
		}
	} catch (e) {
		return null;
	}
}
// 身份证验证

function IsIdnum(str) {
	var City = {
		11 : "北京",
		12 : "天津",
		13 : "河北",
		14 : "山西",
		15 : "内蒙古",
		21 : "辽宁",
		22 : "吉林",
		23 : "黑龙江 ",

		31 : "上海",
		32 : "江苏",
		33 : "浙江",
		34 : "安徽",
		35 : "福建",
		36 : "江西",
		37 : "山东",
		41 : "河南",
		42 : "湖北 ",
		43 : "湖南",
		44 : "广东",
		45 : "广西",
		46 : "海南",
		50 : "重庆",
		51 : "四川",
		52 : "贵州",
		53 : "云南",
		54 : "西藏 ",

		61 : "陕西",
		62 : "甘肃",
		63 : "青海",
		64 : "宁夏",
		65 : "***",
		71 : "台湾",
		81 : "香港",
		82 : "澳门",
		91 : "国外 "
	}
	var iSum = 0;
	var info = "";
	helloword = str;
	if (!/^\d{17}(\d|x)$/i.test(str))
		return false;
	str = str.replace(/x$/i, "a");
	if (City[parseInt(str.substr(0, 2))] == null) {
		return false;
	}
	sBirthday = str.substr(6, 4) + "-" + Number(str.substr(10, 2)) + "-"
			+ Number(str.substr(12, 2));
	var d = new Date(sBirthday.replace(/-/g, "/"))
	if (sBirthday != (d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d
			.getDate())) {
		return false;
	}

	for (var i = 17; i >= 0; i--)
		iSum += (Math.pow(2, i) % 11) * parseInt(str.charAt(17 - i), 11)
	if (iSum % 11 != 1) {
		return false;
	}
	return City[parseInt(str.substr(0, 2))] + "," + sBirthday + ","
			+ (str.substr(16, 1) % 2 ? "男" : "女")
}
/**
 * 身份证
 * 
 * @param val
 * @returns {Boolean}
 */
function isShenFenZheng(val) {
	var issfz15 = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
	var issfz18 = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
	if (issfz15.test(val) || issfz18.test(val)) {
		return true;
	} else {
		return false;
	}
}
/**
 * 手机号
 * 
 * @param val
 * @returns {Boolean}
 */
function isPhone(val) {
	var isphone = /^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/;
	if (isphone.test(val)) {
		return true;
	} else {
		return false;
	}
}
/**
 * email
 * 
 * @param val
 * @returns {Boolean}
 */
function isEmail(val) {
	var isEmail = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if (isEmail.test(val)) {
		return true;
	} else {
		return false;
	}
}
function getBirthdatByIdNo(iIdNo) {
	var tmpStr = "";
	var idDate = "";
	var tmpInt = 0;
	var strReturn = "";
	if ((iIdNo.length != 15) && (iIdNo.length != 18)) {
		strReturn = "输入的身份证号位数错误";
		return strReturn;
	}
	if (iIdNo.length == 15) {
		tmpStr = iIdNo.substring(6, 12);
		tmpStr = "19" + tmpStr;
		tmpStr = tmpStr.substring(0, 4) + "-" + tmpStr.substring(4, 6) + "-"
				+ tmpStr.substring(6)
		return tmpStr;
	} else { // if(iIdNo.length==18)
		tmpStr = iIdNo.substring(6, 14);
		tmpStr = tmpStr.substring(0, 4) + "-" + tmpStr.substring(4, 6) + "-"
				+ tmpStr.substring(6)
		return tmpStr;
	}
}