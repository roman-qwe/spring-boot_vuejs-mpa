package base.application.api.guest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping({ "/", "/index", "/about", "/login", "/registration", "/guest/**" })
public class GuestController {

    @GetMapping
    public ModelAndView index() {
        log.info("guest/index");
        return new ModelAndView("guest/index");
    }

    @GetMapping("my")
    @ResponseBody
    public String indexMy() {
        log.info("index my");
        return "my value";
    }

}