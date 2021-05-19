package com.spaghettyArts.projectakrasia.controller;

import com.spaghettyArts.projectakrasia.model.ResetFormModel;
import com.spaghettyArts.projectakrasia.model.ResetModel;
import com.spaghettyArts.projectakrasia.services.NotificationService;
import com.spaghettyArts.projectakrasia.services.ResetService;
import com.spaghettyArts.projectakrasia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResetController {

    @Autowired
    private UserService service;

    @Autowired
    private NotificationService mail;


    @GetMapping("/resetPassword")
    public String greetingForm(@RequestParam(value = "token") String token, Model model) {
        model.addAttribute("reset", new ResetFormModel());
        model.addAttribute("token", token);
        return "resetPassword";
    }

    @PostMapping("/resetPassword")
    public String greetingSubmit(@RequestParam(value = "token") String token, @ModelAttribute ResetFormModel reset, Model model) {
        service.reset(token, reset.getEmail(), reset.getPassword());
        model.addAttribute("reset", reset);
        return "result";
    }
}
