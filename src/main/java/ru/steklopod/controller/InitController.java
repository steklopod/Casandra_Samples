package ru.steklopod.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class InitController {
    @GetMapping(value = "/{fileName}")
    public void getRarFromJar(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        System.err.println(fileName);
    }
}

