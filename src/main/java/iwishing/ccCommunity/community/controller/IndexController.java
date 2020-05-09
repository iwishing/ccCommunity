package iwishing.ccCommunity.community.controller;

import iwishing.ccCommunity.community.domain.User;
import iwishing.ccCommunity.community.service.INotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {


    //用根目录的时候，我们访问的时候什么都不加直接协议加端口访问，就能到这个方法，然后返回index，进入index页
    @GetMapping("/")
    public String index(HttpServletRequest request){

        return "index";
    }
}
