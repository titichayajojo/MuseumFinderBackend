package com.springboot.thymeleaf.controller;

import com.springboot.controller.TagController;
import com.springboot.controller.UserController;
import com.springboot.entity.Tag;
import com.springboot.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.List;

@Controller
public class UserControllerUI {

    @Autowired
    UserController userController;

    @Autowired
    TagController tagController;
    @RequestMapping("/user-profile")
    public String userProfilePage(HttpServletRequest request, Model model) throws URISyntaxException {
        try {
            User user = userController.getCurrentLoggedInUserProfile();
            model.addAttribute("user", user);

            return "userProfile.html";
        }catch (Exception e){
            return "redirect:/login";
        }
    }

    @RequestMapping("/edit-user-profile")
    public String editUserProfilePage(HttpServletRequest request, Model model) throws URISyntaxException {

        User user = userController.getCurrentLoggedInUserProfile();
        List<Tag> tags = tagController.getAllTags();
        model.addAttribute("user", user);
        model.addAttribute("tags", tags);

        return "editUserProfile.html";
    }
}
