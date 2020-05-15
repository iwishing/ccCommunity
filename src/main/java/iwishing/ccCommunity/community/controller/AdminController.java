package iwishing.ccCommunity.community.controller;

import iwishing.ccCommunity.community.DTO.ApplyDTO;
import iwishing.ccCommunity.community.domain.User;
import iwishing.ccCommunity.community.service.IApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private IApplyService applyService;


    @GetMapping("/manager/access/{accessApplyId}")
    public String access(@PathVariable (name = "accessApplyId") String accessApplyId){
        int applyId = Integer.parseInt(accessApplyId);
        applyService.accessApply(applyId);

        return "redirect:/manager";
    }

    @GetMapping("/manager/ignore/{ignoreApplyId}")
    public String ignore(@PathVariable (name = "ignoreApplyId") String ignoreApplyId){
        int applyId = Integer.parseInt(ignoreApplyId);
        applyService.ignoreApply(applyId);

        return "redirect:/manager";
    }


    @RequestMapping("/manager")
    public String enterAdmin(Model model){
        //查询申请信息
        List<ApplyDTO> applyDTOS = applyService.findAllApply();


        model.addAttribute("applyDTOS",applyDTOS);
        return "manager";
    }

    @PostMapping("/manager/getApply")
    public String getApply(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        String name = (String) request.getParameter("name");
        String description = (String) request.getParameter("description");

        ApplyDTO applyDTO = new ApplyDTO();
        applyDTO.setApplyUserId(user.getId());
        applyDTO.setCommunityName(name);
        applyDTO.setCommunityDescription(description);
        applyDTO.setGmt_create(System.currentTimeMillis());
        applyDTO.setGmt_modified(applyDTO.getGmt_create());

        applyService.saveApply(applyDTO);

        return "redirect:/";

    }
}
