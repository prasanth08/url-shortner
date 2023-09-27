package com.example.urlshortner.controller;


import com.example.urlshortner.exception.URLNotFoundException;
import com.example.urlshortner.exception.ValidationException;
import com.example.urlshortner.model.ShortenRequest;
import com.example.urlshortner.service.ShortnerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for URLShortnerController
 *
 * @author  Prasanth Omanakuttan
 * @version 1.0
 * @since   2023-09-27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class URLShortnerControllerTest {

    @Autowired
    private MockMvc mvc;
    @Mock
    private ShortnerService shortnerService;

    @InjectMocks
    URLShortnerController urlShortnerController;


    @Test
    public void testShortening() throws Exception {
        mvc.perform(post("/app/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new ShortenRequest("https://google.com"))))
                .andExpect(status().isCreated());
    }

    @Test
    public void testValidationFailure() throws Exception {
        mvc.perform(post("/app/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new ShortenRequest("https://@google.com"))))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationException));
    }

    @Test
    public void testSameShortenUrlConsistency() throws Exception {
        var newUrl1 = mvc.perform(post("/app/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new ShortenRequest("https://google.com"))))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var newUrl2 = mvc.perform(post("/app/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new ShortenRequest("https://google.com"))))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(newUrl1,newUrl2);
    }

    @Test
    public void testShortenUrlConsistency() throws Exception {
        var newUrl1 = mvc.perform(post("/app/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new ShortenRequest("https://google.com"))))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var newUrl2 = mvc.perform(post("/app/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new ShortenRequest("https://youtube.com"))))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assert.assertNotEquals(newUrl1,newUrl2);
    }

    @Test
    public void testNotFoundFailure() throws Exception {
        mvc.perform(get("/app/bd6cd6b6")
                        .contentType(MediaType.APPLICATION_JSON)
                        )
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof URLNotFoundException));
    }

    @Test
    public void testHandleRedirectForShortUrl() throws Exception {
        // Arrange
        String shortUrl = "abc123";
        String originalUrl = "http://example.com";
        HttpHeaders expectedHeaders = new HttpHeaders();
        expectedHeaders.setLocation(new URI(originalUrl));
        when(shortnerService.getOriginalUrl(shortUrl)).thenReturn(new URI(originalUrl));
        ResponseEntity<String> responseEntity = urlShortnerController.handleRedirectForShortUrl(shortUrl);
        assertEquals(HttpStatus.MOVED_PERMANENTLY, responseEntity.getStatusCode());
        assertEquals(expectedHeaders, responseEntity.getHeaders());
    }

}
