package net.neoremind.bizframework.commons.validator.custom;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValueInSetValidator implements
		ConstraintValidator<ValidValueIn, Object> {

	private ValidValueIn constraintAnnotation;

	@Override
	public void initialize(ValidValueIn constraintAnnotation) {
		this.constraintAnnotation = constraintAnnotation;
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value == null) {
			return constraintAnnotation.nullable();
		}
		List list = Arrays.asList(constraintAnnotation.value());
		if (value instanceof Iterable) {
			Iterable iterable = (Iterable) value;
			for (Object o : iterable) {
				if (o == null) {
					return constraintAnnotation.nullable();
				}
				if (!list.contains(o))
					return false;
			}
			return true;
		}
		return list.contains(value.toString());
	}
}