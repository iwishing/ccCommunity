package iwishing.ccCommunity.community.service.impl;

import iwishing.ccCommunity.community.DTO.CommunityDTO;
import iwishing.ccCommunity.community.DTO.PostDTO;
import iwishing.ccCommunity.community.domain.Community;
import iwishing.ccCommunity.community.domain.User;
import iwishing.ccCommunity.community.mapper.ICommunityMapper;
import iwishing.ccCommunity.community.service.ICommunityService;
import iwishing.ccCommunity.community.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 社区的业务层实现类
 */
@Service("communityService")
public class CommunityServiceImp implements ICommunityService {
    @Autowired
    private ICommunityMapper communityMapper;
    @Autowired
    private IPostService postService;

    /**
     * 根据用户查询社区
     * @param user
     * @return
     */
    @Override
    public List<CommunityDTO> findCommunityByUser(User user) {
        return communityMapper.findCommunityByUser(user);
    }

    /**
     * 根据社区id查询社区
     * @param community_id
     * @return
     */
    @Override
    public CommunityDTO findCommunityById(int community_id){

        //根据id查询出社区，还有他的所有帖子，封装起来传回去
        List<PostDTO> postDTOS = postService.findAllByCommunityId(community_id);
        CommunityDTO communityDTO = communityMapper.findCommunityById(community_id);
        communityDTO.setPostDTOList(postDTOS);

        return communityDTO;
    }
    /**
     * 获取默认前10个社区
     * @return
     */
    @Override
    public List<CommunityDTO> findCommunityDefault(){
        return communityMapper.findCommunityDefault();
    }

    /**
     * 判断用户是否关注该社区
     * @return
     */
    @Override
    public Object findCommunitySubscription(int community_id,int userId){
        return communityMapper.findCommunitySubscription(community_id,userId);
    }


    /**
     * 订阅
     * @param community_id
     * @param userId
     */
    @Override
    public void subscriptionCommunity(int community_id,int userId){
        communityMapper.subscriptionCommunity(community_id,userId);
    }


    /**
     * 取消订阅
     */
    @Override
    public void cancelSubscription(int community_id,int userId){
        communityMapper.cancelSubscription(community_id,userId);
    }

    /**
     * 根据关键字查询社区
     * @param searchKeyWord
     * @return
     */
    @Override
    public List<Community> findUserByKeyWord(String searchKeyWord){
        return communityMapper.findUserByKeyWord(searchKeyWord);
    }
}
