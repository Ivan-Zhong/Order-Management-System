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



## 客户表





## 订单表

客户和订单应该是一对多的关系。

