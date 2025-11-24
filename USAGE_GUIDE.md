# Quickstart Boot Kit 使用指南

## 概述

`quickstart-boot-kit` 是一个轻量级的Spring Boot脚手架库，提供通用的工具类和统一响应格式。本指南将帮助你在引入依赖后快速开始使用。

## 快速开始

### 1. 添加依赖

在你的Spring Boot项目的 `pom.xml` 中添加：

```xml
<dependency>
    <groupId>io.github.flashlack1314</groupId>
    <artifactId>quickstart-boot-kit</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

## 核心组件使用

### 1. ResultVO - 统一响应格式

ResultVO 提供统一的API响应格式，包含状态码、消息和数据。

#### 基本用法

```java
import io.github.flashlack1314.quickstart.vo.ResultVO;

@RestController
@RequestMapping("/api")
public class DemoController {

    // 成功响应（无数据）
    @GetMapping("/success")
    public ResultVO<Void> success() {
        return ResultVO.success();
    }

    // 成功响应（带数据）
    @GetMapping("/data")
    public ResultVO<String> getData() {
        String data = "Hello, Quickstart Boot Kit!";
        return ResultVO.success(data);
    }

    // 错误响应
    @GetMapping("/error")
    public ResultVO<Void> error() {
        return ResultVO.error("操作失败");
    }

    // 自定义错误码
    @GetMapping("/custom-error")
    public ResultVO<Void> customError() {
        return ResultVO.error(400, "请求参数错误");
    }
}
```

#### 响应格式示例

```json
// 成功响应
{
    "code": 200,
    "message": "success",
    "data": "Hello, Quickstart Boot Kit!"
}

// 错误响应
{
    "code": 500,
    "message": "操作失败",
    "data": null
}
```

### 2. PageVO - 分页响应格式

PageVO 提供统一的分页数据格式，包含分页信息和导航属性。

#### Controller中使用

```java
import io.github.flashlack1314.quickstart.vo.ResultVO;
import io.github.flashlack1314.quickstart.vo.PageVO;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 基本分页查询
    @GetMapping
    public ResultVO<PageVO<UserVO>> getUsers(
            @RequestParam(defaultValue = "1") Long current,
            @RequestParam(defaultValue = "10") Long size) {

        List<UserVO> users = userService.getUsers(current, size);
        Long total = userService.countUsers();

        return PageVO.success(users, total, current, size);
    }

    // 空结果处理
    @GetMapping("/empty")
    public ResultVO<PageVO<UserVO>> getEmptyPage() {
        return PageVO.emptyResult();
    }
}
```

#### Service层实现示例

```java
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserVO> getUsers(Long current, Long size) {
        // 计算偏移量
        Long offset = (current - 1) * size;

        // 查询数据
        List<User> users = userMapper.selectUsersByPage(offset, size);

        // 转换为VO
        return users.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public Long countUsers() {
        return userMapper.countUsers();
    }

    private UserVO convertToVO(User user) {
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user, vo);
        return vo;
    }
}
```

#### 分页响应格式

```json
{
    "code": 200,
    "message": "success",
    "data": {
        "records": [
            {"id": 1, "name": "张三", "email": "zhangsan@example.com"},
            {"id": 2, "name": "李四", "email": "lisi@example.com"}
        ],
        "total": 100,
        "current": 1,
        "size": 10,
        "pages": 10
    }
}
```

## 高级用法

### 1. 分页导航判断

```java
@GetMapping("/navigation")
public ResultVO<String> checkNavigation(@RequestParam Long current, @RequestParam Long size) {
    List<UserVO> users = userService.getUsers(current, size);
    Long total = userService.countUsers();

    PageVO<UserVO> page = PageVO.of(users, total, current, size);

    StringBuilder info = new StringBuilder();
    info.append("当前页: ").append(page.getCurrent()).append("\n");
    info.append("总页数: ").append(page.getPages()).append("\n");
    info.append("是否有上一页: ").append(page.hasPrevious()).append("\n");
    info.append("是否有下一页: ").append(page.hasNext()).append("\n");
    info.append("是否为首页: ").append(page.isFirstPage()).append("\n");
    info.append("是否为尾页: ").append(page.isLastPage()).append("\n");
    info.append("当前页记录数: ").append(page.getCurrentPageSize());

    return ResultVO.success(info.toString());
}
```

### 2. 链式调用

```java
public PageVO<UserVO> createCustomPage() {
    List<UserVO> users = userService.getUsers(1L, 10L);

    return new PageVO<UserVO>()
            .setRecords(users)
            .setTotal(100L)
            .setCurrent(1L)
            .setSize(10L)
            .setPages(10L);
}
```

### 3. 自定义总页数

```java
public ResultVO<PageVO<UserVO>> getUsersWithCustomPages() {
    List<UserVO> users = userService.getUsers(1L, 10L);
    Long total = userService.countUsers();

    // 手动指定总页数（在某些特殊场景下使用）
    PageVO<UserVO> page = PageVO.of(users, total, 1L, 10L, 15L);

    return ResultVO.success(page);
}
```

### 4. 空值安全处理

```java
@GetMapping("/safe-handling")
public ResultVO<String> demonstrateNullSafety() {
    // 处理可能为null的参数
    List<String> data = getSomeData(); // 可能返回null
    Long total = getTotalCount();     // 可能返回null

    // PageVO会自动处理null值
    PageVO<String> page = PageVO.of(data, total, 1L, 10L);

    return ResultVO.success("安全处理完成，总页数: " + page.getPages());
}

