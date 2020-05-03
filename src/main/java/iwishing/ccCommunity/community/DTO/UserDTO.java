package iwishing.ccCommunity.community.DTO;

import iwishing.ccCommunity.community.domain.Community;
import lombok.Data;

import java.util.List;

/**
 * 用户传输对象
 */
@Data
public class UserDTO {
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

    //社区和用户是多对多关系
    private List<CommunityDTO> communityList;
}
