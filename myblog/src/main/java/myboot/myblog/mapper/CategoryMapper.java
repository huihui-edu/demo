package myboot.myblog.mapper;

import myboot.myblog.domain.BlogCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper {

    int totalCategory();

    List<BlogCategory> findCategories(@Param("start")int start,@Param("limit")int limit,
                                      @Param("order")String order,@Param("str")String str);

    List<BlogCategory> findCateByName(@Param("name")String categoryName,@Param("icon")String categoryIcon);

    int saveCategory(BlogCategory category);

    List<BlogCategory> findCateById(int categoryId);

    int updateCategory(BlogCategory category);

    int deleteCategory(int id);
}
