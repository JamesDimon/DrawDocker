# DrawDocker操作手册

## Kubeapps应用组件
![](https://github.com/JamesDimon/DrawDocker/blob/main/img/1.png)

每个图形代表helm仓库中的一个应用，点击后会在画布中生成一个对应的图形，可以进行后续操作。

![](https://github.com/JamesDimon/DrawDocker/blob/main/img/2.png)

![](https://github.com/JamesDimon/DrawDocker/blob/main/img/3.png)

生成的图形，具有应用名称、安装状态、chart名称属性，如果完成安装操作会显示release名称和外部链接属性。

## 应用安装/删除

![](https://github.com/JamesDimon/DrawDocker/blob/main/img/4.png)

![](https://github.com/JamesDimon/DrawDocker/blob/main/img/5.png)

选中一个应用图形，在安装参数栏提供安装按钮，点击将在k8s集群内安装此chart的一个实例，相当于`helm install`命令。安装成功后返回release名称以及外部地址，并提供删除按钮。

![](https://github.com/JamesDimon/DrawDocker/blob/main/img/6.png)

![](https://github.com/JamesDimon/DrawDocker/blob/main/img/7.png)

选中多个应用图形，在安装参数栏提供安装按钮和删除按钮，点击将对选中图形中可执行对应操作的应用在k8s集群内安装或删除。如果选中带有箭头的图形进行安装，将以箭头尾部的应用较先、箭头头部应用较后的顺序依次安装应用。对无效图形报错。

![](https://github.com/JamesDimon/DrawDocker/blob/main/img/8.png)

![](https://github.com/JamesDimon/DrawDocker/blob/main/img/9.png)

选中一个或多个已经安装的应用图形，在安装参数栏提供删除按钮，点击将在k8s集群内删除安装的实例，相当于`helm delete --purge`命令。

## 自定义应用组件

![](https://github.com/JamesDimon/DrawDocker/blob/main/img/10.png)

![](https://github.com/JamesDimon/DrawDocker/blob/main/img/11.png)

![](https://github.com/JamesDimon/DrawDocker/blob/main/img/12.png)

点击自定义应用，输入应用chart名称和chart地址，可以生成自定义组件。自定义组件和自带组件一样，可以提供安装和删除等功能。

## 发布和保存

![](https://github.com/JamesDimon/DrawDocker/blob/main/img/13.png)

![](https://github.com/JamesDimon/DrawDocker/blob/main/img/14.png)

点击菜单-文件-发布-链接，可将画布中的设计图形发布为链接，发布后的图形保留原有图形的所有属性。菜单-文件-保存/导出为可将画布中图形保存为文件，具有类似效果。

## 功能演示

![](https://github.com/JamesDimon/DrawDocker/blob/main/img/1.gif)
