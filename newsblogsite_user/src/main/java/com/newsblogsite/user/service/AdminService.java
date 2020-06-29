package com.newsblogsite.user.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import com.newsblogsite.user.dao.AdminDao;
import com.newsblogsite.user.pojo.Admin;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Transactional
@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private IdWorker idWorker;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private HttpServletRequest request;




	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Admin> findAll() {
		checkRole("adminClaims");
		return adminDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereAdmin
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Admin> findSearch(Admin whereAdmin, int page, int size) {
		Specification<Admin> specification = createSpecification(whereAdmin);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return adminDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereAdmin
	 * @return
	 */
	public List<Admin> findSearch(Admin whereAdmin) {
		Specification<Admin> specification = createSpecification(whereAdmin);
		return adminDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Admin findById(String id) {
		return adminDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param admin
	 */
	public void add(Admin admin) {
		admin.setId( idWorker.nextId()+"" );
		admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
		admin.setState("1");
		adminDao.save(admin);
	}

	/**
	 * 修改
	 * @param admin
	 */
	public void update(Admin admin) {
		checkRole("adminClaims");
		adminDao.save(admin);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		checkRole("adminClaims");
		adminDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchAdmin
	 * @return
	 */
	private Specification<Admin> createSpecification(Admin searchAdmin) {
		checkRole("adminClaims");
		return new Specification<Admin>() {

			@Override
			public Predicate toPredicate(Root<Admin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchAdmin.getId()!=null && !"".equals(searchAdmin.getId())) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+searchAdmin.getId()+"%"));
                }
                // 登陆名称
                if (searchAdmin.getLoginName()!=null && !"".equals(searchAdmin.getLoginName())) {
                	predicateList.add(cb.like(root.get("loginName").as(String.class), "%"+searchAdmin.getLoginName()+"%"));
                }
                // 状态
                if (searchAdmin.getState()!=null && !"".equals(searchAdmin.getState())) {
                	predicateList.add(cb.like(root.get("state").as(String.class), "%"+searchAdmin.getState()+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}


	/**
	 * 管理员登录
	 * @param loginName
	 * @param password
	 * @return
	 */
	public Admin login(String loginName, String password) {
		Admin admin = adminDao.findByLoginName(loginName);
		if(admin!=null && bCryptPasswordEncoder.matches(password,admin.getPassword())) {
			return admin;
		}
		return null;
	}


	/**
	 * 获取管理员详细信息
	 * @param token
	 * @return
	 */
	public Admin getInfo(String token) {
		checkRole("adminClaims");
		String id =(String)request.getAttribute("loginId");
		return findById(id);
	}

	/**
	 * 授权
	 * @param adminId
	 * @param level
	 */
	public void grant(String adminId, String level) {
		checkRole("adminClaims");
		adminDao.grant(level,adminId);
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
