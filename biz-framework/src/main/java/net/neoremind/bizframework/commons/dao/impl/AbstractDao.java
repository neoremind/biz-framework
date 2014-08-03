package net.neoremind.bizframework.commons.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.neoremind.bizframework.commons.dao.BaseDao;

public abstract class AbstractDao<KEY extends Serializable, ENTITY> implements BaseDao<KEY, ENTITY> {

	protected Map<KEY, ENTITY> entityMap = new HashMap<KEY, ENTITY>();
	
	protected List<ENTITY> entityList = new ArrayList<ENTITY>();
	
	/**
	 * query
	 */
	public ENTITY get(KEY id) {
		return entityMap.get(id);
	}
	
	/**
	 * query
	 */
	public List<ENTITY> getByIds(List<KEY> ids) {
		List<ENTITY> result = new ArrayList<ENTITY>();
		for (KEY id : ids) {
			if (entityMap.containsKey(id)) {
				result.add(entityMap.get(id));
			}
		}
		return result;
	}
	
	/**
	 * query
	 */
	public Map<KEY, ENTITY> getMapByIds(List<KEY> ids) {
		Map<KEY, ENTITY> result = new HashMap<KEY, ENTITY>();
		for (KEY id : ids) {
			if (entityMap.containsKey(id)) {
				result.put(id, entityMap.get(id));
			}
		}
		return result;
	}

	public List<ENTITY> getAll() {
		return entityList;
	}
	
	/**
	 * delete
	 */
	public boolean delete(KEY id) {
		if (!entityMap.containsKey(id)) {
			throw new RuntimeException("Id not exist");
		}
		entityList.remove(entityMap.get(id));
		entityMap.remove(id);
		return true;
	}
	
}
