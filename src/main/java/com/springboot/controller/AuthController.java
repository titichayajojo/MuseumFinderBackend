package com.springboot.controller;

import com.springboot.entity.Role;
import com.springboot.entity.Tag;
import com.springboot.entity.User;
import com.springboot.payload.LoginDto;
import com.springboot.payload.SignUpDto;
import com.springboot.repository.RoleRepository;
import com.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/userLogin")
    public ResponseEntity<User> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String usernameOrEmail = loginDto.getUsernameOrEmail().toString();
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail).get();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/guest/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) throws ParseException {

        // add check for username exists in a DB
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        // create user object
        User user = new User();
        user.setFirstName(signUpDto.getFirstName());
        user.setLastName(signUpDto.getLastName());
        user.setDateOfBirth(df.parse(signUpDto.getDateOfBirth()));
        user.setZipCode(signUpDto.getZipCode());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setTags(signUpDto.getTags());

        String role = signUpDto.getRole();
        Role roles = role.equals("admin")? roleRepository.findByName("ROLE_ADMIN").get() : roleRepository.findByName("ROLE_USER").get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>(user, HttpStatus.OK);

    }
}
