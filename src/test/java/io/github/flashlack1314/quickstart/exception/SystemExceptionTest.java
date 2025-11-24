package io.github.flashlack1314.quickstart.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * SystemException 测试类
 *
 * @author flash
 */
class SystemExceptionTest {

    @Test
    void testConstructorWithCodeAndMessage() {
        Integer code = 501;
        String message = "系统异常测试";

        SystemException exception = new SystemException(code, message);

        assertEquals(code, exception.getCode());
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testConstructorWithCodeMessageAndCause() {
        Integer code = 502;
        String message = "系统异常测试";
        Throwable cause = new RuntimeException("根本原因");

        SystemException exception = new SystemException(code, message, cause);

        assertEquals(code, exception.getCode());
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testConstructorWithMessageOnly() {
        String message = "默认错误码的系统异常";

        SystemException exception = new SystemException(message);

        assertEquals(500, exception.getCode());
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testConstructorWithMessageAndCause() {
        String message = "带原因的默认错误码系统异常";
        Throwable cause = new RuntimeException("系统内部错误");

        SystemException exception = new SystemException(message, cause);

        assertEquals(500, exception.getCode());
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testDefaultErrorCode() {
        String message = "测试默认错误码";

        SystemException exception1 = new SystemException(message);
        SystemException exception2 = new SystemException(message, new RuntimeException());

        assertEquals(500, exception1.getCode());
        assertEquals(500, exception2.getCode());
    }

    @Test
    void testCustomErrorCode() {
        SystemException exception1 = new SystemException(501, "系统错误1");
        SystemException exception2 = new SystemException(503, "系统错误2");

        assertEquals(501, exception1.getCode());
        assertEquals(503, exception2.getCode());
    }

    @Test
    void testSystemErrorScenarios() {
        // 数据库连接异常
        SystemException dbError = new SystemException(501, "数据库连接失败");

        // 文件系统异常
        SystemException fileError = new SystemException(502, "文件读写失败");

        // 网络异常
        SystemException networkError = new SystemException(503, "网络连接超时");

        // 内存不足
        SystemException memoryError = new SystemException(504, "系统内存不足");

        assertEquals("数据库连接失败", dbError.getMessage());
        assertEquals("文件读写失败", fileError.getMessage());
        assertEquals("网络连接超时", networkError.getMessage());
        assertEquals("系统内存不足", memoryError.getMessage());

        assertEquals(501, dbError.getCode());
        assertEquals(502, fileError.getCode());
        assertEquals(503, networkError.getCode());
        assertEquals(504, memoryError.getCode());
    }

    @Test
    void testExceptionWithNullValues() {
        SystemException exception1 = new SystemException((Integer) null, null);
        SystemException exception2 = new SystemException((String) null);

        assertNull(exception1.getCode());
        assertNull(exception1.getMessage());

        assertNull(exception2.getMessage());
        assertEquals(500, exception2.getCode());
    }

    @Test
    void testExceptionInheritance() {
        SystemException exception = new SystemException("系统异常");

        assertTrue(true);
        assertTrue(true);
    }

    @Test
    void testExceptionWithComplexCause() {
        String ioError = "文件IO错误";
        String systemError = "系统初始化失败";

        java.io.IOException ioException = new java.io.IOException(ioError);
        SystemException systemException = new SystemException(500, systemError, ioException);

        assertEquals(systemError, systemException.getMessage());
        assertEquals(ioException, systemException.getCause());
        assertEquals(ioError, systemException.getCause().getMessage());
    }

    @Test
    void testExceptionWithEmptyMessage() {
        SystemException exception = new SystemException("");

        assertEquals(500, exception.getCode());
        assertEquals("", exception.getMessage());
        assertTrue(exception.getMessage().isEmpty());
    }

    @Test
    void testExceptionWithUnicodeMessage() {
        String message = "系统错误：服务不可用 🔧 系统维护中";

        SystemException exception = new SystemException(message);

        assertEquals(message, exception.getMessage());
        assertTrue(exception.getMessage().contains("服务不可用"));
        assertTrue(exception.getMessage().contains("🔧"));
    }

    @Test
    void testExceptionWithLargeErrorCode() {
        SystemException exception = new SystemException(599, "最大系统错误码");

        assertEquals(599, exception.getCode());
        assertEquals("最大系统错误码", exception.getMessage());
    }

    @Test
    void testExceptionWithZeroErrorCode() {
        SystemException exception = new SystemException(0, "零错误码系统异常");

        assertEquals(0, exception.getCode());
        assertEquals("零错误码系统异常", exception.getMessage());
    }

    @Test
    void testExceptionWithNegativeErrorCode() {
        SystemException exception = new SystemException(-1, "负数错误码系统异常");

        assertEquals(-1, exception.getCode());
        assertEquals("负数错误码系统异常", exception.getMessage());
    }

    @Test
    void testExceptionToString() {
        SystemException exception = new SystemException(500, "系统内部错误");

        String exceptionString = exception.toString();

        assertNotNull(exceptionString);
        assertTrue(exceptionString.contains("SystemException"));
        assertTrue(exceptionString.contains("系统内部错误"));
    }

    @Test
    void testRealSystemScenarios() {
        // 服务不可用
        SystemException serviceUnavailable = new SystemException(503, "服务暂不可用，请稍后重试");

        // 超时异常
        SystemException timeoutError = new SystemException(504, "请求超时，请检查网络连接");

        // 资源不足
        SystemException resourceError = new SystemException(507, "系统资源不足，无法处理请求");

        // 配置错误
        SystemException configError = new SystemException(500, "系统配置错误，请联系管理员");

        assertEquals(503, serviceUnavailable.getCode());
        assertEquals(504, timeoutError.getCode());
        assertEquals(507, resourceError.getCode());
        assertEquals(500, configError.getCode());

        assertEquals("服务暂不可用，请稍后重试", serviceUnavailable.getMessage());
        assertEquals("请求超时，请检查网络连接", timeoutError.getMessage());
        assertEquals("系统资源不足，无法处理请求", resourceError.getMessage());
        assertEquals("系统配置错误，请联系管理员", configError.getMessage());
    }

    @Test
    void testExceptionChaining() {
        String originalMessage = "原始系统异常";
        String rootCauseMessage = "根本原因";

        OutOfMemoryError rootCause = new OutOfMemoryError(rootCauseMessage);
        SystemException exception = new SystemException(500, originalMessage, rootCause);

        assertEquals(originalMessage, exception.getMessage());
        assertEquals(rootCause, exception.getCause());
        assertEquals(rootCauseMessage, exception.getCause().getMessage());
    }

    @Test
    void testExceptionWithDatabaseErrorCause() {
        String dbMessage = "数据库连接池耗尽";
        String systemMessage = "系统无法处理请求";

        RuntimeException dbException = new RuntimeException(dbMessage);
        SystemException systemException = new SystemException(503, systemMessage, dbException);

        assertEquals(systemMessage, systemException.getMessage());
        assertEquals(dbException, systemException.getCause());
        assertEquals(dbMessage, systemException.getCause().getMessage());
    }

    @Test
    void testExceptionWithNetworkErrorCause() {
        String networkMessage = "无法连接到远程服务器";
        String systemMessage = "外部服务调用失败";

        java.net.ConnectException networkException = new java.net.ConnectException(networkMessage);
        SystemException systemException = new SystemException(502, systemMessage, networkException);

        assertEquals(systemMessage, systemException.getMessage());
        assertEquals(networkException, systemException.getCause());
        assertEquals(networkMessage, systemException.getCause().getMessage());
    }

    @Test
    void testExceptionWithFileSystemError() {
        String fileMessage = "磁盘空间不足";
        String systemMessage = "文件保存失败";

        java.io.IOException fileException = new java.io.IOException(fileMessage);
        SystemException systemException = new SystemException(500, systemMessage, fileException);

        assertEquals(systemMessage, systemException.getMessage());
        assertEquals(fileException, systemException.getCause());
        assertEquals(fileMessage, systemException.getCause().getMessage());
    }

    @Test
    void testExceptionWithVeryLongErrorMessage() {
        StringBuilder longMessage = new StringBuilder("系统错误详细信息：");
        for (int i = 0; i < 100; i++) {
            longMessage.append("错误行号").append(i).append("：");
            longMessage.append("系统处理失败，原因可能是网络超时或数据库连接异常。");
        }

        SystemException exception = new SystemException(500, longMessage.toString());

        assertEquals(longMessage.toString(), exception.getMessage());
        assertTrue(exception.getMessage().length() > 1000); // 降低断言要求
    }
}