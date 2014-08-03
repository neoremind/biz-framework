package net.neoremind.bizframework.keyword.dao;

import java.util.List;

import net.neoremind.bizframework.commons.dao.BaseDao;
import net.neoremind.bizframework.keyword.entity.Keyword;

public interface KeywordDao extends BaseDao<Integer, Keyword> {

	List<Keyword> findByGroupId(Integer groupId);
	
}
