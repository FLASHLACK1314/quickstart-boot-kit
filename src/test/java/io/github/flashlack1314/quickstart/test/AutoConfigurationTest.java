package io.github.flashlack1314.quickstart.test;

import io.github.flashlack1314.quickstart.config.QuickStartBootKitAutoConfiguration;
import io.github.flashlack1314.quickstart.handler.GlobalExceptionHandler;
import io.github.flashlack1314.quickstart.properties.QuickStartBootKitProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 验证自动配置是否正常工作
 */
@SpringBootApplication
public class AutoConfigurationTest {

    public static void main(String[] args) {
        System.out.println("开始测试自动配置...");

        // 启动Spring Boot应用，测试自动配置
        ConfigurableApplicationContext context = SpringApplication.run(AutoConfigurationTest.class, args);

        // 检查GlobalExceptionHandler是否自动配置
        if (context.containsBean("globalExceptionHandler")) {
            System.out.println("✅ GlobalExceptionHandler 自动配置成功");
            GlobalExceptionHandler handler = context.getBean(GlobalExceptionHandler.class);
            System.out.println("   - Bean类型: " + handler.getClass().getName());
        } else {
            System.out.println("❌ GlobalExceptionHandler 未自动配置");
        }

        // 检查QuickStartBootKitProperties是否自动配置
        if (context.containsBean("quickStartBootKitProperties")) {
            System.out.println("✅ QuickStartBootKitProperties 自动配置成功");
            QuickStartBootKitProperties props = context.getBean(QuickStartBootKitProperties.class);
            System.out.println("   - exception-handler: " + props.isExceptionHandler());
        } else {
            System.out.println("❌ QuickStartBootKitProperties 未自动配置");
        }

        System.out.println("自动配置测试完成，正在关闭应用...");
        context.close();
        System.out.println("应用已关闭");
    }
}