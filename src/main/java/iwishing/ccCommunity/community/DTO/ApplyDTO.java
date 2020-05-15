package iwishing.ccCommunity.community.DTO;

import lombok.Data;

@Data
public class ApplyDTO {
    private int applyId;
    private int applyUserId;
    private String communityName;
    private String communityDescription;
    private long gmt_create;
    private long gmt_modified;
}
