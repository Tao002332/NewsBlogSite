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

import util.IdWorker;

import com.newsblogsite.base.dao.CityDao;
import com.newsblogsite.base.pojo.City;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class CityService {

	@Autowired
	private CityDao cityDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<City> findAll() {
		return cityDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereCity
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<City> findSearch(City whereCity, int page, int size) {
		Specification<City> specification = createSpecification(whereCity);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return cityDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereCity
	 * @return
	 */
	public List<City> findSearch(City whereCity) {
		Specification<City> specification = createSpecification(whereCity);
		return cityDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public City findById(String id) {
		return cityDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param city
	 */
	public void add(City city) {
		city.setId( idWorker.nextId()+"" );
		cityDao.save(city);
	}

	/**
	 * 修改
	 * @param city
	 */
	public void update(City city) {
		cityDao.save(city);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		cityDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchCity
	 * @return
	 */
	private Specification<City> createSpecification(City searchCity) {

		return new Specification<City>() {

			@Override
			public Predicate toPredicate(Root<City> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchCity.getId()!=null && !"".equals(searchCity.getId())) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+searchCity.getId()+"%"));
                }
                // 父级id
                if (searchCity.getPid()!=null && !"".equals(searchCity.getPid())) {
                	predicateList.add(cb.equal(root.get("pid").as(String.class), searchCity.getPid()));
                }
                // 城市名称
                if (searchCity.getCityName()!=null && !"".equals(searchCity.getCityName())) {
                	predicateList.add(cb.like(root.get("cityName").as(String.class), "%"+searchCity.getCityName()+"%"));
                }
                // 所属类型
                if (searchCity.getType()!=null && !"".equals(searchCity.getType())) {
                	predicateList.add(cb.like(root.get("type").as(String.class), "%"+searchCity.getType()+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}
