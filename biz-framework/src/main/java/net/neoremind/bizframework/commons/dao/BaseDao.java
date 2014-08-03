/**
 * adx-common#com.baidu.ub.generic.dao.BaseDao.java
 * 下午6:50:08 created by Darwin(Tianxin)
 */
package net.neoremind.bizframework.commons.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * DAO的定义
 * 
 * @author Darwin(Tianxin)
 */
public interface BaseDao<KEY extends Serializable, ENTITY> {

	/**
	 * 根据主键获取对象
	 * @param id
	 * @return
	 */
	ENTITY get(KEY id);
	
	/**
	 * 根据主键集合获取结果列表
	 * @param keys
	 * @return
	 */
	List<ENTITY> getByIds(List<KEY> keys);
	
	Map<KEY, ENTITY> getMapByIds(List<KEY> ids);
	
	/**
	 * 保存对象
	 * @param entity
	 * @return
	 */
	ENTITY create(ENTITY entity);
	
	/**
	 * 保存多个对象
	 * @param entities
	 * @return
	 */
	List<ENTITY> create(List<ENTITY> entities);
	
	/**
	 * 修改一个对象
	 * @param entity
	 * @return
	 */
	boolean update(ENTITY entity);
	
	/**
	 * 修改一个对象列表
	 * @param entities
	 * @return
	 */
	int update(List<ENTITY> entities);
	
	/**
	 * 获取全部对象
	 * @return
	 * 下午4:30:53 created by Darwin(Tianxin)
	 */
	//List<ENTITY> findAll();
	
	/**
	 * 删除一个对象
	 * @param id
	 * @return
	 */
	//boolean delete(ENTITY entity);
	
	/**
	 * 根据条件删除多个对象
	 * @param ids
	 * @return
	 */
	//int delete(Collection<ENTITY> entities);
	
	/**
	 * 删除一个对象
	 * @param id
	 * @return
	 */
	boolean delete(KEY id);
	
	/**
	 * 根据条件删除多个对象
	 * @param ids
	 * @return
	 */
	//int delete(List<KEY> ids);
}

