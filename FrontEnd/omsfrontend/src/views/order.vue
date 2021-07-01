<template>
    <div>
        <Sidebar></Sidebar>
        <h1>order</h1>
        <button><router-link :to="{path: '/createorder'}" v-if="showcre">创建新订单</router-link></button>
        
        <table style="margin:0px auto;">
            <tr>
                <th>编号</th> 
                <th>客户</th> 
                <th>订单描述</th>
                <th>长度</th>
                <th>宽度</th>
                <th>高度</th>
                <th>数量</th>
                <th>材质</th>
                <th>报价</th>
                <th>样板图</th>
                <th>订单状态</th>    
                <th>操作</th>
            </tr>
            <tr v-for="(order,index) in orders" :key="order.id">
                <td>{{ index+1 }}</td>
                <td>{{ order.clientname }}</td>
                <td>{{ order.description }}</td>
                <td>{{ order.length }}</td>
                <td>{{ order.width }}</td>
                <td>{{ order.height }}</td>
                <td>{{ order.number }}</td>
                <td>{{ order.material }}</td>
                <td>{{ order.price }}</td>
                <td><a href="#"></a>查看图片</td>
                <td>{{ order.status }}</td>
                <td>
                    <button><router-link :to="{path: '/createorder', query: {id: order.id }}">编辑</router-link></button>
                    <button @click="deleteOrder(order.id)" v-if="showdel">删除</button>
                </td>                
            </tr>

        </table>


    </div>
</template>

<script>
import Sidebar from '../components/sidebar'
import axios from "axios"
    export default {
        name:"order",
        data(){
            return {
                orders:[],
                identity:"",
                showdel:false,
                showcre:false,
            }
        },
        created: function () {
            axios.get("/api/person/read/own")
            .then((response) => {
                if(response.data.message == "success")
                {
                        this.identity = response.data.data.identity;
                        if(this.identity=="root")
                        {
                            axios.get("/api/order/create/read/all")
                            .then((response) => {
                            if(response.data.message == "success")
                            {
                                this.orders = response.data.data;
                            }
                            })
                            this.showdel=true;
                            this.showcre=true;
                        }
                        if(this.identity=="handler")
                        {
                            axios.get("/api/order/create/read/all")
                            .then((response) => {
                            if(response.data.message == "success")
                            {
                                this.orders = response.data.data;
                            }
                            })
                            this.showcre=true;
                        }
                        if(this.identity=="measurer")
                        {
                            axios.get("/api/order/measure/read/all")
                            .then((response) => {
                            if(response.data.message == "success")
                            {
                                this.orders = response.data.data;
                            }
                            })
                        }
                        if(this.identity=="designer")
                        {

                        }
                        if(this.identity=="pricer")
                        {

                        }
                        if(this.identity=="clienter")
                        {

                        }
                        if(this.identity=="factory")
                        {

                        }
                        if(this.identity=="installer")
                        {

                        }
                        if(this.identity=="finisher")
                        {

                        }
                            
                }
            })
        },

        methods:{
            deleteOrder(ids){
                this.id=ids;
                axios.get(`/api/order/delete/${this.id}`)
                .then((response) => {
                    if(response.data.message == "success")
                    {
                        alert("删除成功！");
                        axios.get("/api/order/create/read/all")
                        .then((response) => {
                          if(response.data.message == "success")
                          {
                             this.orders = response.data.data;  
                         }
                        })
                        this.$router.replace("/order");
                    }
                })
            },

        },
        components:{
            Sidebar
        }
    }
</script>

<style lang="scss" scoped>
</style> 