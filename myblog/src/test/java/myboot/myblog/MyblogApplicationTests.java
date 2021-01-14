package myboot.myblog;

import myboot.myblog.domain.AdminUser;
import myboot.myblog.domain.BlogCategory;
import myboot.myblog.domain.BlogLink;
import myboot.myblog.mapper.ConfigurationMapper;
import myboot.myblog.mapper.TagMapper;
import myboot.myblog.service.AdminUserService;
import myboot.myblog.service.BlogService;
import myboot.myblog.service.CategoryService;
import myboot.myblog.service.LinkService;
import myboot.myblog.utils.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MyblogApplicationTests {

    @Autowired
    AdminUserService adminUserService;
    @Autowired
    BlogService blogService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    LinkService linkService;
    @Autowired
    TagMapper tagMapper;
    @Autowired
    ConfigurationMapper mapper;
    @Test
    void contextLoads() throws ParseException {

        Map<String,Object> map = new HashMap();
        map.put("_search",false);
        map.put("nd","1609471516083");
        map.put("limit","5");
        map.put("page","1");
        map.put("sidx","linkRank");
        map.put("order","asc");
        map.put("keyword","");

//        PageQueryUtil result = PageUtil.getPageResult(map);
//        PageResult result = blogService.findBlogByPage(map);
//        System.out.println(result.getList().get(0));

//        String s = categoryService.saveCategory("刷个", "/admin/dist/img/category/16.png");
//        System.out.println(s);
//        System.out.println(DateUtil.getNowDate());
//        System.out.println(new Date());
//        String string = PageUtil.getString("adminUser");
//        System.out.println(string);
//        PageResult link = linkService.findAllLink(result);
//        List<BlogLink> lists = (List<BlogLink>) link.getList();
//        for (BlogLink list : lists) {
//            System.out.println(list.getLinkRank());
//        }
//        map.remove("sidx");
//        map.put("sidx","linkRank");
//        result = PageUtil.getPageResult(map);
//        PageResult link1 = linkService.findAllLink(result);
//        List<BlogLink> list = (List<BlogLink>) link1.getList();
//        for (BlogLink link2 : list) {
//            System.out.println(link2.getLinkRank());
//        }
//        tagMapper.saveTagRelation(3,68,new Date());

    }

}
