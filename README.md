# Order-Management-System

## 项目概述

该项目实现了一个广告牌公司订单管理系统的网站实现。该网站能实现人员客户信息的管理以及整个订单的业务流程。

### 业务流程如下：

1. 接单人员登录系统创建订单，若客户不存在则先创建客户。录入客户信息以及测量数据。
2. 测量人员前往现场进行测量，将测量数据录入系统。录入长度、宽度、高度以及数量等信息。
3. 设计人员根据测量数据，设计广告牌，将设计样板图上传至系统。录入材质、样板图片等信息。
4. 报价人员根据设计广告牌的大小、材质、数量等，给出报价并录入系统。录入报价等信息。
5. 客户人员通过系统查看设计信息和报价后，决定是否接受。
6. 工厂人员登录系统，查看设计信息后进行生产，生产完毕则在系统进行确认。
7. 安装人员线下从工厂获得产品后，前往客户地址进行安装，安装完毕则在系统进行确认。
8. 结案人员登录系统，确认该笔订单无误，则该订单结束；否则线下上报给公司管理员，公司管理员进行修改。


### 开发环境及框架

#### 前端

- 框架及库：Vue.js、element-UI、Vue router、axios、Vue-cli


#### 后端

- SpringBoot
- Spring Data JPA
- Java(TM) SE Runtime Environment (build 16+36-2231)
- Java HotSpot(TM) 64-Bit Server VM (build 16+36-2231, mixed mode, sharing)

#### 数据库

- MySQL

#### 其他

- nginx反向代理

### 项目模块

- 页面展示模块
- 人员管理模块
- 客户管理模块
- 订单管理模块



## 前端

前端分为界面展示模块，人员、客户管理模块以及订单管理模块四个部分

### 页面展示模块

使用vue组件的思想，将页面分成侧边栏以及展示操作主界面两个部分。

