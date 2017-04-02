package com.oplever.sioe.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.oplever.sioe.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.oplever.sioe.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.oplever.sioe.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.oplever.sioe.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(com.oplever.sioe.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(com.oplever.sioe.domain.Usuario.class.getName(), jcacheConfiguration);
            cm.createCache(com.oplever.sioe.domain.Peticionario.class.getName(), jcacheConfiguration);
            cm.createCache(com.oplever.sioe.domain.Peticion.class.getName(), jcacheConfiguration);
            cm.createCache(com.oplever.sioe.domain.Prevencion.class.getName(), jcacheConfiguration);
            cm.createCache(com.oplever.sioe.domain.Anexo.class.getName(), jcacheConfiguration);
            cm.createCache(com.oplever.sioe.domain.Anexo_prevencion.class.getName(), jcacheConfiguration);
            cm.createCache(com.oplever.sioe.domain.Anexo_evaluacion.class.getName(), jcacheConfiguration);
            cm.createCache(com.oplever.sioe.domain.Evaluacion.class.getName(), jcacheConfiguration);
            cm.createCache(com.oplever.sioe.domain.Procedente.class.getName(), jcacheConfiguration);
            cm.createCache(com.oplever.sioe.domain.Improcedente.class.getName(), jcacheConfiguration);
            cm.createCache(com.oplever.sioe.domain.Presentacion.class.getName(), jcacheConfiguration);
            cm.createCache(com.oplever.sioe.domain.Usuario.class.getName() + ".peticions", jcacheConfiguration);
            cm.createCache(com.oplever.sioe.domain.Peticionario.class.getName() + ".peticions", jcacheConfiguration);
            cm.createCache(com.oplever.sioe.domain.Peticion.class.getName() + ".anexos", jcacheConfiguration);
            cm.createCache(com.oplever.sioe.domain.Prevencion.class.getName() + ".anexo_prevencions", jcacheConfiguration);
            cm.createCache(com.oplever.sioe.domain.Evaluacion.class.getName() + ".anexo_evaluacions", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
