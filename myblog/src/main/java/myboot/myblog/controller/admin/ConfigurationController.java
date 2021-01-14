package myboot.myblog.controller.admin;

import myboot.myblog.domain.BlogConfig;
import myboot.myblog.service.ConfigurationService;
import myboot.myblog.utils.Result;
import myboot.myblog.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    //跳转系统配置页面
    @GetMapping("/configurations")
    public String configuration(HttpServletRequest request){
        Map<String, String> configurations = configurationService.findAllConfigs();
        request.getSession().setAttribute("path","configurations");
        request.setAttribute("configurations",configurations);
        return "admin/configuration";
    }

    //修改站点信息
    @PostMapping("/configurations/website")
    @ResponseBody
    public Result updateWebsite(@RequestParam Map<String,String> params){
        int rows = configurationService.updateWebsite(params);
        if (rows == params.size()) {
            return ResultGenerator.getSuccessResult(rows);
        }else {
            return ResultGenerator.getFailResult("修改失败!");
        }
    }

    //修改个人信息
    @PostMapping("/configurations/userInfo")
    @ResponseBody
    public Result updateUserInfo(@RequestParam Map<String,String> params){
        int rows = configurationService.updateUserInfo(params);
        if (rows == params.size()) {
            return ResultGenerator.getSuccessResult(rows);
        }else {
            return ResultGenerator.getFailResult("修改失败!");
        }
    }

    //修改底部设置
    @PostMapping("/configurations/footer")
    @ResponseBody
    public Result updateFooter(@RequestParam Map<String,String> params){
        int rows = configurationService.updateFooter(params);
        if (rows == params.size()) {
            return ResultGenerator.getSuccessResult(rows);
        }else {
            return ResultGenerator.getFailResult("修改失败!");
        }
    }

}
