package io.github.flashlack1314.quickstart.handler;

import io.github.flashlack1314.quickstart.exception.BaseException;
import io.github.flashlack1314.quickstart.exception.BusinessException;
import io.github.flashlack1314.quickstart.exception.ParameterException;
import io.github.flashlack1314.quickstart.exception.SystemException;
import io.github.flashlack1314.quickstart.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 * 统一异常捕获和处理，自动转换为ResultVO格式
 *
 * @author flash
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理基础异常
     *
     * @param e 基础异常
     * @return ResultVO格式的错误响应
     */
    @ExceptionHandler(BaseException.class)
    public ResultVO<Void> handleBaseException(BaseException e) {
        logger.error("基础异常: {}", e.getMessage(), e);
        return ResultVO.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理业务异常
     *
     * @param e 业务异常
     * @return ResultVO格式的错误响应
     */
    @ExceptionHandler(BusinessException.class)
    public ResultVO<Void> handleBusinessException(BusinessException e) {
        logger.error("业务异常: {}", e.getMessage(), e);
        return ResultVO.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数异常
     *
     * @param e 参数异常
     * @return ResultVO格式的错误响应
     */
    @ExceptionHandler(ParameterException.class)
    public ResultVO<Void> handleParameterException(ParameterException e) {
        logger.error("参数异常: {}", e.getMessage(), e);
        return ResultVO.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理系统异常
     *
     * @param e 系统异常
     * @return ResultVO格式的错误响应
     */
    @ExceptionHandler(SystemException.class)
    public ResultVO<Void> handleSystemException(SystemException e) {
        logger.error("系统异常: {}", e.getMessage(), e);
        return ResultVO.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理运行时异常
     *
     * @param e 运行时异常
     * @return ResultVO格式的错误响应
     */
    @ExceptionHandler(RuntimeException.class)
    public ResultVO<Void> handleRuntimeException(RuntimeException e) {
        logger.error("运行时异常: {}", e.getMessage(), e);
        return ResultVO.error(500, "系统内部错误");
    }

    /**
     * 处理通用异常
     *
     * @param e 通用异常
     * @return ResultVO格式的错误响应
     */
    @ExceptionHandler(Exception.class)
    public ResultVO<Void> handleException(Exception e) {
        logger.error("系统异常: {}", e.getMessage(), e);
        return ResultVO.error(500, "系统异常，请联系管理员");
    }

    /**
     * 处理空指针异常
     *
     * @param e 空指针异常
     * @return ResultVO格式的错误响应
     */
    @ExceptionHandler(NullPointerException.class)
    public ResultVO<Void> handleNullPointerException(NullPointerException e) {
        logger.error("空指针异常: {}", e.getMessage(), e);
        return ResultVO.error(500, "系统内部错误 - 空指针异常");
    }

    /**
     * 处理非法参数异常
     *
     * @param e 非法参数异常
     * @return ResultVO格式的错误响应
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResultVO<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        logger.error("非法参数异常: {}", e.getMessage(), e);
        return ResultVO.error(400, "参数错误: " + e.getMessage());
    }

    /**
     * 处理类未找到异常
     *
     * @param e 类未找到异常
     * @return ResultVO格式的错误响应
     */
    @ExceptionHandler(ClassNotFoundException.class)
    public ResultVO<Void> handleClassNotFoundException(ClassNotFoundException e) {
        logger.error("类未找到异常: {}", e.getMessage(), e);
        return ResultVO.error(500, "系统配置错误");
    }

    /**
     * 处理算术异常
     *
     * @param e 算术异常
     * @return ResultVO格式的错误响应
     */
    @ExceptionHandler(ArithmeticException.class)
    public ResultVO<Void> handleArithmeticException(ArithmeticException e) {
        logger.error("算术异常: {}", e.getMessage(), e);
        return ResultVO.error(400, "计算错误: " + e.getMessage());
    }

    /**
     * 处理数组越界异常
     *
     * @param e 数组越界异常
     * @return ResultVO格式的错误响应
     */
    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public ResultVO<Void> handleArrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException e) {
        logger.error("数组越界异常: {}", e.getMessage(), e);
        return ResultVO.error(400, "数据访问越界");
    }

    /**
     * 处理类型转换异常
     *
     * @param e 类型转换异常
     * @return ResultVO格式的错误响应
     */
    @ExceptionHandler(ClassCastException.class)
    public ResultVO<Void> handleClassCastException(ClassCastException e) {
        logger.error("类型转换异常: {}", e.getMessage(), e);
        return ResultVO.error(500, "数据类型转换错误");
    }

    /**
     * 处理数字格式异常
     *
     * @param e 数字格式异常
     * @return ResultVO格式的错误响应
     */
    @ExceptionHandler(NumberFormatException.class)
    public ResultVO<Void> handleNumberFormatException(NumberFormatException e) {
        logger.error("数字格式异常: {}", e.getMessage(), e);
        return ResultVO.error(400, "数字格式错误");
    }

    /**
     * 批量处理异常信息
     *
     * @param errors 错误信息集合
     * @return ResultVO格式的错误响应
     */
    public ResultVO<Map<String, Object>> handleBatchErrors(Map<String, String> errors) {
        logger.error("批量异常处理，错误数量: {}", errors.size());

        Map<String, Object> errorData = new HashMap<>();
        errorData.put("errors", errors);
        errorData.put("errorCount", errors.size());

        return ResultVO.<Map<String, Object>>error(400, "批量参数校验失败").setData(errorData);
    }
}