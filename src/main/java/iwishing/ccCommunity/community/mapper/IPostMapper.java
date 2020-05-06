package iwishing.ccCommunity.community.mapper;

import iwishing.ccCommunity.community.DTO.PostDTO;
import iwishing.ccCommunity.community.domain.Post;
import iwishing.ccCommunity.community.domain.Tag;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 根据社区id，查询的页号，大小查询帖子
     * @param community_id
     * @param queryPage
     * @param size
     * @return
     */
    List<PostDTO> findByCommunityId(@Param("community_id") int community_id,@Param("queryPage") int queryPage,@Param("size") int size);

    /**
     * 根据用户id，当前页，和size查询当前分页
     * @param userId
     * @param queryPage
     * @param size
     * @return
     */
    List<PostDTO> findPostByUserId(@Param("userId") long userId, @Param("queryPage")int queryPage, @Param("size")int size);

    /**
     * 根据社区id，查询社区所有帖子总数
     * @param community_id
     * @return
     */
    Integer findNumByCommunityId(Integer community_id);

    /**
     * 根据用户id，查询用户所有帖子总数
     * @param userId
     * @return
     */
    Integer findNumByUserId(long userId);

    /**
     * 根据帖子id去查询标签
     * @param postId
     * @return
     */
    List<Tag> findTagByPostId(int postId);

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
    void addViewCountByPostId(@Param("postId") int postId);

    /**
     * 根据帖子id增加评论数
     * @param postId
     */
    void saveCommentCount(@Param("postId") int postId);
}
