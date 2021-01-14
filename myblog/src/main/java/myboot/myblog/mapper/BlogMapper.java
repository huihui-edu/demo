package myboot.myblog.mapper;

import myboot.myblog.domain.Blog;
import myboot.myblog.domain.BlogDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogMapper {
    /**
     * 查询所有博客
     * @return
     */
    List<Blog> findBlogByPage(@Param("start")Integer start,@Param("limit")Integer limit,
                              @Param("order")String order,@Param("key")String key,@Param("str")String str);

    /**
     * 查询博客数量
     * @param
     * @return
     */
    int totalCount(int status);

    /**
     * 首页查询博客
     * @param start
     * @return
     */
    List<Blog> findBlog(@Param("start") int start);

    /**
     * 按照浏览量排序
     * @return
     */
    List<Blog> findByViews();

    /**
     * 查询博客
     * @param id
     * @return
     */
    BlogDetail findBlogDetailById(Integer id);

    /**
     * 浏览量加一
     * @param id
     */
    void addView(Integer id);

    /**
     * 根据标签查询博客
     * @param tagName
     * @return
     */
    List<Blog> totalCountByTag(@Param("tagName")String tagName,@Param("start") Integer start);

    /**
     * 根据分类名称查询博客
     * @param categoryName
     * @param start
     * @return
     */
    List<Blog> findBlogByCate(@Param("cateName")String categoryName,@Param("start") int start);

    /**
     * 模糊搜索
     * @param keyword
     * @param start
     * @return
     */
    List<Blog> searchBlog(@Param("keyword") String keyword,@Param("start") int start);

    /**
     * 保存博客,返回影响行
     * @param blog
     * @return
     */
    void saveBlog(Blog blog);

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
    int updateBlog(Blog blog);

    /**
     * 删除博客
     * @param blogId
     * @return
     */
    int deleteBlog(Integer blogId);
}
