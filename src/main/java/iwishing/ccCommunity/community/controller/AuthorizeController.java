package iwishing.ccCommunity.community.controller;

import iwishing.ccCommunity.community.DTO.GithubUser;
import iwishing.ccCommunity.community.DTO.RequestAccessTokenDTO;
import iwishing.ccCommunity.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 获取github信息并处理的controller
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;


    @Value("${github.client.id}")
    private String client_id;
    @Value("${github.client.secret}")
    private String client_secret;
    @Value("${github.redirect.uri}")
    private String redirect_uri;

    /**
     * 接受GitHub响应
     * @param code
     * @param state
     * @return
     */
    @GetMapping("/callback")
    public String callBack(@RequestParam(name="code") String code, @RequestParam(name = "state") String state){

        RequestAccessTokenDTO requestAccessTokenDTO = new RequestAccessTokenDTO();
        requestAccessTokenDTO.setCode(code);
        requestAccessTokenDTO.setState(state);
        requestAccessTokenDTO.setClient_id(client_id);
        requestAccessTokenDTO.setClient_secret(client_secret);
        requestAccessTokenDTO.setRedirect_uri(redirect_uri);
        //使用accessToken获取用户信息
        String accessTokenk = githubProvider.getAccessToken(requestAccessTokenDTO);

        System.out.println("accessToken:" + accessTokenk);

        GithubUser user = githubProvider.getUser(accessTokenk);
        System.out.println(user);

        return "index";
    }
}
