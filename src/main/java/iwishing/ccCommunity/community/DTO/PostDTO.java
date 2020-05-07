package iwishing.ccCommunity.community.DTO;

import iwishing.ccCommunity.community.domain.Post;
import iwishing.ccCommunity.community.domain.Tag;
import iwishing.ccCommunity.community.domain.User;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 帖子数据传输对象
 */
@Data
public class PostDTO implements Comparable{
    //帖子id
    private int id;
    //帖子标题
    private String title;
    //帖子描述
    private String description;
    //帖子等级
    private int postLevel;
    //创建人id
    private int creator;
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

    /**
     * 默认，根据创建时间排序
     * @param o
     * @return
     */
    @Override
    public int compareTo(@NotNull Object o) {
        PostDTO post = (PostDTO) o;
        if(this.gmt_create < post.getGmt_create()){
            return 1;
        }
        else {
            return -1;
        }
    }
}
