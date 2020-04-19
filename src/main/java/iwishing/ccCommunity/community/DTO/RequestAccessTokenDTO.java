package iwishing.ccCommunity.community.DTO;

import org.springframework.stereotype.Component;

/**
 * 请求获取accessToken所需信息封装类
 */
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

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
