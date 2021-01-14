package myboot.myblog.service;

import myboot.myblog.utils.PageQueryUtil;
import myboot.myblog.utils.PageResult;

import java.text.ParseException;
import java.util.Map;

public interface CategoryService {


    PageResult findCategories(PageQueryUtil page);

    int totalCategory();

    /**
     * 保存
     * @param categoryName
     * @param categoryIcon
     */
    String saveCategory(String categoryName, String categoryIcon);

    /**
     * 修改
     * @param categoryId
     * @param categoryName
     * @param categoryIcon
     * @return
     */
    String updateCategory(String categoryId, String categoryName, String categoryIcon);

    /**
     * 删除
     * @param ids
     * @return
     */
    String deleteCategory(Integer[] ids);
}
