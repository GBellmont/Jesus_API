package br.com.luan.barcella.jesus.api.service.support;

import static java.util.Collections.emptyMap;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Stream.of;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Component
@RefreshScope
public class ConfigService {

    @Autowired
    private Environment env;

    public String get(final Key key) {
        return env.getProperty(key.getValue());
    }

    public Integer getInt(final Key key) {
        return env.getProperty(key.getValue(), Integer.class);
    }

    public Boolean getBoolean(final Key key) {
        return env.getProperty(key.getValue(), Boolean.class);
    }

    public Map<String, String> getStringKeyMap(final Key key) {

        final String property = env.getProperty(key.getValue());

        return ofNullable(property)
                .map(propertyText -> of(propertyText.substring(1, property.length() - 1).split(","))
                        .collect(toMap(entry -> entry.split(":")[0].trim(), entry -> entry.split(":")[1].trim())))
                .orElse(emptyMap());
    }

    @Getter(AccessLevel.PROTECTED)
    @RequiredArgsConstructor
    public enum Key {

        KEY("key.final");

        private final String value;
    }
}
