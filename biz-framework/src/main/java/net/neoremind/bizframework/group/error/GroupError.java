package net.neoremind.bizframework.group.error;

import net.neoremind.bizframework.group.commons.GroupColumns;

/**
 * 保存错误信息到message.properties的映射，用于非hibernate validation框架的自定义验证
 * 
 * @author zhangxu04
 *
 */
public enum GroupError {
	
	GROUP_STATE_INVALID(GroupColumns.GroupBoColumns.GROUP_STATE, "com.baidu.beidou.group.state"),
	GROUP_TARGET_TYPE_INVALID(GroupColumns.GroupBoColumns.TARGET_TYPE, "com.baidu.beidou.group.targettype"),
	GROUP_NOT_FOUND(GroupColumns.GroupBoColumns.GROUP_ID, "com.baidu.beidou.group.notfound"),
	;
	
	public String property = null;
	public String message = null;

	private GroupError(String property, String message) {
		this.property = property;
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	public String getProperty() {
		return property;
	}
	
}
