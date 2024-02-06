package br.com.luan.barcella.jesus.api.config;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.RandomUtils.nextLong;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@SuppressWarnings("all")
@RunWith(MockitoJUnitRunner.class)
public class CacheConfigurationTest {

    private static final String CACHE_TTL_PREFIX = "cache.ttl.";

    @Mock
    private Environment environment;
    @Mock
    private RedisConnectionFactory redisConnectionFactory;

    private CacheConfiguration target;
    private Method method;

    @Before
    public void setup() throws Exception {
        target = new CacheConfiguration(environment, redisConnectionFactory);
        method = getClass().getDeclaredMethod("setup");
    }

    @Test
    public void generateWithParamsNull() {
        final String expected = String.format("%s#%s", getClass().getName(), method.getName());

        assertEquals(expected, target.keyGenerator().generate(this, method, null));
    }

    @Test
    public void generateWithParamsEmpty() {

        final String expected = String.format("%s#%s", getClass().getName(), method.getName());

        assertEquals(expected, target.keyGenerator().generate(this, method, new Object[]{}));
    }

    @Test
    public void generateWithParams() {

        final Object primeiroParametro = randomAlphanumeric(5);
        final Object segundoParametro = nextLong();

        final String expected = String.format("%s,%s,null", primeiroParametro, segundoParametro);

        assertEquals(expected, target.keyGenerator().generate(this, method,
            new Object[]{primeiroParametro, segundoParametro, null}));
    }
}