private List<String> getSomeData() {
    return null; // 演示null处理
}

private Long getTotalCount() {
    return null; // 演示null处理
}
```

## 最佳实践

### 1. 统一响应格式

```java
// ✅ 推荐：所有接口都返回ResultVO格式
@GetMapping("/users/{id}")
public ResultVO<UserVO> getUser(@PathVariable Long id) {
    UserVO user = userService.getById(id);
    return ResultVO.success(user);
}

// ❌ 不推荐：直接返回对象
@GetMapping("/users/{id}")
public UserVO getUser(@PathVariable Long id) {
    return userService.getById(id);
}
```

### 2. 异常处理

```java
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResultVO<Void> handleBusinessException(BusinessException e) {
        return ResultVO.error(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ResultVO<Void> handleValidationException(ValidationException e) {
        return ResultVO.error(400, "参数验证失败: " + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResultVO<Void> handleException(Exception e) {
        log.error("系统异常", e);
        return ResultVO.error("系统内部错误");
    }
}
```

### 3. 分页参数校验

```java
@GetMapping("/users")
public ResultVO<PageVO<UserVO>> getUsers(
        @RequestParam(defaultValue = "1") @Min(1) Long current,
        @RequestParam(defaultValue = "10") @Range(min = 1, max = 100) Long size) {

    List<UserVO> users = userService.getUsers(current, size);
    Long total = userService.countUsers();

    return PageVO.success(users, total, current, size);
}
```

## 前端集成示例

### JavaScript 解析分页数据

```javascript
// 处理分页响应
async function loadUsers(current = 1, size = 10) {
    try {
        const response = await fetch(`/api/users?current=${current}&size=${size}`);
        const result = await response.json();

        if (result.code === 200) {
            const pageData = result.data;

            // 渲染用户数据
            renderUsers(pageData.records);

            // 渲染分页控件
            renderPagination({
                current: pageData.current,
                pageSize: pageData.size,
                total: pageData.total,
                totalPages: pageData.pages,
                hasNext: pageData.hasNext,
                hasPrevious: pageData.hasPrevious
            });
        } else {
            alert(result.message);
        }
    } catch (error) {
        console.error('请求失败:', error);
        alert('网络错误');
    }
}

// 渲染分页控件
function renderPagination(pagination) {
    console.log('分页信息:', pagination);
    // 实现你的分页组件逻辑
}
```

## 注意事项

1. **类型安全**：确保泛型类型正确，避免类型转换错误
2. **空值处理**：PageVO已内置null安全，但传入数据时仍需注意
3. **分页计算**：总页数会自动计算，通常不需要手动指定
4. **数据一致性**：确保总记录数与实际查询的数据一致

## 常见问题

### Q: 如何处理大数据量分页？
A: 建议使用游标分页或优化SQL查询，避免深分页性能问题。

### Q: 可以在非Spring Boot项目中使用吗？
A: 可以，这是一个纯Java工具库，不依赖Spring Boot框架。

### Q: 如何自定义响应状态码？
A: 使用 `ResultVO.error(code, message)` 方法可以指定自定义状态码。

## 版本信息

- 当前版本：1.0.0-SNAPSHOT
- Java要求：21+
- 依赖：Lombok 1.18.30（compile时需要）

## 更多示例

更多完整的使用示例请参考项目中的测试代码：
- `ResultVO` 测试：`src/test/java/io/github/flashlack1314/quickstart/vo/PageVOTest.java`
- `PageVO` 测试：同上文件