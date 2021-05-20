# xWash-backend
xWash应用的后端

# 技术栈

`Java 1.8`

`Spring SpringMVC Mybatis`

`Redis`

# 运行流程

### 整体流程

![main.png (1369×781) (raw.githubusercontent.com)](https://raw.githubusercontent.com/wulnm/img/master/main.png)

### 请求API流程

![APIChecker.png (1369×781) (raw.githubusercontent.com)](https://raw.githubusercontent.com/wulnm/img/master/APIChecker.png)

# 文件结构

`com.xWash.admin `存放Controller

`com.xWash.entity` 存放Model

`com.xWash.service` 存放业务层代码

`com.xWash.service.Impl`
	├── Distributor.java   ---  分发器, 将宿舍楼不同类型洗衣机分发给不同Checker
	├── LocationDealer.java   ---  添加返回接口中的位置信息
	├── SodaChecker.java   ---  苏打校园Checker (现废弃)
	├── UCleanAPPChecker.java   ---  U净APP Checker
	└── UCleanChecker.java   ---  U净微信扫码 Checker

`com.xWash.tasks` 存放定时任务

