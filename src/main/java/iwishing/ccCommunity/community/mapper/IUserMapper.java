package iwishing.ccCommunity.community.mapper;

import iwishing.ccCommunity.community.model.User;
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
}
