package net.neoremind.bizframework.commons.mgr;

import java.util.List;

import net.neoremind.bizframework.commons.sterotype.BizResult;

/**
 * 
 * 封装WEB/API暴露接口的Interface
 * 
 * <p>泛型中的T代表前端传入的VO类型，V为要存储到DB的VO/PO等类型，KEY为该类的数据库主键类型
 *
 * @author <a href="mailto:zhangxu04@baidu.com">Zhang Xu</a>
 * @version 2013-4-12 下午12:41:43
 */
public interface BizMgr<T, V, KEY> {

	/**
	 * 严格添加，失败即退出，不保存通过验证的对象
	 * @param bizObjects
	 * @return
	 */
	BizResult<V> addStrictValid(List<T> bizObjects);

	/**
	 * 宽松添加，保存通过验证的对象
	 * @param bizObjects
	 * @return
	 */
	BizResult<V> addIngoreError(List<T> bizObjects);

	/**
	 * 严格覆盖，失败即退出，不保存通过验证的对象
	 * @param bizObjects
	 * @return
	 */
	BizResult<V> overrideAddStrictValid(List<T> bizObjects);

	/**
	 * 宽松覆盖，保存通过验证的对象
	 * @param bizObjects
	 * @return
	 */
	BizResult<V> overrideAddIngoreError(List<T> bizObjects);

	/**
	 * 严格更新，失败即退出，不保存通过验证的对象
	 * @param bizObjects
	 * @return
	 */
	BizResult<V> updateStrictValid(List<T> bizObjects);

	/**
	 * 宽松更新，保存通过验证的对象
	 * @param bizObjects
	 * @return
	 */
	BizResult<V> updateIngoreError(List<T> bizObjects);

	/**
	 * 严格删除，失败即退出，不保存通过验证的对象
	 * @param bizObjects
	 * @return
	 */
	BizResult<V> deleteStrictValid(List<T> bizObjects);

	/**
	 * 宽松删除，保存通过验证的对象
	 * @param bizObjects
	 * @return
	 */
	BizResult<V> deleteIngoreError(List<T> bizObjects);

}
