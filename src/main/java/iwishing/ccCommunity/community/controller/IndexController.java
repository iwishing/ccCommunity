package iwishing.ccCommunity.community.controller;

import iwishing.ccCommunity.community.DTO.UserDTO;
import iwishing.ccCommunity.community.domain.Community;
import iwishing.ccCommunity.community.domain.User;
import iwishing.ccCommunity.community.service.ICommunityService;
import iwishing.ccCommunity.community.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ICommunityService communityService;

    //用根目录的时候，我们访问的时候什么都不加直接协议加端口访问，就能到这个方法，然后返回index，进入index页
    @GetMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model){
       //根据token查询用户，判断用户是否登录过
        //遍历cookie,如果token存在，直接将该用户存入session
        Cookie [] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie ck : cookies
            ) {
                if ("token".equals(ck.getName())) {
                    String token = ck.getValue();
                    User user = userService.findByToken(token);
                    //如果能通过这个token查询到用户，则直接登录这个用户
                    request.getSession().setAttribute("user",user);
                    break;
                }
            }
        }else{
            return "index";
        }
        return "index";
    }
}
