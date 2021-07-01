<template>
    <div>
        <br>
        <h1>用户登录</h1>
        <form>

            用户名：
                <el-input v-model="name" placeholder="name" style="width:20%"></el-input>
            <!-- <input name="username" type="text" placeholder="请输入用户名" v-model="name" /> -->
            <br>
            <br>
            密码：
                <el-input v-model="password" placeholder="password" style="width:20%" show-password></el-input>
            <br>
            <br>
            <el-button type="primary" @click="submitLogin()">登录</el-button>
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
                        this.$store.dispatch('get_useinfo')
                        this.$router.push("/home");
                        this.$message({
                            type: 'success',
                            message: '登录成功!'
                        });
                    }
                    else
                    {                        
                        this.$message({
                            type: 'error',
                            message: '用户名或密码错误，请重试！'
                        });
                        this.$router.replace("/");
                    }
                // })
                // }).catch((error)=>{
                //     this.$store.dispatch('get_useinfo')
                //     this.$router.push("/home");
                // })
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
