package myboot.myblog.mapper;

import myboot.myblog.domain.BlogLink;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkMapper {
    /**
     * 查询所有链接
     * @param start
     * @param limit
     * @return
     */
    List<BlogLink> findAllLinks(@Param("start")int start,@Param("limit") int limit,
                                @Param("order")String order,@Param("str")String str);

    /**
     * 查询链接数量
     * @return
     */
    int totalLinks();

    List<BlogLink> findLinkByType(int status);

    /**
     * 新建链接
     * @param link
     * @return
     */
    int saveLink(BlogLink link);

    /**
     * 更新链接
     * @param link
     * @return
     */
    int updateLink(BlogLink link);

    /**
     * 删除链接
     * @param linkId
     * @return
     */
    int deleteLink(int linkId);

    /**
     * 根据id查询链接
     * @param linkId
     * @return
     */
    BlogLink findLinkById(Integer linkId);
}
