package myboot.myblog.service.Impl;

import myboot.myblog.mapper.CommentMapper;
import myboot.myblog.domain.BlogComment;
import myboot.myblog.service.CommentService;
import myboot.myblog.utils.DateUtil;
import myboot.myblog.utils.PageUtil;
import myboot.myblog.utils.PageQueryUtil;
import myboot.myblog.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 查询留言
     *
     * @param params
     * @return
     */
    @Override
    public PageResult findComments(PageQueryUtil params) {
        int total = commentMapper.totalComment();
        List<BlogComment> list = commentMapper.findCommentByPage(params.getStart(), params.getLimit(),params.getOrder(),params.getStr());
        PageResult result = new PageResult(params.getCurrentPage(), params.getLimit(), total, list);
        return result;
    }

    @Override
    public int totalComment() {
        return commentMapper.totalComment();
    }

    /**
     * 回复留言
     *
     * @param params
     * @return
     */
    @Override
    @Transactional
    public String replyComment(Map<String, Object> params) {
        //获取回复的评论id
        int commentId = Integer.parseInt((String) params.get("commentId"));
        //查询当前博客是否留言
        BlogComment comment = commentMapper.findCommentById(commentId);
        if (comment.getReplyBody() != null){
            return "该留言已回复，不可以再回复!";
        }
        //获取回复的内容
        String replyBody = (String) params.get("replyBody");
        //回复的时间
        Date date = DateUtil.getNowDate();
        int row = commentMapper.replyComment(commentId, replyBody,date);
        if (row > 0) {
            return "success";
        } else {
            return "error";
        }
    }

    /**
     * 审核
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public String checkComment(Integer[] ids) {
        for (int i = 0 ; i < ids.length ; i++) {
            //获取回复的评论id
            int cId = ids[i];
            //查询当前博客是否已经审核
            BlogComment comment = commentMapper.findCommentById(cId);
            if (comment.getCommentStatus() == 1){
                return "您有选择已通过审核的评论，请重新选择未通过审核的评论!";
            }
        }
        try {
            int sum = 0;
            for (int i = 0 ; i < ids.length ; i++) {
                int row = commentMapper.checkComment(ids[i]);
                sum++;
            }
            if (sum == ids.length && sum != 0){
                return "success";
            }else {
                return "审核失败，请稍后重试!";
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @Override
    public String deleteComment(Integer[] ids) {
        try {
            int sum = 0;
            for (int i = 0 ; i < ids.length ; i++){
                int row = commentMapper.deleteComment(ids[i]);
                sum += row;
            }
            if (sum == ids.length && sum != 0){
                return "success";
            }else {
                return "删除失败，请稍后重试!";
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * 查询博客的留言
     * @param id
     * @return
     */
    @Override
    public PageResult findCommentByBlog(Integer id,Integer commentPage) {
        //查询当前博客总留言数
        List<BlogComment> comment = commentMapper.totalCommentByBlogId(id,-1);
        int total = comment.size();
        int start = (commentPage - 1) * 8;
        //分页查询
        List<BlogComment> comments = commentMapper.totalCommentByBlogId(id, start);
        PageResult result = new PageResult(commentPage,8,total,comments);
        return result;
    }

    /**
     * 保存留言
     * @param params
     * @return
     */
    @Override
    public String saveComment(Map<String, String> params) {
        int blogId = Integer.parseInt((String) params.get("blogId"));
        String commentator = params.get("commentator");
        String email = params.get("email");
        String websiteUrl = params.get("websiteUrl");
        String commentBody = params.get("commentBody");
        Date date = new Date();
        BlogComment comment = new BlogComment((long) blogId,commentator,email,websiteUrl,commentBody,date);
        //返回影响行
        int row = commentMapper.saveComment(comment);
        if (row > 0){
            return "success";
        }else {
            return "保存失败!";
        }
    }
}
