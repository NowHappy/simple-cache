package com.example.simplecache.common;

import com.example.simplecache.domain.cache.Cache;
import com.example.simplecache.domain.cache.CacheService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

@Component
@Aspect
public class CacheAdvice {

    private final CacheService cacheService;

    public CacheAdvice(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Around("@annotation(com.example.simplecache.common.CacheEviction)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Map<String, Cache> cacheObj = cacheService.cache;
        // 메모리를 너무 많이 사용해서 램 공간 부족으로 서비스 장애가 발생하지 않도록 cache 데이터가 100개가 넘어가면 eviction 실행
        if (cacheObj.size() >= 100) {
            // LRU 값을 찾음
            Optional<Integer>
                min =
                cacheObj.values().stream().map(cache -> cache.getHowManyUsed()).min(Comparator.comparingInt(Integer::intValue));
            // LRU
            String removeKey = cacheObj.keySet().stream().filter(key -> cacheObj.get(key).getHowManyUsed() <= min.orElse(0)).findFirst().get();
            cacheObj.remove(removeKey);
        }

        // @CacheEviction 애노테이션이 붙어있는 타겟 메소드를 실행
        Object proceed = joinPoint.proceed();

        return proceed; // 결과 리턴
    }

}
