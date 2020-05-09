package iwishing.ccCommunity.community.service.impl;

import iwishing.ccCommunity.community.DTO.CommentDTO;
import iwishing.ccCommunity.community.domain.Comment;
import iwishing.ccCommunity.community.mapper.ICommentMapper;
import iwishing.ccCommunity.community.mapper.IPostMapper;
import iwishing.ccCommunity.community.service.ICommentService;
import iwishing.ccCommunity.community.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 评论业务层实现类
 */
@Service("commentService")
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private IPostMapper postMapper;
    @Autowired
    private ICommentMapper commentMapper;

    /**
     * 保存评论
     * @param comment
     */
    @Override
    @Transactional
    public void saveComment(Comment comment) {
        //添加评论创建时间
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());

        postMapper.saveCommentCount(comment.getPostId());
        commentMapper.insertComent(comment);
    }


    /**
     * 根据帖子id查询一级评论
     * @param postId
     * @return
     */
    @Override
    public List<CommentDTO> findCommentByPostId(int postId,int type) {
        List<CommentDTO> commentDTOList = commentMapper.findCommentByPostId(postId,type);
        for (CommentDTO cdto:commentDTOList
        ) {
            cdto.setComment_count(commentMapper.findCommentCountByCommentId(cdto.getId()));
        }
        return commentDTOList;
    }

    /**
     * 根据评论id查询二级评论
     * @param parent_id
     * @return
     */
    @Override
    public List<CommentDTO> findCommentByCommentId(int parent_id,int type){
        List<CommentDTO> commentDTOList = commentMapper.findCommentByCommentId(parent_id,type);
        for (CommentDTO cdto:commentDTOList
        ) {
            cdto.setComment_count(commentMapper.findCommentCountByCommentId(cdto.getId()));
        }
        return commentDTOList;
    }

    /**
     * 点赞评论
     * @param commentId
     */
    @Override
    public void addLikeCountByComentId(int commentId){
        commentMapper.addLikeCountByComentId(commentId);
    }

    /**
     * 根据评论的父评论查评论
     * @param parent_id
     * @return
     */
    @Override
    public Comment findCommentByParentId(int parent_id){
        return commentMapper.findCommentByParentId(parent_id);
    }


    /**
     * 根据评论查询帖子id
     * @param commentId
     * @return
     */
    @Override
    public int findPostIdByCommentId(int commentId){
        return commentMapper.findPostIdByCommentId(commentId);
    }
}
