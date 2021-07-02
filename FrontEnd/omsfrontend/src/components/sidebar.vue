<template>
    <div class="sidebar">
        <br>
        <span><router-link :to="{path: '/home'}" style="color:rgb(191, 203, 217);font-size: 18px;font-weight:normal;text-decoration:none;">订单管理系统</router-link></span>
        <br>
        <br>
        <div class="route_item" v-for="route1 in route_list" :key="route1">
            <router-link :to="route1.router" style="color:white;text-decoration:none"> 
                <i class="el-icon-user" v-if="route1.name=== '人员管理'"></i>
                <i class="el-icon-s-custom" v-if="route1.name=== '客户管理'"></i>       
                <i class="el-icon-s-order" v-if="route1.name=== '订单管理'"></i>       
                <i class="el-icon-document" v-if="route1.name=== '个人信息'"></i>               
                {{ route1.name }}
            </router-link> 
        </div>
    </div>
</template>

<script>
import axios from "axios";
export default ({
    name:"sidebar",
    data() {    
        return {
            // role : this.$store.state.user.identity,//获得用户类型
            role:"",
            route_list:[]//路由的列表
        };
    },
    created: function () {
        axios.get("/api/person/read/own")
        .then((response) => {
            if(response.data.message == "success")
            {
                    this.role = response.data.data.identity;
        if(this.role == 'root')
        {
            this.route_list[0] = {name:"人员管理",router:"/staff"};
            this.route_list[1] = {name:"客户管理",router:"/client"};
            this.route_list[2] = {name:"订单管理",router:"/order"};
            this.route_list[3] = {name:"个人信息",router:"/myinfo"};

        }
        else if(this.role == "handler")
        {
            this.route_list[0] = {name:"客户管理",router:"/client"};
            this.route_list[1] = {name:"订单管理",router:"/order"};
            this.route_list[2] = {name:"个人信息",router:"/myinfo"};
        }
        else if(this.role == 'measurer')
        {
            this.route_list[0] = {name:"客户管理",router:"/client"};
            this.route_list[1] = {name:"订单管理",router:"/order"};
            this.route_list[2] = {name:"个人信息",router:"/myinfo"};
        }
        else if(this.role == 'designer')
        {
            this.route_list[0] = {name:"订单管理",router:"/order"};
            this.route_list[1] = {name:"个人信息",router:"/myinfo"};
        }
        else if(this.role == 'pricer')
        {
            this.route_list[0] = {name:"客户管理",router:"/client"};
            this.route_list[1] = {name:"订单管理",router:"/order"};
            this.route_list[2] = {name:"个人信息",router:"/myinfo"};
        }
        else if(this.role == 'factory')
        {
            this.route_list[0] = {name:"订单管理",router:"/order"};
            this.route_list[1] = {name:"个人信息",router:"/myinfo"};
        }
        else if(this.role == 'installer')
        {
            this.route_list[0] = {name:"订单管理",router:"/order"};
            this.route_list[1] = {name:"个人信息",router:"/myinfo"};
        }
        else if(this.role == 'finisher')
        {
            this.route_list[0] = {name:"客户管理",router:"/client"};
            this.route_list[1] = {name:"订单管理",router:"/order"};
            this.route_list[2] = {name:"个人信息",router:"/myinfo"};
        }
        else if(this.role == 'clienter')
        {
            this.route_list[0] = {name:"订单管理",router:"/order"};
            this.route_list[1] = {name:"个人信息",router:"/myinfo"};
        }
            }
        })
        
    },
    })
</script>

<style scoped>
.sidebar{
    background-color: #304156;
    width:180px;
    height: 100vh;
    display:fixed;
    float: left;
    position:fixed;
    
}
.route_item{
    background-color:rgba(212, 211, 209, 0.493);
    margin:10px 5px;
    border: 1px rgb(128, 128, 128) solid;
    padding: 10px 5px;
    border-radius: 10px;
    transition: 0.5s;
}
.route_item:hover{
    background-color:rgba(214, 214, 212, 0.87);
    border: 1px black solid;

}

</style>

