package com.example.urlshortner.service;

import com.example.urlshortner.model.ShortenRequest;
import com.example.urlshortner.model.ShortenResponse;
import com.google.common.hash.Hashing;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Service
public class ShortnerService {

    private static final UrlValidator validator =
            new UrlValidator(new String[]{"http","https"});

    public ShortenResponse createShortUrl(@NonNull ShortenRequest request){
       var originalUrl =  request.originalUrl();
       if(validator.isValid(originalUrl)){
         var newUrl = Hashing.murmur3_32().hashString(originalUrl, StandardCharsets.UTF_8).toString();
           return new ShortenResponse(newUrl);
       }
       else{
           throw new RuntimeException("");
       }
    }
}
