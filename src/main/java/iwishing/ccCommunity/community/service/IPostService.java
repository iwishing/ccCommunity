package iwishing.ccCommunity.community.service;

import iwishing.ccCommunity.community.DTO.PostDTO;
import iwishing.ccCommunity.community.DTO.QueryPaginDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 帖子业务层接口
 */
public interface IPostService {
    /**
     * 保存帖子
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

    /**
     * 根据分页类查询帖子
     * @param community_id
     * @param page
     * @param size
     * @return
     */
    QueryPaginDTO findByQueryPagin(int community_id,int page,int size);

    /**
     * 根据用户id查询帖子
     * @param userid
     * @param page
     * @param size
     * @return
     */
    QueryPaginDTO findPostByUser(int userid, int page,int size);

    /**
     * 通过帖子id查找帖子
     * @param postId
     * @return
     */
    PostDTO findPostByPostId(Integer postId);

    /**
     * 根据帖子id增加阅读数
     * @param postId
     */
    void addViewCountByPostId(int postId);
}
