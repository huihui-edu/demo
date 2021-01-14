package myboot.myblog.service;

import myboot.myblog.domain.BlogComment;
import myboot.myblog.utils.PageQueryUtil;
import myboot.myblog.utils.PageResult;

import java.util.List;
import java.util.Map;

public interface CommentService {
    /**
     * 分页查询查询留言
     * @param page
     * @return
     */
    PageResult findComments(PageQueryUtil page);

    int totalComment();

    /**
     * 回复留言
     * @param params
     * @return
     */
    String replyComment(Map<String, Object> params);

    /**
     * 审核
     * @param ids
     * @return
     */
    String checkComment(Integer[] ids);

    /**
     * 删除
     * @param ids
     * @return
     */
    String deleteComment(Integer[] ids);

    /**
     * 查询博客的留言
     * @param id
     * @return
     */
    PageResult findCommentByBlog(Integer id,Integer commentPage);

    /**
     * 保存留言
     * @param params
     * @return
     */
    String saveComment(Map<String, String> params);
}
