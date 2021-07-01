<template>
    <div>
        <Sidebar></Sidebar>
        <h1>myinfo</h1>
        <!-- <form @submit.prevent="modifyMyInfo"> -->
        <form>
            姓名：<input type="text" placeholder="name" v-model="name" />
            <br>
            <br>
            联系方式：<input type="text" placeholder="tel" v-model="tel" />
            <br>
            <br>
            电子邮箱：<input type="text" placeholder="email" v-model="email" />
            <br>
            <br>
            密码：<input type="text" placeholder="password" v-model="password" />
            <br>
            <br>
            身份：&nbsp;&nbsp;{{identity}}
            <br>
            <br>
            <input type="button" @click="modifyMyInfo()" value="提交更改">
            <br>
            <br>
            <!-- <button>提交更改</button>&nbsp;&nbsp;&nbsp; -->
        </form>

<button @click="logout()">注销</button>
        <!-- <input type="button" @click="logout()" value="注嗯嗯销"> -->
        
    </div>
</template>

<script>
import Sidebar from '../components/sidebar'
import axios from "axios";
    export default {
        name:"myinfo",
        data(){
            return {
                name: "",
                tel: "",
                email: "",
                password: "",
                identity:""
            }
        },
        created: function () {
            axios.get("/api/person/read/own")
            .then((response) => {
                if(response.data.message == "success")
                {
                        this.name = response.data.data.name;
                        this.tel = response.data.data.tel;
                        this.email = response.data.data.email;
                        this.password = response.data.data.password;
                        this.identity = response.data.data.identity;
                }
            })
        },
        methods:{
            logout(){
                axios.get("/api/person/logout")
                .then((response) => {
                if(response.data.message == "success")
                {
                    this.$router.push("/");
                }
            })
            },
            modifyMyInfo(){
                let fd = new FormData();
                fd.append("name", this.name);
                fd.append("tel", this.tel);
                fd.append("email", this.email);
                fd.append("password", this.password);
                axios.post("/api/person/update/own", fd)
                .then((response) => {
                    if(response.data.message == "success")
                    {
                        this.$router.push("/home");
                        alert("修改成功！")
                    }
                    else
                    {
                        alert("修改失败，用户名重复！")
                    }
                })
            }
        },

        components:{
            Sidebar
        }
    }

</script>

<style lang="scss" scoped>
</style> 