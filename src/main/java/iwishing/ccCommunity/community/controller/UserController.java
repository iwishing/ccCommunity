package iwishing.ccCommunity.community.controller;

import iwishing.ccCommunity.community.domain.User;
import iwishing.ccCommunity.community.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * 用户控制器
 */
@Controller
public class UserController {
    @Autowired
    private IUserService userService;

    /**
     * 用户登录
     * @param username
     * @param password
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/login")
    public String userLogin(@RequestParam String username,
                            @RequestParam String password,
                            HttpServletRequest request,
                            HttpServletResponse response){
        //1.创建user对象
        User user = new User();

        user.setAccount_id(Long.valueOf(username));
        user.setPassword(password);
        user.setName("社区用户");
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());

        User backUser = userService.findByUser(user);
        if(backUser != null){
            //登录成功，将用户存入cookie
            request.getSession().setAttribute("user",user);
            response.addCookie(new Cookie("username",username));
            return "index";
        }else {
            //
            return "index";
        }
    }

    /**
     * 用户注册
     * @return
     */
    @PostMapping("/register")
    public String userRegister(){
        return null;
    }

    /**
     * 用户退出登录
     * @param request
     * @return
     */
    @GetMapping("/userExit")
    public String userExit(HttpServletRequest request){
        //
        //后来，传用户id，退出当前账号
        //
        request.getSession().setAttribute("user",null);
        return "index";
    }

    @PostMapping("/obtainCode")
    @ResponseBody
    public void obtainCode(HttpServletRequest request){
        String username = request.getParameter("userName");
        System.out.println("为"+username+"获取验证码");
    }
}
