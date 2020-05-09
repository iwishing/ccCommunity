package iwishing.ccCommunity.community.controller;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import iwishing.ccCommunity.community.DTO.FileDTO;
import iwishing.ccCommunity.community.provider.TencentCloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件上传组件控制器
 */
@Controller
public class FileController {

    @Autowired
    private TencentCloudProvider tencentCloudProvider;

//    上传
    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request){

        MultipartHttpServletRequest multipartHttpServletRequest =(MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartHttpServletRequest.getFile("editormd-image-file");

        String fileUrl = tencentCloudProvider.upload(multipartFile);
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setMessage("图片上传");
        fileDTO.setUrl(fileUrl);

        return fileDTO;
    }
}
