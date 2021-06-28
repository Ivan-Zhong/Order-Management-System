# 后端API

[toc]

## 返回值类型

统一为ReturnValue类型，Java类：

```java
public class ReturnValue {
    private String message;
    private Object data;
	// getters and setters
}
```

前端从返回值中取message和data即可。



## Person

### 用户登陆

#### URL

/api/person/login

#### 请求方法

post

#### 请求参数

```json
{
    name: string,
    password: string
}
```

#### 返回参数

 message为"success"或者"failure"。前端接收到success应该进入网站，接收到failure应该回到登录页面，并且提示登陆失败。



### 注册新用户

#### URL

/api/person/add

#### 请求方法

post

#### 请求参数

```json
{
    name: string,
    tel: string,
    email: string,
    password: string,
    identity: string
}
```

#### 返回参数

message为"success"或者"failure"。前端接收到success应该提示添加成功，接收到failure应该提示添加失败，失败情况目前只可能是用户名重复了，应给出相应提示。



### 查看所有用户

#### URL

/api/person/read/all

#### 请求方法

get

#### 请求参数

无

#### 返回参数

后端会判断当前用户是否有权限查看所有用户。

message为"success"或者"failure"。前端接收到success表示有权限查看，否则为无权限。

success对应的data是所有用户，failure对应null。



### 获得自己的信息

存在的目的：比如人员更新自己的信息时，前端应该先向后端请求这个人员的信息，填写在表格中，这样用户就可以只更新自己想更新的部分，比较友好。

#### URL

/api/person/read/own

#### 请求方法

get

#### 请求参数

无

#### 返回参数

message为"success"或者"failure"。

success对应的data是当前用户的信息，failure对应null。



### 获得别人的信息

存在的目的：管理员更新别人的信息时，前端应该先向后端请求这个人员的信息，填写在表格中，这样管理员就可以只更新自己想更新的部分，比较友好。

#### URL

/api/person/read/others/{id}

#### 请求方法

get

#### 请求参数

无

#### 返回参数

message为"success"或者"failure"。

success对应的data是查询用户的信息，failure对应null。



### 删除用户

#### URL

/api/person/delete/{id}

#### 请求方法

get

#### 请求参数

无，路径中带有要删除的人员id，应该是从上面访问all时获得的。

#### 返回参数

message为"success"或者"failure"。前端接收到success表示删除成功，否则为失败（非管理员试图删除用户的时候会失败，但前端不应该让这种情况发生；任何人员试图删除自己的时候会失败，这种提示一下）。



### 修改自己的信息

#### URL

/api/person/update/own

#### 请求方法

post


#### 请求参数

```json
{
    name: string,
    tel: string,
    email: string,
    password: string
}
```

（即，自己不可以更新自己的identity！）

#### 返回参数

message为"success"或者"failure"。前端接收到success表示更新成功，否则为失败，失败可能原因是name重复了。



### 修改别的用户的信息

#### URL

/api/person/update/others/{id}

#### 请求方法

post

#### 请求参数

```json
{
    name: string,
    tel: string,
    email: string,
    password: string，
    identity: string
}
```

#### 返回参数

message为"success"或者"failure"。前端接收到success表示更新成功，否则为失败，失败可能原因是name重复了。



### 用户登出

#### URL

/api/person/logout

#### 请求方法

get

#### 请求参数

无

#### 返回参数

message为"success"或者"failure"。前端接收到success表示登出成功，否则为失败。







## Client

### 创建新客户

#### URL

/api/client/add

#### 请求方法

post

#### 请求参数

```json
{
	name: string, // 比如公司名，应当不重复
    contactname: string, // 联系人姓名
    tel: string,
    address: string
}
```

#### 返回参数

message为"success"或者"failure"。前端接收到success表示创建成功，否则为失败（原因目前只可能是name重复）。



### 查看所有客户

#### URL

/api/client/read/all

#### 请求方法

get

#### 请求参数

无

#### 返回参数

message为"success"或者"failure"。前端接收到success表示有权限查看并且成功，否则失败。

success对应的data是所有客户，failure对应null。



### 查看某个客户

#### URL

/api/client/read/one/{id}

#### 请求方法

get

#### 请求参数

无

#### 返回参数

message为"success"或者"failure"。前端接收到success表示有权限查看并且成功，否则失败。

success对应的data是该客户的信息，failure对应null。



### 修改客户信息

#### URL

/api/client/update/{id}

#### 请求方法

post

#### 请求参数

```json
{
	name: string, // 比如公司名，应当不重复
    contactname: string, // 联系人姓名
    tel: string,
    address: string
}
```

#### 返回参数

message为"success"或者"failure"。前端接收到success表示更新成功，否则为失败（原因目前只可能是name重复）。级联更新订单表的客户名。



### 删除客户

#### URL

/api/client/delete/{id}

#### 请求方法

get

#### 请求参数

无

#### 返回参数

message为"success"或者"failure"。前端接收到success表示删除成功，否则失败。删除用户会级联删除他的所有订单。





## Order

### 创建新订单

#### URL

/api/order/add

#### 请求方法

post

#### 请求参数

```json
{
	clientname: string,
    description: string,
    length: int,
    width: int,
    height: int,
    number: int,
    material: string,
    price: int,
    image: MultipartFile,
    status: string
}
```

#### 返回参数

message为"success"或者"failure"。前端接收到success表示创建成功，否则为失败。



### 查看所有订单

#### URL

/api/order/read/all

#### 请求方法

get

#### 请求参数

无

#### 返回参数

message为"success"或者"failure"。前端接收到success表示有权限查看并且成功，否则失败。

success对应的data是所有客户，failure对应null。

imagePos不用显示，clientId不用显示。为每个订单显示下载按钮，链接：/api/order/download/{id}。



### 修改订单信息



### 删除订单







\1. 用户的创建，修改，删除，查看（finished）
\2. 客户的创建，修改，删除，查看
\3. 订单的创建，修改，删除，查看
