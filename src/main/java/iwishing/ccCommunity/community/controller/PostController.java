package iwishing.ccCommunity.community.controller;

import iwishing.ccCommunity.community.domain.Post;
import iwishing.ccCommunity.community.domain.Tag;
import iwishing.ccCommunity.community.domain.User;
import iwishing.ccCommunity.community.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 帖子控制器
 */
@Controller
public class PostController {
    @Autowired
    private IPostService postService;

    //获取帖子列表
    @GetMapping("/postList")
    public String getArticleList(HttpServletRequest request){
        /*List<Post> postList = postService.findAll();
        request.getSession().setAttribute("postList",postList);*/
        return "postList";
    }
    //发表帖子界面
    @GetMapping("/publish")
    public String toPublishPage(){
        return "publish";
    }

    /**
     * 发表帖子
     * @return
     */
    @PostMapping("publishPost")
    public String publishPost(@RequestParam String title,
                              @RequestParam String description,
                              @RequestParam String tag,
                              HttpServletRequest request,
                              HttpServletResponse response,
                              Model model){
        User user = (User) request.getSession().getAttribute("user");
        if(user != null && user.getName() != null){

            Post post = new Post();
            post.setTitle(title);
            post.setDescription(description);
            post.setCreator(user.getAccount_id());
            post.setGmt_create(System.currentTimeMillis());
            post.setGmt_modified(post.getGmt_create());

            //保存帖子，并且返回该帖子的id
            int pid = postService.savePost(post);
            //保存该帖子的标签
            //1.分割标签
            String [] tags = tag.split("/");
            //2.封装标签
            List taglist = new ArrayList<Tag>();
            for (int i=0; i<tags.length; i++){
                Tag tagbean = new Tag();
                tagbean.setPost_id(pid);
                tagbean.setTagtype(tags[i]);
                taglist.add(tagbean);
            }

            System.out.println("帖子id"+pid);
            postService.saveTag(taglist);
            return "redirect:/postList";
        }else {
//            model.addAttribute("error",Error)
            return "redirect:/postList";
        }
    }
}
