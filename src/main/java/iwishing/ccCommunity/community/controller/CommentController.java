package iwishing.ccCommunity.community.controller;

import com.alibaba.fastjson.JSON;
import iwishing.ccCommunity.community.DTO.CommentDTO;
import iwishing.ccCommunity.community.DTO.PostDTO;
import iwishing.ccCommunity.community.domain.Comment;
import iwishing.ccCommunity.community.domain.Notification;
import iwishing.ccCommunity.community.domain.User;
import iwishing.ccCommunity.community.service.ICommentService;
import iwishing.ccCommunity.community.service.INotifyService;
import iwishing.ccCommunity.community.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 评论控制器
 */
@Controller
public class CommentController {

    @Autowired
    private IPostService postService;
    @Autowired
    private ICommentService commentService;

    @Autowired
    private INotifyService notifyService;

    /**
     *
     *
     * 回复帖子
     * @param comment
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object addPostComment(@RequestBody Comment comment,
                                 HttpServletRequest request){
        HashMap<String, Object> objectHashMap = new HashMap<>();

        System.out.println("评论信息：" + comment.toString());
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            objectHashMap.put("message","用户未登录，是否进行登录？");
            return JSON.toJSONString(objectHashMap.get("message"));
        }

        //回复帖子
        comment.setCommentator(user.getId());
        commentService.saveComment(comment);

        PostDTO postDTO = postService.findPostByPostId(comment.getPostId());
        System.out.println("回复的帖子：" + postDTO);
        //type等于1是回复帖子，等于2是回复评论
        if(comment.getType() == 1){
            Notification notification = new Notification();
            notification.setNotifier(user.getId());
            notification.setReceiver(postDTO.getCreator());
            notification.setOuterId(comment.getPostId());
            notification.setGmtCreate(System.currentTimeMillis());
            //type=0是回复的帖子
            notification.setType(0);

            if (comment.getCommentator() == postDTO.getCreator()){
                //两个相等表示楼主回复自己，不用加入通知
            }else {
                notifyService.saveNotify(notification);
            }
        }else {
            Comment dbcomment = commentService.findCommentByParentId(comment.getParent_id());
            System.out.println("父评论：" + comment);
            Notification notification = new Notification();
            notification.setNotifier(user.getId());
            notification.setReceiver(dbcomment.getCommentator());
            notification.setOuterId(comment.getParent_id());
            notification.setGmtCreate(System.currentTimeMillis());
            //type=1是回复的评论
            notification.setType(1);

            if (comment.getCommentator() == dbcomment.getCommentator()){
                //两个相等表示楼主回复自己，不用加入通知
            }else {
                notifyService.saveNotify(notification);
            }
        }




        objectHashMap.put("message","success");
        return JSON.toJSONString(objectHashMap.get("message"));
    }

    /**
     * 查找二级评论
     * @param parent_id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/comment/{parent_id}",method = RequestMethod.GET)
    public Object findtCommentByCommentId(@PathVariable(name = "parent_id")String parent_id){

        System.out.println("父评论id：" + parent_id);

        List<CommentDTO> commentDTOS = commentService.findCommentByCommentId(Integer.valueOf(parent_id),2);
        Collections.sort(commentDTOS);

        System.out.println(commentDTOS.toString());
        return JSON.toJSONString(commentDTOS);
    }

    /**
     * 增加评论点赞
     * @param commentId
     * @return
     */
    @RequestMapping(value = "/comment/addlike/{commentId}",method = RequestMethod.GET)
    @ResponseBody
    public Object addLikeComment(@PathVariable(name = "commentId") String commentId){
        commentService.addLikeCountByComentId(Integer.parseInt(commentId));
        return "success";
    }
}
