# MojangYggdrasil(Color_yr edit)
## 不知道为什么fork的丢了，这里说明
## https://github.com/Karlatemp/YggdrasilMojangProxy
## 这是原作者的作品
统一和正版混合登录  
只支持在BC上部署
```
功能列表：
1. 皮肤统一显示
2. 保持UUID一致
3. 可以让玩家先统一登录还是再正版登录，例如你的模式设置为1，则玩家先需要正版登录才能使用统一登录
```
---
## 测试环境
```
java version "1.8.0_121"
Java(TM) SE Runtime Environment (build 1.8.0_121-b13)
Java HotSpot(TM) 64-Bit Server VM (build 25.121-b13, mixed mode)
```
---
## 部署
1. 我们只需要为jvm启动参数加上一点点东西
```
-javaagent:{下载的jar路径}
# 这是示例
java -javaagent:MojangYggdrasil.jar -jar spigot-1.14.4.jar
```
2. 修改配置文件
---
## 配置文件  
```
{
  "Port" : 25566, //端口
  "Address": "https://auth2.nide8.com:233/xxxxxxxxx",  //通行证地址
  "Priority": 0  //模式
  0：为不限制第一次登录
  1：只能从统一登录
  2：只能从正版登录
}
```
---
## 正版用户显示伪正版皮肤
首先,你需要获取[修改器](https://github.com/Karlatemp/YggdrasilMojangProxy/blob/master/out/artifacts/SkinViewer_jar/SkinViewer.jar)
,并放在一个你认为友好的地方(比如 `F:\Java\ `) 并记住这个jar的完整路径

(如果你把下载的jar放到了 `F:\Java`, 并且 jar 的名字是 `SkinViewer.jar`, 那么这个jar的完整路径就是 `F:\Java\SkinViewer.jar`)

然后打开你的启动器配置页面并找到JVM选项

![启动器配置](https://github.com/Karlatemp/YggdrasilMojangProxy/blob/master/imgs/0.png?raw=true)

![启动器配置2](https://github.com/Karlatemp/YggdrasilMojangProxy/blob/master/imgs/1.png?raw=true)

在JVM选项的末尾加上这段参数(不要忘记空格)
```
[空格] -javaagent:{你下载的jar的完整路径}
#这是示例
 -javaagent:F:\Java\SkinViewer.jar
```
如果你的完整路径是有空格的(比如`F:\Java Tools\SkinViewer.jar`)
那么请使用下面的这段参数
```
[空格] "-javaagent:{路径}"
#示例
"-javaagent:F:\Java Tools\SkinViewer.jar"
```

最好不要尝试使用相对位置,因为我无法确定文件应该放在哪,可能每个启动器都不一样?
如果相对位置是可以用的请找Karlatemp修改