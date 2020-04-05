package zwm.forum.community.community.provider;


import com.alibaba.fastjson.JSON;

import okhttp3.*;
import org.springframework.stereotype.Component;
import zwm.forum.community.community.dto.AccessTokenDTO;
import zwm.forum.community.community.dto.GithubUser;

import java.io.IOException;

/*发送post请求*/
@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        String code=accessTokenDTO.getCode();
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                /*?client_id=2595068defe3dbc9d8dd&client_secret=0a608cf686f6cab80c270a0c28228bbf4e447dc9&code="+code+"&redirect_uri=http://localhost:8080/callback&state=1*/
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println(string);
            String str[]=string.split("&");
            String token_str=str[0];
            String token=token_str.split("=")[1];
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string=response.body().string();
            GithubUser githubUser=JSON.parseObject(string,GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}


