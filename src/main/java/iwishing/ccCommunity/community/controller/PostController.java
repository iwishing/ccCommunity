package iwishing.ccCommunity.community.controller;

import com.alibaba.fastjson.JSON;
import iwishing.ccCommunity.community.DTO.PostDTO;
import iwishing.ccCommunity.community.domain.Post;
import iwishing.ccCommunity.community.domain.Tag;
import iwishing.ccCommunity.community.domain.User;
import iwishing.ccCommunity.community.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
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
    @PostMapping("/publishPost")
    public String publishPost(@RequestBody PostDTO postDTO,
                              HttpServletRequest request,
                              HttpServletResponse response,
                              Model model){
        User user = (User) request.getSession().getAttribute("user");
        if ( user == null){
            return JSON.toJSONString("用户未登录！");
        }else if (StringUtils.isEmpty(postDTO.getTitle())){
            return JSON.toJSONString("标题不为空！");
        }else if (StringUtils.isEmpty(postDTO.getDescription())){
            return JSON.toJSONString("内容不为空！");
        }else if (StringUtils.isEmpty(postDTO.getTag())){
            return JSON.toJSONString("标签不为空！");
        }else {
            String[] strs = postDTO.getTag().split("/");
            List taglist = new ArrayList<Tag>();
            for (int i=0; i<strs.length; i++){
                Tag tagbean = new Tag();
                tagbean.setPost_id(postDTO.getUser().getId());
                tagbean.setTagtype(strs[i]);
                taglist.add(tagbean);
            }

            postDTO.setGmt_create(System.currentTimeMillis());
            postDTO.setGmt_modified(postDTO.getGmt_create());
            postDTO.setTags(taglist);
            postDTO.setUser(user);
            postService.savePost(postDTO);
            postService.saveTag(postDTO.getTags());
            return "success";
        }
    }
}
