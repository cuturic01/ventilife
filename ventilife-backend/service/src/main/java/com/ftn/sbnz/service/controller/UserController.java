package com.ftn.sbnz.service.controller;

import com.ftn.sbnz.service.dto.ErrorDTO;
import com.ftn.sbnz.service.dto.UserDTO;
import com.ftn.sbnz.service.dto.UserTokenState;
import com.ftn.sbnz.service.mapper.UserMapper;
import com.ftn.sbnz.service.model.User;
import com.ftn.sbnz.service.service.UserService;
import com.ftn.sbnz.service.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;


@CrossOrigin
@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping(value="/register", consumes = "application/json")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO){
        User user = userService.findByEmail(userDTO.getEmail());
        if (user != null) return new ResponseEntity<>(new ErrorDTO("User with that email already exists!"), HttpStatus.BAD_REQUEST);
        UserDTO createdUser = userService.register(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<?> login(@RequestBody UserDTO authenticationRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(), authenticationRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = (User) authentication.getPrincipal();
            String jwt = tokenUtils.generateToken(user);
            int expiresIn = tokenUtils.getExpiredIn();

            System.out.println(user.getAuthorities());

            return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorDTO("Wrong username or password!"), HttpStatus.BAD_REQUEST);
        }

    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping(value = "/logout")
    public ResponseEntity<String> logout(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)){
            SecurityContextHolder.clearContext();
        }
        return new ResponseEntity<>("You successfully logged out!", HttpStatus.OK);

    }

}
