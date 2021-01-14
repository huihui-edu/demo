package myboot.myblog.controller.admin;

import myboot.myblog.domain.Blog;
import myboot.myblog.domain.BlogDetail;
import myboot.myblog.service.BlogService;
import myboot.myblog.service.CategoryService;
import myboot.myblog.utils.PageQueryUtil;
import myboot.myblog.utils.PageResult;
import myboot.myblog.utils.Result;
import myboot.myblog.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired(required = true)
    private BlogService blogService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 博客列表和搜索功能
     * @param params
     * @return
     */
    @GetMapping("/blogs/list")
    @ResponseBody
    public Result list(@RequestParam Map<String,Object> params){
        if (StringUtils.isEmpty((String) params.get("page")) ||StringUtils.isEmpty((String) params.get("limit")) ){
            ResultGenerator.getFailResult("参数异常！");
        }
        //params携带的参数: _search  limit: 10  page: 1  order: asc or desc
        PageResult result = blogService.findBlogByPage(params);
        return ResultGenerator.getSuccessResult(result);
    }

    @GetMapping("/blogs")
    public String showBlogs(HttpServletRequest request){
        request.setAttribute("path","blogs");
        return "admin/blog";
    }

    /**
     * 添加博客
     * @return
     */
    @GetMapping("/blogs/edit")
    public String saveBlog(HttpServletRequest request){
        PageQueryUtil queryUtil = new PageQueryUtil(1,1000,"asc",0,null,null);
        //查询所有的分类,显示在前台
        PageResult result = categoryService.findCategories(queryUtil);
        request.setAttribute("categories",result.getList());
        request.setAttribute("path","edit");
        return "admin/edit";
    }

    /**
     * 修改博客
     * @return
     */
    @GetMapping("/blogs/edit/{id}")
    public String editBlog(HttpServletRequest request,@PathVariable("id")Integer blogId){
        Blog blog = blogService.findBlogById(blogId);
        request.setAttribute("blog",blog);
        PageQueryUtil queryUtil = new PageQueryUtil(1,1000,"asc",0,null,null);
        //查询所有的分类,显示在前台
        PageResult result = categoryService.findCategories(queryUtil);
        request.setAttribute("categories",result.getList());
        request.setAttribute("path","edit");
        return "admin/edit";
    }

    /**
     * 修改博客
     * @param request
     * @param blog
     * @return
     */
    @PostMapping("/blogs/update")
    @ResponseBody
    public Result update(HttpServletRequest request,Blog blog){
        String result = blogService.updateBlog(blog);
        if ("success".equals(result)){
            return ResultGenerator.getSuccessResult("success");
        }else {
            return ResultGenerator.getFailResult(result);
        }
    }

    /**
     * 保存博客
     * @param blog
     * @return
     */
    @PostMapping("/blogs/save")
    @ResponseBody
    public Result saveBlog(Blog blog){
        String result = blogService.saveBlog(blog);
        if ("success".equals(result)){
            return ResultGenerator.getSuccessResult("success");
        }else {
            return ResultGenerator.getFailResult(result);
        }
    }

    @PostMapping("/blogs/delete")
    @ResponseBody
    public Result deleteBlog(@RequestBody Integer[] ids){
        String result = blogService.deleteBlog(ids);
        if ("success".equals(result)){
            return ResultGenerator.getSuccessResult("success");
        }else {
            return ResultGenerator.getFailResult(result);
        }
    }

}
