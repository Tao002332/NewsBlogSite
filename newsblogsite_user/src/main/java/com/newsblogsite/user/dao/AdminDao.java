package com.newsblogsite.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.newsblogsite.user.pojo.Admin;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface AdminDao extends JpaRepository<Admin,String>,JpaSpecificationExecutor<Admin>{

    /**
     * 通过名称获取用户信息
     * @param loginName
     * @return
     */
    Admin findByLoginName(String loginName);


    /**
     * 修改权限
     * @param level
     * @param adminId
     */
    @Modifying
    @Query(value = "update tb_admin set state=? where id=?",nativeQuery = true)
    void grant(String level, String adminId);
}
