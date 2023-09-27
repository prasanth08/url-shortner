package com.example.urlshortner.controller;

import com.example.urlshortner.service.AnalyticsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Test class for AnalyticsController
 *
 * @author  Prasanth Omanakuttan
 * @version 1.0
 * @since   2023-09-27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AnalyticsControllerTest {
    @Mock
    private AnalyticsService analyticsService;

    @InjectMocks
    private AnalyticsController controller;

    @Test
    public void testHandleRedirectForShortUrl() {
        // Arrange
        Map<String, Long> expectedReport = new HashMap<>();
        expectedReport.put("clicks", 100L);
        expectedReport.put("views", 200L);
        when(analyticsService.getAllReport()).thenReturn(expectedReport);

        ResponseEntity<Map<String, Long>> responseEntity = controller.handleRedirectForShortUrl();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedReport, responseEntity.getBody());
    }

}
