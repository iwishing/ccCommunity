package iwishing.ccCommunity.community.DTO;

import iwishing.ccCommunity.community.domain.User;
import lombok.Data;

import java.util.List;

/**
 * 社区数据传输对象
 */
@Data
public class CommunityDTO {
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

    //社区和用户是多对多关系
    private List<User> userList;
    //社区和帖子是一对多的关系
    private List<PostDTO> postDTOList;


    @Override
    public boolean equals(Object e){
        CommunityDTO communityDTO = (CommunityDTO)e;
        if (this.community_id == communityDTO.community_id){
            return true;
        }else {
            return false;
        }
    }
}
