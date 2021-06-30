<template>
    <div>
        <Sidebar></Sidebar>
        <h1>userinfo</h1>
        <form @submit.prevent="editUser">
            姓名<input type="text" placeholder="name" v-model="name" />
            <br>
            <br>
            电话<input type="text" placeholder="tel" v-model="tel" />
            <br>
            <br>
            电子邮箱<input type="text" placeholder="email" v-model="email" />
            <br>
            <br>
            密码<input type="text" placeholder="password" v-model="password" />
            <br>
            <br>
            身份<input type="text" placeholder="identity" v-model="identity" />
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
        name:"userinfo",
        data(){
            return {
                id:0,
                name:"",
                tel:"",
                email:"",
                password:"",
                identity:"",
                status: 'create',
            }
        },
        created: function () {
                console.log("Hello");
                this.id=this.$route.query.id;
                axios.get(`/api/person/read/others/${this.id}`)
                .then((response) => {
                    if(response.data.message == "success")
                    {
                        this.name = response.data.data.name;
                        this.tel = response.data.data.tel;
                        this.email = response.data.data.email;
                        this.password = response.data.data.password;
                        this.identity = response.data.data.identity;
                        this.status = 'modify'
                    }
                })
        },

        methods:{
            createUser(){
                let fd = new FormData();
                fd.append("name", this.name);
                fd.append("tel", this.tel);
                fd.append("email", this.email);
                fd.append("password", this.password);
                fd.append("identity", this.identity);
                axios.post("/api/person/add", fd)
                .then((response) => {
                    if(response.data.message == "success")
                    {
                        this.$router.push("/staff");
                        alert("添加成功！");
                    }
                    else
                    {
                        alert("添加失败，用户名重复！");
                    }
                })
            },

            editUser(){
                let fd = new FormData();
                fd.append("name", this.name);
                fd.append("tel", this.tel);
                fd.append("email", this.email);
                fd.append("password", this.password);
                fd.append("identity", this.identity);
                axios.post(`/api/person/update/others/${this.id}`, fd)
                .then((response) => {
                    if(response.data.message == "success")
                    {
                        this.$router.push("/staff");
                        alert("添加成功！");
                    }
                    else
                    {
                        alert("修改失败，用户名重复！");
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