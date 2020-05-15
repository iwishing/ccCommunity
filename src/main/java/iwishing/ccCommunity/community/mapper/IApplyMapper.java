package iwishing.ccCommunity.community.mapper;

import iwishing.ccCommunity.community.DTO.ApplyDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IApplyMapper {
//    保存申请
    public void saveApply(ApplyDTO applyDTO);
//查询所有申请
    public List<ApplyDTO> findAllApply();
//    根据申请id查询申请
    public ApplyDTO findApplyById(int applyId);

//    根据申请id删除申请
    public void deleteApply(int applyId);
}
