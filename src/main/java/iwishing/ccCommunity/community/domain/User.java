package iwishing.ccCommunity.community.domain;

import lombok.Data;

/**
 * 用户模型
 */
@Data
public class User {
    //用户id
    private int id;
    //用户名
    private long username;
    //密码
    private String password;
    //用户昵称
    private String name;
    //用户令牌
    private String token;
    //用户头像
    private String avatar;
    //用户性别
    private String gender;
    //用户权限角色
    private int user_role;
    //数据创建时间
    private long gmtCreate;
    //数据修改时间
    private long gmtModified;
    // 验证码
    private String obtainCode;
    //是否关注该社区
    private boolean Subscription;
}
