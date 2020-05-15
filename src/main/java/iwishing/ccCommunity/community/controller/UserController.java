package iwishing.ccCommunity.community.controller;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import iwishing.ccCommunity.community.domain.User;
import iwishing.ccCommunity.community.provider.TencentCloudProvider;
import iwishing.ccCommunity.community.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * 用户控制器
 */
@Controller
public class UserController {
    //验证码
    private String code;
    @Autowired
    private IUserService userService;
    @Autowired
    private TencentCloudProvider tencentCloudProvider;



    @Value("${messageCode.accessKeyId}")
    private String accessKeyId;
    @Value("${messageCode.secret}")
    private String secret;
    @Value("${messageCode.domain}")
    private String domain;
    @Value("${messageCode.version}")
    private String version;
    @Value("${messageCode.action}")
    private String action;
    @Value("${messageCode.regionId}")
    private String regionId;
    @Value("${messageCode.signName}")
    private String signName;
    @Value("${messageCode.templateCode}")
    private String templateCode;

    /**
     * 用户登录
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/userLogin")
    @ResponseBody
    public String userLogin(@RequestBody User user,
                            HttpServletRequest request,
                            HttpServletResponse response,
                            Model model){
        System.out.println(user.toString());
        Map<String, Object> m = new HashMap<>();
        //如果用户名和密码都不为空则继续进行
        if(user.getUsername() == 0){
            m.put("errorMessage","用户名不能为空！");
            return JSON.toJSONString(m.get("errorMessage"));
        }else if (StringUtils.isEmpty(user.getPassword())){
            m.put("errorMessage","密码不能为空！");
            return JSON.toJSONString(m.get("errorMessage"));
        }else {
            //根据登录信息，查询出该用户
            User backUser = userService.findByUser(user);
            if(backUser != null){
                //登录成功，将用户存入cookie
                response.addCookie(new Cookie("token",backUser.getToken()));
                return JSON.toJSONString("success");
            }else {
                m.put("errorMessage","用户名或密码不正确！");
                return JSON.toJSONString(m.get("errorMessage"));
            }
        }
    }

    /**
     * 用户注册
     * @return
     */
    @PostMapping("/userRegister")
    @ResponseBody
    public String userRegister(@RequestBody User user,
                               HttpServletResponse response
                               ){
        Map<String, Object> m = new HashMap<>();

        System.out.println(user);
        //判断注册用户名是否为空
        if(user.getUsername() == 0){
            m.put("errorMessage","用户名不能为空！");
            return JSON.toJSONString(m.get("errorMessage"));
        //判断密码是否为空
        }else if (StringUtils.isEmpty(user.getPassword())){
            m.put("errorMessage","密码不能为空！");
            return JSON.toJSONString(m.get("errorMessage"));
        //判断验证码是否正确*/
        } else if(code == null || !code.equals(user.getObtainCode())) {
            m.put("errorMessage","验证码不正确！");
            return JSON.toJSONString(m.get("errorMessage"));
        } else {

            //如果该用户未注册，则进行注册
            if(userService.findByUsername(Long.valueOf(user.getUsername())) == null){

                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                user.setName("社区用户");
                //生成唯一识别码
                String token = UUID.randomUUID().toString();
                user.setToken(token);
                userService.insertUser(user);
                //注册成功顺便登录加入cookie

                response.addCookie(new Cookie("token",user.getToken()));
                return JSON.toJSONString( "success");
            }else {
                m.put("errorMessage","用户已存在！");
                return JSON.toJSONString(m.get("errorMessage"));
            }
        }
    }

    /**
     * 用户退出登录
     * @param request
     * @return
     */
    @GetMapping("/userExit")
    public String userExit(HttpServletRequest request,
                           HttpServletResponse response){
        //删除session中的user
        request.getSession().removeAttribute("user");
        //替换cookie中的token，并设置立即删除，所有目录均有效
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);//立即删除
        cookie.setPath("/");//项目所有目录均有效
        response.addCookie(cookie);

        return "redirect:/";
    }

    @PostMapping("/obtainCode")
    @ResponseBody
    public void obtainCode(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse){
        //获取手机号
        String username = httpServletRequest.getParameter("userName");

        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setVersion(version);
        request.setAction(action);
        request.putQueryParameter("RegionId", regionId);
        request.putQueryParameter("PhoneNumbers", username);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        //使用种子，产生随机数
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        code = String.valueOf(random.nextInt(900000)+10000);
        //设置验证码
        request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            httpServletResponse.getWriter().write("发送完成");
            httpServletResponse.getWriter().flush();
            httpServletResponse.getWriter().close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("为"+username+"获取验证码");
    }

    @RequestMapping(value = "/user/update",method = RequestMethod.POST)
    @ResponseBody
    public String upUserInfo(HttpServletRequest request){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获得文件：
        MultipartFile name_data= multipartRequest.getFile("file_data");
        String userUpdateId = (String)request.getParameter("userUpdateId");
        //String userUpdateName = (String)request.getParameter("userUpdateName");
        //2222222222
        System.out.println(name_data);
        System.out.println(userUpdateId);
       // System.out.println(userUpdateName);
        //2222222222222
        String url = tencentCloudProvider.upload(name_data);

        userService.updateUserByUserId(Integer.parseInt(userUpdateId),url);
        return "redirect:/profile/mineInfo";
    }
}
