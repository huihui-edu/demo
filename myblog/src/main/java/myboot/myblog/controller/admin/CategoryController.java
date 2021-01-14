package myboot.myblog.controller.admin;

import myboot.myblog.service.CategoryService;
import myboot.myblog.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //跳转分类首页
    @GetMapping("/categories")
    public String toCategory(HttpServletRequest request){
        request.setAttribute("path","categories");
        return "admin/category";
    }
    //查询所有分类列表
    @GetMapping("/categories/list")
    @ResponseBody
    public Result categoryList(@RequestParam Map<String,Object> params){
        //解析参数
        PageQueryUtil page = PageUtil.getPageResult(params);
        //获取查询的数据
        PageResult categories = categoryService.findCategories(page);
        //返回Json数据
        return ResultGenerator.getSuccessResult(categories);
    }
    //保存新的类别
    @PostMapping("/categories/save")
    @ResponseBody
    public Result saveCategory(@RequestParam Map<String,Object> params) {
        String categoryName = (String) params.get("categoryName");
        String categoryIcon = (String) params.get("categoryIcon");
        String saveResult = categoryService.saveCategory(categoryName, categoryIcon);
        if (("success").equals(saveResult)){
            return ResultGenerator.getSuccessResult("success");
        }else {
            return ResultGenerator.getFailResult("添加失败，请稍后重试！");
        }
    }
    //修改类别
    @PostMapping("/categories/update")
    @ResponseBody
    public Result updateCategory(@RequestParam Map<String,Object> params){
        String categoryId = (String) params.get("categoryId");
        String categoryName = (String) params.get("categoryName");
        String categoryIcon = (String) params.get("categoryIcon");
        String result = categoryService.updateCategory(categoryId, categoryName, categoryIcon);
        if (("success").equals(result)){
            return ResultGenerator.getSuccessResult("success");
        }else {
            return ResultGenerator.getFailResult("修改失败，请稍后重试！");
        }
    }
    //删除类别
    @PostMapping("/categories/delete")
    @ResponseBody
    public Result deleteCategory(@RequestBody Integer[] ids){
        String result = categoryService.deleteCategory(ids);
        if (("success").equals(result)){
            return ResultGenerator.getSuccessResult("success");
        }else {
            return ResultGenerator.getFailResult("删除失败，请稍后重试！");
        }
    }
}
