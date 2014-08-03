package net.neoremind.bizframework.commons.validator;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ResourceBundleMessageSource;

import net.neoremind.bizframework.commons.validator.error.BizError;
import net.neoremind.bizframework.commons.validator.error.BizErrorMsg;

/**
 * 验证器实现类的公共继承父类，内涵hibernate validations框架工具
 * 
 * @author zhangxu04
 *
 */
public class AbstractValidator {
	
	private final static Logger LOG = LoggerFactory.getLogger(AbstractValidator.class); 

	@Resource
	private ResourceBundleMessageSource messageSource;
	
	protected static Validator validator;

	static {
		HibernateValidatorConfiguration configuration = Validation
				.byProvider(HibernateValidator.class)
				.configure()
				.messageInterpolator(
						new ResourceBundleMessageInterpolator(
								new PlatformResourceBundleLocator(
										"error_message")));
		ValidatorFactory factory = configuration.failFast(false)
				.buildValidatorFactory();
		// Validator validator = factory.getValidator();
		// ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	/**
	 * 根据错误code，从message.properties中获取到错误信息
	 * @param code
	 * @return
	 */
	protected String getBizErrMsg(String code) {
		return getBizErrMsg(code, null, "{com.baidu.beidou.syserror}", null);
	}
	
	protected String getBizErrMsg(String code, Object[] args, String defaultMessage, Locale locale) {
		String msg = messageSource.getMessage(code, args, defaultMessage, locale);
		return msg != null ? msg.trim() : msg;
	}
	
	/**
	 * 验证对象
	 * @param bizObj
	 * @param index
	 * @param errors
	 */
	protected <T> void validateBizObj(T bizObj, int index, List<BizError> errors) {
		validateBizObj(bizObj, index, errors, null);
	}
	
	/**
	 * 根据validation group，验证对象
	 * @param bizObj
	 * @param index
	 * @param errors
	 * @param validationGroup
	 */
	@SuppressWarnings("rawtypes")
	protected <T> void validateBizObj(T bizObj, int index, List<BizError> errors, Class validationGroup) {
		Set<ConstraintViolation<T>> constraintViolations = null;
		if (validationGroup == null) {
			constraintViolations = validator.validate(bizObj);
		} else {
			constraintViolations = validator.validate(bizObj, validationGroup);
		}
		//System.out.println(constraintViolations.size());
		ConstraintViolation<T> tmp = null;
		for (Iterator<ConstraintViolation<T>> iter = constraintViolations.iterator() ; iter.hasNext();) {
			tmp = iter.next();
			BizErrorMsg error = BizErrorMsg.build(tmp.getMessage());
			errors.add(new BizError(index, tmp.getPropertyPath().toString(), error.getCode(), error.getMessage(), tmp.getInvalidValue()));
		}
	}
	
	/**
	 * 添加错误信息
	 * @param index 错误的对象索引
	 * @param errors 错误list
	 * @param errorEnumClass 错误枚举
	 * @param errorValue 错误值
	 */
	@SuppressWarnings("rawtypes")
	protected <T> void addBizError(int index, List<BizError> errors, Enum errorEnumClass, T errorValue) { 
		try {
			String property = (String)(errorEnumClass.getClass().getDeclaredField("property").get(errorEnumClass));
	        String message = (String)(errorEnumClass.getClass().getDeclaredField("message").get(errorEnumClass));
	        BizErrorMsg error = BizErrorMsg.build(getBizErrMsg(message));
			errors.add(new BizError(index, property, error.getCode(), error.getMessage(), errorValue));
		} catch (Exception e) {
			LOG.error("Error to resolve enum error type - " + errorEnumClass.getClass().getName(), e);
		}
	}
	
	protected void handleResultError(Map<Integer, BizError> result, int index, List<BizError> errors) {
		// 将错误信息放入返回对象
		if (result != null && !CollectionUtils.isEmpty(errors)) {
			result.put(index, errors.get(0));
		}
	}
	
	protected Map<Integer, BizError> buildValidationResult() {
		return new LinkedHashMap<Integer, BizError>();
	}
	
}
