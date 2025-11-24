package io.github.flashlack1314.quickstart.exception;

import lombok.Getter;

/**
 * 基础异常类
 * 所有自定义异常的父类
 *
 * @author flash
 */
public class BaseException extends RuntimeException {

    /**
     * 错误码
     */
    @Getter
    private final Integer code;

    /**
     * 错误消息
     */
    private final String message;

    /**
     * 构造函数
     *
     * @param code    错误码
     * @param message 错误消息
     */
    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 构造函数（带原因）
     *
     * @param code    错误码
     * @param message 错误消息
     * @param cause   异常原因
     */
    public BaseException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}