package iwishing.ccCommunity.community.service.impl;

import iwishing.ccCommunity.community.DTO.PostDTO;
import iwishing.ccCommunity.community.DTO.QueryPaginDTO;
import iwishing.ccCommunity.community.DTO.TagDTO;
import iwishing.ccCommunity.community.domain.Post;
import iwishing.ccCommunity.community.domain.Tag;
import iwishing.ccCommunity.community.domain.User;
import iwishing.ccCommunity.community.mapper.IPostMapper;
import iwishing.ccCommunity.community.service.IPostService;
import iwishing.ccCommunity.community.service.ITagService;
import iwishing.ccCommunity.community.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
    @Autowired
    private ITagService tagService;

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
        System.out.println("----------");
        System.out.println(postList);
        System.out.println("=============");
        for (PostDTO pstd:postList
        ) {
            User user = userService.findByUserId(pstd.getCreator());
            pstd.setUser(user);
        }
        return postList;
    }


    /**
     * 根据分页类查询帖子
     * @param str
     * @param page
     * @param size
     * @return
     */
    @Override
    public QueryPaginDTO findByQueryPagin(String str,String idType,int page,int size) {
        //查询该社区的帖子数
        int totalCount;
        String tagtype = null;
        System.out.println(idType);
        if ("community_id".equals(idType)){
            //根据社区查帖子
            totalCount = iPostMapper.findNumByCommunityId(Integer.valueOf(str));
        }else{
            //根据标签查帖子
            tagtype = tagService.findTagTypeByTagId(Integer.parseInt(str));
            totalCount = iPostMapper.findNumByTagType(tagtype);
        }


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
        List<PostDTO> postList = new ArrayList<>();
        if ("community_id".equals(idType)){
            postList = iPostMapper.findByCommunityId(Integer.valueOf(str),queryPage,size);
        }else {
            postList = iPostMapper.findPostByTagType(tagtype,queryPage,size);
        }
        for (PostDTO pstd:postList
        ) {
            User user = userService.findByUserId(pstd.getCreator());
            List<Tag> tagList = iPostMapper.findTagByPostId(pstd.getId());
            pstd.setTags(tagList);
            pstd.setUser(user);
        }
        //将帖子按照创建时间从新到旧排序
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
        System.out.println("====================");
        System.out.println(postDTO.getTags().toString());
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

/*
     * 根据标签id获取帖子
     * @param tagtype
     * @return
     */
    @Override
    public List<PostDTO> findPostByTagType(String TagType) {
        return iPostMapper.findRelatPostByTagType(TagType);
    }

    /**
     * 增加帖子的点赞数
     * @param postId
     */
    @Override
    public void addLikeCountByPostId(int postId) {
        iPostMapper.addLikeCountByPostId(postId);
    }


    /**
     * 根据关键字搜索帖子
     * @param searchKeyWord
     */
    @Override
    public List<Post> findUserByKeyWord(String searchKeyWord){
        return iPostMapper.findUserByKeyWord(searchKeyWord);
    }
}
