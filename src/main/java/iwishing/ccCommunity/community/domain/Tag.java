package iwishing.ccCommunity.community.domain;

import lombok.Data;

/**
 * 标签
 */
@Data
public class Tag {
    private int id;
    private int post_id;
    private String tagtype;
}
