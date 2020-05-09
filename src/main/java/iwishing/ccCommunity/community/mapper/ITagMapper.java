package iwishing.ccCommunity.community.mapper;

import org.springframework.stereotype.Repository;

@Repository
public interface ITagMapper {
    /**
     * 根据标签id查询标签类型
     * @param tagId
     * @return
     */
    public String findTagTypeByTagId(int tagId);
}
