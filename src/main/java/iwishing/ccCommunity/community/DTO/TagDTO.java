package iwishing.ccCommunity.community.DTO;

import lombok.Data;

import java.util.List;

/**
 * 标签数据传输对象
 */
@Data
public class TagDTO {
//    标签id
    private int id;
//    帖子id
    private int post_id;
//    标签类型
    private String tagtype;
    //帖子列表，因为帖子和标签是多对多的关系
    private List<PostDTO> postList;
}
