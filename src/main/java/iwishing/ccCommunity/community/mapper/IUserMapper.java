package iwishing.ccCommunity.community.mapper;

import iwishing.ccCommunity.community.domain.User;
import org.springframework.stereotype.Repository;

/**
 * 用户持久层接口
 */

@Repository("userMapper")
public interface IUserMapper {
    /**
     * 插入用户方法
     * @param user
     */
    public void insertUser(User user);

    /**
     * 根据令牌查询用户
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
    public User findByUsername(long username);
}
