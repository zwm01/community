package zwm.forum.community.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zwm.forum.community.community.dto.AccessTokenDTO;
import zwm.forum.community.community.dto.GithubUser;
import zwm.forum.community.community.mapper.UserMapper;
import zwm.forum.community.community.model.User;
import zwm.forum.community.community.provider.GithubProvider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public  String callback(@RequestParam(name="code") String code,
                            @RequestParam(name = "state") String state,
                            HttpServletRequest request,
                            HttpServletResponse response){
        AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
        /*2595068defe3dbc9d8dd*/

        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirectUri);
        /*0a608cf686f6cab80c270a0c28228bbf4e447dc9*/
        accessTokenDTO.setClient_secret(clientSecret);
        String aceessToken=githubProvider.getAccessToken(accessTokenDTO);
//        System.out.println(aceessToken);
        GithubUser githubUser=githubProvider.getUser(aceessToken);

        if (githubUser!=null){
            User user=new User();
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setToken(UUID.randomUUID().toString());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            response.addCookie(new Cookie("token",user.getToken()));
//            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }
        else {
            System.out.println("失败");
            return "redirect:/";
        }
    }
}
