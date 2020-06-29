package com.newsblogsite.gathering.service;

import java.util.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.servlet.http.HttpServletRequest;

import com.newsblogsite.gathering.dao.UserJoinGatheringDao;
import com.newsblogsite.gathering.pojo.UserJoinGathering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import com.newsblogsite.gathering.dao.GatheringDao;
import com.newsblogsite.gathering.pojo.Gathering;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Transactional
@Service
public class GatheringService {

	@Autowired
	private GatheringDao gatheringDao;

	@Autowired
	private UserJoinGatheringDao userJoinGatheringDao;
	
	@Autowired
	private IdWorker idWorker;

	@Autowired
	private HttpServletRequest request;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Gathering> findAll() {
		return gatheringDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereGathering
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Gathering> findSearch(Gathering whereGathering, int page, int size) {
		Specification<Gathering> specification = createSpecification(whereGathering);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return gatheringDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereGathering
	 * @return
	 */
	public List<Gathering> findSearch(Gathering whereGathering) {
		Specification<Gathering> specification = createSpecification(whereGathering);
		return gatheringDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Gathering findById(String id) {
		return gatheringDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param gathering
	 */
	public void add(Gathering gathering) {
		checkRole("adminClaims");
		gathering.setId( idWorker.nextId()+"" );
		gathering.setJoinCount(0);
		gatheringDao.save(gathering);
	}

	/**
	 * 修改
	 * @param gathering
	 */
	public void update(Gathering gathering) {
		checkRole("adminClaims");
		gatheringDao.save(gathering);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		checkRole("adminClaims");
		stateChange(id,"1");
	}

	/**
	 * 动态条件构建
	 * @param searchGathering
	 * @return
	 */
	private Specification<Gathering> createSpecification(Gathering searchGathering) {

		return new Specification<Gathering>() {

			@Override
			public Predicate toPredicate(Root<Gathering> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // 活动ID
                if (searchGathering.getId()!=null && !"".equals(searchGathering.getId())) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+searchGathering.getId()+"%"));
                }
                // 活动名称
                if (searchGathering.getName()!=null && !"".equals(searchGathering.getName())) {
                	predicateList.add(cb.like(root.get("name").as(String.class), "%"+searchGathering.getName()+"%"));
                }
                // 主办方
                if (searchGathering.getSponsor()!=null && !"".equals(searchGathering.getSponsor())) {
                	predicateList.add(cb.like(root.get("sponsor").as(String.class), "%"+searchGathering.getSponsor()+"%"));
                }
                // 举办地点
                if (searchGathering.getAddress()!=null && !"".equals(searchGathering.getAddress())) {
                	predicateList.add(cb.like(root.get("address").as(String.class), "%"+searchGathering.getAddress()+"%"));
                }
                // 活动状态
                if (searchGathering.getState()!=null && !"".equals(searchGathering.getState())) {
                	predicateList.add(cb.like(root.get("state").as(String.class), "%"+searchGathering.getState()+"%"));
                }
                // 频道ID
                if (searchGathering.getChannelId()!=null && !"".equals(searchGathering.getChannelId())) {
                	predicateList.add(cb.like(root.get("channelId").as(String.class), "%"+searchGathering.getChannelId()+"%"));
                }
                // 城市
                if (searchGathering.getCity()!=null && !"".equals(searchGathering.getCity())) {
                	predicateList.add(cb.like(root.get("city").as(String.class), "%"+searchGathering.getCity()+"%"));
                }
				//开始范围
				if (searchGathering.getStartDateTime()!=null && searchGathering.getStartDateTime().length>1) {
					predicateList.add(cb.between(root.get("startTime"),searchGathering.getStartDateTime()[0],searchGathering.getStartDateTime()[1]));
				}
				//结束范围
				if (searchGathering.getEndDateTime()!=null && searchGathering.getEndDateTime().length>1) {
					predicateList.add(cb.between(root.get("endTime"),searchGathering.getEndDateTime()[0],searchGathering.getEndDateTime()[1]));
				}
				//截止范围
				if (searchGathering.getEnrollDateTime()!=null && searchGathering.getEnrollDateTime().length>1) {
					predicateList.add(cb.between(root.get("enrollTime"),searchGathering.getEnrollDateTime()[0],searchGathering.getEnrollDateTime()[1]));
				}
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}


	/**
	 * 修改状态
	 * @param gatheringId
	 * @param value
	 */
	public void stateChange(String gatheringId, String value) {
		checkRole("adminClaims");
		gatheringDao.stateChange(value,gatheringId);
	}

	/**
	 * 用户参与活动
	 * @param gatheringId
	 */
	public void join(String gatheringId) {
		checkRole("userClaims");
		String id =(String)request.getAttribute("loginId");
		Gathering gathering = findById(gatheringId);
		if("1".equals(gathering.getState()) ||gathering.getEnrollTime().getTime()< System.currentTimeMillis()) {
			throw new RuntimeException("活动已经结束");
		}
		UserJoinGathering gau = userJoinGatheringDao.findByGathIdAndUserId(gatheringId, id);
		if( gau != null) {
			throw new RuntimeException("不能参加多次");
		}
		gau= new UserJoinGathering();
		gau.setGathId(gatheringId);
		gau.setUserId(id);
		gau.setExeTime(new Date());
		userJoinGatheringDao.save(gau);
		addJoinCount(gatheringId,1);
	}

	/**
	 * 增加参与人数
	 */
	public void addJoinCount(String gatheringId,int num) {
		gatheringDao.addJoinCount(num,gatheringId);
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
