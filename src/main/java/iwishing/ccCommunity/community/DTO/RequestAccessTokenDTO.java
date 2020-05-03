package iwishing.ccCommunity.community.DTO;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 请求获取accessToken所需信息封装类
 */
@Data
public class RequestAccessTokenDTO {
    //客户端id
    private String client_id;
    //客户端密令
    private String client_secret;
    //第一次请求返回的code
    private String code;
    //设置的返回地址
    private String redirect_uri;
    //第一次请求返回的state
    private String state;
}
