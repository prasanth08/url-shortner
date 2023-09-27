package com.example.urlshortner.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("app")
public class URLShortnerController {

    @GetMapping("redirect/{shortUrl}")
    public ResponseEntity<?> handleRedirectForShortUrl(@PathVariable String shortUrl){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
