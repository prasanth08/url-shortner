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


    /**
     * Creates an entry into a concurrent multiset for each count
     * It's an async method since it does not concern user response
     * We are using a jvm in memory data structure to avoid network calls towards redis
     * @param url
     */
    @Async
    public void analyzeURL(String url){
        url = StringUtils.substringBeforeLast(url,".");
        url = StringUtils.substringAfter(url,"//");
        counter.add(url);
    }

    /**
     *
     * @return count of each host that is shortened
     */
    public Map<String,Long> getAllReport(){
        var countMap = new HashMap<String,Long>();
        var keys = redisTemplate.keys("*");
        keys.parallelStream()
                .map(s->redisTemplate.opsForValue().get(s))
                .map(s -> StringUtils.substringBefore(s,"."))
                .map(s-> StringUtils.substringAfter(s,"//"))
                .forEach(s -> countMap.put(s, (long) counter.count(s)));
        return countMap;
    }

}
