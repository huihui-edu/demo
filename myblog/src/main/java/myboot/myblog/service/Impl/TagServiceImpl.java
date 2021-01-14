package myboot.myblog.service.Impl;

import myboot.myblog.domain.BlogTag;
import myboot.myblog.domain.TagCount;
import myboot.myblog.mapper.TagMapper;
import myboot.myblog.service.TagService;
import myboot.myblog.utils.DateUtil;
import myboot.myblog.utils.PageQueryUtil;
import myboot.myblog.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    /**
     * 后台管理功能
     */
    @Autowired
    private TagMapper tagMapper;
    //查询
    @Override
    public PageResult findTags(PageQueryUtil page) {
        List<BlogTag> list = tagMapper.findAllTags(page.getStart(), page.getLimit(),page.getOrder(),page.getStr());
        int total = tagMapper.totalTags();
        PageResult result = new PageResult(page.getCurrentPage(), page.getLimit(), total, list);
        return result;
    }

    @Override
    public int totalTags() {
        return tagMapper.totalTags();
    }
    //保存
    @Override
    @Transactional
    public String addTag(String name) {
        List<BlogTag> tags = tagMapper.findByName(name);
        if (tags.size() > 0){
            return "error";
        }
        try {
            BlogTag tag = new BlogTag();
            tag.setTagName(name);
            tag.setIsDeleted((byte)0);
            tag.setCreateTime(DateUtil.getNowDate());
            int rows = tagMapper.saveTag(tag);
            if (rows > 0){
                return "success";
            }else {
                return "error";
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    //删除
    @Override
    @Transactional
    public String deleteTag(Integer[] ids) {
        try {
            int sum = 0;
            for (int i = 0 ; i < ids.length ; i++) {
                int row = tagMapper.deleteTag(ids[0]);
                sum += row;
            }
            if (sum == ids.length && sum != 0){
                return "success";
            }else {
                return "error";
            }
        } catch (Exception e) {
            throw new RuntimeException("删除失败！");
        }
    }

    /**
     * 前台显示tagName,TagCount
     * @return
     */
    @Override
    public List<TagCount> findAll() {
        List<BlogTag> tags = tagMapper.findTagUsed();
        List<TagCount> list = new ArrayList<>();
        for (BlogTag tag : tags) {
            TagCount tagCount = new TagCount();
            tagCount.setTagId(tag.getTagId());
            tagCount.setTagName(tag.getTagName());
            tagCount.setTagCount(tagMapper.TagUsedCount(tag.getTagId()));
            list.add(tagCount);
        }
        return list;
    }
}
