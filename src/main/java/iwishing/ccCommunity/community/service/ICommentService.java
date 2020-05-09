package iwishing.ccCommunity.community.service;

import iwishing.ccCommunity.community.DTO.CommentDTO;
import iwishing.ccCommunity.community.domain.Comment;

import java.util.List;

/**
 * 评论业务层接口
 */
public interface ICommentService {
    /**
     * 保存评论
     * @param comment
     */
    public void saveComment(Comment comment);

    /**
     * 根据帖子id查询评论
     * @param postId
     * @return
     */
    List<CommentDTO> findCommentByPostId(int postId,int type);


    /**
     * 根据评论id查询评论
     * @param parent_id
     * @return
     */
    public List<CommentDTO> findCommentByCommentId(int parent_id,int type);

    /**
     *
     * 点赞评论
     * @param commentId
     */
    public void addLikeCountByComentId(int commentId);

    /**
     * 根据评论的父评论查评论
     * @param parent_id
     * @return
     */
    public Comment findCommentByParentId(int parent_id);

    /**
     * 根据评论查询帖子id
     * @param commentId
     * @return
     */
    public int findPostIdByCommentId(int commentId);
}
