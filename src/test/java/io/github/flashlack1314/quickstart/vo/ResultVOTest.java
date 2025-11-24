package io.github.flashlack1314.quickstart.vo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * ResultVO 测试类
 *
 * @author flash
 */
class ResultVOTest {

    private String testString;
    private List<String> testList;
    private Map<String, Object> testMap;

    @BeforeEach
    void setUp() {
        testString = "测试数据";
        testList = Arrays.asList("item1", "item2", "item3");
        testMap = new HashMap<>();
        testMap.put("key1", "value1");
        testMap.put("key2", 123);
    }

    @Test
    void testSuccessWithoutData() {
        ResultVO<Void> result = ResultVO.success();

        assertNotNull(result);
        assertEquals(200, result.getCode());
        assertEquals("success", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testSuccessWithStringData() {
        ResultVO<String> result = ResultVO.success(testString);

        assertNotNull(result);
        assertEquals(200, result.getCode());
        assertEquals("success", result.getMessage());
        assertEquals(testString, result.getData());
    }

    @Test
    void testSuccessWithListData() {
        ResultVO<List<String>> result = ResultVO.success(testList);

        assertNotNull(result);
        assertEquals(200, result.getCode());
        assertEquals("success", result.getMessage());
        assertEquals(testList, result.getData());
        assertEquals(3, result.getData().size());
    }

    @Test
    void testSuccessWithMapData() {
        ResultVO<Map<String, Object>> result = ResultVO.success(testMap);

        assertNotNull(result);
        assertEquals(200, result.getCode());
        assertEquals("success", result.getMessage());
        assertEquals(testMap, result.getData());
        assertEquals("value1", result.getData().get("key1"));
        assertEquals(123, result.getData().get("key2"));
    }

    @Test
    void testSuccessWithNullData() {
        ResultVO<String> result = ResultVO.success(null);

        assertNotNull(result);
        assertEquals(200, result.getCode());
        assertEquals("success", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testErrorWithMessage() {
        String errorMessage = "操作失败";
        ResultVO<Void> result = ResultVO.error(errorMessage);

        assertNotNull(result);
        assertEquals(500, result.getCode());
        assertEquals(errorMessage, result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testErrorWithCustomCodeAndMessage() {
        Integer errorCode = 400;
        String errorMessage = "请求参数错误";
        ResultVO<Void> result = ResultVO.error(errorCode, errorMessage);

        assertNotNull(result);
        assertEquals(errorCode, result.getCode());
        assertEquals(errorMessage, result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testErrorWithNullMessage() {
        ResultVO<Void> result = ResultVO.error((String) null);

        assertNotNull(result);
        assertEquals(500, result.getCode());
        assertNull(result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testErrorWithZeroCode() {
        ResultVO<Void> result = ResultVO.error(0, "自定义错误");

        assertNotNull(result);
        assertEquals(0, result.getCode());
        assertEquals("自定义错误", result.getMessage());
    }

    @Test
    void testErrorWithNegativeCode() {
        ResultVO<Void> result = ResultVO.error(-1, "系统错误");

        assertNotNull(result);
        assertEquals(-1, result.getCode());
        assertEquals("系统错误", result.getMessage());
    }

    @Test
    void testConstructorWithAllParameters() {
        Integer code = 201;
        String message = "创建成功";
        String data = "用户创建成功";

        ResultVO<String> result = new ResultVO<>(code, message, data);

        assertNotNull(result);
        assertEquals(code, result.getCode());
        assertEquals(message, result.getMessage());
        assertEquals(data, result.getData());
    }

    @Test
    void testConstructorWithNullParameters() {
        ResultVO<String> result = new ResultVO<>(null, null, null);

        assertNotNull(result);
        assertNull(result.getCode());
        assertNull(result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testChainSetters() {
        Integer code = 200;
        String message = "操作成功";
        String data = "处理完成";

        ResultVO<String> result = new ResultVO<String>()
                .setCode(code)
                .setMessage(message)
                .setData(data);

        assertNotNull(result);
        assertEquals(code, result.getCode());
        assertEquals(message, result.getMessage());
        assertEquals(data, result.getData());
    }

    @Test
    void testChainSettersWithVoid() {
        Integer code = 200;
        String message = "操作成功";

        ResultVO<Void> result = new ResultVO<Void>()
                .setCode(code)
                .setMessage(message);

        assertNotNull(result);
        assertEquals(code, result.getCode());
        assertEquals(message, result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testToString() {
        ResultVO<String> result = ResultVO.success(testString);

        String resultString = result.toString();

        assertNotNull(resultString);
        assertTrue(resultString.contains("ResultVO"));
        assertTrue(resultString.contains("code=200"));
        assertTrue(resultString.contains("message=success"));
    }

    @Test
    void testEqualsAndHashCode() {
        ResultVO<String> result1 = ResultVO.success("test");
        ResultVO<String> result2 = ResultVO.success("test");
        ResultVO<String> result3 = ResultVO.success("different");

        // Same data should be equal
        assertEquals(result1, result2);
        assertEquals(result1.hashCode(), result2.hashCode());

        // Different data should not be equal
        assertNotEquals(result1, result3);

        // Null comparison
        assertNotEquals(result1, null);
        assertNotEquals(result1, "some string");
    }

    @Test
    void testWithComplexObject() {
        class TestObject {
            private String name;
            private Integer age;

            public TestObject(String name, Integer age) {
                this.name = name;
                this.age = age;
            }

            public String getName() { return name; }
            public Integer getAge() { return age; }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                TestObject that = (TestObject) o;
                return java.util.Objects.equals(name, that.name) &&
                       java.util.Objects.equals(age, that.age);
            }

            @Override
            public int hashCode() {
                return java.util.Objects.hash(name, age);
            }
        }

        TestObject testObj = new TestObject("张三", 25);
        ResultVO<TestObject> result = ResultVO.success(testObj);

        assertNotNull(result);
        assertEquals(200, result.getCode());
        assertEquals(testObj, result.getData());
        assertEquals("张三", result.getData().getName());
        assertEquals(25, result.getData().getAge());
    }

    @Test
    void testEmptyData() {
        ResultVO<String> result = ResultVO.success("");

        assertNotNull(result);
        assertEquals(200, result.getCode());
        assertEquals("success", result.getMessage());
        assertEquals("", result.getData());
        assertTrue(result.getData().isEmpty());
    }

    @Test
    void testLargeData() {
        StringBuilder largeString = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            largeString.append("数据").append(i).append("abc"); // 增加更多字符确保长度足够
        }

        ResultVO<String> result = ResultVO.success(largeString.toString());

        assertNotNull(result);
        assertEquals(200, result.getCode());
        assertEquals(largeString.toString(), result.getData());
        assertTrue(result.getData().length() > 5000); // 现在应该超过5000字符
    }

    @Test
    void testSpecialCharactersInMessage() {
        String specialMessage = "测试特殊字符：!@#$%^&*()_+-={}[]|\\:;\"'<>?,./";
        ResultVO<Void> result = ResultVO.error(specialMessage);

        assertNotNull(result);
        assertEquals(500, result.getCode());
        assertEquals(specialMessage, result.getMessage());
    }

    @Test
    void testUnicodeCharactersInData() {
        String unicodeData = "中文字符测试 🚀 Emoji表情符号 αβγδεζηθ";
        ResultVO<String> result = ResultVO.success(unicodeData);

        assertNotNull(result);
        assertEquals(200, result.getCode());
        assertEquals(unicodeData, result.getData());
        assertTrue(result.getData().contains("中文"));
        assertTrue(result.getData().contains("🚀"));
    }

    @Test
    void testEmptyConstructor() {
        ResultVO<String> result = new ResultVO<>();

        assertNotNull(result);
        assertNull(result.getCode());
        assertNull(result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testAllArgsConstructor() {
        Integer code = 404;
        String message = "未找到资源";
        String data = null;

        ResultVO<String> result = new ResultVO<>(code, message, data);

        assertNotNull(result);
        assertEquals(code, result.getCode());
        assertEquals(message, result.getMessage());
        assertEquals(data, result.getData());
    }

    @Test
    void testBooleanData() {
        ResultVO<Boolean> trueResult = ResultVO.success(true);
        ResultVO<Boolean> falseResult = ResultVO.success(false);

        assertNotNull(trueResult);
        assertTrue(trueResult.getData());

        assertNotNull(falseResult);
        assertFalse(falseResult.getData());
    }

    @Test
    void testNumericData() {
        Integer intValue = 42;
        Long longValue = 123456789L;
        Double doubleValue = 3.14159;

        ResultVO<Integer> intResult = ResultVO.success(intValue);
        assertEquals(intValue, intResult.getData());

        ResultVO<Long> longResult = ResultVO.success(longValue);
        assertEquals(longValue, longResult.getData());

        ResultVO<Double> doubleResult = ResultVO.success(doubleValue);
        assertEquals(doubleValue, doubleResult.getData());
    }
}