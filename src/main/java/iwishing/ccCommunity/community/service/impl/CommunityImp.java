package iwishing.ccCommunity.community.service.impl;

import iwishing.ccCommunity.community.DTO.CommunityDTO;
import iwishing.ccCommunity.community.domain.User;
import iwishing.ccCommunity.community.mapper.ICommunityMapper;
import iwishing.ccCommunity.community.service.ICommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 社区的业务层实现类
 */
@Service("communityService")
public class CommunityImp implements ICommunityService {
    @Autowired
    private ICommunityMapper communityMapper;

    @Override
    public List<CommunityDTO> findCommunityByUser(User user) {
        return communityMapper.findCommunityByUser(user);
    }
}
