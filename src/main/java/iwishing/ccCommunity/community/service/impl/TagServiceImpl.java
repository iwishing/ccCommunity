package iwishing.ccCommunity.community.service.impl;

import iwishing.ccCommunity.community.mapper.ITagMapper;
import iwishing.ccCommunity.community.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("tagService")
public class TagServiceImpl implements ITagService {
    @Autowired
    private ITagMapper tagMapper;


    /**
     * 根据标签id查询标签类型
     * @param tagId
     * @return
     */
    @Override
    public String findTagTypeByTagId(int tagId){
        return tagMapper.findTagTypeByTagId(tagId);
    }
}
