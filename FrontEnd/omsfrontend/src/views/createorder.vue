<template>
    <div>
        <Sidebar></Sidebar>
        <h1>createorder</h1>
        <form @submit.prevent="createorder">
            客户名：
            <select v-model="clientname" @change="selectClass($event)">
                <option :value="client.name" v-for="client in clients" :key="client.id" >{{client.name}}</option>
            </select>
            <br>
            <br>
            <!-- 订单描述：<input type="textarea" rows="3" cols="20" placeholder="description" v-model="description" /> -->
            订单描述<br><textarea v-model="description"></textarea>
            <br>
            <br>
            <button>submit</button>
        </form>



</div>


</template>

<script>


import Sidebar from '../components/sidebar'
import axios from "axios"
    export default {
        name:"createorder",
        data(){
            return {
                clients:[],
                clientname:"",
                description:"",
            }
        },
        created: function () {
            axios.get("/api/client/read/all")
            .then((response) => {
                if(response.data.message == "success")
                {
                    this.clients = response.data.data;
                }
            })
        },

        methods:{
            createorder(){
                let fd = new FormData();
                fd.append("description", this.description);
                fd.append("clientname", this.clientname);
                axios.post("/api/order/create/add", fd)
                .then((response) => {
                    if(response.data.message == "success")
                    {
                        this.$router.push("/order");
                        alert("添加成功！")
                    }
                })
            },

            selectClass(event){
             this.clientname = event.target.value; 
             },

        },
        components:{
            Sidebar
        }
    }
    

    
</script>

<style>

</style> 