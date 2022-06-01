package com.springboot.thymeleaf.controller;

import com.springboot.controller.MuseumController;
import com.springboot.controller.TagController;
import com.springboot.controller.UserController;
import com.springboot.entity.Museum;
import com.springboot.entity.Tag;
import com.springboot.entity.User;
import com.springboot.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class MuseumControllerUI {

    @Autowired
    MuseumController museumController;
    @Autowired
    TagController tagController;

    @Autowired
    UserController userController;

    @RequestMapping(value = "/museum/{id}", method = GET)
    public String MuseumByIdPage(HttpServletRequest request, Model model, @PathVariable("id") long id) throws ResourceNotFoundException {

        Museum museum = (Museum) museumController.getMuseumById(id).getBody();
        model.addAttribute("museum", museum);

        try {
            User user = userController.getCurrentLoggedInUserProfile();
            model.addAttribute("user", user);
        }catch (Exception e){}

        return "museum.html";
    }

    @RequestMapping(value = "/museum-add", method = GET)
    public String CreateMuseumPage(HttpServletRequest request, Model model){

        List<Tag> tags = tagController.getAllTags();
        model.addAttribute("tags",tags);
        model.addAttribute("museum", new Museum());

        try {
            User user = userController.getCurrentLoggedInUserProfile();
            model.addAttribute("user", user);
        }catch (Exception e){}

        return "createMuseum.html";
    }

    @RequestMapping(value = "/museum-add/created", method = POST)
    public String CreateMuseumMethod(HttpServletRequest request, Model model,Museum museumDetails ,@RequestParam("image") MultipartFile multipartFile) throws IOException {
        List<String> tags = museumDetails.getTags();
        while (tags.remove(null));
        museumDetails.setTags((ArrayList<String>) tags);

        Museum museum = (Museum) museumController.createMuseum(museumDetails).getBody();
        museumController.addImage(museum.getId(),multipartFile);

        model.addAttribute("museum", museum);

        try {
            User user = userController.getCurrentLoggedInUserProfile();
            model.addAttribute("user", user);
        }catch (Exception e){}

        return "redirect:/museum/" + museum.getId();
    }

}
