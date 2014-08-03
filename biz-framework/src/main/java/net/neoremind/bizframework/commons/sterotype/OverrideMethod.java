package net.neoremind.bizframework.commons.sterotype;

/**
 * 
 * 覆盖策略枚举
 *
 * @author <a href="mailto:zhangxu04@baidu.com">Zhang Xu</a>
 * @version 2013-4-12 下午12:43:18
 */
public enum OverrideMethod {
	OVERRIDE,
	NOT_OVERRIDE,
	DEFAULT  //更新、删除等操作可以设置为默认
}
