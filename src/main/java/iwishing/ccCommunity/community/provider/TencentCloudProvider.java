package iwishing.ccCommunity.community.provider;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

/**
 * 腾讯云api接口
 */
@Component
public class TencentCloudProvider {

    //密钥id
    @Value("${coc.SecretId}")
    private String secretId;
    //密钥
    @Value("${coc.SecretKey}")
    private String secretKey;
    //地区
    @Value("${coc.Region}")
    private String bucketRegion;
    //存储桶名称
    @Value("${coc.BucketName}")
    private String bucketName;


    public String upload(MultipartFile multipartFile){

        // 1 初始化用户身份信息（secretId, secretKey）
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的区域
        Region region = new Region(bucketRegion);
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);


        String generateFileName;
        String [] filePath = multipartFile.getOriginalFilename().split("\\.");
        if (filePath.length > 1){
            generateFileName = UUID.randomUUID().toString()+"."+multipartFile.getOriginalFilename();
        }else{
            //不是一个文件
            return null;
        }

       try {
           // 指定要上传的文件
          //   = new File(multipartFile.getOriginalFilename());
           File localFile=File.createTempFile("tmp", null);
           multipartFile.transferTo(localFile);

           // 指定要上传到 COS 上对象键
           String key = generateFileName;
           PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
           PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
           //生成预签名url
           GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, key, HttpMethodName.GET);
            // 设置签名过期时间(可选), 若未进行设置, 则默认使用 ClientConfig 中的签名过期时间(1小时)
            // 这里设置签名在十年后过期
           Date expirationDate = new Date(System.currentTimeMillis() + 60L * 60L * 1000L * 24L * 365L * 10L);
           req.setExpiration(expirationDate);
           URL url = cosClient.generatePresignedUrl(req);
           cosClient.shutdown();

           //删除本地缓冲文件
           localFile.deleteOnExit();


           return url.toString();
       }catch (Exception e){
           return null;
       }
    }

}
