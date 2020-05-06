package iwishing.ccCommunity.community.domain;

import lombok.Data;

/**
 * 评论类
 */
@Data
public class Comment {
    //所在帖子的id
    private int postId;
    //所评论的评论或帖子的id
    private int parent_id;
    //评论类型，一级评论，二级评论
    private int type;
    //评论内容
    private String content;
    //评论人id
    private int commentator;
    //评论创建时间
    private long gmtCreate;
    //评论修改时间
    private long gmtModified;
}
