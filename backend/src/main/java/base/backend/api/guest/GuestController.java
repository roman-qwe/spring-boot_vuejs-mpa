package base.backend.api.guest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({ "/", "/about", "/login" })
public class GuestController {

    @GetMapping
    public ModelAndView index() {
        System.out.println("guest/index");
        return new ModelAndView("guest/index");
    }

    @GetMapping("my")
    @ResponseBody
    public String indexMy() {
        System.out.println("index my");
        return "my value";
    }

}