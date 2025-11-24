package io.github.flashlack1314.quickstart.exception;

/**
 * 参数异常类
 * 用于处理参数校验失败等异常情况
 *
 * @author flash
 */
public class ParameterException extends BaseException {

    /**
     * 构造函数
     *
     * @param code    错误码
     * @param message 错误消息
     */
    public ParameterException(Integer code, String message) {
        super(code, message);
    }

    /**
     * 构造函数（带原因）
     *
     * @param code    错误码
     * @param message 错误消息
     * @param cause   异常原因
     */
    public ParameterException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }

    /**
     * 构造函数（默认错误码为400）
     *
     * @param message 错误消息
     */
    public ParameterException(String message) {
        this(400, message);
    }

    /**
     * 构造函数（默认错误码为400，带原因）
     *
     * @param message 错误消息
     * @param cause   异常原因
     */
    public ParameterException(String message, Throwable cause) {
        this(400, message, cause);
    }
}