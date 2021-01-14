package myboot.myblog.service.Impl;

import myboot.myblog.domain.*;
import myboot.myblog.mapper.BlogMapper;
import myboot.myblog.mapper.CategoryMapper;
import myboot.myblog.mapper.CommentMapper;
import myboot.myblog.mapper.TagMapper;
import myboot.myblog.service.BlogService;
import myboot.myblog.service.CategoryService;
import myboot.myblog.service.CommentService;
import myboot.myblog.service.TagService;
import myboot.myblog.utils.MarkDownUtil;
import myboot.myblog.utils.PageUtil;
import myboot.myblog.utils.PageQueryUtil;
import myboot.myblog.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 管理员
     * 分页查询所有博客
     * @return
     */
    @Override
    public PageResult findBlogByPage(Map<String,Object> params) {
        //解析查询需要的参数
        PageQueryUtil page = PageUtil.getPageResult(params);
        //总记录数
        int total = blogMapper.totalCount(1);
        List<Blog> list = blogMapper.findBlogByPage(page.getStart(),page.getLimit(),page.getOrder(),page.getKeyword(),page.getStr());
        PageResult result = new PageResult(page.getCurrentPage(),page.getLimit(),total,list);
        return result;

    }

    /**
     * 管理员
     * 添加博客
     * @param blog
     * @return
     */
    @Transactional
    @Override
    public String saveBlog(Blog blog) {
        //事务控制
        try {
            String[] tags = blog.getBlogTags().split(",");
            //关系不存在时，新建标签
            List<Integer> list = new ArrayList();
            for (String tag : tags) {
                List<BlogTag> t = tagMapper.findByName(tag);
                if (t.size() == 0){
                    //新建标签
                    BlogTag newTag = new BlogTag();
                    newTag.setTagName(tag);
                    newTag.setIsDeleted((byte)0);
                    newTag.setCreateTime(new Date());
                    tagMapper.saveTag(newTag);
                    //查询标签的id并存入list中
                    List<BlogTag> byName = tagMapper.findByName(tag);
                    list.add(byName.get(0).getTagId());
                }else {
                    //标签存在
                    list.add(t.get(0).getTagId());
                }
            }
            //查询分类
            List<BlogCategory> categories = categoryMapper.findCateById(blog.getBlogCategoryId());
            //设置博客的一些默认值
            blog.setBlogCategoryName(categories.get(0).getCategoryName());
            blog.setBlogSubUrl("");
            blog.setBlogViews(0);
            blog.setIsDeleted(0);
            blog.setCreateTime(new Date());
            //保存博客
            blogMapper.saveBlog(blog);
            //获取博客的Id
            int blogId = blogMapper.searchBlog(blog.getBlogTitle(), 0).get(0).getBlogId();
            //建立博客和标签的关系
            for (Integer tagId : list) {
                tagMapper.saveTagRelation(blogId,tagId,new Date());
            }
            return "success";
        } catch (Exception e) {
            throw new RuntimeException("保存失败!");
        }
    }

    /**
     * 管理员
     * 修改博客
     * @param blog
     */
    @Override
    public String updateBlog(Blog blog) {
        blog.setUpdateTime(new Date());
        //更新操作
        int rows = blogMapper.updateBlog(blog);
        if (rows > 0){
            return "success";
        }else {
            return "修改失败!";
        }
    }

    /**
     * 管理员
     * 根据ID查询博客
     * @param blogId
     * @return
     */
    @Override
    public Blog findBlogById(Integer blogId) {
        Blog blog = blogMapper.findBlogById(blogId);
        return blog;
    }

    /**
     * 博客总数
     * @return
     */
    @Override
    public int totalBlog() {
        return blogMapper.totalCount(1);
    }

    /**
     * 查询博客
     * @param pageNum
     * @return
     */
    @Override
    public PageResult findBlog(int pageNum) {
        int count = blogMapper.totalCount(0);
        int start = (pageNum - 1) * 5;
        List<Blog> blogs = blogMapper.findBlog(start);
        PageResult result = new PageResult(pageNum,5,count,blogs);
        return result;
    }

    /**
     * 首页
     * 根据浏览量排序博客
     * @return
     */
    @Override
    public List<Blog> findByViews() {
        List<Blog> views = blogMapper.findByViews();
        return views;
    }

    /**
     * 首页
     * 博客详情
     * @param id
     * @return
     */
    @Override
    public BlogDetail findBlogDetailById(Integer id) {
        //博客内容
        BlogDetail blogDetail = blogMapper.findBlogDetailById(id);
        //博客的标签
        List<String> tagList = tagMapper.findTagByBlog(id);
        //博客的留言数量
        List<BlogComment> comments = commentMapper.totalCommentByBlogId(id,-1);
        blogDetail.setCommentCount(comments.size());
        //将markDown形式转换成Html
        blogDetail.setBlogContent(MarkDownUtil.mdToHtml(blogDetail.getBlogContent()));
        blogDetail.setBlogTags(tagList);
        return blogDetail;
    }

    /**
     * 首页
     * 博客的浏览量加一
     * @param id
     */
    @Override
    public void AddViews(Integer id) {
        blogMapper.addView(id);
    }

    /**
     * 首页
     * 根据标签id查询博客
     * @param tagName
     * @param page
     * @return
     */
    @Override
    public PageResult findBlogByTagId(String tagName, Integer page) {
        List<Blog> blogSize = blogMapper.totalCountByTag(tagName,0);
        int total = blogSize.size();
        int start = (page - 1) * 5;
        List<Blog> blogList = blogMapper.totalCountByTag(tagName, start);
        PageResult result = new PageResult(page,5,total,blogList);
        return result;
    }

    /**
     * 首页
     * 根据分类名称查询博客列表
     * @param categoryName
     * @param pageNum
     * @return
     */
    @Override
    public PageResult findBlogByCate(String categoryName, Integer pageNum) {
        List<Blog> totalBlog = blogMapper.findBlogByCate(categoryName, 0);
        int total = totalBlog.size();
        int start = (pageNum - 1) * 5;
        List<Blog> list = blogMapper.findBlogByCate(categoryName, start);
        PageResult result = new PageResult(pageNum,5,total,list);
        return result;
    }

    /**
     * 首页
     * 模糊搜索
     * @param keyword
     * @param pageNum
     * @return
     */
    @Override
    public PageResult searchBlog(String keyword, Integer pageNum) {
        keyword = "%" + keyword + "%";
        List<Blog> searchBlog = blogMapper.searchBlog(keyword, 0);
        int total = searchBlog.size();
        int start = (pageNum - 1) * 5;
        List<Blog> list = blogMapper.searchBlog(keyword, start);
        PageResult result = new PageResult(pageNum,5,total,list);
        return result;
    }

    @Transactional
    @Override
    public String deleteBlog(Integer[] ids) {
        try {
            //计数,计算被删除的博客数量
            int sum = 0;
            for (Integer blogId : ids) {
                int row = blogMapper.deleteBlog(blogId);
                sum += row;
            }
            if (sum != 0 && sum == ids.length){
                return "success";
            }else {
                return "删除失败!";
            }
        } catch (Exception e) {
            throw new RuntimeException("删除失败!");
        }
    }
}
