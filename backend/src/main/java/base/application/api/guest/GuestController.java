package base.application.api.guest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({ "/", "/about", "/login" })
public class GuestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuestController.class);

    @GetMapping
    public ModelAndView index() {
        LOGGER.error("guest/index");
        return new ModelAndView("guest/index");
    }

    @GetMapping("my")
    @ResponseBody
    public String indexMy() {
        LOGGER.info("index my");
        return "my value";
    }

}