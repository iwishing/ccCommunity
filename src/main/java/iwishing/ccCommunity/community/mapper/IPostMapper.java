package iwishing.ccCommunity.community.mapper;

import iwishing.ccCommunity.community.DTO.PostDTO;
import iwishing.ccCommunity.community.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 帖子持久层接口
 */
@Repository("postMapper")
public interface IPostMapper {
    /**
     * 保存帖子方法
     * @param post
     */
    public int savePost(PostDTO post);

    /**
     * 保存标签
     * @param taglist
     */
    public void saveTag(List taglist);

    /**
     * 查询所有帖子
     * @return
     */
    public List findAllByCommunityId(int community_id);
}
