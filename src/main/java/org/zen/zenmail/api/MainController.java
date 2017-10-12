package org.zen.zenmail.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class MainController {

    @RequestMapping(value={"/"})
    public String index() {
        return "index.html";
    }

}
