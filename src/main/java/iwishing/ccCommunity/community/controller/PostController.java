package iwishing.ccCommunity.community.controller;

import com.alibaba.fastjson.JSON;
import iwishing.ccCommunity.community.DTO.*;
import iwishing.ccCommunity.community.domain.Post;
import iwishing.ccCommunity.community.domain.Tag;
import iwishing.ccCommunity.community.domain.User;
import iwishing.ccCommunity.community.service.ICommentService;
import iwishing.ccCommunity.community.service.INotifyService;
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
    @Autowired
    private INotifyService notifyService;


    //获取帖子列表
    @GetMapping("/postList")
    public String getArticleList(HttpServletRequest request,
                                 Model model,
                                 @RequestParam(name = "page",defaultValue = "1")Integer page,
                                 @RequestParam(name = "size",defaultValue = "9")Integer size){
        String community_id = (String)request.getSession().getAttribute("community_id");
        String tagId = (String)request.getParameter("tagId");

        QueryPaginDTO queryPaginDTO;
        //标签id等于null，说明不是点击标签访问的列表
        if (tagId == null){
            System.out.println(community_id);
            queryPaginDTO = postService.findByQueryPagin(community_id,"community_id",page,size);
        }else {
            System.out.println("tagid-------"+tagId);
            queryPaginDTO = postService.findByQueryPagin(tagId,"tagId",page,size);
        }
        System.out.println("分页列表："+queryPaginDTO.getPostList());
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
            postDTO.setCreator(user.getId());
            postDTO.setGmt_create(System.currentTimeMillis());
            postDTO.setGmt_modified(postDTO.getGmt_create());
            postDTO.setUser(user);
            postDTO.setCommunity_id(Integer.valueOf(community_id));

            postService.savePost(postDTO);
            System.out.println("postDTO.getId()"+postDTO.getId());

            String[] strs = postDTO.getTag().split("/");

            List taglist = new ArrayList<Tag>();
            for (int i=0; i<strs.length; i++){
                Tag tagbean = new Tag();
                tagbean.setPost_id(postDTO.getId());
                tagbean.setTagtype(strs[i]);
                taglist.add(tagbean);
            }
            postService.saveTag(taglist);
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
            postService.addViewCountByPostId(postDTO.getId());
        }
        List<CommentDTO> commentDTOList = commentService.findCommentByPostId(Integer.parseInt(postId),1);
        //排序，创建时间早的在后面
        Collections.sort(commentDTOList);
        //查一个相关post集合
        List<PostDTO> relatPost = new ArrayList<>();
        for (Tag pd:postDTO.getTags()
             ) {
            relatPost.addAll(postService.findPostByTagType(pd.getTagtype()));
        }
        model.addAttribute("post",postDTO);
        model.addAttribute("relatPost",relatPost);
        model.addAttribute("commentDTOList",commentDTOList);
        return "postPage";
    }

    /**
     * 点赞评论
     * @param postId
     * @return
     */
        @GetMapping("/post/addlike/{postId}")
        @ResponseBody
        public Object addLikeCount(@PathVariable(name = "postId") String postId){
            postService.addLikeCountByPostId(Integer.parseInt(postId));
            return JSON.toJSONString("success");
        }


        @GetMapping("/post/notify")
        public String nitify(HttpServletRequest request){
            int notifyType = Integer.parseInt(request.getParameter("notifyType"));
            int notifyOuterId = Integer.parseInt(request.getParameter("notifyOuterId"));
            int notificationId = Integer.parseInt(request.getParameter("notificationId"));
            User user = (User) request.getSession().getAttribute("user");

            if (notifyType == 0){
                //等于0回复的是帖子,状态设置成已读，再跳转
                notifyService.updateNotificationStatus(notificationId);
                return "redirect:/post/"+notifyOuterId;
            }else {
                //等于1回复的是评论,根据评论id找到帖子id，再将通知设置为已读，然后直接跳转
                int postId = commentService.findPostIdByCommentId(notifyOuterId);
                notifyService.updateNotificationStatus(notificationId);
                return "redirect:/post/"+postId;
            }
        }

}
