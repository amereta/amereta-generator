package tech.amereta.generator.web.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexMVC {

    @RequestMapping(value = "")
    public String index() {
        return "html/index";
    }
}
