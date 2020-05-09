package iwishing.ccCommunity.community.mapper;

import iwishing.ccCommunity.community.DTO.CommentDTO;
import iwishing.ccCommunity.community.domain.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评论的持久层接口
 */
@Repository
public interface ICommentMapper {
    /**
     * 保存评论
     * @param comment
     */
    public void insertComent(Comment comment);

    /**
     * 根据帖子id查询评论
     * @param postId
     * @return
     */
    List<CommentDTO> findCommentByPostId(@Param("postId") int postId,@Param("type") int type);

    /**
     * 根据评论id查询评论
     * @param parent_id
     * @return
     */
    List<CommentDTO> findCommentByCommentId(@Param("parent_id")int parent_id,@Param("type") int type);

    /**
     * 根据评论id查询评论数量
     * @param comment_id
     * @return
     */
    public int findCommentCountByCommentId(int comment_id);

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
     * 根据id查询评论conten
     * @param commentId
     * @return
     */
    public String findCommentTitleById(int commentId);


    /**
     * 根据评论查询帖子id
     * @param commentId
     * @return
     */
    public int findPostIdByCommentId(int commentId);
}
