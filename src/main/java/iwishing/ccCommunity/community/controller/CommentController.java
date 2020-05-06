package iwishing.ccCommunity.community.controller;

import com.alibaba.fastjson.JSON;
import iwishing.ccCommunity.community.domain.Comment;
import iwishing.ccCommunity.community.domain.User;
import iwishing.ccCommunity.community.service.ICommentService;
import iwishing.ccCommunity.community.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * 评论控制器
 */
@Controller
public class CommentController {

    @Autowired
    private IPostService postService;
    @Autowired
    private ICommentService commentService;

    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    @ResponseBody
    public Object addPostComment(@RequestBody Comment comment,
                                 HttpServletRequest request){
        HashMap<String, Object> objectHashMap = new HashMap<>();

        System.out.println("评论信息：" + comment.toString());
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            objectHashMap.put("message","用户未登录，请登录后再进行评论！");
            return JSON.toJSONString(objectHashMap.get("message"));
        }



        commentService.saveComment(comment);
        objectHashMap.put("message","success");
        return JSON.toJSONString(objectHashMap.get("message"));
    }
}
