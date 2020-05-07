package iwishing.ccCommunity.community.controller;

import com.alibaba.fastjson.JSON;
import iwishing.ccCommunity.community.DTO.CommentDTO;
import iwishing.ccCommunity.community.domain.Comment;
import iwishing.ccCommunity.community.domain.User;
import iwishing.ccCommunity.community.service.ICommentService;
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


        comment.setCommentator(user.getId());
        commentService.saveComment(comment);
        objectHashMap.put("message","success");
        return JSON.toJSONString(objectHashMap.get("message"));
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{parent_id}",method = RequestMethod.GET)
    public Object findtCommentByCommentId(@PathVariable(name = "parent_id")String parent_id){

        System.out.println("父评论id：" + parent_id);

        List<CommentDTO> commentDTOS = commentService.findCommentByCommentId(Integer.valueOf(parent_id),2);
        Collections.sort(commentDTOS);

        System.out.println(commentDTOS.toString());
        return JSON.toJSONString(commentDTOS);
    }
}
