package myboot.myblog.service;

import myboot.myblog.domain.Blog;
import myboot.myblog.domain.BlogDetail;
import myboot.myblog.utils.PageQueryUtil;
import myboot.myblog.utils.PageResult;

import java.util.List;
import java.util.Map;

public interface BlogService {
    /**
     * 分页查询博客
     * @return
     */
    PageResult findBlogByPage(Map<String,Object> params);

    /**
     * 总文章数
     * @return
     */
    int totalBlog();

    /**
     * 根据页面查询博客
     * @param pageNum
     * @return
     */
    PageResult findBlog(int pageNum);

    /**
     * 根据浏览量排序博客
     * @return
     */
    List<Blog> findByViews();

    /**
     * 根据id查询博客详情
     * @param id
     * @return
     */
    BlogDetail findBlogDetailById(Integer id);

    /**
     * 博客浏览量加一
     * @param id
     */
    void AddViews(Integer id);

    /**
     * 根据标签id查询博客
     * @param tagName
     * @param page
     * @return
     */
    PageResult findBlogByTagId(String tagName, Integer page);

    /**
     * 根据分类查询博客
     * @param categoryName
     * @param pageNum
     */
    PageResult findBlogByCate(String categoryName, Integer pageNum);

    /**
     * 模糊搜索
     * @param keyword
     * @param pageNum
     * @return
     */
    PageResult searchBlog(String keyword, Integer pageNum);

    /**
     * 保存博客
     * @param blog
     * @return
     */
    String saveBlog(Blog blog);

    /**
     * 查询博客
     * @param blogId
     * @return
     */
    Blog findBlogById(Integer blogId);

    /**
     * 修改博客
     * @param blog
     */
    String updateBlog(Blog blog);

    /**
     * 批量删除博客
     * @param ids
     * @return
     */
    String deleteBlog(Integer[] ids);
}
