package iwishing.ccCommunity.community.controller;

import iwishing.ccCommunity.community.domain.Community;
import iwishing.ccCommunity.community.domain.Post;
import iwishing.ccCommunity.community.domain.User;
import iwishing.ccCommunity.community.service.ICommunityService;
import iwishing.ccCommunity.community.service.INotifyService;
import iwishing.ccCommunity.community.service.IPostService;
import iwishing.ccCommunity.community.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IPostService postService;
    @Autowired
    private ICommunityService communityService;

    //用根目录的时候，我们访问的时候什么都不加直接协议加端口访问，就能到这个方法，然后返回index，进入index页
    @GetMapping("/")
    public String index(HttpServletRequest request){

        return "index";
    }
    @GetMapping("/search/{searchKeyWord}")
    public String search(@PathVariable(name = "searchKeyWord")String searchKeyWord
            ,Model model){
        List<User> userList = userService.findUserByKeyWord(searchKeyWord);
        System.out.println("++++++++++++++");
        System.out.println("用户列表："+userList.toString());
        List<Post> postList = postService.findUserByKeyWord(searchKeyWord);
        System.out.println("++++++++++++++");
        System.out.println("帖子列表："+postList.toString());
        List<Community> communityList = communityService.findUserByKeyWord(searchKeyWord);
        System.out.println("++++++++++++++");
        System.out.println("社区列表："+communityList.toString());

        model.addAttribute("userList",userList);
        model.addAttribute("postList",postList);
        model.addAttribute("communityList",communityList);

        return "/searchResult";
    }
}
