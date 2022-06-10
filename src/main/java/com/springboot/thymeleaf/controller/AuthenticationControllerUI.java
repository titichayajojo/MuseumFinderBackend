package com.springboot.thymeleaf.controller;

import com.springboot.controller.AuthController;
import com.springboot.entity.User;
import com.springboot.payload.LoginDto;
import com.springboot.payload.ResetPasswordDto;
import com.springboot.payload.SignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            model.addAttribute("error", "Invalid username or password!");
            return "loginPage.html";
        }

    }

    @RequestMapping(value = "/logout")
    public String Logout(HttpServletRequest request) throws ServletException {
        authController.logout(request);
        return "redirect:/login";
    }

    @RequestMapping("/register")
    public String RegisterPage(HttpServletRequest request, Model model){
        model.addAttribute("signupDto", new SignUpDto());
        return "registerPage.html";
    }

    @RequestMapping(value = "/method/register", method = POST)
    public String Signup(HttpServletRequest request, Model model, SignUpDto signUpDto){
        model.addAttribute("signupDto", new SignUpDto());
        ResponseEntity response = null;
        try {
            response = authController.registerUser(signUpDto);
            User user = (User) response.getBody();
            model.addAttribute("user",user);
            return "redirect:/login";
        }catch (Exception e){
            String error = (String) response.getBody();
            model.addAttribute("error", error);
            return "registerPage.html";
        }

    }

    @RequestMapping("/reset-password")
    public String ResetPasswordPage(HttpServletRequest request, Model model){
        model.addAttribute("resetPasswordDto",new ResetPasswordDto());
        return "resetPassword.html";
    }

    @RequestMapping(value = "/method/reset-password", method = POST)
    public String ResetPassword(HttpServletRequest request, Model model, ResetPasswordDto resetPasswordDto){
        model.addAttribute("resetPasswordDto", new ResetPasswordDto());
        ResponseEntity response = null;
        try {
            response = authController.resetPassword(resetPasswordDto);
            User user = (User) response.getBody();
            model.addAttribute("user",user);
            return "redirect:/login";
        }catch (Exception e){
            String error = (String) response.getBody();
            model.addAttribute("error", error);
            return "resetPassword.html";
        }
    }
}
