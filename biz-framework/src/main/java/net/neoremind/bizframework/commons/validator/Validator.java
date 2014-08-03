package net.neoremind.bizframework.commons.validator;

import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import net.neoremind.bizframework.commons.validator.error.BizError;

/**
 * 验证器
 * 
 * @author zhangxu04
 * 
 */
public interface Validator<T> {

	/**
	 * 验证添加对象
	 * @param bizObjects
	 * @return
	 */
	Map<Integer, BizError> validateAdd(List<T> bizObjects) throws OperationNotSupportedException;
	
	/**
	 * 验证更新对象
	 * @param bizObjects
	 * @return
	 */
	Map<Integer, BizError> validateUpdate(List<T> bizObjects) throws OperationNotSupportedException;
	
	/**
	 * 验证删除对象
	 * @param bizObjects
	 * @return
	 */
	Map<Integer, BizError> validateDel(List<T> bizObjects) throws OperationNotSupportedException;

}
