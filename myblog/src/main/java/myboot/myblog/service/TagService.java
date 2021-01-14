package myboot.myblog.service;

import myboot.myblog.domain.BlogTag;
import myboot.myblog.domain.TagCount;
import myboot.myblog.utils.PageQueryUtil;
import myboot.myblog.utils.PageResult;

import java.util.List;

public interface TagService {
    /**
     * 后台管理功能
     * @param page
     * @return
     */
    //后台查询所有标签
    PageResult findTags(PageQueryUtil page);

    int totalTags();

    String addTag(String name);

    String deleteTag(Integer[] ids);

    /**
     * 前台首页功能
     * @return
     */
    //首页查询所有标签
    List<TagCount> findAll();
}
