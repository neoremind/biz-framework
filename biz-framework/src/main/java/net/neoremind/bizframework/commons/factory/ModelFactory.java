package net.neoremind.bizframework.commons.factory;

import java.util.List;

import net.neoremind.bizframework.commons.model.BizModel;

/**
 * 经过验证后的数据，构造成业务model工厂类
 * 
 * @author zhangxu04
 *
 */
public interface ModelFactory<KEY, T, V> {

	/**
	 * 业务对象转化为Model，为添加方法使用
	 * @param bizObjects
	 * @return
	 */
	BizModel<KEY, V> createModel4Add(List<T> bizObjects);
	
	/**
	 * 业务对象转化为Model，为覆盖方法使用
	 * 
	 * <pre>
	 * |<---------- bizObjects -------->| 
	 *                           |<----[data in db]--->|
	 * ------------------------------------------------
	 * |                  |             |              |
	 * | [not valid list] | toAddList   |  toDelIds    |      
	 * |                  |             |              |
	 * -------------------------------------------------      
	 * </pre>
	 * 
	 * @param bizObjects
	 * @return
	 */
	BizModel<KEY, V> createModel4OverrideAdd(List<T> bizObjects);

	/**
	 * 业务对象转化为Model，为更新方法使用
	 * @param bizObjects
	 * @return
	 */
	BizModel<KEY, V> createModel4Update(List<T> bizObjects);

	/**
	 * 业务对象转化为Model，为删除方法使用
	 * @param bizObjects
	 * @return
	 */
	BizModel<KEY, V> createModel4Del(List<T> bizObjects);

}
