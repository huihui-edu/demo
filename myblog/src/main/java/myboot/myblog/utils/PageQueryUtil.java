package myboot.myblog.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 分页查询参数
 */
public class PageQueryUtil {
    //当前页码
    private int currentPage;
    //每页大小
    private int limit;
    //排序字段
    private String order;
    //开始条数
    private int start;
    //查询关键字
    private String keyword;
    //排序字段
    private String str;

    public PageQueryUtil(int currentPage, int limit, String order, int start, String keyword, String str) {
        this.currentPage = currentPage;
        this.limit = limit;
        this.order = order;
        this.start = start;
        this.keyword = keyword;
        this.str = str;
    }

    @Override
    public String toString() {
        return "PageQueryUtil{" +
                "currentPage=" + currentPage +
                ", limit=" + limit +
                ", order='" + order + '\'' +
                ", start=" + start +
                ", keyword='" + keyword + '\'' +
                ", str='" + str + '\'' +
                '}';
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
