package base.backend.api.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user/**")
public class UserController {

    @GetMapping
    public ModelAndView index() {
        System.out.println("user/index");
        return new ModelAndView("user/index");
    }

}