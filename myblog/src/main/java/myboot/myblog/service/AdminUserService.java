package myboot.myblog.service;

import myboot.myblog.domain.AdminUser;

import java.util.List;

public interface AdminUserService {
    /**
     * 根据用户名查找
     * @param userName
     * @return
     */
    AdminUser findByUserName(String userName);

    /**
     * 更新管理员基本信息
     * @param user
     */
    boolean updateAdminUser(AdminUser user);

    /**
     * 根据userId查询管理员用户
     * @param userId
     * @return
     */
    AdminUser findByUserId(Integer userId);
}
