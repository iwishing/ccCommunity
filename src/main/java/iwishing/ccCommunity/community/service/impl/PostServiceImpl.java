package iwishing.ccCommunity.community.service.impl;

import iwishing.ccCommunity.community.DTO.PostDTO;
import iwishing.ccCommunity.community.DTO.QueryPaginDTO;
import iwishing.ccCommunity.community.domain.Tag;
import iwishing.ccCommunity.community.domain.User;
import iwishing.ccCommunity.community.mapper.IPostMapper;
import iwishing.ccCommunity.community.service.IPostService;
import iwishing.ccCommunity.community.service.IUserService;
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
    @Autowired
    private IUserService userService;

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
        List<PostDTO> postList = iPostMapper.findAllByCommunityId(community_id);
        for (PostDTO pstd:postList
        ) {
            User user = userService.findByUserId(pstd.getCreator());
            pstd.setUser(user);
        }
        return postList;
    }

    /**
     * 根据分页类查询帖子
     * @param community_id
     * @param page
     * @param size
     * @return
     */
    @Override
    public QueryPaginDTO findByQueryPagin(int community_id,int page,int size) {
        //查询该社区的帖子数
        int totalCount = iPostMapper.findNumByCommunityId(Integer.valueOf(community_id));
        //开始封装queryPagingDTO
        QueryPaginDTO queryPaginDTO = new QueryPaginDTO();
        queryPaginDTO.setPagination(totalCount,page,size);
        //如果用户直接在url上输入page=-1或者大于totalpage的数，就设置容错一下
        if(page < 1){
            page = 1;
        }
        if(page > queryPaginDTO.getTotalPage()){
            page = queryPaginDTO.getTotalPage();
        }
        //计算查询的页号
        int queryPage = size*(page-1);
        List<PostDTO> postList = iPostMapper.findByCommunityId(community_id,queryPage,size);
        for (PostDTO pstd:postList
        ) {
            User user = userService.findByUserId(pstd.getCreator());
            List<Tag> tagList = iPostMapper.findTagByPostId(pstd.getId());
            pstd.setTags(tagList);
            pstd.setUser(user);
        }

        queryPaginDTO.setPostList(postList);

        System.out.println(queryPaginDTO.getPages());
        return queryPaginDTO;
    }

    /**
     * 根据三个信息查询分页
     * @param userid
     * @param page
     * @param size
     * @return
     */
    @Override
    public QueryPaginDTO findPostByUser(int userid, int page, int size) {
        //查询该用户的帖子数
        int totalCount = iPostMapper.findNumByUserId(userid);

        //开始封装queryPagingDTO
        QueryPaginDTO queryPaginDTO = new QueryPaginDTO();
        queryPaginDTO.setPagination(totalCount,page,size);
        //如果用户直接在url上输入page=-1或者大于totalpage的数，就设置容错一下
        if(page < 1){
            page = 1;
        }
        if(page > queryPaginDTO.getTotalPage()){
            page = queryPaginDTO.getTotalPage();
        }
        //计算查询的页号
        int queryPage = size*(page-1);
        List<PostDTO> postList = iPostMapper.findPostByUserId(userid,queryPage,size);
        for (PostDTO pstd:postList
        ) {
            User user = userService.findByUserId(pstd.getCreator());
            List<Tag> tagList = iPostMapper.findTagByPostId(pstd.getId());
            pstd.setTags(tagList);
            pstd.setUser(user);
        }

        queryPaginDTO.setPostList(postList);
        return queryPaginDTO;
    }

    /**
     * 根据帖子id查找帖子
     * @param postId
     * @return
     */
    @Override
    public PostDTO findPostByPostId(Integer postId) {
        PostDTO postDTO = iPostMapper.findPostByPostId(postId);
        User user = userService.findByUserId(postDTO.getCreator());
        postDTO.setUser(user);
        return postDTO;
    }

    /**
     * 根据帖子id增加阅读数
     * @param postId
     */
    @Override
    public void addViewCountByPostId(int postId){
        iPostMapper.addViewCountByPostId(postId);
    }
}
