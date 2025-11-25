package io.github.flashlack1314.quickstart.config;

import io.github.flashlack1314.quickstart.handler.GlobalExceptionHandler;
import io.github.flashlack1314.quickstart.properties.QuickStartBootKitProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Spring Boot 自动配置测试
 *
 * @author flash
 */
class QuickStartBootKitAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(TestConfiguration.class)
            .withConfiguration(AutoConfigurations.of(QuickStartBootKitAutoConfiguration.class));

    @Test
    void testGlobalExceptionHandlerAutoConfiguration() {
        contextRunner.withPropertyValues("quickstart.bootkit.exception-handler=true")
                .run(context -> {
                    assertThat(context).hasSingleBean(GlobalExceptionHandler.class);
                    assertThat(context).hasSingleBean(QuickStartBootKitProperties.class);
                });
    }

    @Test
    void testGlobalExceptionHandlerDisabled() {
        contextRunner.withPropertyValues("quickstart.bootkit.exception-handler=false")
                .run(context -> {
                    assertThat(context).doesNotHaveBean(GlobalExceptionHandler.class);
                    assertThat(context).hasSingleBean(QuickStartBootKitProperties.class);
                });
    }

    @Test
    void testDefaultConfiguration() {
        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(GlobalExceptionHandler.class);
            assertThat(context).hasSingleBean(QuickStartBootKitProperties.class);
        });
    }

    @Configuration
    static class TestConfiguration {
        // 测试配置类
    }
}