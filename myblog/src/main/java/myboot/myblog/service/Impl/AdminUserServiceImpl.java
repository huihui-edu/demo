package myboot.myblog.service.Impl;

import myboot.myblog.mapper.AdminUserMapper;
import myboot.myblog.domain.AdminUser;
import myboot.myblog.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private AdminUserMapper adminUserMapper;

    /**
     * 根据用户名查找
     * @param userName
     * @return
     */
    @Override
    public AdminUser findByUserName(String userName) {
        List<AdminUser> adminUsers = adminUserMapper.findByUserName(userName);
        if (adminUsers.size() == 1){
            return adminUsers.get(0);
        }else {
            return null;
        }
    }

    /**
     * 修改管理员基本信息
     * @param userId
     */
    @Override
    public boolean updateAdminUser(AdminUser user) {
        //返回影响行
        int rows = adminUserMapper.updateAdminUser(user);
        if (rows > 0){
            //更新成功
            return true;
        }else {
            //更新失败
            return false;
        }
    }

    /**
     * 根据userId查询管理员用户
     * @param userId
     * @return
     */
    @Override
    public AdminUser findByUserId(Integer userId) {
        AdminUser adminUser = adminUserMapper.findByUserId(userId);
        return adminUser;
    }
}
