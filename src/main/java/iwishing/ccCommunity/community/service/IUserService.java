package iwishing.ccCommunity.community.service;

import iwishing.ccCommunity.community.DTO.UserDTO;
import iwishing.ccCommunity.community.domain.User;

/**
 * 用户业务层接口
 */
public interface IUserService {
    /**
     * 插入用户方法
     * @param user
     */
    public void insertUserOfGithub(User user);
    /**
     * 注册插入用户
     * @param user
     */
    public void insertUser(User user);
    /**
     * 根据token去查询用户
     * @param token 令牌
     * @return
     */
    public User findByToken(String token);

    /**
     * 根据用户查询用户，登录操作
     * @param user
     * @return
     */
    public User findByUser(User user);
    /**
     * 根据用户名查询用户，使用cookie登录时使用
     * @param username
     * @return
     */
    public UserDTO findByUsername(long username);
}
