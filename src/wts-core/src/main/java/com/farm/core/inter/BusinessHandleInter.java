package com.farm.core.inter;

import com.farm.core.inter.domain.BusinessHandler;

/**
 * 具体任务的接口,在BusinessHandleServer和具体业务接口间传递方法（加载系统自定义的注入对象...）
 * 
 * @author wangdong
 *
 */
public interface BusinessHandleInter {
	public void execute(BusinessHandler impl);
}
