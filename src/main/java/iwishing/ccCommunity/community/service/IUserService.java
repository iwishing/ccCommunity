package iwishing.ccCommunity.community.service;

import iwishing.ccCommunity.community.domain.User;

/**
 * 用户业务层接口
 */
public interface IUserService {
    /**
     * 插入用户方法
     * @param user
     */
    public void insertUser(User user);

    /**
     * 根据token去查询用户
     * @param token 令牌
     * @return
     */
    public User findByToken(String token);
}
