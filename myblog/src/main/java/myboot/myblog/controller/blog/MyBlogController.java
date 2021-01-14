package myboot.myblog.controller.blog;

import myboot.myblog.domain.*;
import myboot.myblog.service.*;
import myboot.myblog.utils.PageQueryUtil;
import myboot.myblog.utils.PageResult;
import myboot.myblog.utils.Result;
import myboot.myblog.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;


@Controller
public class MyBlogController {
    //当前主题
    private String theme = "blog/amaze/";

    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private TagService tagService;
    @Autowired
    private LinkService linkService;

    /**
     * 接收"/"请求，直接调用index方法，传入参数1和request
     * @param request
     * @return
     */
    @GetMapping(value = {"/","/index"})
    public String toIndex(HttpServletRequest request) {
        return this.index(request,1);
    }

    /**
     * 首页
     * @param request
     * @param pageNum
     * @return
     */
    @GetMapping(value = "/page/{pageNum}")
    public String index(HttpServletRequest request, @PathVariable("pageNum") int pageNum){
        request.setAttribute("configurations",configurationService.findAllConfigs());
        //根据页码查询博客
        PageResult result = blogService.findBlog(pageNum);
        //生成页面固有信息
        SideBuild(request,pageNum);
        //博客列表
        request.setAttribute("blogPageResult",result);
        //页面名字
        request.setAttribute("pageName","首页");
        return theme + "index";
    }

    /**
     * 博客详情页
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/blog/{blogId}")
    public String DetailBlog(@PathVariable("blogId")Integer id,HttpServletRequest request ,
                             @RequestParam(value = "commentPage",required = false,defaultValue = "1")Integer commentPage){
        //博客详情
        BlogDetail blog = blogService.findBlogDetailById(id);
        //留言详情
        PageResult result = commentService.findCommentByBlog(id, commentPage);
        //点击一次详情页，博客的浏览量加1
        blogService.AddViews(id);
        request.setAttribute("blogDetailVO",blog);
        request.setAttribute("commentPageResult",result);
        request.setAttribute("configurations",configurationService.findAllConfigs());
        request.setAttribute("pageName","详情页");
        return theme + "detail";
    }

    /**
     * 留言功能
     * @param params
     * @param session
     * @return
     */
    @PostMapping("/blog/comment")
    @ResponseBody
    public Result saveComment(@RequestParam Map<String,String> params, HttpSession session){
        //1.判断验证码是否正确,从session中获取正确的session
        String trueVerifyCode = (String) session.getAttribute("successCode");
        String verifyCode = params.get("verifyCode");
        if (!trueVerifyCode.equals(verifyCode)){
            return ResultGenerator.getFailResult("验证码不正确!");
        }
        String result = commentService.saveComment(params);
        if ("success".equals(result)){
            return ResultGenerator.getSuccessResult("success");
        }else {
            return ResultGenerator.getFailResult("发表失败，请稍后重试!");
        }
    }

    /**
     * 接收根据标签查询的博客列表，调用tag方法
     * @param tagName
     * @param request
     * @return
     */
    @GetMapping("/tag/{tagName}")
    public String tag(@PathVariable("tagName") String tagName,HttpServletRequest request){
        return tagList(tagName,1,request);
    }

    /**
     * 根据Tag查询博客列表
     * @param tagName
     * @param page
     * @param request
     * @return
     */
    @GetMapping("/tag/{tagName}/{page}")
    public String tagList(@PathVariable("tagName")String tagName,@PathVariable("page")Integer page,HttpServletRequest request){
        PageResult result = blogService.findBlogByTagId(tagName, page);
        //前台显示的博客
        request.setAttribute("blogPageResult",result);
        //标题
        request.setAttribute("pageName", "标签");
        //链接的地址
        request.setAttribute("keyword", tagName);
        request.setAttribute("pageUrl", "tag");
        //生成页面固有信息
        SideBuild(request,page);
        return theme + "list";
    }

    @GetMapping("/category/{categoryName}")
    public String toCategory(HttpServletRequest request,@PathVariable("categoryName") String categoryName){
        return category(request,categoryName,1);
    }

    /**
     * 根据Category查询博客列表
     * @param request
     * @param categoryName
     * @param pageNum
     * @return
     */
    @GetMapping("/category/{categoryName}/{pageNum}")
    public String category(HttpServletRequest request,@PathVariable("categoryName")String categoryName,@PathVariable("pageNum")Integer pageNum){
        //分类博客列表
        PageResult result = blogService.findBlogByCate(categoryName, pageNum);
        request.setAttribute("blogPageResult",result);
        //拼接链接的地址
        request.setAttribute("keyword", categoryName);
        request.setAttribute("pageUrl", "tag");
        //页面名称
        request.setAttribute("pageName","分类详情");
        //生成页面固有信息
        SideBuild(request,pageNum);
        return theme + "list";
    }

    @GetMapping("/search/{keyword}")
    public String toSearch(HttpServletRequest request,@PathVariable("keyword") String keyword){
        return search(request,keyword,1);
    }

    /**
     * 文章标题搜索
     * @param request
     * @param keyword
     * @param pageNum
     * @return
     */
    @GetMapping("/search/{keyword}/{pageNum}")
    public String search(HttpServletRequest request,@PathVariable("keyword")String keyword,@PathVariable("pageNum") Integer pageNum){
        PageResult result = blogService.searchBlog(keyword, pageNum);
        request.setAttribute("blogPageResult",result);
        //页面名称
        request.setAttribute("pageName","搜索");
        //拼接链接的地址
        request.setAttribute("keyword", keyword);
        request.setAttribute("pageUrl", "search");
        //生成页面固有信息
        SideBuild(request,pageNum);
        return theme + "list";
    }

    @GetMapping("/link")
    public String link(HttpServletRequest request){
        //友链,favoriteLinks,Type值为0
        List<BlogLink> favoriteLinks = linkService.findLinkByType(0);
        request.setAttribute("favoriteLinks",favoriteLinks);
        //推荐网站,recommendLinks,Type值为1
        List<BlogLink> recommendLinks = linkService.findLinkByType(1);
        request.setAttribute("recommendLinks",recommendLinks);
        //个人网站personalLinks
        List<BlogLink> personalLinks = linkService.findLinkByType(2);
        request.setAttribute("personalLinks",personalLinks);
        //系统设置
        request.setAttribute("configurations",configurationService.findAllConfigs());
        request.setAttribute("pageName","链接");
        return theme + "link";
    }

    /**
     * 侧边栏信息
     * @param request
     * @param pageNum
     */
    public void SideBuild(HttpServletRequest request,int pageNum){
        //热门标签
        request.setAttribute("hotTags",tagService.findAll());
        //点击最多,显示前五条
        List<Blog> byViews = blogService.findByViews();
        if (byViews.size() < 5) {
            request.setAttribute("hotBlogs", byViews.subList(0, byViews.size()));
        }else {
            request.setAttribute("hotBlogs", byViews.subList(0, 5));
        }
        //最新发布,显示前五条
        List<Blog> list = (List<Blog>) blogService.findBlog(pageNum).getList();
        if (list.size() < 5) {
            request.setAttribute("newBlogs", list.subList(0, list.size()));
        }else {
            request.setAttribute("newBlogs", list.subList(0, 5));
        }
        //系统设置
        request.setAttribute("configurations",configurationService.findAllConfigs());
    }

}
