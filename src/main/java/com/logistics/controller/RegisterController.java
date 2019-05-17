package com.logistics.controller;

import com.logistics.dto.user.AdminDTO;
import com.logistics.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegisterController {
    private RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping("/register")
    public ModelAndView showRegistrationForm(ModelAndView modelAndView) {
        modelAndView.setViewName("accessManagement/registerForm");
        modelAndView.addObject("user", new AdminDTO());
        return modelAndView;
    }

    @PostMapping("/register")
    public String processRegistration(@Valid @ModelAttribute("user") AdminDTO user, Errors errors) {
        if (errors.hasErrors()) {
            return "accessManagement/registerForm";
        }
        registerService.register(user);
        return "home";
    }
}
