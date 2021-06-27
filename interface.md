# 后端API



## API列表

### Person

#### 用户登陆

##### URL

/api/person/login

##### 请求方法

post

##### 请求参数

```json
{
    name: string,
    password: string
}
```

##### 返回参数

string类型，"success"或者"failure"。前端接收到success应该进入网站，接收到failure应该回到登录页面，并且提示登陆失败。
