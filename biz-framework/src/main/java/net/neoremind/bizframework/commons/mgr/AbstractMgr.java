package net.neoremind.bizframework.commons.mgr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.neoremind.bizframework.commons.exception.OperationNotSupportException;
import net.neoremind.bizframework.commons.factory.ModelFactory;
import net.neoremind.bizframework.commons.model.BizModel;
import net.neoremind.bizframework.commons.repository.ModelRepository;
import net.neoremind.bizframework.commons.sterotype.BizResult;
import net.neoremind.bizframework.commons.sterotype.ExecuteType;
import net.neoremind.bizframework.commons.sterotype.FailStrategy;
import net.neoremind.bizframework.commons.sterotype.OverrideMethod;
import net.neoremind.bizframework.commons.validator.Validator;
import net.neoremind.bizframework.commons.validator.constant.ErrorConstant;
import net.neoremind.bizframework.commons.validator.error.BizError;
import com.google.common.collect.Lists;

/**
 * 
 * 封装WEB/API暴露接口抽象类
 * 
 * <p>凡是要提供WEB/API功能的接口都需要继承此抽象类，实现BizMgr接口。
 * <br>该类作为一个模板，将具体的验证、组装model以及数据库操作方法延迟到具体的service来实现。
 * 
 * <p>泛型中的T代表前端传入的VO类型，V为要存储到DB的VO/PO等类型，KEY为该类的数据库主键类型
 *
 * @author <a href="mailto:zhangxu04@baidu.com">Zhang Xu</a>
 * @version 2013-4-12 上午11:58:29
 * @see BizMgr
 */
public class AbstractMgr<T, V, KEY> implements BizMgr<T, V, KEY> {

	private final static Logger LOG = LoggerFactory.getLogger(AbstractMgr.class); 
	
	protected Validator<T> validator;
	
	protected ModelFactory<KEY, T, V> modelFactory;
	
	protected ModelRepository<KEY, V> modelRepository;
	
	/**
	 * 
	 * 执行验证和访问数据库操作
	 *
	 * @param targetList 前端传入的VO列表 
	 * @param executeType 执行的类型，添加、删除或者更新
	 * @param errorDealMethod 遇到错误的处理方式，退出或者允许部分成功
	 * @param overrideMethod 是否需要覆盖操作，如果覆盖则删除targetList中不存在，而DB中也不存在的数据
	 * @return ResponseDTO<E> 
	 * @see ExecuteType
	 * @see FailStrategy
	 * @see OverrideMethod
	 */
	protected BizResult<V> execute(List<T> bizObjects, ExecuteType executeType, FailStrategy errorDealMethod, OverrideMethod overrideMethod) {
		
		// 检查基本参数
		if (executeType != ExecuteType.ADD && executeType != ExecuteType.UPDATE && executeType != ExecuteType.DELETE) {
			throw new IllegalArgumentException("ExecuteType Param Error");
		}
		if (errorDealMethod != FailStrategy.FAIL_FAST && errorDealMethod != FailStrategy.FAIL_CONTINUE) {
			throw new IllegalArgumentException("ErrorDealMethod Param Error");
		}
		if (overrideMethod != OverrideMethod.OVERRIDE && overrideMethod != OverrideMethod.NOT_OVERRIDE && overrideMethod != OverrideMethod.DEFAULT) {
			throw new IllegalArgumentException("OverrideMethod Param Error");
		}
		
		// 新建返回结果
		BizResult<V> result = BizResult.build();
				
		// 目标对象list为空则返回
		if (CollectionUtils.isEmpty(bizObjects)) {
			return result;
		}
		
		// 验证
		try {
			Map<Integer, BizError> errorMap = null;
			switch (executeType) {
			case ADD:
				errorMap = validator.validateAdd(bizObjects); 
				break;
			case UPDATE:
				errorMap = validator.validateUpdate(bizObjects); 
				break;
			case DELETE:
				errorMap = validator.validateDel(bizObjects); 
				break;
			}
			
			// 填充错误信息
			if (errorMap != null && !errorMap.isEmpty()) {
				 // 如果ErrorDealMethod为遇元素错误退出，则退出，否则继续
				if (errorDealMethod == FailStrategy.FAIL_FAST) {
					return fillFailFastError(errorMap);
				}
				
				fillError(result, errorMap);
			}
			
			// 构建经过校验合法的参数对象Map
			List<T> validBizList = createValidBizList(bizObjects, errorMap.keySet());
			
			// 调用模型repository保存
			BizModel<KEY, V> bizModel;
			List<V> executeResult = Lists.newArrayList();
			switch (executeType) {
			case ADD:
				if (overrideMethod == OverrideMethod.OVERRIDE) {
					bizModel = modelFactory.createModel4OverrideAdd(validBizList); 
					executeResult = modelRepository.overrideAdd(bizModel.getToAddModel(), bizModel.getToDelModel());
					break;
				} else {
					bizModel = modelFactory.createModel4Add(validBizList); 
					executeResult = modelRepository.add(bizModel.getToAddModel());
					break;
				}
			case UPDATE:
				bizModel = modelFactory.createModel4Update(validBizList); 
				executeResult = modelRepository.update(bizModel.getToUpdateModel());
				break;
			case DELETE:
				bizModel = modelFactory.createModel4Del(validBizList); 
				executeResult = modelRepository.del(bizModel.getToDelModel());
				break;
			}
			
			// 填充结果
			result.addAllData(executeResult);

		} catch (OperationNotSupportException e) {
			result.addError(new BizError(ErrorConstant.DEAULT_ERROR_CODE, e.getMessage()));
		} catch (Exception e) {
			new BizError(ErrorConstant.DEAULT_ERROR_CODE, e.getMessage());
			LOG.error("Exception occurrs " + e, e);
		}

		return result;
	}

