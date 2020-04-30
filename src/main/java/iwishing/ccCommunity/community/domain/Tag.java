package iwishing.ccCommunity.community.domain;

/**
 * 标签
 */
public class Tag {
    private int post_id;
    private String tagtype;

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getTagtype() {
        return tagtype;
    }

    public void setTagtype(String tagtype) {
        this.tagtype = tagtype;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "post_id=" + post_id +
                ", tagtype='" + tagtype + '\'' +
                '}';
    }
}
