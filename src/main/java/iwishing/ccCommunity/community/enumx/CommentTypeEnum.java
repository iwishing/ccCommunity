package iwishing.ccCommunity.community.enumx;

/**
 * 评论的枚举类型
 */
public enum CommentTypeEnum {
    POST(1),
    COMMENT(2);
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }
}
