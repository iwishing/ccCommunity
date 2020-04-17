package iwishing.ccCommunity.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    //用根目录的时候，我们访问的时候什么都不加直接协议加端口访问，就能到这个方法，然后返回index，进入index页
    @GetMapping("/")
    public String index(){
        System.out.println("index请求");
        return "index";
    }
}
