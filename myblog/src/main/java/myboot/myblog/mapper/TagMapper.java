package myboot.myblog.mapper;

import myboot.myblog.domain.BlogTag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TagMapper {
    /**
     * 查询所有标签
     * @param start
     * @param limit
     * @return
     */
    List<BlogTag> findAllTags(int start, int limit,String order,String str);

    int totalTags();

    /**
     * 根据名字查询
     * @param name
     * @return
     */
    List<BlogTag> findByName(String name);

    /**
     * 保存
     * @param tag
     * @return
     */
    int saveTag(BlogTag tag);

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteTag(Integer id);


    /**
     * 查询被引用的标签
     * @return
     */
    List<BlogTag> findTagUsed();
    //查询被引用的次数
    int TagUsedCount(int tagId);
    //查询博客所属标签
    List<String> findTagByBlog(int id);

    /**
     * 新建标签个博客的关系
     */
    void saveTagRelation(@Param("blogId") int blogId, @Param("tagId") int tagId, @Param("date") Date date);

}
