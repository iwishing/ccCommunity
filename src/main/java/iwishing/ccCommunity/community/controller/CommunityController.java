package iwishing.ccCommunity.community.controller;

import com.alibaba.fastjson.JSON;
import iwishing.ccCommunity.community.DTO.CommunityDTO;
import iwishing.ccCommunity.community.DTO.PostDTO;
import iwishing.ccCommunity.community.DTO.UserDTO;
import iwishing.ccCommunity.community.domain.Community;
import iwishing.ccCommunity.community.domain.Post;
import iwishing.ccCommunity.community.domain.User;
import iwishing.ccCommunity.community.mapper.ICommunityMapper;
import iwishing.ccCommunity.community.service.ICommunityService;
import iwishing.ccCommunity.community.service.IPostService;
import iwishing.ccCommunity.community.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 社区控制器
 */
@Controller
public class CommunityController {
    @Autowired
    private IUserService userService;
    @Autowired
    private ICommunityService communityService;

    //获取社区列表
    @GetMapping("/indexCommunityList")
    public String getCommunityList(HttpServletRequest request,
                                   Model model){
        //根据登录的用户的用户名查找到user，并进行查询，一对多，将它的社区也一并查出来
        User user = (User) request.getSession().getAttribute("user");

        if(user == null){
            //获取关注数前10的社区显示
            List<CommunityDTO> communityList = communityService.findCommunityDefault();
            model.addAttribute("communityList",communityList);
            return "indexCommunityList";
        }else {
            UserDTO userDTO = userService.findByUsername(Long.valueOf(user.getUsername()));
            if (userDTO != null) {
                //社区列表传入社区页
                //如果用户关注的社区数少于10个，则将关注数前10的添加进去
                if (userDTO.getCommunityList().size() < 10){
                    List<CommunityDTO> communityList = communityService.findCommunityDefault();
                    userDTO.getCommunityList().removeAll(communityList);
                    userDTO.getCommunityList().addAll(communityList);

                    model.addAttribute("communityList",userDTO.getCommunityList());
                }
            }else {
                //获取关注数前10的社区显示
                List communityList = communityService.findCommunityDefault();
                model.addAttribute("communityList",communityList);
                return "indexCommunityList";
            }
        }
        return "indexCommunityList";
    }

    //进入社区
    @RequestMapping("/community")
    public String toCommunity(HttpServletRequest request,
                              Model model){
        //获得社区id
        String community_id = request.getParameter("community_id");
        User user = (User)request.getSession().getAttribute("user");
        //判断用户是否关注该社区
        if (user != null){
            if (communityService.findCommunitySubscription(Integer.parseInt(community_id),user.getId()) != null){
                user.setSubscription(true);
            }else {
                user.setSubscription(false);
            }
            request.getSession().setAttribute("user",user);
        }
        //获取用户
        CommunityDTO communityDTO = communityService.findCommunityById(Integer.valueOf(community_id));

           request.getSession().setAttribute("postlist",communityDTO.getPostDTOList());
               //把社区id也存进去，方便后面发表帖子操作
           request.getSession().setAttribute("community_id",community_id);

        model.addAttribute("community",communityDTO);
           return "community";
    }



    //根据传过来的社区id，和用户id查询是否订阅，订阅过就取消订阅，没订阅就增加订阅
    @GetMapping("/community/subscription/{community_id}")
    @ResponseBody
    public Object subscriptionCommunity(@PathVariable(name = "community_id")String community_id,
                                        HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return "not_login";
        }

        if (communityService.findCommunitySubscription(Integer.parseInt(community_id),user.getId()) != null){
            //不等于0，订阅了，所以取消订阅
            communityService.cancelSubscription(Integer.parseInt(community_id),user.getId());
            return "cancel";
        }else {
            //等于0，表示没有订阅，所以订阅
            communityService.subscriptionCommunity(Integer.parseInt(community_id),user.getId());
            return "subscription";
        }
    }

    @GetMapping("/community/applyCommunity")
    public String applyCommunity(){

        return "applyCommunity";
    }
}
