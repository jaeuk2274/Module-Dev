package me.jaeuk.hr_module.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {
    @GetMapping("/")
    public String index(Model model) {
        System.out.println("index");
    //public String index(Model model, @LoginUser SessionUser user) {
        //model.addAttribute("posts", postsService.findAllDesc());

        // @LoginUser 활용, 설정 참고(LoginUserArgumentResolver)
        // SessionUser user = (SessionUser) httpSession.getAttribute("user");

        //if (user != null) {
        //    model.addAttribute("userName", user.getName());
        //}

        return "index";
    }
}
