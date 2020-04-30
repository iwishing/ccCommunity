package iwishing.ccCommunity.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 社区控制器
 */
@Controller
public class CommunityController {
    //获取社区列表
    @GetMapping("/indexCommunityList")
    public String getCommunityList(){
        return "indexCommunityList";
    }

    //进入社区
    @GetMapping("/community")
    public String toCommunity(){
        return "community";
    }
}
