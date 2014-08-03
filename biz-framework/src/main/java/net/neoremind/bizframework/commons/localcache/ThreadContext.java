package net.neoremind.bizframework.commons.localcache;

import java.util.HashMap;
import java.util.Map;

/**
 * 线程执行的上下文内容
 * 
 */
public class ThreadContext {

    /**
     * 线程上下文变量的持有者
     */
    private final static ThreadLocal<Map<String, Object>> CTX_HOLDER = new ThreadLocal<Map<String, Object>>();

//    static {
//        CTX_HOLDER.set(new HashMap<String, Object>());
//    }

    /**
     * 添加内容到线程上下文中
     * 
     * @param key
     * @param value
     */
    public final static void putContext(String key, Object value) {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx == null) {
        	ctx = new HashMap<String, Object>();
        	CTX_HOLDER.set(ctx);
        }
        ctx.put(key, value);
    }

    /**
     * 从线程上下文中获取内容
     * 
     * @param key
     */
    @SuppressWarnings("unchecked")
    public final static <T extends Object> T getContext(String key) {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx == null) {
            return null;
        }
        return (T) ctx.get(key);
    }
    
    /**
     * 获取线程上下文
     * 
     * @param key
     */
    public final static Map<String, Object> getContext() {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx == null) {
            return null;
        }
        return ctx;
    }

    /**
     * 删除上下文中的key
     * 
     * @param key
     */
    public final static void remove(String key) {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx != null) {
            ctx.remove(key);
        }
    }

    /**
     * 上下文中是否包含此key
     * 
     * @param key
     * @return
     */
    public final static boolean contains(String key) {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx != null) {
            return ctx.containsKey(key);
        }
        return false;
    }

    /**
     * 存储线程上下文中的数据库dbkey
     * 
     * @param key
     */
    public final static void putDBKEY(String dbName, String dbKey) {
    	putContext(dbName, dbKey);
    	return;
    }
    /**
     * 获取线程上下文中的数据库dbkey
     * 
     * @param key
     */
    public final static String getDBKEY(String dbName ) {
    	return getContext(dbName);
    }

    /**
     * 删除上下文中的dbkey
     * 
     * @param key
     */
    public final static void clearDBKEY(String dbName) {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx != null) {
            ctx.remove(dbName);
        }
    }
    
    
    /**
     * 清空线程上下文
     */
    public final static void clean() {
        CTX_HOLDER.set(null);
    }

    /**
     * 初始化线程上下文
     */
    public final static void init() {
        CTX_HOLDER.set(new HashMap<String, Object>());
    }


    /**
     * 获取当前用户的userId
     * 
     * @return
     */
    public final static Integer getUserId() {
        return getContext(USERID);
    }

    /**
     * 把当前用户的userId存储到线程上下文中
     * 
     * @param userId
     */
    public final static void putUserId(Integer userId) {
        putContext(USERID, userId);
    }

    private final static String USERID = "userId";
    
}
