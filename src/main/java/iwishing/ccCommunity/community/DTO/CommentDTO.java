package iwishing.ccCommunity.community.DTO;

import iwishing.ccCommunity.community.domain.User;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * 评论类数据传输对象
 */
@Data
public class CommentDTO implements Comparable{
    //评论的id
    private int id;
    //所在帖子的id
    private int postId;
    //所评论的评论或帖子的id
    private int parent_id;
    //评论类型，一级评论，二级评论
    private int type;
    //评论内容
    private String content;
    //评论创建时间
    private long gmtCreate;
    //评论修改时间
    private long gmtModified;
    //点赞数
    private int like_count;
    //评论人
    private User user;
    //回复数
    private int comment_count;

    /**
     * 默认，根据创建时间排序
     * @param o
     * @return
     */
    @Override
    public int compareTo(@NotNull Object o) {
        CommentDTO commentDTO = (CommentDTO) o;
        if(this.gmtCreate < commentDTO.getGmtCreate()){
            return 1;
        }
        else {
            return -1;
        }
    }
}
