package iwishing.ccCommunity.community.service.impl;

import iwishing.ccCommunity.community.DTO.PostDTO;
import iwishing.ccCommunity.community.domain.Post;
import iwishing.ccCommunity.community.mapper.IPostMapper;
import iwishing.ccCommunity.community.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 帖子业务层接口实现类
 */
@Service("postService")
public class PostServiceImpl implements IPostService {

    @Autowired
    private IPostMapper iPostMapper;

    /**
     * 保存帖子
     * @param post
     */
    @Override
    public int savePost(PostDTO post) {
        return iPostMapper.savePost(post);
    }

    /**
     * 保存标签
     * @param taglist
     */
    @Override
    public void saveTag(List taglist) {
        iPostMapper.saveTag(taglist);
    }

    /**
     * 查询所有帖子
     * @return
     */
    @Override
    public List findAllByCommunityId(int community_id) {
        return iPostMapper.findAllByCommunityId(community_id);
    }
}
