package com.example.urlshortner.service;

import com.google.common.collect.ConcurrentHashMultiset;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AnalyticsService {

    private static final ConcurrentHashMultiset<String> counter  =  ConcurrentHashMultiset.create();
    private StringRedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(StringRedisTemplate stringRedisTemplate){
        this.redisTemplate = stringRedisTemplate;
    }

    @Async
    public void analyzeURL(String url){
        url = StringUtils.substringBeforeLast(url,".");
        counter.add(url);
    }

    public Map<String,Long> getAllReport(){
        var countMap = new HashMap<String,Long>();
        var keys = redisTemplate.keys("*");
        keys.parallelStream()
                .map(s->redisTemplate.opsForValue().get(s))
                .map(s -> StringUtils.substringBefore(s,"."))
                .forEach(s -> countMap.put(s, (long) counter.count(s)));
        return countMap;
    }

}
