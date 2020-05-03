package iwishing.ccCommunity.community.controller;

import iwishing.ccCommunity.community.DTO.GithubUser;
import iwishing.ccCommunity.community.DTO.RequestAccessTokenDTO;
import iwishing.ccCommunity.community.domain.User;
import iwishing.ccCommunity.community.provider.GithubProvider;
import iwishing.ccCommunity.community.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 获取github信息并处理的controller
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private IUserService userService;

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
    public String callBack(@RequestParam(name="code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){

        RequestAccessTokenDTO requestAccessTokenDTO = new RequestAccessTokenDTO();
        requestAccessTokenDTO.setCode(code);
        requestAccessTokenDTO.setState(state);
        requestAccessTokenDTO.setClient_id(client_id);
        requestAccessTokenDTO.setClient_secret(client_secret);
        requestAccessTokenDTO.setRedirect_uri(redirect_uri);
        System.out.println(requestAccessTokenDTO.toString());
        //获取access Token
        String accessTokenk = githubProvider.getAccessToken(requestAccessTokenDTO);

        System.out.println("accessToken:" + accessTokenk);

        //使用accessToken获取用户信息
        GithubUser githubUser = githubProvider.getUser(accessTokenk);
        System.out.println(githubUser);
        //判断登录是否成功,用户不为空，且用户id不为空
        if(githubUser != null && githubUser.getId() != 0L){
            //登录成功，保存用户数据，持久化数据
            User user = new User();

            user.setUsername(githubUser.getId());
            user.setName(githubUser.getName());
            user.setAvatar(githubUser.getAvatar_url());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            //创建用户密令
            String token = UUID.randomUUID().toString();
            user.setToken(token);

            //将user加入session，并创建cookie
            request.getSession().setAttribute("user",user);
            System.out.println(user);
            //存储用户信息
            userService.insertUserOfGithub(user);
            //将token存入cookie
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else{
            //登录失败
            return "redirect:/";
        }
    }
}
