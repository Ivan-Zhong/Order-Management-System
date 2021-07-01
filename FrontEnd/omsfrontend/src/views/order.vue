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
                    <button><router-link :to="{path: '/createorder', query: {id: order.id }}" v-if="showedit">编辑</router-link></button>
                    <button @click="deleteOrder(order.id)" v-if="showdel">删除</button>

                    <!-- <button @click="AcceptOrder(order.id)" v-if="showacc">接受</button>
                    <button @click="RejectOrder(order.id)" v-if="showrej">拒绝</button>
                    <button @click="ProduceOrder(order.id)" v-if="showpro">生产完成</button>
                    <button @click="InstallOrder(order.id)" v-if="showins">安装完成</button>
                    <button @click="FinishOrder(order.id)" v-if="showfin">结束订单</button> -->

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
                showedit:false,
                // showacc:false,
                // showrej:false,
                // showpro:false,
                // showins:false,
                // showfin:false,
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
                            axios.get("/api/order/create/read/all")//待确认
                            .then((response) => {
                            if(response.data.message == "success")
                            {
                                this.orders = response.data.data;
                            }
                            })
                            this.showdel=true;
                            this.showcre=true;
                            this.showedit=true;
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
                            this.showedit=true;

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
                            this.showedit=true;                            
                        }
                        if(this.identity=="designer")
                        {
                            axios.get("/api/order/design/read/all")
                            .then((response) => {
                            if(response.data.message == "success")
                            {
                                this.orders = response.data.data;
                            }
                            })
                            this.showedit=true;
                        }
                        if(this.identity=="pricer")
                        {
                            axios.get("/api/order/price/read/all")
                            .then((response) => {
                            if(response.data.message == "success")
                            {
                                this.orders = response.data.data;
                            }
                            })
                            this.showedit=true;
                        }
                        if(this.identity=="clienter")
                        {
                            axios.get("/api/order/consider/read/all")
                            .then((response) => {
                            if(response.data.message == "success")
                            {
                                this.orders = response.data.data;

                            }
                            })
                            this.showacc=true;
                            this.showrej=true;
                        }
                        if(this.identity=="factory")
                        {
                            axios.get("/api/order/produce/read/all")
                            .then((response) => {
                            if(response.data.message == "success")
                            {
                                this.orders = response.data.data;
                            }
                            })
                            this.showpro=true;
                        }
                        if(this.identity=="installer")
                        {
                            axios.get("/api/order/install/read/all")
                            .then((response) => {
                            if(response.data.message == "success")
                            {
                                this.orders = response.data.data;
                            }
                            })
                            this.showins=true;
                        }
                        if(this.identity=="finisher")
                        {
                            axios.get("/api/order/finish/read/all")
                            .then((response) => {
                            if(response.data.message == "success")
                            {
                                this.orders = response.data.data;
                            }
                            })
                            this.showfin=true;
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

            AcceptOrder(ids){
                this.id=ids;
                axios.get(`/api/order/delete/${this.id}`) //api待修改
                .then((response) => {
                    if(response.data.message == "success")
                    {
                        alert("已接受！");
                        axios.get("/api/order/consider/read/all")
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

            RejectOrder(ids){
                this.id=ids;
                axios.get(`/api/order/delete/${this.id}`) //api待修改
                .then((response) => {
                    if(response.data.message == "success")
                    {
                        alert("已拒绝！");
                        axios.get("/api/order/consider/read/all")
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

            ProduceOrder(ids){
                this.id=ids;
                axios.get(`/api/order/delete/${this.id}`) //api待修改
                .then((response) => {
                    if(response.data.message == "success")
                    {
                        alert("已确认生产完毕！");
                        axios.get("/api/order/produce/read/all")
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

            
            InstallOrder(ids){
                this.id=ids;
                axios.get(`/api/order/delete/${this.id}`) //api待修改
                .then((response) => {
                    if(response.data.message == "success")
                    {
                        alert("已确认安装完毕！");
                        axios.get("/api/order/install/read/all")
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

            FinishOrder(ids){
                this.id=ids;
                axios.get(`/api/order/delete/${this.id}`) //api待修改
                .then((response) => {
                    if(response.data.message == "success")
                    {
                        alert("已结束订单！");
                        axios.get("/api/order/finish/read/all")
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