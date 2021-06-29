<template>
    <div>
        <h1>login</h1>
        <form @submit.prevent="submitLogin" class="loginForm">
            <label for="name">用户名
            <input name="username" type="text" placeholder="请输入用户名" v-model="name" />
            </label>
            <br>
            <label for="password">密码
            <input name="password" type="password" placeholder="请输入密码" v-model="password" />
            </label>
            <br>
            <button>submit</button>
        </form>
    </div>
</template>

<script>
import axios from "axios";
    export default {
        name:"login",
        data(){
            return {
                name: "",
                password: ""
            }
        },
        methods:{
            submitLogin(){
                let fd = new FormData();
                fd.append("name", this.name);
                fd.append("password", this.password);
                axios.post("/api/person/login", fd)
                .then((response) => {
                    if(response.data.message == "success")
                    {
                        this.$router.push("/staff");
                    }
                    else
                    {
                        // callback(new Error('login failed!'))
                        console.log("login failed!");
                    }
                })
            }
        }
    }
</script>

<style scoped>
.loginForm{
    line-height: 40px;
}
.loginForm input{
    height: 20px;
}
</style> 