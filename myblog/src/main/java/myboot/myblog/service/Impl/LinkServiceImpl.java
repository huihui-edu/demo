package myboot.myblog.service.Impl;

import myboot.myblog.domain.BlogLink;
import myboot.myblog.mapper.LinkMapper;
import myboot.myblog.service.LinkService;
import myboot.myblog.utils.PageQueryUtil;
import myboot.myblog.utils.PageResult;
import myboot.myblog.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkMapper linkMapper;

    @Override
    public PageResult findAllLink(PageQueryUtil page) {
        List<BlogLink> links = linkMapper.findAllLinks(page.getStart(), page.getLimit(),page.getOrder(),page.getStr());
        int total = linkMapper.totalLinks();
        PageResult result = new PageResult(page.getCurrentPage(),page.getLimit(),total,links);
        return result;
    }

    @Override
    public int totalLink() {
        return linkMapper.totalLinks();
    }

    @Override
    public List<BlogLink> findLinkByType(int status) {
        List<BlogLink> link = linkMapper.findLinkByType(status);
        return link;
    }

    /**
     * 新建链接
     * @param link
     * @return
     */
    @Override
    public int saveLink(BlogLink link) {
        //设置没有的属性
        link.setCreateTime(new Date());
        int rows = linkMapper.saveLink(link);
        return rows;
    }

    /**
     * 更新链接
     * @param link
     * @return
     */
    @Override
    public int updateLink(BlogLink link) {
        int rows = linkMapper.updateLink(link);
        return rows;
    }

    /**
     * 根据链接id查询
     * @param linkId
     * @return
     */
    @Override
    public BlogLink findLinkById(Integer linkId) {
        return linkMapper.findLinkById(linkId);
    }

    /**
     * 删除链接
     * @param ids
     * @return
     */
    @Override
    public int deleteLink(Integer[] ids) {
        int sum = 0;
        for (Integer id : ids) {
            sum += linkMapper.deleteLink(id);
        }
        if (sum != 0 && sum == ids.length){
            return sum;
        }else {
            return 0;
        }
    }
}
