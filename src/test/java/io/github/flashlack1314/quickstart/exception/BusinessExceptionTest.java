package io.github.flashlack1314.quickstart.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * BusinessException 测试类
 *
 * @author flash
 */
class BusinessExceptionTest {

    @Test
    void testConstructorWithCodeAndMessage() {
        Integer code = 501;
        String message = "业务异常测试";

        BusinessException exception = new BusinessException(code, message);

        assertEquals(code, exception.getCode());
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testConstructorWithCodeMessageAndCause() {
        Integer code = 502;
        String message = "业务异常测试";
        Throwable cause = new RuntimeException("根本原因");

        BusinessException exception = new BusinessException(code, message, cause);

        assertEquals(code, exception.getCode());
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testConstructorWithMessageOnly() {
        String message = "默认错误码的业务异常";

        BusinessException exception = new BusinessException(message);

        assertEquals(500, exception.getCode());
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testConstructorWithMessageAndCause() {
        String message = "带原因的默认错误码业务异常";
        Throwable cause = new IllegalArgumentException("业务规则违反");

        BusinessException exception = new BusinessException(message, cause);

        assertEquals(500, exception.getCode());
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testDefaultErrorCode() {
        String message = "测试默认错误码";

        BusinessException exception1 = new BusinessException(message);
        BusinessException exception2 = new BusinessException(message, new RuntimeException());

        assertEquals(500, exception1.getCode());
        assertEquals(500, exception2.getCode());
    }

    @Test
    void testCustomErrorCode() {
        BusinessException exception1 = new BusinessException(1001, "自定义错误码1");
        BusinessException exception2 = new BusinessException(2002, "自定义错误码2");

        assertEquals(1001, exception1.getCode());
        assertEquals(2002, exception2.getCode());
    }

    @Test
    void testBusinessScenarioExamples() {
        // 用户不存在
        BusinessException userNotFound = new BusinessException(1001, "用户不存在");

        // 账户余额不足
        BusinessException insufficientBalance = new BusinessException(1002, "账户余额不足");

        // 订单已取消
        BusinessException orderCancelled = new BusinessException(1003, "订单已取消");

        assertEquals(1001, userNotFound.getCode());
        assertEquals("用户不存在", userNotFound.getMessage());

        assertEquals(1002, insufficientBalance.getCode());
        assertEquals("账户余额不足", insufficientBalance.getMessage());

        assertEquals(1003, orderCancelled.getCode());
        assertEquals("订单已取消", orderCancelled.getMessage());
    }

    @Test
    void testExceptionWithNullValues() {
        BusinessException exception1 = new BusinessException((Integer) null, null);
        BusinessException exception2 = new BusinessException((String) null);

        assertNull(exception1.getCode());
        assertNull(exception1.getMessage());

        assertNull(exception2.getMessage());
        assertEquals(500, exception2.getCode());
    }

    @Test
    void testExceptionInheritance() {
        BusinessException exception = new BusinessException("业务异常");

        assertTrue(exception instanceof BaseException);
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testExceptionWithComplexCause() {
        String validationError = "参数验证失败";
        String businessError = "业务处理失败";

        BusinessException validationException = new BusinessException(400, validationError);
        BusinessException businessException = new BusinessException(500, businessError, validationException);

        assertEquals(businessError, businessException.getMessage());
        assertEquals(validationException, businessException.getCause());
        assertEquals(validationError, businessException.getCause().getMessage());
    }

    @Test
    void testExceptionWithEmptyMessage() {
        BusinessException exception = new BusinessException("");

        assertEquals(500, exception.getCode());
        assertEquals("", exception.getMessage());
        assertTrue(exception.getMessage().isEmpty());
    }

    @Test
    void testExceptionWithUnicodeMessage() {
        String message = "业务异常：用户权限不足 🚫";

        BusinessException exception = new BusinessException(message);

        assertEquals(message, exception.getMessage());
        assertTrue(exception.getMessage().contains("权限不足"));
        assertTrue(exception.getMessage().contains("🚫"));
    }

    @Test
    void testExceptionWithLargeErrorCode() {
        BusinessException exception = new BusinessException(9999, "超大错误码业务异常");

        assertEquals(9999, exception.getCode());
        assertEquals("超大错误码业务异常", exception.getMessage());
    }

    @Test
    void testExceptionWithZeroErrorCode() {
        BusinessException exception = new BusinessException(0, "零错误码业务异常");

        assertEquals(0, exception.getCode());
        assertEquals("零错误码业务异常", exception.getMessage());
    }

    @Test
    void testExceptionWithNegativeErrorCode() {
        BusinessException exception = new BusinessException(-100, "负数错误码业务异常");

        assertEquals(-100, exception.getCode());
        assertEquals("负数错误码业务异常", exception.getMessage());
    }

    @Test
    void testExceptionToString() {
        BusinessException exception = new BusinessException(1001, "业务逻辑错误");

        String exceptionString = exception.toString();

        assertNotNull(exceptionString);
        assertTrue(exceptionString.contains("BusinessException"));
        assertTrue(exceptionString.contains("业务逻辑错误"));
    }

    @Test
    void testRealBusinessScenarios() {
        // 库存不足
        BusinessException stockOut = new BusinessException(2001, "商品库存不足");

        // 优惠券已过期
        BusinessException couponExpired = new BusinessException(2002, "优惠券已过期");

        // 超出购买限制
        BusinessException purchaseLimit = new BusinessException(2003, "超出购买限制");

        // 验证各种业务场景
        assertEquals(2001, stockOut.getCode());
        assertEquals(2002, couponExpired.getCode());
        assertEquals(2003, purchaseLimit.getCode());

        assertEquals("商品库存不足", stockOut.getMessage());
        assertEquals("优惠券已过期", couponExpired.getMessage());
        assertEquals("超出购买限制", purchaseLimit.getMessage());
    }
}