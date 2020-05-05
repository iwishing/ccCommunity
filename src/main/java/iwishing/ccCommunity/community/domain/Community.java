package iwishing.ccCommunity.community.domain;

import lombok.Data;
import org.springframework.context.annotation.Bean;

/**
 * 社区
 */
@Data
public class Community {
    //社区id
    private int community_id;
    //创建人id
    private long creator;
    //社区名称
    private String communityName;
    //社区简介
    private String description;
    //社区图片
    private String avatar;
    //关注数
    private int focus_count;
    //帖子数
    private int post_count;
    //创建时间
    private long gmt_create;
    //修改时间
    private long gmt_modified;
}
