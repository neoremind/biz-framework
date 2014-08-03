package net.neoremind.bizframework.commons.localcache;

import java.util.concurrent.Callable;

public class ContextCache<K, V> {

	public V get(K key, Callable<V> valueLoader) throws Exception {
		V res = ThreadContext.getContext(key.toString());
		if (res == null) {
			res = valueLoader.call();
			ThreadContext.putContext(key.toString(), res);
		} else {
			System.out.println("命中cache, key=" + key);
		}
		return res;
	}

}
