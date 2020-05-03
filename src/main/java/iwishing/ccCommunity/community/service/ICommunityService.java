package iwishing.ccCommunity.community.service;

import iwishing.ccCommunity.community.DTO.CommunityDTO;
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
}
