package com.springboot.thymeleaf.controller;


import com.springboot.controller.TagController;
import com.springboot.controller.UserController;
import com.springboot.entity.Museum;
import com.springboot.entity.Role;
import com.springboot.entity.Tag;
import com.springboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class AdminControllerUI {

    @Autowired
    UserController userController;

    @Autowired
    TagController tagController;

    @Autowired
    TopNavigationBarControllerUI topNavigationBarControllerUI;


    @RequestMapping(value = "/tag-add", method = GET)
    public String CreateMuseumPage(HttpServletRequest request, Model model){

        List<Tag> tags = tagController.getAllTags();
        model.addAttribute("tags",tags);
        model.addAttribute("tag", new Tag());

        topNavigationBarControllerUI.topNavigationBarController(request, model);

        return "createTag.html";
    }

    @RequestMapping(value = "/tag-add/created", method = POST)
    public String CreateMuseumMethod(HttpServletRequest request, Model model,Tag tagDetails ) {

        Tag tag = (Tag) tagController.createTag(tagDetails).getBody();

        model.addAttribute("tag", tag);

        topNavigationBarControllerUI.topNavigationBarController(request, model);

        return "redirect:/tag-add";
    }
}
