package com.newsblogsite.news.service;

import java.util.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.servlet.http.HttpServletRequest;

import com.newsblogsite.news.client.GatheringClient;
import com.newsblogsite.news.dao.UserCollectedDao;
import com.newsblogsite.news.pojo.UserCollected;
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

import com.newsblogsite.news.dao.NewsDao;
import com.newsblogsite.news.pojo.News;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Transactional
@Service
public class NewsService {

	@Autowired
	private NewsDao newsDao;
	
	@Autowired
	private IdWorker idWorker;


	@Autowired
	private UserCollectedDao userCollectedDao;

	@Autowired
	private HttpServletRequest request;


	/**
	 * 查询全部列表
	 * @return
	 */
	public List<News> findAll() {
		return newsDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereNews
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<News> findSearch(News whereNews, int page, int size) {
		Specification<News> specification = createSpecification(whereNews);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return newsDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereNews
	 * @return
	 */
	public List<News> findSearch(News whereNews) {
		Specification<News> specification = createSpecification(whereNews);
		return newsDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public News findById(String id) {
		return newsDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param news
	 */
	public void add(News news) {
		checkRole("userClaims");
		String id =(String)request.getAttribute("loginId");
		news.setId( idWorker.nextId()+"" );
		news.setUserId(id);
		news.setComment(0);
		news.setCreateTime(new Date());
		news.setIsDeath("0");
		news.setState("0");
		news.setIsPublic("1");
		news.setIsTop("0");
		news.setVisits(0);
		newsDao.save(news);
	}

	/**
	 * 修改
	 * @param news
	 */
	public void update(News news) {
		news.setUpdateTime(new Date());
		News old = findById(news.getId());
		BeanUtils.copyProperties(news,old, BeanUtilsExt.getNullPropertyNames(news));
		newsDao.save(old);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		newsDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchNews
	 * @return
	 */
	private Specification<News> createSpecification(News searchNews) {

		return new Specification<News>() {

			@Override
			public Predicate toPredicate(Root<News> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				// ID
				if (searchNews.getId()!=null && !"".equals(searchNews.getId())) {
					predicateList.add(cb.like(root.get("id").as(String.class), "%"+searchNews.getId()+"%"));
				}
                // 专栏ID
                if (searchNews.getColumnId()!=null && !"".equals(searchNews.getColumnId())) {
                	predicateList.add(cb.like(root.get("columnId").as(String.class), "%"+searchNews.getColumnId()+"%"));
                }
				// 用户ID
				if (searchNews.getUserId()!=null && !"".equals(searchNews.getUserId())) {
					predicateList.add(cb.like(root.get("userId").as(String.class), "%"+searchNews.getUserId()+"%"));
				}
                // 标题
                if (searchNews.getTitle()!=null && !"".equals(searchNews.getTitle())) {
                	predicateList.add(cb.like(root.get("title").as(String.class), "%"+searchNews.getTitle()+"%"));
                }
				// 是否公开
				if (searchNews.getIsPublic()!=null && !"".equals(searchNews.getIsPublic())) {
					predicateList.add(cb.like(root.get("isPublic").as(String.class), "%"+searchNews.getIsPublic()+"%"));
				}
				// 是否置顶
				if (searchNews.getIsTop()!=null && !"".equals(searchNews.getIsTop())) {
					predicateList.add(cb.like(root.get("isTop").as(String.class), "%"+searchNews.getIsTop()+"%"));
				}
				// 审核状态
				if (searchNews.getState()!=null && !"".equals(searchNews.getState())) {
					predicateList.add(cb.like(root.get("state").as(String.class), "%"+searchNews.getState()+"%"));
				}
                // 频道ID
                if (searchNews.getChannelId()!=null && !"".equals(searchNews.getChannelId())) {
                	predicateList.add(cb.like(root.get("channelId").as(String.class), "%"+searchNews.getChannelId()+"%"));
                }
                // 活动ID
                if (searchNews.getGatheringId()!=null && !"".equals(searchNews.getGatheringId())) {
                	predicateList.add(cb.like(root.get("gatheringId").as(String.class), "%"+searchNews.getGatheringId()+"%"));
                }
				// 是否死亡
				if (searchNews.getIsDeath()!=null && !"".equals(searchNews.getIsDeath())) {
					predicateList.add(cb.like(root.get("isDeath").as(String.class), "%"+searchNews.getIsDeath()+"%"));
				}
                // 标签列表
                if (searchNews.getLabels()!=null && !"".equals(searchNews.getLabels())) {
                	predicateList.add(cb.like(root.get("labels").as(String.class), "%"+searchNews.getLabels()+"%"));
                }
				//创建时间范围
				if (searchNews.getCreateDateTime()!=null && searchNews.getCreateDateTime().length>1) {
					predicateList.add(cb.between(root.get("createTime"),searchNews.getCreateDateTime()[0],searchNews.getCreateDateTime()[1]));
				}
				//修改时间范围
				if (searchNews.getUpdateDateTime()!=null && searchNews.getUpdateDateTime().length>1) {
					predicateList.add(cb.between(root.get("updateTime"),searchNews.getUpdateDateTime()[0],searchNews.getUpdateDateTime()[1]));
				}
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

	/**
	 * 审核新闻
	 * @param newsId
	 */
	public void examine(String newsId) {
		checkRole("adminClaims");
		News news = new News();
		news.setId(newsId);
		news.setState("1");
		update(news);
	}


	/**
	 * 是否死亡
	 * @param newsId
	 * @param value
	 */
	public void setDeath(String newsId, String value) {
		checkRole("userClaims");
		News news = new News();
		news.setId(newsId);
		news.setIsDeath(value);
		update(news);
	}

	/**
	 * 是否置顶
	 * @param newsId
	 * @param value
	 */
	public void setTop(String newsId, String value) {
		checkRole("userClaims");
		News news = new News();
		news.setId(newsId);
		news.setIsTop(value);
		update(news);
	}

	/**
	 * 是否公开
	 * @param newsId
	 * @param value
	 */
	public void setPublic(String newsId, String value) {
		checkRole("userClaims");
		News news = new News();
		news.setId(newsId);
		news.setIsPublic(value);
		update(news);
	}

	/**
	 * 增加评论数
	 * @param newsId
	 */
	public void addCommentCount(String newsId) {
		checkRole("userClaims");
		newsDao.addCommentCount(newsId);
	}

	/**
	 * 增加浏览数
	 * @param newsId
	 */
	public void addVisitsCount(String newsId) {
		newsDao.addVisitsCount(newsId);
	}


	/**
	 * 查询自己的新闻 条件查询+分页
	 * @param whereNews
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<News> findMySearch(News whereNews, int page, int size) {
		checkRole("userClaims");
		String id =(String)request.getAttribute("loginId");
		whereNews.setUserId(id);
		Specification<News> specification = createSpecification(whereNews);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return newsDao.findAll(specification, pageRequest);
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


	/**
	 * 用户收藏新闻
	 * @param newsId
	 */
	public void collected(String newsId) {
		checkRole("userClaims");
		String id =(String)request.getAttribute("loginId");
		if( isCollected(newsId)) {
			throw  new RuntimeException("不能重复收藏");
		}
		UserCollected uc= new UserCollected();
		uc.setUserId(id);
		uc.setOriginId(newsId);
		uc.setOriginType("1");
		userCollectedDao.save(uc);
	}


	/**
	 * 用户取消收藏新闻
	 * @param newsId
	 */
	public void cancelCollect(String newsId) {
		checkRole("userClaims");
		String id =(String)request.getAttribute("loginId");
		userCollectedDao.deleteByUserIdAndOriginId(id,newsId);
	}

	/**
	 * 用户收藏新闻集合
	 */
	public List<News> findCollectList() {
		checkRole("userClaims");
		String id =(String)request.getAttribute("loginId");
		return newsDao.findByUserCollected(id);
	}



	/**
	 * 用户是否收藏新闻
	 * @param newsId
	 */
	public boolean isCollected(String newsId) {
		checkRole("userClaims");
		String id =(String)request.getAttribute("loginId");
		UserCollected uc = userCollectedDao.findByUserIdAndOriginId(id, newsId);
		return  uc!=null? true: false;
	}


	/**
	 * 获取用户最近的5跳新闻
	 * @param userId
	 * @return
	 */
	public List<News> getLastTop5(String userId) {
		return newsDao.getLastTop5(userId);
	}




}
