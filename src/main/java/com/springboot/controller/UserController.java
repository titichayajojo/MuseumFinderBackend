package com.springboot.controller;

import com.springboot.entity.Employee;
import com.springboot.entity.Museum;
import com.springboot.entity.Tag;
import com.springboot.entity.User;
import com.springboot.exception.ResourceNotFoundException;
import com.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Path;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    MuseumController museumController;

    @GetMapping("/admin/users/info")
    public List<User> getAllUsers() { return userRepository.findAll(); }

    @PostMapping("/user/users/info/{id}")
    public String updateUserById(@PathVariable(value = "id") Long userId,
                                 @Valid User userDetails) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

        if(userDetails.getFirstName() != null) user.setFirstName(userDetails.getFirstName());
        if(userDetails.getLastName() != null) user.setLastName(userDetails.getLastName());
        if(userDetails.getTags() != null) {
            List<String> tags = userDetails.getTags();
            while (tags.remove(null));
            userDetails.setTags((ArrayList<String>) tags);
            editCurrentUserTags(userDetails);
        }
        if(userDetails.getZipCode() != 0) user.setZipCode(userDetails.getZipCode());

        final User updatedUser = userRepository.save(user);

        return "redirect:/user-profile";
    }

    @GetMapping("/user/info")
    public User getCurrentLoggedInUserProfile(){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = userRepository.findByUsernameOrEmail(username,username).get();
        return user;
    }

    @PostMapping("/user/tags")
    public  User editCurrentUserTags(@Valid @RequestBody User userDetails){
        User user = this.getCurrentLoggedInUserProfile();
        if(userDetails.getTags() != null){ user.setTags(userDetails.getTags()); }

        final User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    @GetMapping("/user/museums")
    public List<Museum> getMuseumsFromCurrentUser() {
        try {
            User user = this.getCurrentLoggedInUserProfile();
            ArrayList<String> userTags = user.getTags();
            List<Museum> museums = (List<Museum>) museumController.sortMuseumsByTags(userTags).getBody();
            return museums;
        } catch (Exception e) {
            return museumController.getAllMuseums();
        }
    }


}
