package zwm.forum.community.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zwm.forum.community.community.dto.AccessTokenDTO;
import zwm.forum.community.community.dto.GithubUser;
import zwm.forum.community.community.provider.GithubProvider;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @GetMapping("/callback")
    public  String callback(@RequestParam(name="code") String code,
                            @RequestParam(name = "state") String state){
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
        GithubUser user=githubProvider.getUser(aceessToken);
        System.out.println(user.getName());
        return "index";
    }
}
