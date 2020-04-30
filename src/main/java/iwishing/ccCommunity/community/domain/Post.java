package iwishing.ccCommunity.community.domain;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 帖子
 */
public class Post implements Comparable{
    private int id;
    private String title;
    private String description;
    private long creator;
    private int comment_count;
    private int view_count;
    private int like_count;
    private List<String> tag;
    private long gmt_create;
    private long gmt_modified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreator() {
        return creator;
    }

    public void setCreator(long creator) {
        this.creator = creator;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public long getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(long gmt_create) {
        this.gmt_create = gmt_create;
    }

    public long getGmt_modified() {
        return gmt_modified;
    }

    public void setGmt_modified(long gmt_modified) {
        this.gmt_modified = gmt_modified;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", creator=" + creator +
                ", comment_count=" + comment_count +
                ", view_count=" + view_count +
                ", like_count=" + like_count +
                ", tag=" + tag +
                ", gmt_create=" + gmt_create +
                ", gmt_modified=" + gmt_modified +
                '}';
    }

    @Override
    public int compareTo(@NotNull Object o) {
        Post post = (Post)o;
        if(this.gmt_create < post.getGmt_create()){
            return 1;
        }
        else {
            return -1;
        }
    }
}
