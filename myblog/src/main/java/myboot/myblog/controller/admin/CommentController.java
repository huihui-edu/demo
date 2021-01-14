package myboot.myblog.controller.admin;

import myboot.myblog.service.CommentService;
import myboot.myblog.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //跳转评论管理页面
    @GetMapping("/comments")
    public String toComments(HttpServletRequest request){
        request.setAttribute("path","comments");
        return "admin/comment";
    }
    //获取评论信息
    @GetMapping("/comments/list")
    @ResponseBody
    public Result commentList(@RequestParam Map<String,Object> params){
        PageQueryUtil page = PageUtil.getPageResult(params);
        PageResult comments = commentService.findComments(page);
        return ResultGenerator.getSuccessResult(comments);
    }
    //回复评论
    @PostMapping("/comments/reply")
    @ResponseBody
    public Result replyComment(@RequestParam Map<String,Object> params){
        String result = commentService.replyComment(params);
        if (("success").equals(result)){
            return ResultGenerator.getSuccessResult("success");
        }else {
            return ResultGenerator.getFailResult(result);
        }
    }
    //审核评论
    @PostMapping("/comments/checkDone")
    @ResponseBody
    public Result checkComment(@RequestBody Integer[] ids){
        String result = commentService.checkComment(ids);
        if (("success").equals(result)){
            return ResultGenerator.getSuccessResult("success");
        }else {
            return ResultGenerator.getFailResult(result);
        }
    }
    //删除评论
    @PostMapping("/comments/delete")
    @ResponseBody
    public Result deleteComment(@RequestBody Integer[] ids){
        String result = commentService.deleteComment(ids);
        if (("success").equals(result)) {
            return ResultGenerator.getSuccessResult("success");
        }else {
            return ResultGenerator.getFailResult(result);
        }
    }
}
