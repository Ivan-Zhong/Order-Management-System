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