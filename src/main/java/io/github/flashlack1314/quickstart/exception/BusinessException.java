package io.github.flashlack1314.quickstart.exception;

/**
 * 业务异常类
 * 用于处理业务逻辑中的异常情况
 *
 * @author flash
 */
public class BusinessException extends BaseException {

    /**
     * 构造函数
     *
     * @param code    错误码
     * @param message 错误消息
     */
    public BusinessException(Integer code, String message) {
        super(code, message);
    }

    /**
     * 构造函数（带原因）
     *
     * @param code    错误码
     * @param message 错误消息
     * @param cause   异常原因
     */
    public BusinessException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }

    /**
     * 构造函数（默认错误码为500）
     *
     * @param message 错误消息
     */
    public BusinessException(String message) {
        this(500, message);
    }

    /**
     * 构造函数（默认错误码为500，带原因）
     *
     * @param message 错误消息
     * @param cause   异常原因
     */
    public BusinessException(String message, Throwable cause) {
        this(500, message, cause);
    }
}