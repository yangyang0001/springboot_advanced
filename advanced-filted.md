# advanced-filted — 过滤注解 Agent 使用说明

## 工程结构

```
advanced-filted/                                   ← 父工程（packaging=pom，版本管理）
│
├── filte-api/                                     ← 对外暴露的注解 API（接入方只知道这个）
│   ├── pom.xml                                    ← 零外部依赖
│   └── src/main/java/com/deepblue/
│       ├── annotation/
│       │   ├── FilteAdvanced.java                 ← 组合注解（对外唯一入口）
│       │   ├── FilteRule.java                     ← 规则元注解
│       │   └── common/                            ← 各独立过滤注解
│       │       ├── FilteWhite / FilteBlack / FilteWelth
│       │       ├── FilteStop  / FiltePause / FilteCheck
│       │       ├── FilteSupp  / FilteProvi / FilteDupli
│       │       ├── FilteResul / RateLimite
│       ├── enums/
│       │   ├── ChannelEnum.java                   ← SMS / EMAIL / APP_PUSH ...
│       │   ├── SiteIdEnum.java                    ← BP / AP / GP ...
│       │   └── LimitEnum.java
│       └── constants/
│           └── Constants.java
│
└── filte-agent/                                   ← 切面实现（对接入方完全不可见）
    ├── pom.xml                                    ← 依赖 filte-api + spring-boot-starter-aop
    └── src/main/java/com/deepblue/
        ├── aspect/
        │   ├── FilteAdvancedAspect.java            ← 组合切面（Order=5）
        │   └── common/                             ← 各独立切面（Order 10~80）
        ├── agent/
        │   └── FilteAgentMain.java                 ← -javaagent 入口（premain）
        ├── config/
        │   └── FilteAdvancedAutoConfiguration.java ← Spring Boot 自动装配
        └── src/main/resources/
            └── META-INF/spring/
                └── org.springframework.boot.autoconfigure.AutoConfiguration.imports
```

---

## 工作原理

```
JVM 启动
  └─ -javaagent:filte-agent-1.0.0.jar
       └─ FilteAgentMain.premain()
            └─ appendToSystemClassLoaderSearch(agentJar)   ← 把 agent JAR 加入系统类路径
                  └─ Spring Boot 启动
                       └─ 自动发现 AutoConfiguration.imports
                            └─ 注册 FilteAdvancedAspect Bean
                                 └─ 拦截 @FilteAdvanced 方法，读取 JVM 参数
```

`filte-agent` 对接入方完全透明：接入方 IDE 里只能看到 `filte-api` 的注解，切面实现细节全部封装在 agent 内部。

---

## 接入方使用步骤

### 第一步：发布到 Maven 仓库

在本工程根目录执行（首次或版本升级时）：

```bash
mvn clean install -DskipTests
```

> 如有私有 Maven 仓库，改用 `mvn clean deploy`。

### 第二步：接入方 pom.xml 引入注解依赖

```xml
<dependency>
    <groupId>com.deepblue</groupId>
    <artifactId>filte-api</artifactId>
    <version>1.0.0</version>
</dependency>
```

> 使用默认 `compile` scope，注解类会打入接入方的 fat JAR，Spring 运行时能正常解析 `@FilteAdvanced`。  
> **不需要** 引入 `filte-agent`，切面 JAR 通过 `-javaagent` 加载，与 Maven 依赖无关。

### 第三步：在业务方法上加注解

```java
@FilteAdvanced(
    channel = ChannelEnum.SMS,
    siteId  = SiteIdEnum.BP
)
public void sendMessage(List<String> items, String isp) {
    // 业务逻辑
}
```

### 第四步：部署时加载 Agent

将 `filte-agent/target/filte-agent-1.0.0.jar` 放到服务器，启动命令加上 `-javaagent`：

```bash
java -javaagent:filte-agent-1.0.0.jar \
     -jar your-spring-boot-app.jar
```

---

## 在 IDEA 中本地开发调试（另一个 Spring Boot 工程）

本节说明如何在 IntelliJ IDEA 的另一个 Spring Boot 工程里接入 agent，在本地开发阶段就能触发切面拦截。

### 前提

1. 已在 `springboot_advanced` 根目录执行过 `mvn clean install -DskipTests`，本地 Maven 仓库（`D:\Repository`）已有：
   - `com.deepblue:filte-api:1.0.0`
   - `com.deepblue:filte-agent:1.0.0`（agent JAR，含 MANIFEST.MF）

