package myboot.myblog.controller.admin;

import myboot.myblog.domain.AdminUser;
import myboot.myblog.service.*;
import myboot.myblog.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private TagService tagService;
    @Autowired
    private LinkService linkService;

    //跳转登录页
    @GetMapping("/login")
    public String toLogin(){
        return "admin/login";
    }
    //登录
    @PostMapping("/login")
    public String login(String userName, String password, String verifyCode, HttpSession session) {
        //1.判断验证码是否正确,从session中获取正确的session
        String trueVerifyCode = (String) session.getAttribute("successCode");
        if ("ba" == null){
            session.setAttribute("errorMsg","验证码不正确！");
            return "admin/login";
        }
        //2.判断是否存在该用户
        AdminUser adminUser = adminUserService.findByUserName(userName);
        if (adminUser == null){
            session.setAttribute("errorMsg","用户名输入错误！");
            return "admin/login";
        }
        //3.判断密码是否正确
        if (!adminUser.getLoginPassword().equals(MD5Utils.MD5Encoding(password))){
            session.setAttribute("errorMsg","密码不正确！");
            return "admin/login";
        }
        //4.将用户存入session中
        session.setAttribute("loginUser",adminUser);
        //5.返回index首页
        return "redirect:/admin/index";
    }

    //跳转首页
    @GetMapping({"/","/index","index.html"})
    public String toIndex(HttpServletRequest request){
        request.setAttribute("totalBlog",blogService.totalBlog());
        request.setAttribute("totalComment",commentService.totalComment());
        request.setAttribute("totalCategory",categoryService.totalCategory());
        request.setAttribute("totalTag",tagService.totalTags());
        request.setAttribute("totalLink",linkService.totalLink());
        request.setAttribute("path","index");
        return "admin/index";
    }

    //跳转修改密码页面
    @GetMapping("/profile")
    public String profile(HttpServletRequest request){
        //获取session中的用户
        AdminUser adminUser = (AdminUser) request.getSession().getAttribute("loginUser");
        //存入session中
        request.getSession().setAttribute("loginUserId",adminUser.getAdminUserId());
        request.setAttribute("loginUserName",adminUser.getLoginUsername());
        request.setAttribute("nickName",adminUser.getNickName());
        request.setAttribute("path","profile");
        return "admin/profile";
    }
    //修改登录信息
    @PostMapping("/profile/name")
    @ResponseBody
    public String profileName(String loginUserName,String nickName,HttpSession session){
        //获取当前用户
        Integer userId = (Integer) session.getAttribute("loginUserId");
        AdminUser adminUser = adminUserService.findByUserId(userId);
        AdminUser user = new AdminUser();
        user.setAdminUserId(adminUser.getAdminUserId());
        user.setLoginUsername(loginUserName);
        user.setNickName(nickName);
        if (adminUserService.updateAdminUser(user)){
            adminUser = adminUserService.findByUserId(userId);
            session.setAttribute("loginUser",adminUser);
            return "success";
        }else {
            return "修改失败";
        }
    }
    //修改密码
    @PostMapping("/profile/password")
    @ResponseBody
    public String profilePassword(String originalPassword,String newPassword,HttpSession session){
        Integer userId = (Integer) session.getAttribute("loginUserId");
        AdminUser adminUser = adminUserService.findByUserId(userId);
        if (!MD5Utils.MD5Encoding(originalPassword).equals(adminUser.getLoginPassword())) {
            return "修改失败";
        }
        //修改管理员密码
        AdminUser user = new AdminUser();
        user.setAdminUserId(userId);
        user.setLoginPassword(MD5Utils.MD5Encoding(newPassword));
        if (adminUserService.updateAdminUser(user)){
            return "success";
        }else {
            return "修改失败";
        }
    }

    //登出
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.setAttribute("loginUser",null);
        return "admin/login";
    }
}
