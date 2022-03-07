# xWash-backend
xWash应用的后端

## 部署
1. 修改`jdbc.properties`与`docker-compose.yml`中MySQL的密码
2. `mvn package`，docker volumes会自动挂载target目录到tomcat中
3. `docker-compose up -d`

# 技术栈

`Java 1.8`

`Spring SpringMVC Mybatis`

`Redis`

# 项目流程

## 整体流程

![main.png (1369×781) (raw.githubusercontent.com)](https://raw.githubusercontent.com/wulnm/img/master/main.png)

## 请求API流程

![APIChecker.png (1369×781) (raw.githubusercontent.com)](https://raw.githubusercontent.com/wulnm/img/master/APIChecker.png)

# 支持的洗衣机牌子
1. U净网页端与APP端
2. Washpayer
3. Zhuam
4. Mplink
5. Sodalife(苏打校园，因为学校已经没有了，无法测试因此废弃)

# 注意事项
### 密码设置
在`src/main/resources/mapper/jdbc.properties`中需要添加MySQL的密码，需要和`docker-compose.yml`中一致。

### 错误日志
`/var/log/xwash`
