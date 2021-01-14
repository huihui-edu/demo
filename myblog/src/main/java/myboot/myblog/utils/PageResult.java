package myboot.myblog.utils;

import java.io.Serializable;
import java.util.List;

/**
 * 返回浏览器结果工具类
 */
public class PageResult implements Serializable {

    //当前页
    private Integer currPage;
    //总页数
    private Integer totalPage;
    //页面大小
    private Integer pageSize;
    //总记录数
    private Integer totalCount;
    //数据列表
    private List<?> list;

    public PageResult(Integer currPage, Integer pageSize, Integer totalCount, List<?> list) {
        this.currPage = currPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.list = list;
        this.totalPage = (totalCount / pageSize) + 1;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "currPage=" + currPage +
                ", totalPage=" + totalPage +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", list=" + list +
                '}';
    }

    public Integer getCurrPage() {
        return currPage;
    }

    public void setCurrPage(Integer currPage) {
        this.currPage = currPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }
}
