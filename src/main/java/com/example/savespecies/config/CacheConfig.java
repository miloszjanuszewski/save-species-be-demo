package com.example.savespecies.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class CacheConfig {

    public static final String ALL_SPECIES_CACHE = "allSpeciesCache";

    @Bean
    public CacheManager cacheManager() {
        final SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(List.of(getAllSpeciesCache()));
        return cacheManager;
    }

    @Bean
    public CaffeineCache getAllSpeciesCache() {
        return new CaffeineCache(ALL_SPECIES_CACHE,
                                 Caffeine.newBuilder()
                                         .expireAfterWrite(10L, TimeUnit.MINUTES)
                                         .maximumSize(100)
                                         .build());
    }
}
