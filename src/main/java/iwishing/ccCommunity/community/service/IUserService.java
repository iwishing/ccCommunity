package iwishing.ccCommunity.community.service;

import iwishing.ccCommunity.community.model.User;

/**
 * 用户业务层接口
 */
public interface IUserService {
    /**
     * 插入用户方法
     * @param user
     */
    public void insertUser(User user);
}
