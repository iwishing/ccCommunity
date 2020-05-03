package iwishing.ccCommunity.community.mapper;

import iwishing.ccCommunity.community.DTO.CommunityDTO;
import iwishing.ccCommunity.community.domain.User;
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
}
