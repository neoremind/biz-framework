package net.neoremind.bizframework.commons.repository;

import java.util.List;

/**
 * 根据model，调用DAO保存的接口
 * 
 * @author zhangxu04
 * 
 */
public interface ModelRepository<KEY, V> {

	/**
	 * 添加
	 * @param groupModels key为保存对象的索引index位置，V为model
	 * @return
	 */
	List<V> add(List<V> toAddBizModel);
	
	/**
	 * 覆盖
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
	 * @param toAddBizModel key为保存对象的索引index位置，V为model
	 * @param toDelIds 待删除对象的主键
	 * @return
	 */
	List<V> overrideAdd(List<V> toAddBizModel, List<KEY> toDelIds);

	/**
	 * 更新
	 * @param groupModels key为保存对象的索引index位置，V为model
	 * @return
	 */
	List<V> update(List<V> toUpdateBizModel);

	/**
	 * 删除
	 * @param ids 
	 * @return
	 */
	List<V> del(List<KEY> ids);

}
