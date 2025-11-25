package io.github.flashlack1314.quickstart.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * QuickStart Boot Kit 配置属性
 *
 * @author flash
 */
@ConfigurationProperties(prefix = "quickstart.bootkit")
public class QuickStartBootKitProperties {

    /**
     * 是否启用全局异常处理器
     */
    private boolean exceptionHandler = true;

    /**
     * 是否启用响应格式统一
     */
    private boolean responseFormat = true;

    public boolean isExceptionHandler() {
        return exceptionHandler;
    }

    public void setExceptionHandler(boolean exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    public boolean isResponseFormat() {
        return responseFormat;
    }

    public void setResponseFormat(boolean responseFormat) {
        this.responseFormat = responseFormat;
    }
}