package iwishing.ccCommunity.community.service;

import iwishing.ccCommunity.community.domain.Comment;

/**
 * 评论业务层接口
 */
public interface ICommentService {
    /**
     * 保存评论
     * @param comment
     */
    public void saveComment(Comment comment);
}
