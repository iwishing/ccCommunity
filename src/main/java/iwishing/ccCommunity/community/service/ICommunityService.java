package iwishing.ccCommunity.community.service;

import iwishing.ccCommunity.community.DTO.CommunityDTO;
import iwishing.ccCommunity.community.domain.Community;
import iwishing.ccCommunity.community.domain.User;

import java.util.List;

/**
 * 社区业务层抽象类
 */
public interface ICommunityService {
    /**
     * 根据用户查询该用户所订阅的所有社区
     * @param user
     * @return
     */
    public List<CommunityDTO> findCommunityByUser(User user);

    /**
     * 根据社区id查询社区
     * @param community_id
     * @return
     */
    public CommunityDTO findCommunityById(int community_id);

    /**
     * 获取默认前10个社区
     * @return
     */
    public List<CommunityDTO> findCommunityDefault();

    /**
     * 判断用户是否关注该社区
     * @return
     */
    public Object findCommunitySubscription(int community_id,int userId);

    /**
     * 订阅
     * @param community_id
     * @param userId
     */
    public void subscriptionCommunity(int community_id,int userId);

    /**
     * 取消订阅
     */
    public void cancelSubscription(int community_id,int userId);
}
