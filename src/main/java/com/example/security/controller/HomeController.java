package com.example.security.controller;

import com.example.security.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @PreAuthorize("isAuthenticated()")
   @GetMapping("/")
    public String HomePage(){
       return "home";
   }
//    @PreAuthorize("isAuthenticated()")
    @GetMapping("/sign-in")
    public String signInPage(){
        return "sign-in";
    }



}
