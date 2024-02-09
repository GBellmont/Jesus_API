package br.com.luan.barcella.jesus.api.config;

import static java.time.Duration.ofSeconds;
import static java.util.Objects.nonNull;
import static org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import br.com.luan.barcella.jesus.api.domain.CacheName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.stream.Collectors;

@Configuration
@EnableCaching
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CacheConfiguration extends CachingConfigurerSupport {

    private static final String CACHE_TTL_PREFIX = "cache.ttl.";
    private static final String CACHE_NAME_PREFIX = "JesusApi:";
    private final Environment environment;
    private final RedisConnectionFactory redisConnectionFactory;

    @Bean
    @Override
    public CacheManager cacheManager() {
        final Map<String, RedisCacheConfiguration> cacheConfiguration = setupCacheFor(
            CacheName.CONSULTA_LIVROS,
            CacheName.CONSULTA_LIVRO
        );

        return RedisCacheManager.builder(redisConnectionFactory)
            .withInitialCacheConfigurations(cacheConfiguration)
            .build();
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            log.info("Gerando chave de cache: target={}, method={}, params={}", target, method, params);

            if (nonNull(params) && params.length > 0) {
                return Arrays.stream(params).map(String::valueOf).collect(Collectors.joining(","));
            }

            return target.getClass().getName().concat("#").concat(method.getName());
        };
    }

    private Map<String, RedisCacheConfiguration> setupCacheFor(final String... cacheName) {
        final Map<String, RedisCacheConfiguration> cacheConfiguration = new HashMap<>();
        Arrays.stream(cacheName).forEach(cache -> cacheConfiguration.put(cache, createCacheConfiguration(cache)));
        return cacheConfiguration;
    }

    private RedisCacheConfiguration createCacheConfiguration(final String ttlKey) {
        final Long ttl = environment.getProperty(CACHE_TTL_PREFIX + getEntryApplicationKey(ttlKey), Long.class);
        log.info("Configurando cache para: key={}, ttl={}", ttlKey, ttl);
        return defaultCacheConfig().entryTtl(ofSeconds(ttl));
    }

    private String getEntryApplicationKey(final String key) {
        return key.replace(CACHE_NAME_PREFIX, "");
    }

}
