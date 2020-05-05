package iwishing.ccCommunity.community.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
            return "indexCommunityList";
        }
        UserDTO userDTO = userService.findByUsername(Long.valueOf(user.getUsername()));
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
    @RequestMapping("/community")
    public String toCommunity(HttpServletRequest request,
                              Model model){
        //获得社区id
        String community_id = request.getParameter("community_id");
        //获取用户
        CommunityDTO communityDTO = communityService.findCommunityById(Integer.valueOf(community_id));
/*        UserDTO userDTO = (UserDTO) request.getSession().getAttribute("user");

        //和该用户所加入的所有社区比较，找出这是哪个社区，为了下一步输出该社区信息
        for (CommunityDTO c:userDTO.getCommunityList()
             ) {
            if (Integer.valueOf(community_id) == c.getCommunity_id()){
                BeanUtils.copyProperties(c,community);
                break;
            }
        }*/

           request.getSession().setAttribute("postlist",communityDTO.getPostDTOList());
               //把社区id也存进去，方便后面发表帖子操作
           request.getSession().setAttribute("community_id",community_id);
           model.addAttribute("community",communityDTO);
           return "community";
    }
}
