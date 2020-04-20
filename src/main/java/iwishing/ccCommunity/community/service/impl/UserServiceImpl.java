package iwishing.ccCommunity.community.service.impl;

import iwishing.ccCommunity.community.mapper.IUserMapper;
import iwishing.ccCommunity.community.model.User;
import iwishing.ccCommunity.community.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户业务层实现类
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
    @Resource(name = "userMapper")
    private IUserMapper userMapper;

    @Override
    public void insertUser(User user) {
        userMapper.insertUser(user);
    }
}
