package com.kelempok7.serverapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAnyRole('ADMIN','USER')")
public class home {

    @GetMapping("/home")
    public String getHome(){
        return "Welcome Home";
    }
}
