package iwishing.ccCommunity.community.service;

/**
 * 标签业务层接口
 */
public interface ITagService {
    /**
     * 根据标签id查询标签类型
     * @param tagId
     * @return
     */
    public String findTagTypeByTagId(int tagId);
}
