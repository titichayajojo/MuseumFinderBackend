package com.springboot.controller;

import com.springboot.entity.Employee;
import com.springboot.entity.Tag;
import com.springboot.entity.User;
import com.springboot.exception.ResourceNotFoundException;
import com.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Path;
import javax.validation.Valid;
import java.lang.module.ResolutionException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/admin/users/info")
    public List<User> getAllUsers() { return userRepository.findAll(); }

    @PutMapping("/user/users/info/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable(value = "id") Long userId,
                                                   @Valid @RequestBody User userDetails) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

        if(userDetails.getTags() != null) user.setTags(userDetails.getTags());
        if(userDetails.getFirstName() != null) user.setFirstName(userDetails.getFirstName());
        if(userDetails.getLastName() != null) user.setLastName(userDetails.getLastName());

        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/user/users/tags/{id}")
    public ResponseEntity<ArrayList<String>> getAllTagsByUserId(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException{
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
        ArrayList<String> tags = user.getTags();
        return ResponseEntity.ok(tags);
    }

    @PutMapping("/user/users/tags/{id}")
    public ResponseEntity<User> addUserTagByUserId(@PathVariable(value = "id") Long userId,
                                                   @Valid @RequestBody User userDetails) throws ResourceNotFoundException{
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResolutionException("User not found for this id :: " + userId));
        ArrayList<String> userTags = user.getTags();
        ArrayList<String> newTags = userDetails.getTags();

        for(String tag : newTags){
            if(!userTags.contains(tag)) userTags.add(tag);
        }
        user.setTags(userTags);

        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/user/users/tags/{id}")
    public ResponseEntity<User> deleteUserTagByUserId(@PathVariable(value = "id") Long userId,
                                                      @Valid @RequestBody User userDetails) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResolutionException("User not found for this id :: " + userId));

        ArrayList<String> userTags = user.getTags();
        ArrayList<String> deleteTags = userDetails.getTags();

        for(String tag: deleteTags){
            userTags.remove(tag);
        }
        user.setTags(userTags);
        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
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
        System.out.println("HEHE");
        System.out.println(username);
        User user = userRepository.findByUsernameOrEmail(username,username).get();
        return user;
    }

}
