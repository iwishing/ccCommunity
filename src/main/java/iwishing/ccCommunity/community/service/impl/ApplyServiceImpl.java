package iwishing.ccCommunity.community.service.impl;

import iwishing.ccCommunity.community.DTO.ApplyDTO;
import iwishing.ccCommunity.community.DTO.CommunityDTO;
import iwishing.ccCommunity.community.mapper.IApplyMapper;
import iwishing.ccCommunity.community.mapper.ICommentMapper;
import iwishing.ccCommunity.community.mapper.ICommunityMapper;
import iwishing.ccCommunity.community.service.IApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("applyService")
public class ApplyServiceImpl implements IApplyService {
    @Autowired
    private IApplyMapper applyMapper;
    @Autowired
    private ICommunityMapper communityMapper;

    @Override
    public void saveApply(ApplyDTO applyDTO){
        applyMapper.saveApply(applyDTO);
    }
    @Override
    public List<ApplyDTO> findAllApply(){
        return applyMapper.findAllApply();
    }

    @Override
    @Transactional
    public void accessApply(int applyId) {
        ApplyDTO applyDTO = applyMapper.findApplyById(applyId);
        CommunityDTO communityDTO = new CommunityDTO();
        communityDTO.setCommunityName(applyDTO.getCommunityName());
        communityDTO.setDescription(applyDTO.getCommunityDescription());
        communityDTO.setCreator(applyDTO.getApplyUserId());
        communityDTO.setGmt_create(System.currentTimeMillis());
        communityDTO.setGmt_modified(communityDTO.getGmt_create());
        //创建社区
        communityMapper.saveCommunity(communityDTO);
        applyMapper.deleteApply(applyId);
    }

    @Override
    public void ignoreApply(int applyId) {
        applyMapper.deleteApply(applyId);
    }
}
