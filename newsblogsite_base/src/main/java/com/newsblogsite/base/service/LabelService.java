package com.newsblogsite.base.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import com.newsblogsite.base.dao.LabelDao;
import com.newsblogsite.base.pojo.Label;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Transactional
@Service
public class LabelService {

	@Autowired
	private LabelDao labelDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Label> findAll() {
		return labelDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereLabel
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Label> findSearch(Label whereLabel, int page, int size) {
		Specification<Label> specification = createSpecification(whereLabel);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return labelDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereLabel
	 * @return
	 */
	public List<Label> findSearch(Label whereLabel) {
		Specification<Label> specification = createSpecification(whereLabel);
		return labelDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Label findById(String id) {
		return labelDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param label
	 */
	public void add(Label label) {
		label.setId( idWorker.nextId()+"" );
		label.setState("0");
		label.setRecommend("0");
		label.setCount(0L);
		label.setFans(0L);
		System.out.println(label);
		labelDao.save(label);
	}

	/**
	 * 修改
	 * @param label
	 */
	public void update(Label label) {
		labelDao.save(label);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		labelDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchLabel
	 * @return
	 */
	private Specification<Label> createSpecification(Label searchLabel) {

		return new Specification<Label>() {

			@Override
			public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // 标签ID
                if (searchLabel.getId()!=null && !"".equals(searchLabel.getId())) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+searchLabel.getId()+"%"));
                }
                // 标签名称
                if (searchLabel.getLabelName()!=null && !"".equals(searchLabel.getLabelName())) {
                	predicateList.add(cb.like(root.get("labelName").as(String.class), "%"+searchLabel.getLabelName()+"%"));
                }
                // 状态
                if (searchLabel.getState()!=null && !"".equals(searchLabel.getState())) {
                	predicateList.add(cb.like(root.get("state").as(String.class), "%"+searchLabel.getState()+"%"));
                }
                // 是否推荐
                if (searchLabel.getRecommend()!=null && !"".equals(searchLabel.getRecommend())) {
                	predicateList.add(cb.like(root.get("recommend").as(String.class), "%"+searchLabel.getRecommend()+"%"));
                }
				// 使用人数
				if (searchLabel.getCount()!=null && !"".equals(searchLabel.getCount())) {
					predicateList.add(cb.like(root.get("count").as(String.class), "%"+searchLabel.getCount()+"%"));
				}
				// 粉丝数
				if (searchLabel.getFans()!=null && !"".equals(searchLabel.getFans())) {
					predicateList.add(cb.like(root.get("fans").as(String.class), "%"+searchLabel.getFans()+"%"));
				}
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

	/**
	 * 增加使用数量
	 * @param labelId
	 * @return
	 */
    public void addCount(String labelId) {
    	labelDao.addCount(labelId);
    }

	/**
	 * 是否推荐
	 * @param labelId
	 * @return
	 */
	public void recommend(String labelId, String value) {
		labelDao.recommend(value,labelId);
	}

	/**
	 * 增加粉丝数
	 * @param labelId
	 * @return
	 */
	public void addFans(String labelId) {
		labelDao.addFans(labelId);
	}

	/**
	 * 审核
	 * @param labelId
	 * @return
	 */
	public void examine(String labelId) {
		labelDao.examine(labelId);
	}
}