2. 接入方工程 `pom.xml` 已引入 `filte-api` 依赖：

   ```xml
   <dependency>
       <groupId>com.deepblue</groupId>
       <artifactId>filte-api</artifactId>
       <version>1.0.0</version>
   </dependency>
   ```

### 第一步：找到 agent JAR 的本地路径

agent JAR 打包后会同时存在两处，任选其一：

| 来源 | 路径 |
|---|---|
| 构建产物 | `D:\IdeaProjects\springboot_advanced\filte-agent\target\filte-agent-1.0.0.jar` |
| 本地 Maven 仓库 | `D:\Repository\com\deepblue\filte-agent\1.0.0\filte-agent-1.0.0.jar` |

> 推荐使用本地 Maven 仓库路径，路径稳定，不受 `mvn clean` 影响。

### 第二步：在 IDEA 运行配置中添加 JVM 参数

1. 打开接入方工程，菜单栏点击 **Run → Edit Configurations...**
2. 在左侧选中启动类对应的 **Spring Boot** 运行配置
3. 找到 **VM options** 输入框（若不可见，点击右上角 **Modify options → Add VM options** 展开）
4. 填入以下内容（路径替换为实际路径，Windows 用反斜杠）：

```
-javaagent:D:\Repository\com\deepblue\filte-agent\1.0.0\filte-agent-1.0.0.jar
```

若需要同时覆盖过滤开关，追加 `-D` 参数，多个参数空格分隔：

```
-javaagent:D:\Repository\com\deepblue\filte-agent\1.0.0\filte-agent-1.0.0.jar
-Dfilte.black.enabled=false
-Dfilte.white.enabled=false
```

5. 点击 **OK** 保存，正常启动即可。

### 第三步：验证 Agent 已加载

启动日志中出现以下两行说明 agent 加载成功、切面已注册：

```
[FilteAgent] loaded: D:\IdeaProjects\springboot_advanced\filte-agent\target\filte-agent-1.0.0.jar
...
o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port ...
```

调用带 `@FilteAdvanced` 注解的方法后，日志中会出现：

```
FilteAdvancedAspect invoke start, uuid: a3f9..., args: [...]
FilteAdvancedAspect invoke success channel: SMS, whiteFlag: true, blackFlag: true, ...
FilteAdvancedAspect handler.filteHandle end uuid: a3f9..., cost time: 12ms
```

### 注意

- **每次重新打包**（修改了 `filte-agent` 代码后执行 `mvn clean install`），IDEA 无需修改运行配置，下次启动自动用新 JAR。
- Windows 路径中的反斜杠在 VM options 中无需转义，直接填写即可。
- 如果 IDEA 版本较旧，**VM options** 可能显示为 **JVM arguments**，位置相同。

---

## JVM 参数覆盖过滤规则

### 参数格式

```
-Dfilte.<规则名>.enabled=<true|false>
```

- **优先级高于注解的 `openFlag` 默认值**：JVM 参数一旦设置，注解上写的 `openFlag` 即被忽略
- **未设置时**：沿用注解的 `openFlag` 默认值（均为 `true`）
- **作用范围**：对整个应用所有使用 `@FilteAdvanced` 注解的方法全局生效
- **热更新**：JVM 参数在进程启动时读取，修改后需要重启应用

### 参数列表

| JVM 参数 | 规则名 | 作用 | 注解默认值 |
|---|---|---|---|
| `-Dfilte.white.enabled=false` | white | 关闭白名单过滤，白名单内用户不再豁免 | true |
| `-Dfilte.black.enabled=false` | black | 关闭黑名单过滤，黑名单内用户不再拦截 | true |
| `-Dfilte.welth.enabled=false` | welth | 关闭财富等级过滤 | true |
| `-Dfilte.stop.enabled=false`  | stop  | 关闭停止触达过滤，已标记停止的用户仍会被发送 | true |
| `-Dfilte.pause.enabled=false` | pause | 关闭暂停触达过滤，已标记暂停的用户仍会被发送 | true |
| `-Dfilte.check.enabled=false` | check | 关闭合规检查过滤 | true |
| `-Dfilte.supp.enabled=false`  | supp  | 关闭免打扰过滤，免打扰时段内用户仍会被发送 | true |
| `-Dfilte.provi.enabled=false` | provi | 关闭运营商过滤，不再按运营商拦截 | true |
| `-Dfilte.dupli.enabled=false` | dupli | 关闭去重过滤，相同内容允许重复发送 | true |

