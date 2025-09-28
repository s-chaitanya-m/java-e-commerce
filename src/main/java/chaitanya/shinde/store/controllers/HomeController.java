package chaitanya.shinde.store.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }
    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("name", "Chaitanya");
        return "index";
    }

}
