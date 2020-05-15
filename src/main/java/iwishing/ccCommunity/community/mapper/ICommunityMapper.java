package iwishing.ccCommunity.community.mapper;

import iwishing.ccCommunity.community.DTO.CommunityDTO;
import iwishing.ccCommunity.community.domain.Community;
import iwishing.ccCommunity.community.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 社区持久层接口
 */
@Repository("communityMapper")
public interface ICommunityMapper {
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
     * @param community_id
     * @param userId
     * @return
     */
    public Object findCommunitySubscription(@Param(value = "community_id") int community_id,@Param(value = "userId") int userId);

    /**
     * 订阅
     * @param community_id
     * @param userId
     */
    public void subscriptionCommunity(@Param(value = "community_id") int community_id,@Param(value = "userId") int userId);

    /**
     * 取消订阅
     * @param community_id
     * @param userId
     */
    public void cancelSubscription(@Param(value = "community_id") int community_id,@Param(value = "userId") int userId);


    /**
     * 根据关键字查询社区
     * @param searchKeyWord
     * @return
     */
    public List<Community> findUserByKeyWord(String searchKeyWord);

    /**
     * 根据社区id创建社区
     * @param communityDTO
     */
    public void saveCommunity(CommunityDTO communityDTO);
}
