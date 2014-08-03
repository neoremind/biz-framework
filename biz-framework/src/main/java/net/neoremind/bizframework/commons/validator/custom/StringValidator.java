package net.neoremind.bizframework.commons.validator.custom;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

public class StringValidator implements ConstraintValidator<ValidText, Object> {

	private String regex;
	private boolean allowEmpty;
	private int max;
	private int min;
	private String message;

	@Override
	public void initialize(ValidText constraintAnnotation) {
		this.regex = constraintAnnotation.regex();
		this.allowEmpty = constraintAnnotation.allowEmpty();
		this.max = constraintAnnotation.max();
		this.min = constraintAnnotation.min();
		this.message = constraintAnnotation.message();
	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value instanceof String) {
			return validateString(value);
		} else if (value instanceof List) {
			List list = (List) value;
			for (int i = 0; i < list.size(); i++) {
				if (!validateString(list.get(i))) {
					context.disableDefaultConstraintViolation();
					context.buildConstraintViolationWithTemplate(message).addBeanNode()
	                 .inIterable().atIndex(i).addConstraintViolation();
					return false;
				}
			}
		}
		return true;
	}

	private boolean validateString(Object value) {
		if (value == null || StringUtils.isEmpty(value.toString())) {
			return allowEmpty;
		}
		String str = String.valueOf(value).trim();
		//if (str.length() > max || str.length() < min) {
		if (!validGbkStr(str, true, min, max)) {
			return false;
		}
		if (StringUtils.isNotEmpty(regex) && !str.matches(regex)) {
			return false;
		}
		return true;
	}

	/**
	 * 验证：[中文a-zA-Z0-9\\-_]*
	 * @param input
	 * @param checkGbk
	 * @param minLength
	 * @param maxLength
	 * @return
	 */
	public static boolean validGbkStr(String input,
			final boolean checkGbk, final int minLength, final int maxLength) {

		// 对长度有个预先判断
		if ((minLength > -1) && (input.length() < minLength)) {
			return false;
		} else if ((maxLength > -1) && (input.length() > maxLength)) {
			return false;
		}

		// 验证字符合法性和特殊长度要求
		char[] ch = input.toCharArray();

		int length = 0;

		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isNeedAlph(c)) {
				length += 1;
			} else if (isTraditionalChineseCharacter(c, checkGbk)) {
				length += 2;
			} else {
				return false;// 是否为合法字符集
			}
		}

		if ((minLength > -1) && (length < minLength)) {
			return false;
		} else if ((maxLength > -1) && (length > maxLength)) {
			return false;
		}

		return true;
	}

	private static boolean isNeedAlph(char c) {
		if (c >= 'a' && c <= 'z') {
			return true;
		} else if (c >= 'A' && c <= 'Z') {
			return true;
		} else if (c >= '0' && c <= '9') {
			return true;
		} else if (c == '-') {
			return true;
		} else if (c == '_') {
			return true;
		}

		return false;
	}

	public static boolean isTraditionalChineseCharacter(char c,
			boolean checkGbk) {
		Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
		if (!Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS.equals(block)
				&& !Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
						.equals(block)
				&& !Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
						.equals(block)) {
			return false;
		}
		if (checkGbk) {
			try {
				String s = "" + c;
				return s.equals(new String(s.getBytes("GBK"), "GBK"));
			} catch (java.io.UnsupportedEncodingException e) {
				return false;
			}
		}

		return true;
	}

	public static boolean isLatinCharacter(char c) {
		Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
		if (!Character.UnicodeBlock.BASIC_LATIN.equals(block)) {
			return false;
		}

		return true;
	}

}
