package com.newsblogsite.news.service;

import java.util.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.servlet.http.HttpServletRequest;

import com.newsblogsite.news.pojo.News;
import com.newsblogsite.news.pojo.Share;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import util.BeanUtilsExt;
import util.IdWorker;

import com.newsblogsite.news.dao.ColumnDao;
import com.newsblogsite.news.pojo.Column;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Transactional
@Service
public class ColumnService {

	@Autowired
	private ColumnDao columnDao;
	
	@Autowired
	private IdWorker idWorker;

	@Autowired
	private HttpServletRequest request;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Column> findAll() {
		return columnDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereColumn
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Column> findSearch(Column whereColumn, int page, int size) {
		Specification<Column> specification = createSpecification(whereColumn);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return columnDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereColumn
	 * @return
	 */
	public List<Column> findSearch(Column whereColumn) {
		Specification<Column> specification = createSpecification(whereColumn);
		return columnDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Column findById(String id) {
		return columnDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param column
	 */
	public void add(Column column) {
		checkRole("userClaims");
		String id =(String)request.getAttribute("loginId");
		column.setId( idWorker.nextId()+"" );
		column.setUserId(id);
		column.setState("0");
		column.setCreateTime(new Date());
		columnDao.save(column);
	}

	/**
	 * 修改
	 * @param column
	 */
	public void update(Column column) {
		Column old = findById(column.getId());
		BeanUtils.copyProperties(column,old, BeanUtilsExt.getNullPropertyNames(column));
		columnDao.save(old);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		columnDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchColumn
	 * @return
	 */
	private Specification<Column> createSpecification(Column searchColumn) {

		return new Specification<Column>() {

			@Override
			public Predicate toPredicate(Root<Column> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchColumn.getId()!=null && !"".equals(searchColumn.getId())) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+searchColumn.getId()+"%"));
                }
                // 专栏名称
                if (searchColumn.getName()!=null && !"".equals(searchColumn.getName())) {
                	predicateList.add(cb.like(root.get("name").as(String.class), "%"+searchColumn.getName()+"%"));
                }
                // 用户ID
                if (searchColumn.getUserId()!=null && !"".equals(searchColumn.getUserId())) {
                	predicateList.add(cb.like(root.get("userId").as(String.class), "%"+searchColumn.getUserId()+"%"));
                }
                // 状态
                if (searchColumn.getState()!=null && !"".equals(searchColumn.getState())) {
                	predicateList.add(cb.like(root.get("state").as(String.class), "%"+searchColumn.getState()+"%"));
                }
				if (searchColumn.getCreateDateTime()!=null && searchColumn.getCreateDateTime().length>1) {
					predicateList.add(cb.between(root.get("createTime"),searchColumn.getCreateDateTime()[0],searchColumn.getCreateDateTime()[1]));
				}
				//确认时间范围
				if (searchColumn.getCheckDateTime()!=null && searchColumn.getCheckDateTime().length>1) {
					predicateList.add(cb.between(root.get("checkTime"),searchColumn.getCheckDateTime()[0],searchColumn.getCheckDateTime()[1]));
				}
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}


	/**
	 * 审核专栏
	 * @param columnId
	 */
	public void examine(String columnId) {
		checkRole("adminClaims");
		Column column= new Column();
		column.setId(columnId);
		column.setState("1");
		column.setCheckTime(new Date());
		update(column);
	}




	/**
	 * 查询自己的新闻 条件查询+分页
	 * @param whereColumn
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Column> findMySearch(Column whereColumn, int page, int size) {
		checkRole("userClaims");
		String id =(String)request.getAttribute("loginId");
		whereColumn.setUserId(id);
		Specification<Column> specification = createSpecification(whereColumn);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return columnDao.findAll(specification, pageRequest);
	}


	/**
	 * 验证权限 提取
	 * @param rolename
	 */
	protected void checkRole(String rolename) {
		String reqToken =(String)request.getAttribute(rolename);
		if(reqToken == null || "".equals(reqToken)) {
			throw  new RuntimeException("权限不足");
		}
	}



}