	/**
	 * 添加，不允许错误的出现
	 */
	public BizResult<V> addStrictValid(List<T> bizObjects) {
		return execute(bizObjects, ExecuteType.ADD, FailStrategy.FAIL_FAST, OverrideMethod.NOT_OVERRIDE);
	}
	
	/**
	 * 添加，允许错误的出现，即接收部分成功
	 */
	public BizResult<V> addIngoreError(List<T> bizObjects) {
		return execute(bizObjects, ExecuteType.ADD, FailStrategy.FAIL_CONTINUE, OverrideMethod.NOT_OVERRIDE);
	}
	
	/**
	 * 添加，不允许错误的出现，并且覆盖
	 */
	public BizResult<V> overrideAddStrictValid(List<T> bizObjects) {
		return execute(bizObjects, ExecuteType.ADD, FailStrategy.FAIL_FAST, OverrideMethod.OVERRIDE);
	}
	
	/**
	 * 添加，允许错误的出现，即接收部分成功，并且覆盖
	 */
	public BizResult<V> overrideAddIngoreError(List<T> bizObjects) {
		return execute(bizObjects, ExecuteType.ADD, FailStrategy.FAIL_CONTINUE, OverrideMethod.OVERRIDE);
	}

	/**
	 * 更新，不允许错误的出现
	 */
	public BizResult<V> updateStrictValid(List<T> bizObjects) {
		return execute(bizObjects, ExecuteType.UPDATE, FailStrategy.FAIL_FAST, OverrideMethod.DEFAULT);
	}
	
	/**
	 * 更新，允许错误的出现
	 */
	public BizResult<V> updateIngoreError(List<T> bizObjects) {
		return execute(bizObjects, ExecuteType.UPDATE, FailStrategy.FAIL_CONTINUE, OverrideMethod.DEFAULT);
	}

	/**
	 * 删除，允许错误的出现
	 */
	public BizResult<V> deleteStrictValid(List<T> bizObjects) {
		return execute(bizObjects, ExecuteType.DELETE, FailStrategy.FAIL_FAST, OverrideMethod.DEFAULT);
	}
	
	/**
	 * 删除，不允许错误的出现
	 */
	public BizResult<V> deleteIngoreError(List<T> bizObjects) {
		return execute(bizObjects, ExecuteType.DELETE, FailStrategy.FAIL_CONTINUE, OverrideMethod.DEFAULT);
	}
	
	/**
	 * 构建业务合法的正确输入对象Map
	 * @param size
	 * @return
	 */
	private List<T> createValidBizList(List<T> bizObjects, Set<Integer> errorIndexSet) {
		List<T> validBizList = new ArrayList<T>(bizObjects.size());
		for (int i = 0; i < bizObjects.size(); i++) {
			if (errorIndexSet.contains(i)) {
				continue;
			}
			validBizList.add(bizObjects.get(i));
		}
		return validBizList;
	}
	
	/**
	 * 填充验证后的错误信息到结果中
	 * @param errorMap
	 * @throws IllegalAccessException
	 */
	private BizResult<V> fillFailFastError(Map<Integer, BizError> errorMap) throws IllegalAccessException {
		return fillError(BizResult.<V>build(), errorMap);
	}
	
	/**
	 * 填充验证后的错误信息到结果中
	 * @param errorMap
	 * @throws IllegalAccessException
	 */
	private BizResult<V> fillError(BizResult<V> result, Map<Integer, BizError> errorMap) throws IllegalAccessException {
		if (errorMap == null || errorMap.isEmpty()) {
			throw new IllegalAccessException("Result map or errro map should not be empty!");
		}
		for (Map.Entry<Integer, BizError> error : errorMap.entrySet()) {
			result.addError(error.getValue());
		}
		return result;
	}
	
}
