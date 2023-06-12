package com.sen.helloworld;

//import javax.swing.Renderer;

import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Hellocontroller {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Hello, World!");
        return "index";
    }

    @RequestMapping("/home")
    public String home() {
        return "Welcome to Home";
    }
}
