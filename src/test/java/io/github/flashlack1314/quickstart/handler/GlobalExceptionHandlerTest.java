package io.github.flashlack1314.quickstart.handler;

import io.github.flashlack1314.quickstart.exception.*;
import io.github.flashlack1314.quickstart.vo.ResultVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

/**
 * GlobalExceptionHandler 测试类
 *
 * @author flash
 */
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleBaseException() {
        BaseException exception = new BaseException(1001, "基础异常测试") {};

        ResultVO<Void> result = handler.handleBaseException(exception);

        assertEquals(1001, result.getCode());
        assertEquals("基础异常测试", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testHandleBusinessException() {
        BusinessException exception = new BusinessException(2001, "业务逻辑错误");

        ResultVO<Void> result = handler.handleBusinessException(exception);

        assertEquals(2001, result.getCode());
        assertEquals("业务逻辑错误", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testHandleParameterException() {
        ParameterException exception = new ParameterException(4001, "参数验证失败");

        ResultVO<Void> result = handler.handleParameterException(exception);

        assertEquals(4001, result.getCode());
        assertEquals("参数验证失败", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testHandleSystemException() {
        SystemException exception = new SystemException(5001, "系统内部错误");

        ResultVO<Void> result = handler.handleSystemException(exception);

        assertEquals(5001, result.getCode());
        assertEquals("系统内部错误", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testHandleRuntimeException() {
        RuntimeException exception = new RuntimeException("运行时异常");

        ResultVO<Void> result = handler.handleRuntimeException(exception);

        assertEquals(500, result.getCode());
        assertEquals("系统内部错误", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testHandleGenericException() {
        Exception exception = new Exception("通用异常");

        ResultVO<Void> result = handler.handleException(exception);

        assertEquals(500, result.getCode());
        assertEquals("系统异常，请联系管理员", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testHandleNullPointerException() {
        NullPointerException exception = new NullPointerException("空指针异常");

        ResultVO<Void> result = handler.handleNullPointerException(exception);

        assertEquals(500, result.getCode());
        assertEquals("系统内部错误 - 空指针异常", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testHandleIllegalArgumentException() {
        IllegalArgumentException exception = new IllegalArgumentException("参数格式错误");

        ResultVO<Void> result = handler.handleIllegalArgumentException(exception);

        assertEquals(400, result.getCode());
        assertEquals("参数错误: 参数格式错误", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testHandleClassNotFoundException() {
        ClassNotFoundException exception = new ClassNotFoundException("类未找到");

        ResultVO<Void> result = handler.handleClassNotFoundException(exception);

        assertEquals(500, result.getCode());
        assertEquals("系统配置错误", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testHandleArithmeticException() {
        ArithmeticException exception = new ArithmeticException("除零错误");

        ResultVO<Void> result = handler.handleArithmeticException(exception);

        assertEquals(400, result.getCode());
        assertEquals("计算错误: 除零错误", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testHandleArrayIndexOutOfBoundsException() {
        ArrayIndexOutOfBoundsException exception = new ArrayIndexOutOfBoundsException("数组越界");

        ResultVO<Void> result = handler.handleArrayIndexOutOfBoundsException(exception);

        assertEquals(400, result.getCode());
        assertEquals("数据访问越界", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testHandleClassCastException() {
        ClassCastException exception = new ClassCastException("类型转换错误");

        ResultVO<Void> result = handler.handleClassCastException(exception);

        assertEquals(500, result.getCode());
        assertEquals("数据类型转换错误", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testHandleNumberFormatException() {
        NumberFormatException exception = new NumberFormatException("数字格式错误");

        ResultVO<Void> result = handler.handleNumberFormatException(exception);

        assertEquals(400, result.getCode());
        assertEquals("数字格式错误", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testHandleBatchErrors() {
        Map<String, String> errors = new HashMap<>();
        errors.put("username", "用户名不能为空");
        errors.put("email", "邮箱格式不正确");
        errors.put("age", "年龄必须大于0");

        ResultVO<Map<String, Object>> result = handler.handleBatchErrors(errors);

        assertEquals(400, result.getCode());
        assertEquals("批量参数校验失败", result.getMessage());
        assertNotNull(result.getData());

        Map<String, Object> errorData = result.getData();
        assertEquals(errors, errorData.get("errors"));
        assertEquals(3, errorData.get("errorCount"));
    }

    @Test
    void testHandleBatchErrorsWithEmptyMap() {
        Map<String, String> emptyErrors = new HashMap<>();

        ResultVO<Map<String, Object>> result = handler.handleBatchErrors(emptyErrors);

        assertEquals(400, result.getCode());
        assertEquals("批量参数校验失败", result.getMessage());
        assertNotNull(result.getData());

        Map<String, Object> errorData = result.getData();
        assertEquals(emptyErrors, errorData.get("errors"));
        assertEquals(0, errorData.get("errorCount"));
    }

    @Test
    void testHandleBusinessExceptionWithDefaultCode() {
        BusinessException exception = new BusinessException("默认业务异常");

        ResultVO<Void> result = handler.handleBusinessException(exception);

        assertEquals(500, result.getCode());
        assertEquals("默认业务异常", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testHandleParameterExceptionWithDefaultCode() {
        ParameterException exception = new ParameterException("默认参数异常");

        ResultVO<Void> result = handler.handleParameterException(exception);

        assertEquals(400, result.getCode());
        assertEquals("默认参数异常", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testHandleSystemExceptionWithDefaultCode() {
        SystemException exception = new SystemException("默认系统异常");

        ResultVO<Void> result = handler.handleSystemException(exception);

        assertEquals(500, result.getCode());
        assertEquals("默认系统异常", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testHandleExceptionWithNullMessage() {
        RuntimeException exception = new RuntimeException((String) null);

        ResultVO<Void> result = handler.handleRuntimeException(exception);

        assertEquals(500, result.getCode());
        assertEquals("系统内部错误", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testHandleIllegalArgumentExceptionWithNullMessage() {
        IllegalArgumentException exception = new IllegalArgumentException((String) null);

        ResultVO<Void> result = handler.handleIllegalArgumentException(exception);

        assertEquals(400, result.getCode());
        assertEquals("参数错误: null", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testHandleComplexExceptionChain() {
        RuntimeException cause = new RuntimeException("根本原因");
        BusinessException businessException = new BusinessException(2001, "业务异常", cause);

        ResultVO<Void> result = handler.handleBusinessException(businessException);

        assertEquals(2001, result.getCode());
        assertEquals("业务异常", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testHandleExceptionWithUnicodeMessage() {
        BusinessException exception = new BusinessException("业务异常：用户权限不足 🚫");

        ResultVO<Void> result = handler.handleBusinessException(exception);

        assertEquals(500, result.getCode());
        assertEquals("业务异常：用户权限不足 🚫", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testHandleBatchErrorsWithUnicodeErrorMessages() {
        Map<String, String> errors = new HashMap<>();
        errors.put("username", "用户名不能为空");
        errors.put("password", "密码强度不够 🔒");
        errors.put("email", "邮箱格式不正确 ✉️");

        ResultVO<Map<String, Object>> result = handler.handleBatchErrors(errors);

        assertEquals(400, result.getCode());
        assertEquals("批量参数校验失败", result.getMessage());
        assertNotNull(result.getData());

        Map<String, Object> errorData = result.getData();
        @SuppressWarnings("unchecked")
        Map<String, String> returnedErrors = (Map<String, String>) errorData.get("errors");

        assertEquals("用户名不能为空", returnedErrors.get("username"));
        assertEquals("密码强度不够 🔒", returnedErrors.get("password"));
        assertEquals("邮箱格式不正确 ✉️", returnedErrors.get("email"));
        assertEquals(3, errorData.get("errorCount"));
    }

    @Test
    void testHandleExceptionWithVeryLongMessage() {
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            longMessage.append("很长的异常消息内容，包含详细信息");
        }

        BusinessException exception = new BusinessException(longMessage.toString());

        ResultVO<Void> result = handler.handleBusinessException(exception);

        assertEquals(500, result.getCode());
        assertEquals(longMessage.toString(), result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testHandlerMethodReturnTypes() {
        // 验证所有处理方法的返回类型都是正确的
        BaseException baseException = new BaseException(1001, "测试") {};
        BusinessException businessException = new BusinessException("测试");
        ParameterException parameterException = new ParameterException("测试");
        SystemException systemException = new SystemException("测试");

        ResultVO<Void> result1 = handler.handleBaseException(baseException);
        ResultVO<Void> result2 = handler.handleBusinessException(businessException);
        ResultVO<Void> result3 = handler.handleParameterException(parameterException);
        ResultVO<Void> result4 = handler.handleSystemException(systemException);

        assertTrue(result1 != null);
        assertTrue(result2 != null);
        assertTrue(result3 != null);
        assertTrue(result4 != null);
    }
}