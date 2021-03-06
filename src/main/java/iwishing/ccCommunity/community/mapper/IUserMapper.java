package iwishing.ccCommunity.community.mapper;

import iwishing.ccCommunity.community.DTO.UserDTO;
import iwishing.ccCommunity.community.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户持久层接口
 */

@Repository("userMapper")
public interface IUserMapper {
    /**
     * 插入github用户方法
     * @param user
     */
    public void insertUserOfGithub(User user);

    /**
     * 注册插入用户
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
     * 根据用户id查找用户
     * @param userid
     * @return
     */
    public User findByUserId(int userid);
    /**
     * 根据用户名查询用户，使用cookie登录时使用
     * @param username
     * @return
     */
    public UserDTO findByUsername(long username);

    /**
     * 更新用户密令
     * @param user
     */
    public void updateUserToken(User user);

    /**
     * 根据用户民查询用户
     * @param username
     * @return
     */
    public User findUserByUsername(long username);


    /**
     * 根据关键字查询用户
     * @param searchKeyWord
     * @return
     */
    public List<User> findUserByKeyWord(String searchKeyWord);


    public void updateUserByUserId(int userId,String avaterUrl);
}
