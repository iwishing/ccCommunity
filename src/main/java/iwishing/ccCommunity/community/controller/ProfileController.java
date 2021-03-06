package iwishing.ccCommunity.community.controller;

import iwishing.ccCommunity.community.DTO.NotificationDTO;
import iwishing.ccCommunity.community.DTO.NotifyPagination;
import iwishing.ccCommunity.community.DTO.QueryPaginDTO;
import iwishing.ccCommunity.community.domain.User;
import iwishing.ccCommunity.community.service.INotifyService;
import iwishing.ccCommunity.community.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户登录之后，和用户操作相关的controller
 */
@Controller
public class ProfileController {

    @Autowired
    private IPostService postService;
    @Autowired
    private INotifyService notifyService;
    /**
     * 前往我发表的帖子的主页
     * @return
     */
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          @RequestParam(name = "page",defaultValue = "1")int page,
                          @RequestParam(name = "size",defaultValue = "9")int size,
                          HttpServletRequest request,
                          Model model){

        User user = (User) request.getSession().getAttribute("user");
        //登录验证

        //如果user为空，则用户未登录，跳转到主页
        if (user == null){
            return "redirect:/";
        }
        if("mineInfo".equals(action)){

            model.addAttribute("section","mineInfo");
            model.addAttribute("sectionName","个人信息");
        }else if("newReply".equals(action)){
            model.addAttribute("section","newReply");
            model.addAttribute("sectionName","最新回复");
            //1.查询通知
            NotifyPagination notifyPagination = notifyService.findNotificationByUesId(user.getId(),page,size);
            System.out.println("--------------");
            System.out.println(notifyPagination);
            System.out.println("--------------");

            model.addAttribute("notifyPagination",notifyPagination);

        }else if("minePost".equals(action)){
            model.addAttribute("section","minePost");
            model.addAttribute("sectionName","所有帖子");
            //查询帖子
            QueryPaginDTO queryPaginDTO = postService.findPostByUser(user.getId(),page,size);
            model.addAttribute("queryPaginDTO",queryPaginDTO);
        }
        //查询用户未读通知数
        int notifyCount = notifyService.findUnreadNotificationByUserId(user.getId());
        model.addAttribute("notifyCount",notifyCount);
        return "profile";
    }
}
