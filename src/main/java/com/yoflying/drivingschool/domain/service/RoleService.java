package com.yoflying.drivingschool.domain.service;



import com.yoflying.drivingschool.domain.model.Role;

import java.util.List;


/**
 * 角色 业务接口
 *
 * @author StarZou
 * @since 2014年6月10日 下午4:15:01
 **/
public interface RoleService  {
    /**
     * 通过用户id 查询用户 拥有的角色
     * 
     * @param userId
     * @return
     */
    List<Role> selectRolesByUserId(Long userId);

    List<Role> selectRolesALL();
}
