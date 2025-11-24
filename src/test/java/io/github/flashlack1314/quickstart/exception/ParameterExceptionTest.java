package io.github.flashlack1314.quickstart.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * ParameterException 测试类
 *
 * @author flash
 */
class ParameterExceptionTest {

    @Test
    void testConstructorWithCodeAndMessage() {
        Integer code = 401;
        String message = "参数异常测试";

        ParameterException exception = new ParameterException(code, message);

        assertEquals(code, exception.getCode());
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testConstructorWithCodeMessageAndCause() {
        Integer code = 402;
        String message = "参数异常测试";
        Throwable cause = new RuntimeException("根本原因");

        ParameterException exception = new ParameterException(code, message, cause);

        assertEquals(code, exception.getCode());
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testConstructorWithMessageOnly() {
        String message = "默认错误码的参数异常";

        ParameterException exception = new ParameterException(message);

        assertEquals(400, exception.getCode());
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testConstructorWithMessageAndCause() {
        String message = "带原因的默认错误码参数异常";
        Throwable cause = new IllegalArgumentException("参数格式错误");

        ParameterException exception = new ParameterException(message, cause);

        assertEquals(400, exception.getCode());
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testDefaultErrorCode() {
        String message = "测试默认错误码";

        ParameterException exception1 = new ParameterException(message);
        ParameterException exception2 = new ParameterException(message, new RuntimeException());

        assertEquals(400, exception1.getCode());
        assertEquals(400, exception2.getCode());
    }

    @Test
    void testCustomErrorCode() {
        ParameterException exception1 = new ParameterException(401, "参数错误1");
        ParameterException exception2 = new ParameterException(422, "参数错误2");

        assertEquals(401, exception1.getCode());
        assertEquals(422, exception2.getCode());
    }

    @Test
    void testParameterValidationScenarios() {
        // 必填参数为空
        ParameterException requiredEmpty = new ParameterException("用户名不能为空");

        // 参数格式错误
        ParameterException formatError = new ParameterException("邮箱格式不正确");

        // 参数范围错误
        ParameterException rangeError = new ParameterException("年龄必须在18-60岁之间");

        // 参数类型错误
        ParameterException typeError = new ParameterException("参数类型必须是数字");

        assertEquals("用户名不能为空", requiredEmpty.getMessage());
        assertEquals("邮箱格式不正确", formatError.getMessage());
        assertEquals("年龄必须在18-60岁之间", rangeError.getMessage());
        assertEquals("参数类型必须是数字", typeError.getMessage());

        assertEquals(400, requiredEmpty.getCode());
        assertEquals(400, formatError.getCode());
        assertEquals(400, rangeError.getCode());
        assertEquals(400, typeError.getCode());
    }

    @Test
    void testExceptionWithNullValues() {
        ParameterException exception1 = new ParameterException((Integer) null, null);
        ParameterException exception2 = new ParameterException((String) null);

        assertNull(exception1.getCode());
        assertNull(exception1.getMessage());

        assertNull(exception2.getMessage());
        assertEquals(400, exception2.getCode());
    }

    @Test
    void testExceptionInheritance() {
        ParameterException exception = new ParameterException("参数异常");

        assertTrue(exception instanceof BaseException);
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testExceptionWithComplexCause() {
        String formatError = "数字格式错误";
        String paramError = "参数解析失败";

        NumberFormatException numberException = new NumberFormatException(formatError);
        ParameterException paramException = new ParameterException(422, paramError, numberException);

        assertEquals(paramError, paramException.getMessage());
        assertEquals(numberException, paramException.getCause());
        assertEquals(formatError, paramException.getCause().getMessage());
    }

    @Test
    void testExceptionWithEmptyMessage() {
        ParameterException exception = new ParameterException("");

        assertEquals(400, exception.getCode());
        assertEquals("", exception.getMessage());
        assertTrue(exception.getMessage().isEmpty());
    }

    @Test
    void testExceptionWithUnicodeMessage() {
        String message = "参数错误：用户名包含特殊字符 ⚠️";

        ParameterException exception = new ParameterException(message);

        assertEquals(message, exception.getMessage());
        assertTrue(exception.getMessage().contains("特殊字符"));
        assertTrue(exception.getMessage().contains("⚠️"));
    }

    @Test
    void testExceptionWithLargeErrorCode() {
        ParameterException exception = new ParameterException(499, "最大参数错误码");

        assertEquals(499, exception.getCode());
        assertEquals("最大参数错误码", exception.getMessage());
    }

    @Test
    void testExceptionWithZeroErrorCode() {
        ParameterException exception = new ParameterException(0, "零错误码参数异常");

        assertEquals(0, exception.getCode());
        assertEquals("零错误码参数异常", exception.getMessage());
    }

    @Test
    void testExceptionWithNegativeErrorCode() {
        ParameterException exception = new ParameterException(-1, "负数错误码参数异常");

        assertEquals(-1, exception.getCode());
        assertEquals("负数错误码参数异常", exception.getMessage());
    }

    @Test
    void testExceptionToString() {
        ParameterException exception = new ParameterException(400, "参数验证失败");

        String exceptionString = exception.toString();

        assertNotNull(exceptionString);
        assertTrue(exceptionString.contains("ParameterException"));
        assertTrue(exceptionString.contains("参数验证失败"));
    }

    @Test
    void testRealParameterScenarios() {
        // 用户名验证
        ParameterException usernameError = new ParameterException(401, "用户名长度必须在3-20个字符之间");

        // 密码验证
        ParameterException passwordError = new ParameterException(402, "密码必须包含大小写字母和数字");

        // 手机号验证
        ParameterException phoneError = new ParameterException(403, "手机号格式不正确");

        // ID验证
        ParameterException idError = new ParameterException(404, "ID格式不正确");

        assertEquals(401, usernameError.getCode());
        assertEquals(402, passwordError.getCode());
        assertEquals(403, phoneError.getCode());
        assertEquals(404, idError.getCode());

        assertEquals("用户名长度必须在3-20个字符之间", usernameError.getMessage());
        assertEquals("密码必须包含大小写字母和数字", passwordError.getMessage());
        assertEquals("手机号格式不正确", phoneError.getMessage());
        assertEquals("ID格式不正确", idError.getMessage());
    }

    @Test
    void testExceptionChaining() {
        String originalMessage = "原始参数异常";
        String rootCauseMessage = "根本原因";

        IllegalArgumentException rootCause = new IllegalArgumentException(rootCauseMessage);
        ParameterException exception = new ParameterException(400, originalMessage, rootCause);

        assertEquals(originalMessage, exception.getMessage());
        assertEquals(rootCause, exception.getCause());
        assertEquals(rootCauseMessage, exception.getCause().getMessage());
    }

    @Test
    void testExceptionWithSpecialCharactersInParameterName() {
        String message = "参数'user.name'包含特殊字符，请检查参数格式";

        ParameterException exception = new ParameterException(message);

        assertEquals(400, exception.getCode());
        assertEquals(message, exception.getMessage());
        assertTrue(exception.getMessage().contains("user.name"));
    }

    @Test
    void testExceptionWithVeryLongParameterName() {
        StringBuilder longParamName = new StringBuilder("parameter");
        for (int i = 0; i < 100; i++) {
            longParamName.append("_").append(i);
        }
        String message = "参数名过长: " + longParamName.toString();

        ParameterException exception = new ParameterException(message);

        assertEquals(message, exception.getMessage());
        assertTrue(exception.getMessage().length() > 200);
    }
}