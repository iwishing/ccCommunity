package iwishing.ccCommunity.community.Interceptor;

import iwishing.ccCommunity.community.domain.User;
import iwishing.ccCommunity.community.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private UserServiceImpl userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       //请求来之前，先进行登录验证
        Cookie[] cookies = request.getCookies();
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
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
