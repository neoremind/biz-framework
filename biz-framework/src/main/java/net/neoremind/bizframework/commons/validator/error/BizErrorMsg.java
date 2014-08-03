package net.neoremind.bizframework.commons.validator.error;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.neoremind.bizframework.commons.validator.constant.ErrorConstant;

public class BizErrorMsg {

	private final static Logger LOG = LoggerFactory.getLogger(BizErrorMsg.class);

	// 错误码
	private int code = ErrorConstant.OK_CODE;
	
	private String message;

	public static BizErrorMsg build(String rawMsg) {
		if (StringUtils.isEmpty(rawMsg)) {
			return new BizErrorMsg(ErrorConstant.DEAULT_ERROR_CODE, rawMsg);
		}
		String[] arrayMsg = StringUtils.split(rawMsg, ErrorConstant.ERROR_MSG_SPLITTER);
		if (arrayMsg.length != 2) {
			//LOG.error("Message cannot spilt into code and message - " + rawMsg);
			return new BizErrorMsg(ErrorConstant.DEAULT_ERROR_CODE, rawMsg);
		}
		try {
			int code = Integer.parseInt(arrayMsg[0]);
			if (StringUtils.isEmpty(arrayMsg[1])) {
				LOG.error("Message is empty - " + rawMsg);
			}
			return new BizErrorMsg(code, arrayMsg[1].trim());
		} catch (Exception e) {
			LOG.error("Message code cannot be resovled - " + rawMsg);
			return new BizErrorMsg(ErrorConstant.DEAULT_ERROR_CODE, rawMsg);
		}
	}

	public BizErrorMsg(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public BizErrorMsg() {
		super();
	}

	public BizErrorMsg build() {
		return new BizErrorMsg();
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

}
