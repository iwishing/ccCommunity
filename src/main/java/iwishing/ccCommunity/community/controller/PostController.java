package iwishing.ccCommunity.community.controller;

import com.alibaba.fastjson.JSON;
import iwishing.ccCommunity.community.DTO.CommentDTO;
import iwishing.ccCommunity.community.DTO.PostDTO;
import iwishing.ccCommunity.community.DTO.QueryPaginDTO;
import iwishing.ccCommunity.community.DTO.UserDTO;
import iwishing.ccCommunity.community.domain.Post;
import iwishing.ccCommunity.community.domain.Tag;
import iwishing.ccCommunity.community.domain.User;
import iwishing.ccCommunity.community.service.ICommentService;
import iwishing.ccCommunity.community.service.IPostService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 帖子控制器
 */
@Controller
public class PostController {
    @Autowired
    private IPostService postService;
    @Autowired
    private ICommentService commentService;

    //获取帖子列表
    @GetMapping("/postList")
    public String getArticleList(HttpServletRequest request,
                                 Model model,
                                 @RequestParam(name = "page",defaultValue = "1")Integer page,
                                 @RequestParam(name = "size",defaultValue = "9")Integer size){
        String community_id = (String)request.getSession().getAttribute("community_id");
        System.out.println(community_id);
//        List<PostDTO> postList = postService.findAllByCommunityId(Integer.valueOf(community_id));
        QueryPaginDTO queryPaginDTO = postService.findByQueryPagin(Integer.valueOf(community_id),page,size);
        model.addAttribute("queryPaginDTO",queryPaginDTO);
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
    @ResponseBody
    public String publishPost(@RequestBody PostDTO postDTO,
                              HttpServletRequest request,
                              HttpServletResponse response,
                              Model model){
        User user = (User) request.getSession().getAttribute("user");
        String community_id = (String) request.getSession().getAttribute("community_id");


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
                tagbean.setPost_id(postDTO.getId());
                tagbean.setTagtype(strs[i]);
                taglist.add(tagbean);
            }
            postDTO.setCreator(user.getId());
            postDTO.setGmt_create(System.currentTimeMillis());
            postDTO.setGmt_modified(postDTO.getGmt_create());
            postDTO.setTags(taglist);
            postDTO.setUser(user);
            postDTO.setCommunity_id(Integer.valueOf(community_id));

            postService.savePost(postDTO);
            postService.saveTag(postDTO.getTags());
            return JSON.toJSONString( "success");
        }
    }

    /**
     * 进入帖子页面
     * @param postId
     * @param model
     * @return
     */
    @GetMapping("/post/{postId}")
    public String toPostPage(@PathVariable(name = "postId")String postId,
                             Model model){

        System.out.println("postId"+postId);
        PostDTO postDTO = postService.findPostByPostId(Integer.valueOf(postId));
        if (postDTO != null){
            System.out.println(postDTO.getUser());
            postService.addViewCountByPostId(postDTO.getId());
        }
        List<CommentDTO> commentDTOList = commentService.findCommentByPostId(Integer.valueOf(postId),1);
        //排序，创建时间早的在后面
        Collections.sort(commentDTOList);
        model.addAttribute("post",postDTO);
        model.addAttribute("commentDTOList",commentDTOList);
        return "postPage";
    }
}
