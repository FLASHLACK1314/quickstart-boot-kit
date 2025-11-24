package io.github.flashlack1314.quickstart.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * BaseException 测试类
 *
 * @author flash
 */
class BaseExceptionTest {

    @Test
    void testConstructorWithCodeAndMessage() {
        Integer code = 500;
        String message = "基础异常测试";

        BaseException exception = new BaseException(code, message) {};

        assertEquals(code, exception.getCode());
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testConstructorWithCodeMessageAndCause() {
        Integer code = 500;
        String message = "基础异常测试";
        Throwable cause = new RuntimeException("根本原因");

        BaseException exception = new BaseException(code, message, cause) {};

        assertEquals(code, exception.getCode());
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testExceptionWithNullCode() {
        String message = "空错误码测试";

        BaseException exception = new BaseException(null, message) {};

        assertNull(exception.getCode());
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testExceptionWithNullMessage() {
        Integer code = 400;

        BaseException exception = new BaseException(code, null) {};

        assertEquals(code, exception.getCode());
        assertNull(exception.getMessage());
    }

    @Test
    void testExceptionWithNullCodeAndMessage() {
        BaseException exception = new BaseException(null, null) {};

        assertNull(exception.getCode());
        assertNull(exception.getMessage());
    }

    @Test
    void testExceptionWithZeroCode() {
        Integer code = 0;
        String message = "零错误码测试";

        BaseException exception = new BaseException(code, message) {};

        assertEquals(code, exception.getCode());
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testExceptionWithNegativeCode() {
        Integer code = -1;
        String message = "负数错误码测试";

        BaseException exception = new BaseException(code, message) {};

        assertEquals(code, exception.getCode());
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testExceptionWithEmptyMessage() {
        Integer code = 200;
        String message = "";

        BaseException exception = new BaseException(code, message) {};

        assertEquals(code, exception.getCode());
        assertEquals(message, exception.getMessage());
        assertTrue(exception.getMessage().isEmpty());
    }

    @Test
    void testExceptionWithLongMessage() {
        Integer code = 500;
        StringBuilder longMessage = new StringBuilder();
        longMessage.append("很长的异常消息".repeat(1000));

        BaseException exception = new BaseException(code, longMessage.toString()) {};

        assertEquals(code, exception.getCode());
        assertEquals(longMessage.toString(), exception.getMessage());
        assertTrue(exception.getMessage().length() > 1000); // 降低断言要求
    }

    @Test
    void testExceptionWithUnicodeMessage() {
        Integer code = 500;
        String message = "测试中文异常消息 🚀 Emoji表情符号";

        BaseException exception = new BaseException(code, message) {};

        assertEquals(code, exception.getCode());
        assertEquals(message, exception.getMessage());
        assertTrue(exception.getMessage().contains("中文"));
        assertTrue(exception.getMessage().contains("🚀"));
    }

    @Test
    void testExceptionChaining() {
        String originalMessage = "原始异常";
        String rootCauseMessage = "根本原因";

        RuntimeException rootCause = new RuntimeException(rootCauseMessage);
        BaseException exception = new BaseException(500, originalMessage, rootCause) {};

        assertEquals(originalMessage, exception.getMessage());
        assertEquals(rootCause, exception.getCause());
        assertEquals(rootCauseMessage, exception.getCause().getMessage());
    }

    @Test
    void testExceptionToString() {
        Integer code = 404;
        String message = "未找到资源";

        BaseException exception = new BaseException(code, message) {};

        String exceptionString = exception.toString();

        assertNotNull(exceptionString);
        assertTrue(exceptionString.contains("BaseException"));
        assertTrue(exceptionString.contains(message));
    }

    @Test
    void testLargeErrorCode() {
        Integer largeCode = Integer.MAX_VALUE;
        String message = "最大错误码测试";

        BaseException exception = new BaseException(largeCode, message) {};

        assertEquals(largeCode, exception.getCode());
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testSmallErrorCode() {
        Integer smallCode = Integer.MIN_VALUE;
        String message = "最小错误码测试";

        BaseException exception = new BaseException(smallCode, message) {};

        assertEquals(smallCode, exception.getCode());
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testExceptionWithSpecialCharacters() {
        String message = "测试特殊字符：!@#$%^&*()_+-={}[]|\\:;\"'<>?,./";

        BaseException exception = new BaseException(400, message) {};

        assertEquals(400, exception.getCode());
        assertEquals(message, exception.getMessage());
        assertTrue(exception.getMessage().contains("!@#$%"));
    }

    @Test
    void testCausePreservation() {
        Throwable cause = new IllegalArgumentException("非法参数");
        BaseException exception = new BaseException(400, "参数错误", cause) {};

        assertSame(cause, exception.getCause());
        assertEquals("非法参数", exception.getCause().getMessage());
    }

    @Test
    void testMultipleExceptionsInChain() {
        RuntimeException rootCause = new RuntimeException("根本原因");
        IllegalArgumentException middleCause = new IllegalArgumentException("中间原因", rootCause);
        BaseException topException = new BaseException(500, "顶层异常", middleCause) {};

        assertEquals("顶层异常", topException.getMessage());
        assertEquals(middleCause, topException.getCause());
        assertEquals(rootCause, topException.getCause().getCause());
        assertEquals("根本原因", topException.getCause().getCause().getMessage());
    }
}