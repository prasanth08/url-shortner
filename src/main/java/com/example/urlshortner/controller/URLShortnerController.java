package com.example.urlshortner.controller;


import com.example.urlshortner.model.ShortenRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("app")
public class URLShortnerController {

    @GetMapping("redirect/{shortUrl}")
    public ResponseEntity<?> handleRedirectForShortUrl(@PathVariable String shortUrl){
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping(value = "/shorten",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> shortenUrl(@RequestBody ShortenRequest request){
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
