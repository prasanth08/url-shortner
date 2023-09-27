package com.example.urlshortner.service;

import com.example.urlshortner.model.ShortenRequest;
import com.example.urlshortner.model.ShortenResponse;
import com.google.common.hash.Hashing;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ShortnerService {

    private StringRedisTemplate redisTemplate;

    private static final UrlValidator validator =
            new UrlValidator(new String[]{"http","https"});

    private static final String APP = "app";
    private static final String formedURL = "localhost:8080/%s/%s";

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
               return new ShortenResponse(redisTemplate.opsForValue().get(APP+hash));
           }
           // create new if not present
           else{
               LOGGER.log(Level.INFO,"Created a new hash for {0}",originalUrl);
               var newUrl = formedURL.formatted(APP,hash);
               redisTemplate.opsForValue().set(APP+hash,originalUrl);
               return new ShortenResponse(newUrl);

           }
       }
       else{
           throw new RuntimeException("");
       }
    }

    public URI getOriginalUrl(String hash) throws URISyntaxException {
        var value = redisTemplate.opsForValue().get(APP+hash);
        return new URI(value);
    }
}
