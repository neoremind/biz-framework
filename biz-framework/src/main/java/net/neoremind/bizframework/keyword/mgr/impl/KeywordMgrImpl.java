package net.neoremind.bizframework.keyword.mgr.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.neoremind.bizframework.commons.factory.ModelFactory;
import net.neoremind.bizframework.commons.mgr.AbstractMgr;
import net.neoremind.bizframework.commons.repository.ModelRepository;
import net.neoremind.bizframework.commons.validator.Validator;
import net.neoremind.bizframework.keyword.bo.KeywordBo;
import net.neoremind.bizframework.keyword.dao.KeywordDao;
import net.neoremind.bizframework.keyword.entity.Keyword;
import net.neoremind.bizframework.keyword.mgr.KeywordMgr;
import net.neoremind.bizframework.keyword.model.KeywordModel;

@Service
public class KeywordMgrImpl extends AbstractMgr<KeywordBo, KeywordModel, Integer> implements KeywordMgr {

	/**
	 * 验证器
	 */
	@Resource
	protected Validator<KeywordBo> keywordValidator;
	
	/**
	 * 组装model工厂
	 */
	@Resource
	protected ModelFactory<Integer, KeywordBo, KeywordModel> keywordModelFactory;
	
	/**
	 * model存储repository
	 */
	@Resource
	protected ModelRepository<Integer, KeywordModel> keywordModelRepository;

	@PostConstruct
	public void init() {
		this.validator = keywordValidator;
		this.modelFactory = keywordModelFactory;
		this.modelRepository = keywordModelRepository;
	}
	

	@Resource
	private KeywordDao keywordDao;
	
	@Override
	public List<Keyword> findByGroupId(Integer groupId) {
		return keywordDao.findByGroupId(groupId);
	}
	
	@Override
	public int countByGroupId(Integer groupId) {
		return keywordDao.findByGroupId(groupId).size();
	}
	
}
