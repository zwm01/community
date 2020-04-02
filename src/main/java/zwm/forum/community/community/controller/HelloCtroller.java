package zwm.forum.community.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloCtroller {

    @GetMapping("/hello")
    public String greeting(@RequestParam("name") String name, Model model){
            model.addAttribute("name",name);
            return "hello";
    }
}
