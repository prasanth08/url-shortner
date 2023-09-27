package com.example.urlshortner.controller;


import com.example.urlshortner.model.ShortenRequest;
import com.example.urlshortner.service.ShortnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("app")
public class URLShortnerController {

    private ShortnerService shortnerService;

    @Autowired
    public void setShortnerService(ShortnerService shortnerService) {
        this.shortnerService = shortnerService;
    }

    @GetMapping("redirect/{shortUrl}")
    public ResponseEntity<?> handleRedirectForShortUrl(@PathVariable String shortUrl){
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping(value = "/shorten",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> shortenUrl(@RequestBody ShortenRequest request){
        var response = shortnerService.createShortUrl(request);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
}
