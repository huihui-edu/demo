package myboot.myblog.mapper;

import myboot.myblog.domain.AdminUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 管理员接口
 */
@Repository
public interface AdminUserMapper {
    /**
     * 根据用户名查找
     * @param userName
     * @return
     */
    List<AdminUser> findByUserName(String userName);

    /**
     * 修改管理员基本信息
     * @param user
     */
    int updateAdminUser(AdminUser user);

    /**
     * 根据userId查询管理员用户
     * @param userId
     * @return
     */
    AdminUser findByUserId(Integer userId);
}
