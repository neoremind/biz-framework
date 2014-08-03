package net.neoremind.bizframework.commons.localcache;

@SuppressWarnings("rawtypes")
public class ContextCacheBuilder {
	
	private static ContextCache cache = null;

	@SuppressWarnings("unchecked")
	public static <K, V> ContextCache<K, V> build() {
		if (cache == null) {
			synchronized (ContextCacheBuilder.class) {
				if (cache == null)
					cache = new ContextCache();
			}
		}
		return cache;
	}

}
