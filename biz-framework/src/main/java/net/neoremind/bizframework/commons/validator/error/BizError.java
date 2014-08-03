package net.neoremind.bizframework.commons.validator.error;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class BizError {

	// 错误所在list中的索引位置
	private int index = 0;

	// 错误所在类中的变量名称
	private String field = "default";

	// 错误码
	private int code;

	// 错误信息
	private String message;
	
	// 错误信息
	private Object invalidValue;

	public BizError(int index, String field, int code, String message, Object invalidValue) {
		super();
		this.index = index;
		this.field = field;
		this.code = code;
		this.message = message;
		this.invalidValue = invalidValue;
	}

	public BizError(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("index", index).append("field", field)
				.append("code", code).append("message", message).append("invaldValue", invalidValue).toString();
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object getInvalidValue() {
		return invalidValue;
	}

	public void setInvalidValue(Object invalidValue) {
		this.invalidValue = invalidValue;
	}

}
