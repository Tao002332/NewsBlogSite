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

import com.newsblogsite.news.dao.ShareDao;
import com.newsblogsite.news.pojo.Share;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Transactional
@Service
public class ShareService {

	@Autowired
	private ShareDao shareDao;
	
	@Autowired
	private IdWorker idWorker;

	@Autowired
	private HttpServletRequest request;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Share> findAll() {
		return shareDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereShare
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Share> findSearch(Share whereShare, int page, int size) {
		Specification<Share> specification = createSpecification(whereShare);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return shareDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereShare
	 * @return
	 */
	public List<Share> findSearch(Share whereShare) {
		Specification<Share> specification = createSpecification(whereShare);
		return shareDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Share findById(String id) {
		return shareDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param share
	 */
	public void add(Share share) {
		checkRole("userClaims");
		String id =(String)request.getAttribute("loginId");
		share.setId( idWorker.nextId()+"" );
		share.setUserId(id);
		share.setComment(0);
		share.setCreateTime(new Date());
		share.setIsDeath("0");
		share.setState("0");
		share.setThumbup(0);
		share.setVisits(0);
		share.setIsPublic("1");
		share.setIsTop("0");
		shareDao.save(share);
	}

	/**
	 * 修改
	 * @param share
	 */
	public void update(Share share) {
		share.setUpdateTime(new Date());
		Share old = findById(share.getId());
		BeanUtils.copyProperties(share,old, BeanUtilsExt.getNullPropertyNames(share));
		shareDao.save(old);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		shareDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchShare
	 * @return
	 */
	private Specification<Share> createSpecification(Share searchShare) {

		return new Specification<Share>() {

			@Override
			public Predicate toPredicate(Root<Share> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchShare.getId()!=null && !"".equals(searchShare.getId())) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+searchShare.getId()+"%"));
                }
                // 用户ID
                if (searchShare.getUserId()!=null && !"".equals(searchShare.getUserId())) {
                	predicateList.add(cb.like(root.get("userId").as(String.class), "%"+searchShare.getUserId()+"%"));
                }
                // 是否公开
                if (searchShare.getIsPublic()!=null && !"".equals(searchShare.getIsPublic())) {
                	predicateList.add(cb.like(root.get("isPublic").as(String.class), "%"+searchShare.getIsPublic()+"%"));
                }
				// 是否置顶
				if (searchShare.getIsTop()!=null && !"".equals(searchShare.getIsTop())) {
					predicateList.add(cb.like(root.get("isTop").as(String.class), "%"+searchShare.getIsTop()+"%"));
				}
                // 审核状态
                if (searchShare.getState()!=null && !"".equals(searchShare.getState())) {
                	predicateList.add(cb.like(root.get("state").as(String.class), "%"+searchShare.getState()+"%"));
                }
                // 是否死亡
                if (searchShare.getIsDeath()!=null && !"".equals(searchShare.getIsDeath())) {
                	predicateList.add(cb.like(root.get("isDeath").as(String.class), "%"+searchShare.getIsDeath()+"%"));
                }
				//创建时间范围
				if (searchShare.getCreateDateTime()!=null && searchShare.getCreateDateTime().length>1) {
					predicateList.add(cb.between(root.get("createTime"),searchShare.getCreateDateTime()[0],searchShare.getCreateDateTime()[1]));
				}
				//修改时间范围
				if (searchShare.getUpdateDateTime()!=null && searchShare.getUpdateDateTime().length>1) {
					predicateList.add(cb.between(root.get("updateTime"),searchShare.getUpdateDateTime()[0],searchShare.getUpdateDateTime()[1]));
				}
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

	/**
	 * 审核分享
	 * @param shareId
	 */
	public void examine(String shareId) {
		checkRole("adminClaims");
		Share share=new Share();
		share.setId(shareId);
		share.setState("1");
		update(share);
	}


	/**
	 * 是否死亡
	 * @param shareId
	 * @param value
	 */
	public void setDeath(String shareId, String value) {
		checkRole("userClaims");
		Share share=new Share();
		share.setId(shareId);
		share.setIsDeath(value);
		update(share);
	}

	/**
	 * 是否置顶
	 * @param shareId
	 * @param value
	 */
	public void setTop(String shareId, String value) {
		checkRole("userClaims");
		Share share=new Share();
		share.setId(shareId);
		share.setIsTop(value);
		update(share);
	}

	/**
	 * 是否公开
	 * @param shareId
	 * @param value
	 */
	public void setPublic(String shareId, String value) {
		checkRole("userClaims");
		Share share=new Share();
		share.setId(shareId);
		share.setIsPublic(value);
		update(share);
	}



	/**
	 * 增加评论数
	 * @param shareId
	 */
	public void addCommentCount(String shareId) {
		checkRole("userClaims");
		shareDao.addCommentCount(shareId);
	}

	/**
	 * 增加浏览数
	 * @param shareId
	 */
	public void addVisitsCount(String shareId) {
		shareDao.addVisitsCount(shareId);
	}

	/**
	 * 增加点赞数
	 * @param shareId
	 */
	public void addThumupCount(String shareId) {
		checkRole("userClaims");
		shareDao.addThumupCount(shareId);
	}



	/**
	 * 查询自己的新闻 条件查询+分页
	 * @param searchShare
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Share> findMySearch(Share searchShare, int page, int size) {
		checkRole("userClaims");
		String id =(String)request.getAttribute("loginId");
		searchShare.setUserId(id);
		Specification<Share> specification = createSpecification(searchShare);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return shareDao.findAll(specification, pageRequest);
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
