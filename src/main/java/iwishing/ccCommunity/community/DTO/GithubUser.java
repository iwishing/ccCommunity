package iwishing.ccCommunity.community.DTO;

import lombok.Data;

/**
 * 封装github用户信息
 */
@Data
public class GithubUser {
    //用户名
    private String name;
    //id
    private long id;
    //描述
    private String bio;
    //图片
    private String avatar_url;


}
