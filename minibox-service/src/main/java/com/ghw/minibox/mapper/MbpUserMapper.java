package com.ghw.minibox.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ghw.minibox.model.RoleModel;
import com.ghw.minibox.model.UserModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Violet
 * @description 用户 mapper
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
     * 查找用户以及每个用户的所有角色
     *
     * @param userModel 实例
     * @return 列表
     */
    List<UserModel> findUserAndEveryUserRoles(UserModel userModel);

    /**
     * 设置用户的角色
     *
     * @param id     用户id
     * @param roleId 角色的id列表
     */
    int setUserRoles(@Param("id") Long id, @Param("roleId") Long roleId);

    /**
     * 删除用户的角色
     *
     * @param userId 用户id
     * @return 影响行数
     */
    int deleteUserRoles(Long userId);

    /**
     * 删除用户的管理员角色
     *
     * @param userId 用户id
     * @return 影响行数
     */
    int deleteUserAdmin(Long userId);

    /**
     * 查找用户详细信息，包括拥有的游戏
     *
     * @param id 用户id
     */
    UserModel findUserDetail(Long id);
}
