package io.github.flashlack1314.quickstart.config;

import io.github.flashlack1314.quickstart.handler.GlobalExceptionHandler;
import io.github.flashlack1314.quickstart.properties.QuickStartBootKitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * QuickStart Boot Kit 自动配置类
 *
 * @author flash
 */
@Configuration
@EnableConfigurationProperties(QuickStartBootKitProperties.class)
public class QuickStartBootKitAutoConfiguration {

    /**
     * 配置全局异常处理器
     *
     * @return GlobalExceptionHandler实例
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "quickstart.bootkit", name = "exception-handler", havingValue = "true", matchIfMissing = true)
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}