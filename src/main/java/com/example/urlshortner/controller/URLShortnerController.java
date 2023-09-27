package com.example.urlshortner.controller;


import com.example.urlshortner.exception.ValidationException;
import com.example.urlshortner.model.ShortenRequest;
import com.example.urlshortner.model.ShortenResponse;
import com.example.urlshortner.service.ShortnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> handleRedirectForShortUrl(@PathVariable String shortUrl) throws Exception {

        HttpHeaders header = new HttpHeaders();
        header.setLocation(shortnerService.getOriginalUrl(shortUrl));
        return new ResponseEntity<>(header,HttpStatus.MOVED_PERMANENTLY);
    }
    @PostMapping(value = "/shorten",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ShortenResponse> shortenUrl(@RequestBody ShortenRequest request) throws ValidationException {

        var response = shortnerService.createShortUrl(request);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
}
