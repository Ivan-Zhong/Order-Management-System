<template>
    <div>
        <Sidebar></Sidebar>
        <h1>client</h1>
        <!-- <button id="create">创建新客户</button> -->
        <button><router-link :to="{path: '/clientinfo'}">创建新客户</router-link></button>
        <table  style="margin:0px auto;">
            <tr>
                <th>编号</th>
                <th>客户名</th> 
                <th>联系人</th>
                <th>联系方式</th>
                <th>地址</th>
                <th>操作</th>
            </tr>
            <tr v-for="client in clients" :key="client.id">
                <td>{{ client.id }}</td>    
                <td>{{ client.name }}</td>
                <td>{{ client.contactname }}</td>
                <td>{{ client.tel }}</td>
                <td>{{ client.address }}</td>
                <td>
                    <button><router-link :to="{path: '/clientinfo', query: {id: '{{ client.id }}' }}">编辑</router-link></button>
                    <button @click="deleteClient('{{ client.id }}')">删除</button>
                </td>
            </tr>

        </table>



    </div>
</template>

<script>


import Sidebar from '../components/sidebar'
import axios from "axios"
    export default {
        name:"client",
        data(){
            return {
                clients:[],
                id:"",
                name:"",
                contactname:"",
                tel:"",
                address:"",
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
            // createClient(){
            //     let fd = new FormData();
            //     fd.append("name", this.name);
            //     fd.append("contactname", this.contactname);
            //     fd.append("tel", this.tel);
            //     fd.append("address", this.address);
            //     axios.post("/api/client/add", fd)
            //     .then((response) => {
            //         if(response.data.message == "success")
            //         {
            //             this.$router.push("/staff");
            //         }
            //         else
            //         {
            //             alert("添加失败，客户名重复！");
            //         }
            //     })
            // },

            // editClient(){
            //     let fd = new FormData();
            //     fd.append("name", this.name);
            //     fd.append("contactname", this.contactname);
            //     fd.append("tel", this.tel);
            //     fd.append("address", this.address);
            //     axios.post("/api/client/update/{id}", fd)
            //     .then((response) => {
            //         if(response.data.message == "success")
            //         {
            //             this.$router.push("/staff");
            //         }
            //         else
            //         {
            //             alert("修改失败，客户名重复！");
            //         }
            //     })
            // },

            // checkClient(){
            //     axios.get("/api/client/read/one/{id}")
            //     .then((response) => {
            //         if(response.data.message == "success")
            //         {
            //             this.clients = response.data.data;
            //         }
            //     })
            // },

            deleteClient(id){
                this.id=id;
                axios.get("/api/client/delete/{id}")
                .then((response) => {
                    if(response.data.message == "success")
                    {
                        alert("删除成功！");
                        this.$router.push("/client");
                    }
                })
            },
        },
        components:{
            Sidebar
        }
    }
    

    
</script>

<style>

</style> 