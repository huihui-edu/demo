package myboot.myblog.utils;

import java.util.Map;

public class PageUtil {
//    /**
//     * 将驼峰命名的字段转换成数据库下划线类型
//     * @param pageNum
//     * @return
//     */
//    public static String getPageResult(int pageNum){
//        int currentPage = pageNum;
//        int limit =
//    }

    public static PageQueryUtil getPageResult(Map<String,Object> params){
        //当前页
        Integer currentPage = Integer.parseInt((String) params.get("page"));
        //页面大小
        Integer limit =  Integer.parseInt((String) params.get("limit"));
        //查询的方式   desc:降序   asc:升序(默认)
        String order = (String) params.get("order");
        //总记录数
        int total = 0;
        //开始条数
        int start = (currentPage - 1) * limit;
        //搜索的关键字
        String keyword = (String) params.get("keyword");
        //排序的方式,将sidx转换成数据库能识别的字段
        String sidx = (String) params.get("sidx");
        String str = null;
        if (!sidx.equals("")) {
            str = sidx;
        }
        if (keyword != null) {
            keyword = "%" + keyword + "%";
        }else {
            keyword = null;
        }
        PageQueryUtil page = new PageQueryUtil(currentPage,limit,order,start,keyword,str);
        return page;
    }

}
