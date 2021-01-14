package myboot.myblog.service;

import myboot.myblog.domain.BlogLink;
import myboot.myblog.utils.PageQueryUtil;
import myboot.myblog.utils.PageResult;

import java.util.List;

public interface LinkService {
    /**
     * 查询所有的链接
     * @param page
     * @return
     */
    PageResult findAllLink(PageQueryUtil page);

    int totalLink();

    List<BlogLink> findLinkByType(int status);

    /**
     * 新建链接
     * @param link
     * @return
     */
    int saveLink(BlogLink link);

    /**
     * 更新
     * @param link
     * @return
     */
    int updateLink(BlogLink link);

    /**
     * 根据id查询链接
     * @param linkId
     * @return
     */
    BlogLink findLinkById(Integer linkId);

    /**
     * 删除链接
     * @param ids
     * @return
     */
    int deleteLink(Integer[] ids);
}
