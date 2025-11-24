# Maven 本地依赖测试指南

## 方法一：安装到本地 Maven 仓库（推荐）

### 1. 安装依赖到本地仓库

在你的系统有 Maven 的情况下，在项目根目录执行：

```bash
mvn clean install
```

这将会：
- 编译项目代码
- 运行所有单元测试
- 打包生成 JAR 文件
- 安装到本地 Maven 仓库 (`~/.m2/repository/`)

### 2. 在其他项目中引用

在其他项目的 `pom.xml` 中添加：

```xml
<dependency>
    <groupId>io.github.flashlack1314</groupId>
    <artifactId>quickstart-boot-kit</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

### 3. 验证安装

安装完成后，可以验证：

```bash
# 检查本地仓库中的文件
ls ~/.m2/repository/io/github/flashlack1314/quickstart-boot-kit/1.0.0-SNAPSHOT/
```

应该能看到类似以下文件：
- `quickstart-boot-kit-1.0.0-SNAPSHOT.jar`
- `quickstart-boot-kit-1.0.0-SNAPSHOT.pom`

## 方法二：直接引用 JAR 文件

如果你不想安装到本地仓库，也可以直接引用生成的 JAR 文件：

### 1. 生成 JAR 文件

```bash
mvn clean package
```

### 2. 复制 JAR 文件

将生成的 JAR 文件复制到你的项目中：
```bash
# JAR 文件位置
target/quickstart-boot-kit-1.0.0-SNAPSHOT.jar
```

### 3. 在其他项目中引用

```xml
<dependency>
    <groupId>io.github.flashlack1314</groupId>
    <artifactId>quickstart-boot-kit</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/lib/quickstart-boot-kit-1.0.0-SNAPSHOT.jar</systemPath>
</dependency>
```

## 常用 Maven 命令

```bash
# 编译项目
mvn clean compile

# 运行测试
mvn test

# 打包生成 JAR
mvn clean package

# 安装到本地仓库
mvn clean install

# 跳过测试快速安装
mvn clean install -DskipTests

# 清理项目
mvn clean
```

## 项目依赖说明

这个项目只依赖了以下核心库：

- **Lombok 1.18.30** (provided scope)：用于减少样板代码
- **JUnit 5** (test scope)：用于单元测试

没有其他第三方依赖，确保项目轻量级且易于集成。