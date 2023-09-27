package com.example.urlshortner.service;

import com.example.urlshortner.exception.URLNotFoundException;
import com.example.urlshortner.exception.ValidationException;
import com.example.urlshortner.model.ShortenRequest;
import com.example.urlshortner.model.ShortenResponse;
import com.google.common.hash.Hashing;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ShortnerService {

    private StringRedisTemplate redisTemplate;

    private static final UrlValidator validator =
            new UrlValidator(new String[]{"http","https"});

    private static final String APP = "app";
    private static final String FORMED_URL = "localhost:8080/%s/%s";

    private static final Logger LOGGER = Logger.getLogger(ShortnerService.class.getName());


    @Autowired
    public void setRedisTemplate(StringRedisTemplate stringRedisTemplate){
        this.redisTemplate = stringRedisTemplate;
    }

    public ShortenResponse createShortUrl(@NonNull ShortenRequest request){
       var originalUrl =  request.originalUrl();
       //validate url
       if(validator.isValid(originalUrl)){
           //if present use the same
           var hash = Hashing.murmur3_32_fixed().hashString(originalUrl, StandardCharsets.UTF_8).toString();
           if(Boolean.TRUE.equals(redisTemplate.hasKey(APP+hash))){
               return new ShortenResponse(APP+hash);
           }
           // create new if not present
           else{
               LOGGER.log(Level.INFO,"Created a new hash for {0}",originalUrl);
               var newUrl = FORMED_URL.formatted(APP,hash);
               redisTemplate.opsForValue().set(APP+hash,originalUrl);
               return new ShortenResponse(newUrl);

           }
       }
       else{
           throw new ValidationException("Passed in url is not a valid.");
       }
    }

    public URI getOriginalUrl(String hash) throws Exception {
        try {
            var value = redisTemplate.opsForValue().get(APP + hash);
            if (value != null) {
                return new URI(value);
            }
        }
        catch (Exception ex){
            LOGGER.log(Level.SEVERE,"Exception on reading from cache",ex);
            throw ex;
        }
        throw new URLNotFoundException("Url is not shortened before or is evicted from cache.");
    }
}
