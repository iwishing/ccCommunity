package iwishing.ccCommunity.community.provider;

import com.alibaba.fastjson.*;
import iwishing.ccCommunity.community.DTO.RequestAccessTokenDTO;
import iwishing.ccCommunity.community.DTO.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * GitHub信息的提供者
 */
@Component
public class GithubProvider {

    /**
     * 获取访问令牌
     * @param requestAccessTokenDTO 请求获取accessToken所需信息封装类
     * @return
     */
    public String getAccessToken(RequestAccessTokenDTO requestAccessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(JSON.toJSONString(requestAccessTokenDTO),mediaType);
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try{
                Response response = client.newCall(request).execute();
                return response.body().string().split("&")[0].split("=")[1];

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
    }

    /**
     * 获取用户信息
     * @param accessToken：访问令牌
     * @return
     */
    public GithubUser getUser(String accessToken){

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(10,TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .build();
            Request request = new Request.Builder()
                    .url("https://api.github.com/user?access_token="+accessToken)
                    .build();

            try{
                Response response = client.newCall(request).execute();
                String string = response.body().string();
                //使用fastJson将json字符串解析成githubUser对象
                GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
                return githubUser;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
    }
}
