# 订单管理系统 - 数据库部分

## 人员表

```sql
create table person(
	id int primary key not null auto_increment,
    name varchar(32) not null,
    tel varchar(20),
    email varchar(60),
    password varchar(20) not null,
    identity varchar(30) not null
);
```

八种角色对应的identity:

|    角色    | identity  |
| :--------: | :-------: |
| 公司管理员 |   root    |
|  接单人员  |  handler  |
|  测量人员  | measurer  |
|  设计人员  | designer  |
|  报价人员  |  pricer   |
|  工厂人员  |  factory  |
|  安装人员  | installer |
|  结案人员  | finisher  |
|  客户人员  | clienter  |



## 客户表

```sql
create table client(
	id int primary key not null auto_increment,
    name varchar(30),
    contactname varchar(30),
    tel varchar(30),
    address varchar(100)
);
```



## 订单表

客户和订单应该是一对多的关系。

```sql
create table order(
	id int primary key not null auto_increment,
    clientid int,
    clientname varchar(20),
    title varchar(30),
    description varchar(200),
    length int,
    width int,
    height int,
    number int,
    material varchar(20),
    price int,
    imagename varchar(20),
    status varchar(20)
);
```



status可能的取值（后端维护）：

|        status        |        含义        |
| :------------------: | :----------------: |
|       created        |       已创建       |
|       measured       |       已测量       |
|       designed       |      设计完毕      |
|        priced        |       已定价       |
| accepted or rejected | 客户接受or客户拒绝 |
|       produced       |      生产完毕      |
|      installed       |      安装完毕      |
|       finished       |      订单结束      |











