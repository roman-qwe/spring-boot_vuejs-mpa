package base.application.api.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/**")
public class AdminController {

    @GetMapping
    public ModelAndView index() {
        System.out.println("admin/index");
        return new ModelAndView("admin/index");
    }

}