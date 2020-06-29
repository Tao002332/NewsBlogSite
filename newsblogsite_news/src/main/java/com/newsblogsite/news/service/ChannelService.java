package com.newsblogsite.news.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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

import com.newsblogsite.news.dao.ChannelDao;
import com.newsblogsite.news.pojo.Channel;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Transactional
@Service
public class ChannelService {

	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Channel> findAll() {
		return channelDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereChannel
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Channel> findSearch(Channel whereChannel, int page, int size) {
		Specification<Channel> specification = createSpecification(whereChannel);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return channelDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereChannel
	 * @return
	 */
	public List<Channel> findSearch(Channel whereChannel) {
		Specification<Channel> specification = createSpecification(whereChannel);
		return channelDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Channel findById(String id) {
		return channelDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param channel
	 */
	public void add(Channel channel) {
		channel.setId( idWorker.nextId()+"" );
		channel.setState("1");
		channelDao.save(channel);
	}

	/**
	 * 修改
	 * @param channel
	 */
	public void update(Channel channel) {
		channelDao.save(channel);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		channelDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchChannel
	 * @return
	 */
	private Specification<Channel> createSpecification(Channel searchChannel) {

		return new Specification<Channel>() {

			@Override
			public Predicate toPredicate(Root<Channel> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchChannel.getId()!=null && !"".equals(searchChannel.getId())) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+searchChannel.getId()+"%"));
                }
                // 频道名称
                if (searchChannel.getName()!=null && !"".equals(searchChannel.getName())) {
                	predicateList.add(cb.like(root.get("name").as(String.class), "%"+searchChannel.getName()+"%"));
                }
                // 状态
                if (searchChannel.getState()!=null && !"".equals(searchChannel.getState())) {
                	predicateList.add(cb.like(root.get("state").as(String.class), "%"+searchChannel.getState()+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}



}
