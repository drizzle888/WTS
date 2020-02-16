package com.farm.core.page;

/**
 * 页面类型
 * 
 * @author wangdong
 * 
 */
public enum OperateType {
	/**
	 * 展示
	 */
	SHOW(0), /**
	 * 新增
	 */
	ADD(1), /**
	 * 修改
	 */
	UPDATE(2),
	/**
	 * 删除
	 */
	DEL(4),
	/**
	 * 其他
	 */
	OTHER(3);
	public int value;

	private OperateType(int var) {
		value = var;
	}

	/**根据数值获得页面类型枚举对象
	 * @param type
	 * @return
	 */
	public static OperateType getEnum(int type) {
		if (type == 0)
			return OperateType.SHOW;
		if (type == 1)
			return OperateType.ADD;
		if (type == 2)
			return OperateType.UPDATE;
		return null;
	}
}
