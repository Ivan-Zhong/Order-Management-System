<template>
    <div class="sidebar">
        
        <div class="route_item" v-for="route1 in route_list" :key="route1">
            {{ route1 }}
        </div>
    </div>
</template>

<script>
export default ({
    name:"sidebar",
    data() {
        return {
            role : this.$store.state.identity,//获得用户类型
            route_list:[]//路由的列表
        };
    },
    created: function () {
        if(this.role == 'admin')
        {
            this.route_list[0] = "人员管理";
            this.route_list[1] = "客户管理";
            this.route_list[2] = "订单管理";
            this.route_list[3] = "个人信息";
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

