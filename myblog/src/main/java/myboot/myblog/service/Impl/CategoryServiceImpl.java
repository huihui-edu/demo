package myboot.myblog.service.Impl;

import myboot.myblog.domain.BlogCategory;
import myboot.myblog.mapper.CategoryMapper;
import myboot.myblog.service.CategoryService;
import myboot.myblog.utils.DateUtil;
import myboot.myblog.utils.PageQueryUtil;
import myboot.myblog.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 查询全部类别
     * @param page
     * @return
     */
    @Override
    public PageResult findCategories(PageQueryUtil page) {
        List<BlogCategory> list = categoryMapper.findCategories(page.getStart(), page.getLimit(),page.getOrder(),page.getStr());
        int total = categoryMapper.totalCategory();
        PageResult result = new PageResult(page.getCurrentPage(), page.getLimit(), total, list);
        return result;
    }

    /**
     * 查询数量
     * @return
     */
    @Override
    public int totalCategory() {
        return categoryMapper.totalCategory();
    }

    /**
     * 保存分类
     * @param categoryName
     * @param categoryIcon
     */
    @Override
    @Transactional
    public String saveCategory(String categoryName, String categoryIcon) {
        List<BlogCategory> categories = categoryMapper.findCateByName(categoryName,categoryIcon);
        if (categories.size() > 0){
            return "该分类已存在！";
        }else {
            try {
                BlogCategory category = new BlogCategory();
                category.setCategoryName(categoryName);
                category.setCategoryIcon(categoryIcon);
                category.setCategoryRank(0);
                category.setIsDeleted((byte)0);
                category.setCreateTime(DateUtil.getNowDate());
                //影响行
                int i = categoryMapper.saveCategory(category);
                if (i > 0) {
                    return "success";
                }else {
                    return "添加失败！";
                }
            } catch (Exception e) {
                throw new RuntimeException("添加失败");
            }
        }
    }

    /**
     * 更新
     * @param categoryId
     * @param categoryName
     * @param categoryIcon
     * @return
     */
    @Override
    public String updateCategory(String categoryId, String categoryName, String categoryIcon) {
        List<BlogCategory> categories = categoryMapper.findCateById(Integer.parseInt(categoryId));
        BlogCategory category = categories.get(0);
        category.setCategoryName(categoryName);
        category.setCategoryIcon(categoryIcon);
        int i = categoryMapper.updateCategory(category);
        if (i > 0){
            return "success";
        }else {
            return "更新失败！";
        }
    }

    @Override
    @Transactional
    public String deleteCategory(Integer[] ids) {
        try {
            int sum = 0;
            for (int i = 0 ; i < ids.length ; i++){
                int row = categoryMapper.deleteCategory(ids[i]);
                sum += row;
            }
            if (sum == ids.length && sum != 0){
                return "success";
            }else {
                return "删除失败";
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("删除失败!");
        }
    }
}
