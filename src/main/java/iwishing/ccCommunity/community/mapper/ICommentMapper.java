package iwishing.ccCommunity.community.mapper;

import iwishing.ccCommunity.community.domain.Comment;
import org.springframework.stereotype.Repository;

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


}
