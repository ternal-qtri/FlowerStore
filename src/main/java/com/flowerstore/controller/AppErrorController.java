package com.flowerstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class AppErrorController {

    @GetMapping("/401")
    public String error401(Model model) {
        return "errors/401";
    }

    @GetMapping("/403")
    public String error403(Model model) {
        return "errors/403";
    }

    @GetMapping("/404")
    public String error404(Model model) {
        return "errors/404";
    }

    @GetMapping("/500")
    public String error500(Model model) {
        return "errors/500";
    }
}
