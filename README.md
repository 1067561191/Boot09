# Boot09
 JavaHelpYourSign
## 简介
这是一个简(la)易(ji)的  
帮助你实现“梦中签到”的**工具**。
## 环境
JDK11+
## 让我们开始吧!
### 1.打包
`cd`到项目根目录  
命令行：`mvn package`即可打包到同目录下target目录下  
一般打包好的名称为*Boot09-{版本号}.jar*  
### 1.直接下载可执行jar包
如果不想打包可以使用本项目的[GitHub-releases](https://github.com/1067561191/Boot09/releases)选最近的版本直接执行但往往不是最新的
### 2.执行
命令行：`cd ./target/`  
命令行：`java -jar Boot09-{版本号}.jar`   
浏览器[打开这里](http://localhost:8080/)  
输入相应的字段值后提交  
即可开始做(p)其(l)他(a)事(y)  
## 兼容Android
目前没有能力做app  
没有电脑的可以使用[termux-app](https://github.com/termux/termux-app)  
教程：  
下载安装termux-app后打开发现界面是黑色背景的命令行  
首次打开会加载一小会儿  
一次输入下方命令：  
1. 更新包仓库  
`pkg update && pkg upgrade -y`  
2. 安装"root-repo"仓库  
`pkg install root-repo`  
3. 安装jdk17并验证版本  
`pkg install openjdk-17 -y && java -version`  
   3.1 查看一下界面最下方是否有如下内容  
   > openjdk version "17-internal" 2021-09-14  
   > OpenJDK Runtime Environment (build 17-internal+0-adhoc..src)  
   > OpenJDK 64-Bit Server VM (build 17-internal+0-adhoc..src, mixed mode)  
4. 下载本项目releases包并查看  
`curl -LJO https://github.com/1067561191/Boot09/releases/download/v0.0.3/Boot09-0.0.3.jar && ls `  
   4.1 查看界面下方是否有如下内容  
   > Boot09-0.0.3.jar
5. 执行并检查  
`java -jar ./Boot09-0.0.3.jar`  
   5.1 查看界面最下方提示为：  
   > Started Boot09Application in x.xxx seconds (JVM running for x.xxx)  
6. 将termux-app放至后台，打开浏览器访问：[http://127.0.0.1:8080/](http://127.0.0.1:8080/)  
7. 根据打开的页面的提示填写字段并提交  
   即可开始做(p)其(l)他(a)事(y)  