package iwishing.ccCommunity.community.controller;

import iwishing.ccCommunity.community.DTO.GithubUser;
import iwishing.ccCommunity.community.DTO.RequestAccessTokenDTO;
import iwishing.ccCommunity.community.mapper.IUserMapper;
import iwishing.ccCommunity.community.model.User;
import iwishing.ccCommunity.community.provider.GithubProvider;
import iwishing.ccCommunity.community.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

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

    @Autowired
    private IUserService userService;

    /**
     * 接受GitHub响应
     * @param code
     * @param state
     * @return
     */
    @GetMapping("/callback")
    public String callBack(@RequestParam(name="code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request){

        RequestAccessTokenDTO requestAccessTokenDTO = new RequestAccessTokenDTO();
        requestAccessTokenDTO.setCode(code);
        requestAccessTokenDTO.setState(state);
        requestAccessTokenDTO.setClient_id(client_id);
        requestAccessTokenDTO.setClient_secret(client_secret);
        requestAccessTokenDTO.setRedirect_uri(redirect_uri);
        //获取access Token
        String accessTokenk = githubProvider.getAccessToken(requestAccessTokenDTO);

        System.out.println("accessToken:" + accessTokenk);

        //使用accessToken获取用户信息
        GithubUser githubUser = githubProvider.getUser(accessTokenk);
        System.out.println(githubUser);
        //判断登录是否成功
        if(githubUser != null){
            //登录成功，保存用户数据，持久化数据
            User user = new User();
            user.setAccount_id(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setToken(UUID.randomUUID().toString());
            user.setAvatar(githubUser.getAvatar_url());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());

            //将user加入session，并创建cookie
            request.getSession().setAttribute("user",user);
            System.out.println(user);
            userService.insertUser(user);
            return "redirect:/";
        }else{
            //登录失败
            return "redirect:/";
        }
    }
}
