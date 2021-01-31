package com.ghw.minibox.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ghw.minibox.model.RoleModel;
import com.ghw.minibox.model.UserModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Violet
 * @description
 * @date 2021/1/31
 */

public interface MbpUserMapper extends BaseMapper<UserModel> {
    /**
     * 查找用户拥有哪些角色
     *
     * @param id 用户id
     * @return 该用户对应的角色列表
     */
    List<RoleModel> findUserRoles(Long id);

    /**
     * 设置用户的角色
     *
     * @param id      用户id
     * @param roleId 角色的id列表
     * @return
     */
    int setUserRoles(@Param("id") Long id, @Param("roleId") Long roleId);
}
