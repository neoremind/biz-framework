package net.neoremind.bizframework.commons.utils;

import java.util.ArrayList;
import java.util.List;

public class EnumUtil {
	
	@SuppressWarnings("unchecked")
	public static <T extends Enum<T>, E> List<E> listValue(Class<T> enumClass) {
        T[] types = enumClass.getEnumConstants();
        List<E> result = new ArrayList<E>();
        for (T t : types) {
            try {
            	result.add((E)(t.getClass().getDeclaredField("value").get(t)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

	public static <T extends Enum<T>> T valueOf(Class<T> enumClass, Object value) {
		T[] types = enumClass.getEnumConstants();
		for (T t : types) {
			try {
				if (t.getClass().getDeclaredField("value").get(t).equals(value))
					return t;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.err.println("undefined enum type: " + enumClass.getName()
				+ "[value=" + value + "]");
		return null;
	}

	public static <T extends Enum<T>> List<T> list(Class<T> enumClass,
			T... excludes) {
		List<T> list = new ArrayList<T>();
		T[] types = enumClass.getEnumConstants();
		out: for (T t : types) {
			for (T e : excludes) {
				if (t == e) {
					continue out;
				}
			}
			list.add(t);
		}
		return list;
	}

	public static <T extends Enum<T>> List<T> except(T exclude, T... others) {
		List<T> list = new ArrayList<T>();
		T[] types = exclude.getDeclaringClass().getEnumConstants();
		out: for (T t : types) {
			if (t != exclude) {
				for (T e : others) {
					if (t == e) {
						continue out;
					}
				}
				list.add(t);
			}
		}
		return list;
	}
	
}
