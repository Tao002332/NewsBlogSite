package com.newsblogsite.user.service;

import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.servlet.http.HttpServletRequest;

import com.newsblogsite.user.pojo.OtherUser;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import util.BeanUtilsExt;
import util.EntityUtils;
import util.IdWorker;

import com.newsblogsite.user.dao.UserDao;
import com.newsblogsite.user.pojo.User;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Transactional
@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private IdWorker idWorker;


	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private HttpServletRequest request;


	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<User> findAll() {
		checkRole("adminClaims");
		return userDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereUser
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<User> findSearch(User whereUser, int page, int size) {
		Specification<User> specification = createSpecification(whereUser);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return userDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereUser
	 * @return
	 */
	public List<User> findSearch(User whereUser) {
		Specification<User> specification = createSpecification(whereUser);
		return userDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public User findById(String id) {
		return userDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param user
	 */
	public void add(User user) {
		User checkUser = userDao.findByMobile(user.getMobile());
		if ( checkUser != null ) {
			throw  new RuntimeException("手机号已被注册");
		}
		user.setId( idWorker.nextId()+"" );
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setBirthday(new Date());
		user.setEmail("");
		user.setFansCount(0);
		user.setFollowCount(0);
		user.setInterest("");
		user.setNickname("用户"+user.getId());
		user.setPersonality("");
		user.setRegDate(new Date());
		user.setUpdateDate(new Date());
		user.setSex("0");
		user.setAvatar("");
		user.setLastDate(new Date());
		user.setOnline(0L);
		user.setIsAuth("0");
		user.setAuthName("");
		user.setState("1");
		userDao.save(user);
	}

	/**
	 * 修改
	 * @param user
	 */
	public void update(User user) {
		User old= new User();
		String reqToken =(String)request.getAttribute("userClaims");
		if(reqToken == null || "".equals(reqToken)) {
			checkRole("adminClaims");
			old= findById(user.getId());
		} else {
			String id =(String)request.getAttribute("loginId");
			old = findById(id);
			old.setUpdateDate(new Date());
		}
		BeanUtils.copyProperties(user,old, BeanUtilsExt.getNullPropertyNames(user));
		userDao.save(old);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		stateChange(id,"1");
	}

	/**
	 * 动态条件构建
	 * @param searchUser
	 * @return
	 */
	private Specification<User> createSpecification(User searchUser) {
		checkRole("adminClaims");
		return new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchUser.getId()!=null && !"".equals(searchUser.getId())) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+searchUser.getId()+"%"));
                }
                // 手机号码
                if (searchUser.getMobile()!=null && !"".equals(searchUser.getMobile())) {
                	predicateList.add(cb.like(root.get("mobile").as(String.class), "%"+searchUser.getMobile()+"%"));
                }
                // 昵称
                if (searchUser.getNickname()!=null && !"".equals(searchUser.getNickname())) {
                	predicateList.add(cb.like(root.get("nickname").as(String.class), "%"+searchUser.getNickname()+"%"));
                }
                // 性别
                if (searchUser.getSex()!=null && !"".equals(searchUser.getSex())) {
                	predicateList.add(cb.like(root.get("sex").as(String.class), "%"+searchUser.getSex()+"%"));
                }
                // E-Mail
                if (searchUser.getEmail()!=null && !"".equals(searchUser.getEmail())) {
                	predicateList.add(cb.like(root.get("email").as(String.class), "%"+searchUser.getEmail()+"%"));
                }
                // 兴趣
                if (searchUser.getInterest()!=null && !"".equals(searchUser.getInterest())) {
                	predicateList.add(cb.like(root.get("interest").as(String.class), "%"+searchUser.getInterest()+"%"));
                }
                // 个性
                if (searchUser.getPersonality()!=null && !"".equals(searchUser.getPersonality())) {
                	predicateList.add(cb.like(root.get("personality").as(String.class), "%"+searchUser.getPersonality()+"%"));
                }
                // 是否认证
                if (searchUser.getIsAuth()!=null && !"".equals(searchUser.getIsAuth())) {
                	predicateList.add(cb.like(root.get("isAuth").as(String.class), "%"+searchUser.getIsAuth()+"%"));
                }
                // 认证名称
                if (searchUser.getAuthName()!=null && !"".equals(searchUser.getAuthName())) {
                	predicateList.add(cb.like(root.get("authName").as(String.class), "%"+searchUser.getAuthName()+"%"));
                }
				// 用户状态
				if (searchUser.getState()!=null && !"".equals(searchUser.getState())) {
					predicateList.add(cb.like(root.get("state").as(String.class), "%"+searchUser.getState()+"%"));
				}
                //注册时间范围
				if (searchUser.getRegDateTime()!=null && searchUser.getRegDateTime().length>1) {
					predicateList.add(cb.between(root.get("regDate"),searchUser.getRegDateTime()[0],searchUser.getRegDateTime()[1]));
				}
				//修改时间范围
				if (searchUser.getUpdateDateTime()!=null && searchUser.getUpdateDateTime().length>1) {
					predicateList.add(cb.between(root.get("updateDate"),searchUser.getUpdateDateTime()[0],searchUser.getUpdateDateTime()[1]));
				}
				//最后登录时间范围
				if (searchUser.getLastDateTime()!=null && searchUser.getLastDateTime().length>1) {
					predicateList.add(cb.between(root.get("lastDate"),searchUser.getLastDateTime()[0],searchUser.getLastDateTime()[1]));
				}
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

	/**
	 * 发送验证码
	 * @param mobile
	 */
	public void sendSms(String mobile) {
		String checkCode = RandomStringUtils.randomNumeric(6);
		redisTemplate.opsForValue().set("checkCode_"+mobile,checkCode,2, TimeUnit.MINUTES);
		Map<String, String> map = new HashMap<>();
		map.put("mobile",mobile);
		map.put("checkCode",checkCode);
		rabbitTemplate.convertAndSend("sms",map);
		System.out.println("checkCode==="+checkCode);
	}


	/**
	 * 修改密码
	 * @param user
	 */
	public void modifyPassword(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userDao.modifyPassword(user.getPassword(),user.getMobile());
	}

	/**
	 * 登录
	 * @param mobile
	 * @param password
	 * @return
	 */
	public User login(String mobile, String password) {
		User user = userDao.findByMobile(mobile);
		if( user!= null && bCryptPasswordEncoder.matches(password,user.getPassword())) {
			return user;
		}
		return null;
	}

	/**
	 * 获取用户信息
	 * @param token
	 * @return
	 */
	public User getInfo(String token) {
		checkRole("userClaims");
		String id =(String)request.getAttribute("loginId");
		return findById(id);
	}

	/**
	 * 用户认证
	 * @param userId
	 */
	public void examine(String userId) {
		checkRole("adminClaims");
		userDao.examin("1",userId);
	}

	/**
	 * 修改用户状态
	 * @param userId
	 * @param stateId
	 */
	public void stateChange(String userId, String stateId) {
		checkRole("adminClaims");
		userDao.stateChange(stateId,userId);
	}

	/**
	 * 查询其他用户信息
	 * @param userId
	 * @return
	 */
	public OtherUser visitOther(String userId) {
		List<Object[]> users = userDao.visitOther(userId);
		List<OtherUser> otherUsers= EntityUtils.castEntity(users,OtherUser.class,new OtherUser());
		return  otherUsers.get(0);
	}

	/**
	 * 更新用户粉丝数和 关注数
	 * @param userId
	 * @param friendId
	 * @param x
	 */
    public void updateFansCountAndFollowCount(String userId, String friendId, int x) {
    	userDao.updateFansCount(x,friendId);
    	userDao.updateFollowCount(x,userId);
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
	 * 查询我的关注
	 * @return
	 */
    public List<OtherUser> myFollow() {
		checkRole("userClaims");
		String id =(String)request.getAttribute("loginId");
		List<Object[]> users = userDao.myFollow(id);
		List<OtherUser> otherUsers= EntityUtils.castEntity(users,OtherUser.class,new OtherUser());
		return otherUsers;
    }


	/**
	 * 查询我的粉丝
	 * @return
	 */
	public List<OtherUser> myFans() {
		checkRole("userClaims");
		String id =(String)request.getAttribute("loginId");
		List<Object[]> users = userDao.myFans(id);
		List<OtherUser> otherUsers= EntityUtils.castEntity(users,OtherUser.class,new OtherUser());
		return otherUsers;
	}


	/**
	 * 查询我的黑名单
	 * @return
	 */
	public List<OtherUser> myBlacklist() {
		checkRole("userClaims");
		String id =(String)request.getAttribute("loginId");
		List<Object[]> users = userDao.myBlacklist(id);
		List<OtherUser> otherUsers= EntityUtils.castEntity(users,OtherUser.class,new OtherUser());
		return otherUsers;
	}

}
