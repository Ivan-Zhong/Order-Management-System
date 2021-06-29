<template>
    <div class="sidebar">
        
        <div class="route_item" v-for="route1 in route_list" :key="route1">
            <router-link :to="route1.router">            
                {{ route1.name }}
            </router-link> 
        </div>
    </div>
</template>

<script>
export default ({
    name:"sidebar",
    data() {
        return {
            role : this.$store.state.user.identity,//获得用户类型
            route_list:[]//路由的列表
        };
    },
    created: function () {
        if(this.role == 'root')
        {
            this.route_list[0] = {name:"人员管理",router:"/staff"};
            this.route_list[1] = {name:"客户管理",router:"/client"};
            this.route_list[2] = {name:"订单管理",router:"/order"};
            this.route_list[3] = {name:"个人信息",router:"/info"};
        }
        else if(this.role == 'handler')
        {
            this.route_list[0] = {name:"客户管理",router:"/client"};
            this.route_list[1] = {name:"订单管理",router:"/order"};
            this.route_list[2] = {name:"个人信息",router:"/info"};
        }
        else if(this.role == 'measurer')
        {
            this.route_list[0] = {name:"客户管理",router:"/client"};
            this.route_list[1] = {name:"订单管理",router:"/order"};
            this.route_list[2] = {name:"个人信息",router:"/info"};
        }
        else if(this.role == 'designer')
        {
            this.route_list[0] = {name:"订单管理",router:"/order"};
            this.route_list[1] = {name:"个人信息",router:"/info"};
        }
        else if(this.role == 'pricer')
        {
            this.route_list[0] = {name:"客户管理",router:"/client"};
            this.route_list[1] = {name:"订单管理",router:"/order"};
            this.route_list[2] = {name:"个人信息",router:"/info"};
        }
        else if(this.role == 'factory')
        {
            this.route_list[0] = {name:"订单管理",router:"/order"};
            this.route_list[1] = {name:"个人信息",router:"/info"};
        }
        else if(this.role == 'installer')
        {
            this.route_list[0] = {name:"订单管理",router:"/order"};
            this.route_list[1] = {name:"个人信息",router:"/info"};
        }
        else if(this.role == 'finisher')
        {
            this.route_list[0] = {name:"客户管理",router:"/client"};
            this.route_list[1] = {name:"订单管理",router:"/order"};
            this.route_list[2] = {name:"个人信息",router:"/info"};
        }
        else if(this.role == 'clienter')
        {
            this.route_list[0] = {name:"订单管理",router:"/order"};
            this.route_list[1] = {name:"个人信息",router:"/info"};
        }
    },
    })
</script>

<style scoped>
.sidebar{
    background-color: beige;
    width:200px;
    height:1000px;
    display:fixed;
    float: left;
}
.route_item{
    background-color:lemonchiffon;
    margin:10px 5px;
    border: 1px rgb(128, 128, 128) solid;
    padding: 10px 5px;
    border-radius: 10px;
    transition: 0.5s;
}
.route_item:hover{
    background-color:rgb(255, 253, 238);
    border: 1px black solid;

}

</style>

