package myboot.myblog.controller.admin;

import myboot.myblog.service.TagService;
import myboot.myblog.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    //跳转标签管理页面
    @GetMapping("/tags")
    public String toTags(HttpServletRequest request){
        request.setAttribute("path","tags");
        return "admin/tag";
    }
    //查询所有标签信息
    @GetMapping("/tags/list")
    @ResponseBody
    public Result tagsList(@RequestParam Map<String,Object> params){
        PageQueryUtil page = PageUtil.getPageResult(params);
        PageResult tags = tagService.findTags(page);
        return ResultGenerator.getSuccessResult(tags);
    }
    //添加标签
    @PostMapping("/tags/save")
    @ResponseBody
    public Result addTag(@RequestParam("tagName")String name){
        String result = tagService.addTag(name);
        if (("success").equals(result)){
            return ResultGenerator.getSuccessResult("success");
        }else {
            return ResultGenerator.getFailResult("添加失败，请稍后重试！");
        }
    }
    //删除标签
    @PostMapping("/tags/delete")
    @ResponseBody
    public Result deleteTag(@RequestBody Integer[] ids){
        String result = tagService.deleteTag(ids);
        if (("success").equals(result)){
            return ResultGenerator.getSuccessResult("success");
        }else {
            return ResultGenerator.getFailResult("删除失败，请稍后重试!");
        }
    }


}
