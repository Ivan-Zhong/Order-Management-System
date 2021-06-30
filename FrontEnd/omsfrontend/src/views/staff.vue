<template>
    <div>
        <Sidebar></Sidebar>
        <h1>staff</h1>
        <!-- <button @click="getPersons">get persons</button> -->
        <button><router-link :to="{path: '/userinfo'}">创建新用户</router-link></button>
        <button @click="logout">注销</button>
        <table style="margin:0px auto;">
            <tr>
                <th>编号</th> 
                <th>姓名</th> 
                <th>电话</th>
                <th>邮箱</th>
                <th>密码</th>
                <th>身份</th>
                <th>操作</th>
            </tr>
            <tr v-for="person in persons" :key="person.id">
                <td>{{ person.id }}</td>
                <td>{{ person.name }}</td>
                <td>{{ person.tel }}</td>
                <td>{{ person.email }}</td>
                <td>{{ person.password }}</td>
                <td>{{ person.identity }}</td>
                <td>
                    <button><router-link :to="{path: '/userinfo', query: {id: person.id }}">编辑</router-link></button>
                    <button @click="deleteUser('{{ person.id }}')">删除</button>
                </td>                
            </tr>

        </table>


    </div>
</template>

<script>
import Sidebar from '../components/sidebar'
import axios from "axios"
    export default {
        name:"staff",
        data(){
            return {
                persons:[],
                id:"",
                name:"",
                tel:"",
                email:"",
                password:"",
                identity:"",
            }
        },
        created: function () {
            axios.get("/api/person/read/all")
            .then((response) => {
                if(response.data.message == "success")
                {
                    this.persons = response.data.data;
                }
            })
        },

        methods:{
            deleteUser(id){
                this.id=id;
                axios.get("/api/person/delete/{id}")
                .then((response) => {
                    if(response.data.message == "success")
                    {
                        alert("删除成功！")
                        this.$router.push("staff");
                    }
                })
            },
            logout(){
                axios.get("/api/person/logout")
                .then((response) => {
                if(response.data.message == "success")
                {
                    this.$router.push("login");
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