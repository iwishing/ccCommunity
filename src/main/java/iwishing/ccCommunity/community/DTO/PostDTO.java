package iwishing.ccCommunity.community.DTO;

import iwishing.ccCommunity.community.domain.Tag;
import iwishing.ccCommunity.community.domain.User;
import lombok.Data;

import java.util.List;

/**
 * 帖子数据传输对象
 */
@Data
public class PostDTO {
    //帖子id
    private int id;
    //帖子标题
    private String title;
    //帖子描述
    private String description;
    //帖子等级
    private int postLevel;
    //所属社区id
    private int community_id;
    //帖子评论数
    private int comment_count;
    //帖子浏览数
    private int view_count;
    //帖子点赞数
    private int like_count;
    //帖子创建时间
    private long gmt_create;
    //帖子修改时间
    private long gmt_modified;
    //标签
    private String tag;
    //帖子标签列表
    private List<Tag> tags;
    //用户
    private User user;
}
