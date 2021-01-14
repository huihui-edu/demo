package myboot.myblog.controller.admin;

import myboot.myblog.domain.BlogLink;
import myboot.myblog.service.LinkService;
import myboot.myblog.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class LinkController {

    @Autowired
    private LinkService linkService;

    //跳转链接页面
    @GetMapping("/links")
    public String toLink(HttpServletRequest request){
        request.setAttribute("path","links");
        return "admin/link";
    }
    //查询所有链接
    @GetMapping("/links/list")
    @ResponseBody
    public Result linkList(@RequestParam Map<String,Object> params){
        PageQueryUtil page = PageUtil.getPageResult(params);
        PageResult links = linkService.findAllLink(page);
        return ResultGenerator.getSuccessResult(links);
    }

    /**
     * 添加链接
     * @param link
     * @return
     */
    @PostMapping("/links/save")
    @ResponseBody
    public Result saveLink(BlogLink link){
        int result = linkService.saveLink(link);
        if (result > 0){
            return ResultGenerator.getSuccessResult(result);
        }else {
            return ResultGenerator.getFailResult("添加失败!");
        }
    }

    @GetMapping("/links/info/{linkId}")
    @ResponseBody
    public Result toUpdateLink(@PathVariable("linkId")Integer linkId,HttpServletRequest request){
        BlogLink link = linkService.findLinkById(linkId);
        if (link == null){
            return ResultGenerator.getFailResult("网络异常!");
        }else {
            return ResultGenerator.getSuccessResult(link);
        }
    }

    /**
     * 链接修改
     * @param link
     * @return
     */
    @PostMapping("/links/update")
    @ResponseBody
    public Result updateLink(BlogLink link){
        int result = linkService.updateLink(link);
        if (result > 0){
            return ResultGenerator.getSuccessResult(result);
        }else {
            return ResultGenerator.getFailResult("添加失败!");
        }
    }
    @PostMapping("/links/delete")
    @ResponseBody
    public Result deleteLink(@RequestBody Integer[] ids){
        int result = linkService.deleteLink(ids);
        if (result == 0){
            return ResultGenerator.getFailResult("删除失败!");
        }else {
            return ResultGenerator.getSuccessResult(result);
        }
    }
}
