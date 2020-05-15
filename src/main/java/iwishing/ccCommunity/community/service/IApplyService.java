package iwishing.ccCommunity.community.service;

import iwishing.ccCommunity.community.DTO.ApplyDTO;

import java.util.List;

public interface IApplyService {

    public void saveApply(ApplyDTO applyDTO);


    public List<ApplyDTO> findAllApply();


    public void accessApply(int applyId);

    public void ignoreApply(int applyId);
}
