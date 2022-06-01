package com.springboot.thymeleaf.controller;

import com.springboot.controller.AuthController;
import com.springboot.entity.User;
import com.springboot.payload.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class AuthenticationControllerUI {

    @Autowired
    AuthController authController;

    @RequestMapping(value ="/login", method = GET)
    public String LoginPage(HttpServletRequest request, Model model) {

        model.addAttribute("loginDto", new LoginDto());
        return "loginPage.html";
    }

    @RequestMapping(value = "/method/login", method = POST)
    public String Login(HttpServletRequest request, Model model, LoginDto loginDto){
        try {
            User user = authController.authenticateUser(loginDto).getBody();
            model.addAttribute("user",user);
            return "redirect:/";
        }catch (Exception e){
            model.addAttribute("error", "Invalid username or password");
            return "loginPage.html";
        }

    }

    @RequestMapping(value = "/logout")
    public String Logout(HttpServletRequest request) throws ServletException {
        authController.logout(request);
        return "redirect:/login";
    }
}
