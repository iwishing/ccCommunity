package iwishing.ccCommunity.community.service;

import iwishing.ccCommunity.community.DTO.PostDTO;
import iwishing.ccCommunity.community.DTO.QueryPaginDTO;
import iwishing.ccCommunity.community.DTO.TagDTO;
import iwishing.ccCommunity.community.domain.Post;
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
     * @param str
     * @param page
     * @param size
     * @return
     */
    QueryPaginDTO findByQueryPagin(String str,String idType,int page,int size);

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

    /**
     * 根据标签类型获取post
     * @param TagType
     * @return
     */
    public List<PostDTO> findPostByTagType(String TagType);

    /**
     * 增加点赞数
     * @param postId
     */
    public void addLikeCountByPostId(int postId);

    /**
     * 根据关键字搜索帖子
     * @param searchKeyWord
     */
    public List<Post> findUserByKeyWord(String searchKeyWord);
}
