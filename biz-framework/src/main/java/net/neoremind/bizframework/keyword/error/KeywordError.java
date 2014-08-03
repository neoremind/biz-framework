package net.neoremind.bizframework.keyword.error;

import net.neoremind.bizframework.keyword.commons.KeywordColumns;


/**
 * 保存错误信息到message.properties的映射，用于非hibernate validation框架的自定义验证
 * 
 * @author zhangxu04
 *
 */
public enum KeywordError {
	
	KEYWORD_NUM_EXCEED(KeywordColumns.KeywordBoColumns.KEYWORD, "com.baidu.beidou.keyword.num.exceed"),
	KEYWORD_NOT_EXIST(KeywordColumns.KeywordBoColumns.KEYWORD, "com.baidu.beidou.keyword.notexist"),
	;
	
	public String property = null;
	public String message = null;

	private KeywordError(String property, String message) {
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
