package iwishing.ccCommunity.community.service.impl;

import iwishing.ccCommunity.community.DTO.UserDTO;
import iwishing.ccCommunity.community.mapper.IUserMapper;
import iwishing.ccCommunity.community.domain.User;
import iwishing.ccCommunity.community.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户业务层实现类
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserMapper userMapper;

    /**
     * 保存用户
     * @param user
     */
    @Override
    public void insertUserOfGithub(User user) {
        userMapper.insertUserOfGithub(user);
    }

    @Override
    public void insertUser(User user) {
        userMapper.insertUser(user);
    }

    /**
     * 根据令牌查找用户
     * @param token 令牌
     * @return
     */
    @Override
    public User findByToken(String token) {
        return userMapper.findByToken(token);
    }

    @Override
    public User findByUser(User user) {

        return userMapper.findByUser(user);
    }

    @Override
    public UserDTO findByUsername(long username) {
        return userMapper.findByUsername(username);
    }
}
