package com.example.urlshortner.controller;

import com.example.urlshortner.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST Controller class for all the admin facing business use cases
 *
 * @author  Prasanth Omanakuttan
 * @version 1.0
 * @since   2023-09-27
 */
@RestController
@RequestMapping("internal")
public class AnalyticsController {

    private AnalyticsService analyticsService;
    @Autowired
    public void setAnalyticsService(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/analysis")
    public ResponseEntity<Map<String,Long>> handleRedirectForShortUrl() {
        var report = analyticsService.getAllReport();
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
}