### 示例：只开启白名单 + 去重，其余全部关闭

```bash
java -javaagent:filte-agent-1.0.0.jar \
     -Dfilte.black.enabled=false  \
     -Dfilte.welth.enabled=false  \
     -Dfilte.stop.enabled=false   \
     -Dfilte.pause.enabled=false  \
     -Dfilte.check.enabled=false  \
     -Dfilte.supp.enabled=false   \
     -Dfilte.provi.enabled=false  \
     -jar your-spring-boot-app.jar
```

### 示例：全部关闭（透传模式，切面只记录日志不做任何拦截）

```bash
java -javaagent:filte-agent-1.0.0.jar \
     -Dfilte.white.enabled=false \
     -Dfilte.black.enabled=false \
     -Dfilte.welth.enabled=false \
     -Dfilte.stop.enabled=false  \
     -Dfilte.pause.enabled=false \
     -Dfilte.check.enabled=false \
     -Dfilte.supp.enabled=false  \
     -Dfilte.provi.enabled=false \
     -Dfilte.dupli.enabled=false \
     -jar your-spring-boot-app.jar
```

---

## 注解参数说明

```java
@FilteAdvanced(
    channel    = ChannelEnum.SMS,          // 渠道类型，默认 SMS
    siteId     = SiteIdEnum.BP,            // 产品线，默认 BP
    paramNames = {"items", "isp"},         // 过滤参数名，默认 {"items", "isp"}

    // 各规则可单独覆盖 openFlag（也可通过 JVM 参数全局覆盖，JVM 参数优先）
    white = @FilteRule(openFlag = true),
    black = @FilteRule(openFlag = true),
    dupli = @FilteRule(openFlag = false)   // 仅本方法关闭去重
)
```

---

## 打包命令

```bash
# 根目录执行，同时构建 filte-api 和 filte-agent
mvn clean package -DskipTests

# 产物
#   filte-api/target/filte-api-1.0.0.jar       ← 发布到 Maven 仓库，接入方 compile 依赖
#   filte-agent/target/filte-agent-1.0.0.jar   ← 部署到服务器，-javaagent 加载
```

### 验证 Agent JAR

**Windows（PowerShell）：**

```powershell
jar tf filte-agent\target\filte-agent-1.0.0.jar | Select-String -Pattern "MANIFEST|imports|FilteAgent|FilteAdvancedAuto"
```

**macOS / Linux（Terminal）：**

```bash
jar tf filte-agent/target/filte-agent-1.0.0.jar | grep -E "MANIFEST|imports|FilteAgent|FilteAdvancedAuto"
```

期望输出（两个平台结果相同）：
```
META-INF/MANIFEST.MF
com/deepblue/agent/FilteAgentMain.class
com/deepblue/config/FilteAdvancedAutoConfiguration.class
META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
```

---

## 注意事项

| # | 类型 | 说明 |
|---|---|---|
| 1 | **依赖 scope** | `filte-api` 必须使用默认 `compile` scope，不能用 `provided`。Spring Boot fat JAR 不打包 `provided` 依赖，运行时找不到注解类会抛 `ClassNotFoundException` |
| 2 | **Bean 注册** | 所有切面（`CommonPointcut`、`FilteAdvancedAspect` 及 11 个独立切面）均由 `FilteAdvancedAutoConfiguration` 统一注册，接入方无需额外配置，也不要自行 `@ComponentScan` 扫描 `com.deepblue` 包，避免重复注册 |
| 3 | **切点作用域** | `@Around` 切点锁定全限定名 `com.deepblue.annotation.FilteAdvanced`，不会误拦截其他包下的同名注解 |

---

## 切面执行顺序

| 切面 | Order | 说明 |
|---|---|---|
| `FilteAdvancedAspect` | 5  | 组合注解，覆盖所有规则 |
| `FilteWhiteAspect`    | 10 | 白名单 |
| `FilteBlackAspect`    | 20 | 黑名单 |
| `FilteWelthAspect`    | 25 | 财富 |
| `FilteStopAspect`     | 30 | 停止 |
| `FiltePauseAspect`    | 40 | 暂停 |
| `FilteCheckAspect`    | 45 | 检查 |
| `FilteSuppAspect`     | 46 | 免打扰 |
| `FilteProviAspect`    | 50 | 运营商 |
| `FilteDupliAspect`    | 60 | 去重 |
| `RateLimiteAspect`    | 70 | 限流 |
| `FilteResulAspect`    | 80 | 结果处理 |
