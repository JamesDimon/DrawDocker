# DrawDocker

![](https://img.shields.io/badge/bitnami应用商店-DrawDocker-blue.svg)

基于开源图形化技术和容器平台的IT架构“神笔马良”。

## 英文名：DrawDocker  中文名：神笔马良

## 关于
DrawDocker可以通过图形界面，在k8s集群环境内进行helm应用的安装和删除并输出设计视图。

## 版本
v1.0

## 安装
DrawDocker提供下面两种安装方式：
### 普通安装

 **运行环境**
 > - java 14.0.2
 > - maven 3.3
 > - SpringBoot 1.5.2RELEASE

 **k8s环境**
  > - kubernetes v1.17
  > - helm v2.14

* 下载项目代码
```
git clone https://github.com/JamesDimon/DrawDocker 
```
* 修改配置文件\src\main\resources\application.yml，添加k8s主机地址、登录认证token以及安装应用的命名空间等信息；
* 修改文件\src\main\webapp\js\kubeappsPalette.json，添加应用图标、应用名称、chart名称、chart地址等信息以确定左侧组件栏默认显示的应用组件；
* 进入项目目录，执行`mvn package`命令进行打包;
* 进入\target目录，执行`java -jar DrawDocker.war`运行项目。或直接用IDEA运行项目。

### docker版
从DockerHub下拉镜像
```
docker pull drawdocker/drawdocker:latest
```
或者直接使用[Dockerfile](https://github.com/JamesDimon/DrawDocker/blob/main/Dockerfile)构筑镜像
```
docker build -t drawdocker/drawdocker .
```
新建容器运行镜像
```
docker run -P -d drawdocker/drawdocker:latest
docker exec -it {CONTAINER_ID} /bin/sh
cd /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/
sed -i 's/{ORIGINAL_CONTENT}/{NEW_CONTENT}/g' application.yml
cd /usr/local/tomcat/webapps/ROOT/js/
sed -i 's/{ORIGINAL_CONTENT}/{NEW_CONTENT}/g' kubeappsPalette.json
exit
```

## 功能
在已安装helm的k8s集群环境内，通过DrawDocker图形界面，选择代表某个chart的图形，可以进行对此chart安装一个release的操作。安装成功后，返回一些关于release的信息，并可以进行后续的删除release操作。具体的：
* kubeapps应用组件

  展示给定的组件，每个组件代表不同的chart，包含helm仓库地址，chart名和chart版本等信息。点击组件后会在画布中生成一个对应的图形，可以进行后续操作。生成的图形具有应用名称、安装状态、chart名称属性，如果完成安装操作会显示release名称和外部链接属性。
* 安装

  选择某个图形，点击安装应用按钮，在k8s集群内安装此图形代表的chart的一个release，相当于`helm install`命令。若成功，返回并展示release名以及外部地址。
* 按顺序安装应用

  选择用箭头标记顺序的图形，点击安装按钮，按顺序安装相应的chart，箭头尾部的图形相较于箭头指向的图形更优先安装。如果选中无效箭头或图形，则无视。如果形成无效有向图，则提示错误。
* 删除

  对于已安装的图形，点击删除应用按钮，在集群内删除已安装的release，相当于`helm delete --purge`命令。
* 自定义应用组件

  点击自定义应用，输入应用chart名称和chart地址，可以生成自定义组件。自定义组件和自带组件一样，可以提供安装和删除等功能。
* 发布和保存

  点击菜单-文件-发布-链接，可将画布中的设计图形发布为链接，发布后的图形保留原有图形的所有属性。菜单-文件-保存/导出为可将画布中图形保存为文件，具有类似效果。

## 操作手册

[操作手册](https://github.com/JamesDimon/DrawDocker/blob/main/OperationManual.md)

## 相关
关于[kubernetes](https://github.com/kubernetes/kubernetes/)，关于[helm](https://github.com/helm/helm/)，关于[drawio](https://github.com/jgraph/drawio/)。

## 交流与反馈
微信交流群：DrawDocker开源维护群

![](https://github.com/JamesDimon/DrawDocker/blob/main/img/weixin.jpg)

[@DimonJms](https://github.com/JamesDimon) 邮箱：zhaimeng@dcits.com
## 更新日志

## 贡献
### 维护者
[@DimonJms](https://github.com/JamesDimon)
### 贡献代码
我们非常欢迎您为DrawDocker贡献代码或者提供使用建议，期待您的加入！提一个[Issue](https://github.com/JamesDimon/DrawDocker/issues/new)或者提交一个[Pull Request](https://github.com/JamesDimon/DrawDocker/pulls)。

可能改进的地方：
* 现在仅支持普通的`helm install`功能，如果chart安装过程中需要增加其他的flag，则会失败。可能对安装过程进行一些优化。
* 如果chart安装后创建负载均衡类型的服务，则会返回应用的第一个外部地址。如果创建其他类型的服务，或不暴露外部端口，则暂时会显示错误。如果暴露多个端口，则仅显示第一个。如果有特殊的服务命名，则会显示错误。
* 将会提供具有依赖关系的chart依次安装的功能。
* 可能提供查询可用的chart功能，相当于`helm search`。
* 可能提供对现有的release进行查询，删除，修改等功能。
* 可能提供一些k8s的功能。

## 使用许可
[Apache License v2.0 PublicTechR&D DCIE DigitalChina](https://github.com/JamesDimon/DrawDocker/blob/main/LICENSE)
