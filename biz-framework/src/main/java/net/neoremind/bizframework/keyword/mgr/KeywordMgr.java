package net.neoremind.bizframework.keyword.mgr;

import java.util.List;

import net.neoremind.bizframework.commons.mgr.BizMgr;
import net.neoremind.bizframework.keyword.bo.KeywordBo;
import net.neoremind.bizframework.keyword.entity.Keyword;
import net.neoremind.bizframework.keyword.model.KeywordModel;

public interface KeywordMgr extends BizMgr<KeywordBo, KeywordModel, Integer> {

	List<Keyword> findByGroupId(Integer groupId);
	
	int countByGroupId(Integer groupId);
	
}
