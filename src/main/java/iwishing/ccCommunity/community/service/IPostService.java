package iwishing.ccCommunity.community.service;

import iwishing.ccCommunity.community.domain.Post;

import java.util.List;

/**
 * 帖子业务层接口
 */
public interface IPostService {
    /**
     * 保存帖子
     */
    public int savePost(Post post);

    /**
     * 保存标签
     * @param taglist
     */
    public void saveTag(List taglist);

    /**
     * 查询所有帖子
     * @return
     */
    public List findAll();
}
