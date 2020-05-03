package iwishing.ccCommunity.community.controller;

import iwishing.ccCommunity.community.DTO.PostDTO;
import iwishing.ccCommunity.community.DTO.UserDTO;
import iwishing.ccCommunity.community.domain.Community;
import iwishing.ccCommunity.community.domain.Post;
import iwishing.ccCommunity.community.domain.User;
import iwishing.ccCommunity.community.mapper.IUserMapper;
import iwishing.ccCommunity.community.service.IPostService;
import iwishing.ccCommunity.community.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 社区控制器
 */
@Controller
public class CommunityController {
    @Autowired
    private IPostService postService;
    @Autowired
    private IUserService userService;

    //获取社区列表
    @GetMapping("/indexCommunityList")
    public String getCommunityList(HttpServletRequest request,
                                   Model model){
        //根据登录的用户的用户名查找到user，并进行查询，一对多，将它的社区也一并查出来
        User user = (User) request.getSession().getAttribute("user");
        UserDTO userDTO = userService.findByUsername(user.getUsername());
        if (userDTO != null) {
            //新的UserDTO取代user
            request.getSession().setAttribute("user", userDTO);
            //社区列表传入社区页
            List communityList = userDTO.getCommunityList();
            model.addAttribute("communityList",userDTO.getCommunityList());
        }
        return "indexCommunityList";
    }

    //进入社区
    @GetMapping("/community")
    public String toCommunity(@RequestParam(name = "community_id") String community_id,
                              HttpServletRequest request){
        List<Post> postList = postService.findAllByCommunityId(Integer.valueOf(community_id));
        request.getSession().setAttribute("postlist",postList);
        return "community";
    }
}