![image](https://user-images.githubusercontent.com/74498528/124267240-95224700-db6a-11eb-8eb8-9103e5ace4c3.png)

#### 侧边栏实现：

1. 定义vue组件结合Element-UI进行界面的设计
2. 点击不同的按钮会使主界面跳转到不同的操作展示界面
  - 使用vue-router定义不同组件对应的路径，然后使用router-link标签来实现页面的跳转。
3. 不同身份的用户登陆后侧边栏展示的路由跳转不同
  - 根据用户的身份信息以及对应的权限，使用v-for有选择的在界面上展示可跳转的路由。

#### 主界面实现:

- 使用Element-UI实现对界面的展示
- 定义了一系列的vue组件以及对应的路由，以便在进行不同操作时完成主界面路由的跳转。
- 主界面要执行人员添加删除修改、人员订单客户展示等操作。

### 人员管理模块

- 主要功能：为管理员提供用户管理的可视化操作，管理员可以添加，删除，修改，查看所有用户信息。另外，登录，查看修改自己的用户信息功能由于和人员相关，也被放入人员管理模块。
- 用户信息：姓名，电话，邮箱，密码，身份
- 用户身份：公司管理员，接单人员，测量人员，设计人员，报价人员，工厂人员，安装人员，结案人员 
- 用户权限：不同身份的用户有不同的权限，其中只有管理员能进入人员管理模块

1. 用户登录：
- 输入网址，若没有登陆或cookie，拦截进入到登录界面，输入用户密码后，前端向后端提交用户登录请求，通过后获得用户信息，进入到首页。

![image](https://user-images.githubusercontent.com/74498528/124267651-1ed21480-db6b-11eb-8c1e-544571efd09e.png)

2. 人员展示
- 在组件渲染之前，使用axios通过约定好的API向后端请求所有用户的信息，获得这些信息后使用Element-UI的table在前端进行展示。

![image](https://user-images.githubusercontent.com/74498528/124267754-432df100-db6b-11eb-954a-679e56136834.png)

3. 权限管理：
- 通过侧边栏进行管理，只有root用户可以访问此模块。

root用户：
![image](https://user-images.githubusercontent.com/74498528/124267823-56d95780-db6b-11eb-9db9-6d6361dd438d.png)

非root用户：
![image](https://user-images.githubusercontent.com/74498528/124267842-5c36a200-db6b-11eb-921e-1e5c8551111e.png)

4.   修改，删除，添加用户：

- 点击响应的按钮后，会调用响应的请求函数，该函数会使用axios库向服务器提交响应的请求。然后服务端和客户端完成相应操作。

![image](https://user-images.githubusercontent.com/74498528/124267890-6b1d5480-db6b-11eb-9d57-2715a1e46b7d.png)
![image](https://user-images.githubusercontent.com/74498528/124267894-6ce71800-db6b-11eb-957f-6a5ab0ab5a6c.png)

- 点击修改和添加用户，会通过路由展示另一个界面，在该界面可以填写用户信息，然后点击修改或添加按钮就可以完成提交。创建新用户时，会对用户的密码强度，电子邮箱风信息进行是否合法的检测，通过才会提交

![image](https://user-images.githubusercontent.com/74498528/124267921-74a6bc80-db6b-11eb-901f-694e1e5768cd.png)
![image](https://user-images.githubusercontent.com/74498528/124267928-76708000-db6b-11eb-8de2-5b31a7bfce18.png)

5. 查看修改个人信息：
- 点击侧边栏的个人信息按钮可以查看自己的用户信息
- 填写用户信息表格，点击修改按钮后，前端会对后端提交修改后的用户信息，然后跳转到首页。

![image](https://user-images.githubusercontent.com/74498528/124268011-8f793100-db6b-11eb-90f3-20d256ded7b6.png)

### 客户管理模块

- 客户和人员不同，客户代表的是该订单的合作公司，
- 客户信息：有客户名称、联系⼈姓名、联系⼈电话、客户地址四种。
- 主要功能：为管理的用户提供管理客户的可视化操作，操作包括客户的添加，删除以及修改；网站页面展示所有的客户信息；用户权限管理。

1. 客户的添加，删除，修改：

- 客户的添加修改与人员的添加修改一致，点击修改以及添加按钮，前端验证输入的信息是否合法后提交请求给后端，完成客户的修改添加。

![image](https://user-images.githubusercontent.com/74498528/124268123-b20b4a00-db6b-11eb-8920-f170ffd71c21.png)
![image](https://user-images.githubusercontent.com/74498528/124268133-b46da400-db6b-11eb-9722-d4707a582c89.png)

- 客户的删除与人员的删除一致，点击删除按钮后，确认删除后，向后端提出删除请求，之后再次请求所有客户的信息，然后刷新所有的客户信息即可。


2. 展示所有的客户信息

- 每次进入到客户管理的页面、页面组件渲染之前，向后端请求所有的客户信息，获得这些信息后使用Element-UI的table在前端进行展示。

![image](https://user-images.githubusercontent.com/74498528/124268223-d109dc00-db6b-11eb-8cc9-691e9d3f52b7.png)

3. 权限管理

- 开放用户：公司管理员，接单人员，测量人员，报价人员，结案人员。
- 这些用户可以通过侧边栏进入客户管理模块，其中公司管理员可以新建，查看，删除，修改用户；接单人员可以新建，查看，修改客户；其余人员只能查看客户。
- 通过用户的身份，使用v-if语句隐藏或显示不同的接口即可。

![image](https://user-images.githubusercontent.com/74498528/124268341-f991d600-db6b-11eb-9db0-c504e7832ff0.png)
![image](https://user-images.githubusercontent.com/74498528/124268356-fc8cc680-db6b-11eb-8550-0f93a7fa4d9e.png)

### 订单管理模块


**1. 模块功能：**
- 添加，删除订单。公司管理员，接单人员可以添加新的订单；公司管理员可以删除订单；其余人员不能添加删除订单。
- 修改，查看订单。公司管理员可以查看修改订单的所有信息。其他人员只能可以根据自己的身份以及订单的流程对订单的部分信息进行查看修改。
- 完成订单从接单到完成的所有业务流程。

**2. 订单信息：** 订单描述、长度、宽度、高度、数量、材质、报价、设计图、状态。

**3. 订单状态：**
![image](https://user-images.githubusercontent.com/74498528/124268568-42e22580-db6c-11eb-8624-4a5598a1192c.png)

**4. 订单的主界面**

![image](https://user-images.githubusercontent.com/74498528/124337657-54f8ae00-dbd6-11eb-80aa-62392901f757.png)

**5. 订单的添加、删除：**
- 与人员的添加，删除一致，只是填写的信息不同

- 界面如下
![image](https://user-images.githubusercontent.com/74498528/124337673-6772e780-dbd6-11eb-93ac-cec385e3cd5e.png)
![image](https://user-images.githubusercontent.com/74498528/124337676-693cab00-dbd6-11eb-987f-c912811fc6ba.png)

**6. 订单的查看，修改：**
- 实现思路：
  - 由于订单的处理是一整个流程，每一个流程中只有一个身份的用户修改该订单信息的一部分内容，因此将订单的处理流程与用户的身份对应。
  - 当某一身份的用户登录时，如果有订单处于的流程与该用户的身份一致，则该用户可以查看此订单中自己可修改的信息。
  - 第一次填写信息点击提交之后，若该订单会进入到下一个状态，如果下一个状态的用户没有提交该订单的信息，则该用户依旧可以查看并且修改自己可以修改的订单内容。流程图如右图：
![image](https://user-images.githubusercontent.com/74498528/124268806-9a809100-db6c-11eb-9c66-245ca7f9e551.png)

- 界面如下：
![image](https://user-images.githubusercontent.com/74498528/124337692-7f4a6b80-dbd6-11eb-9240-72a9b57037dc.png)
![image](https://user-images.githubusercontent.com/74498528/124337694-81acc580-dbd6-11eb-83dc-ad1bed8f0228.png)



- 每当用户进入该模块时，在组件渲染前向后端请求数据并发送当前人员的身份，后端会将可以查看修改状态的订单信息发送过来，然后在页面上展示

## 后端模块

- 后端模块使用Java语言，SpringBoot框架实现，为前端的人员管理模块，客户管理模块以及订单管理模块提供接口，接口的形式与URL相同
- 接口请求返回值规定：两个部分组成：message以及data，其中message的值有两个，分别为“success”与“failure”，代表请求成功或者失败。之后的data是一个json值，代表传回的数据。


### 人员管理模块：

#### 后端接口
![image](https://user-images.githubusercontent.com/74498528/124269044-f21efc80-db6c-11eb-8713-3ef67895c244.png)


### 客户管理模块

#### 后端接口

![image](https://user-images.githubusercontent.com/74498528/124269076-fcd99180-db6c-11eb-850c-3410724596dc.png)

### 订单管理模块

#### 后端接口

![image](https://user-images.githubusercontent.com/74498528/124269114-0a8f1700-db6d-11eb-9fc8-fe88a187743d.png)

### 项目结构

![image](https://user-images.githubusercontent.com/37100888/124292312-03293700-db88-11eb-801b-19987a2ce4c0.png)


## 数据库模块：

数据库模块用来储存用户信息，客户信息以及订单信息，并且可以用于存取

### 数据库的表：

1. 人员表：
- ![image](https://user-images.githubusercontent.com/74498528/124269174-22ff3180-db6d-11eb-8c90-f3c873a26e1c.png)

2. 客户表：
- ![image](https://user-images.githubusercontent.com/74498528/124269197-298da900-db6d-11eb-9591-c939be986ce4.png)

3. 订单表：
- ![image](https://user-images.githubusercontent.com/74498528/124269214-2f838a00-db6d-11eb-99ab-9a7999625c16.png)

## 心得体会：

- 这次项目，我们使用了前后端分离的开发方法。深刻感受到了接口文档的重要性以及开发效率的提升。
- 学习了vue.js，springboot开发框架，axios库以及Element-UI前端开发框架，学会了新的技术。
- 明白了vue组件开发的好处，这种通过路由切换组件达到页面跳转的开发方式为写网页提供给了新的思路。
- 知道了团队开发沟通的重要性，我们经过交流后选择放弃之前使用vue-element-admin模板开发的想法，转而自己写组件，提高了效率。





