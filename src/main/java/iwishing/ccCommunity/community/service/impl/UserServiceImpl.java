package iwishing.ccCommunity.community.service.impl;

import iwishing.ccCommunity.community.DTO.UserDTO;
import iwishing.ccCommunity.community.mapper.IUserMapper;
import iwishing.ccCommunity.community.domain.User;
import iwishing.ccCommunity.community.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 用户业务层实现类
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserMapper userMapper;

    /**
     * 保存github账号登录用户
     * @param user
     */
    @Override
    public void insertUserOfGithub(User user) {
        userMapper.insertUserOfGithub(user);
    }

    /**
     * 保存普通注册用户
     * @param user
     */
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

    /**
     * 根据用户查找用户，登录操作使用，主要使用user的username和password
     * @param user
     * @return
     */
    @Override
    public User findByUser(User user) {

        return userMapper.findByUser(user);
    }
    /**
     * 根据用户id查找用户
     * @param userid
     * @return
     */
    @Override
    public User findByUserId(int userid){
        return userMapper.findByUserId(userid);
    }

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    @Override
    public UserDTO findByUsername(long username) {
        return userMapper.findByUsername(username);
    }

    /**
     * 保存用户或者更新用户
     * @param user
     * @return
     */
    @Override
    public User insertUserOrUpdate(User user){

        User backUser = userMapper.findUserByUsername(user.getUsername());

        if (backUser != null){
            backUser.setToken(user.getToken());
            backUser.setGmtModified(user.getGmtCreate());
            backUser.setName(user.getName());
            backUser.setAvatar(user.getAvatar());
            userMapper.updateUserToken(backUser);
        }else {
            userMapper.insertUserOfGithub(user);
        }
        return userMapper.findUserByUsername(user.getUsername());
    }
}
