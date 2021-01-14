package myboot.myblog.mapper;

import myboot.myblog.domain.BlogComment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CommentMapper {
    /**
     * 总记录数
     * @return
     */
    int totalComment();

    /**
     * 查询留言
     * @return
     */
    List<BlogComment> findCommentByPage(@Param("start")int start,@Param("limit") int limit,
                                        @Param("order") String order,@Param("str") String str);

    /**
     * 回复留言
     * @param commentId
     * @param replyBody
     * @return
     */
    int replyComment(@Param("id")int commentId,@Param("replyBody")String replyBody,@Param("time") Date date);

    BlogComment findCommentById(int commentId);

    int checkComment(int cId);

    int deleteComment(Integer id);


    /**
     * 查询博客的留言
     * @param id
     * @return
     */
    List<BlogComment> totalCommentByBlogId(@Param("blogId") Integer id,@Param("start") Integer start);

    /**
     * 保存留言
     * @param comment
     * @return
     */
    int saveComment(BlogComment comment);
}
