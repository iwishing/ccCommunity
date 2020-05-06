package iwishing.ccCommunity.community.service.impl;

import iwishing.ccCommunity.community.domain.Comment;
import iwishing.ccCommunity.community.mapper.ICommentMapper;
import iwishing.ccCommunity.community.mapper.IPostMapper;
import iwishing.ccCommunity.community.service.ICommentService;
import iwishing.ccCommunity.community.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 评论业务层实现类
 */
@Service("commentService")
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private IPostMapper postMapper;
    @Autowired
    private ICommentMapper commentMapper;


    @Override
    @Transactional
    public void saveComment(Comment comment) {
        //添加评论创建时间
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());

        postMapper.saveCommentCount(comment.getPostId());
        commentMapper.insertComent(comment);
    }
}
