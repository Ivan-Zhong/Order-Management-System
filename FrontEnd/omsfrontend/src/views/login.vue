<template>
    <div>
        <h1>login</h1>
        <form @submit.prevent="submitLogin">
            <input type="text" placeholder="username" v-model="username" />
            <input type="password" placeholder="password" v-model="password" />
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
                username: "",
                password: ""
            }
        },
        methods:{
            submitLogin(){
                let fd = new FormData();
                fd.append("username", this.username);
                fd.append("password", this.password);
                axios.post("/api/person/login", fd)
                .then((response) => {
                    if(response.data.message == "success")
                    {
                        this.$router.push("/logout");
                    }
                    else
                    {
                        callback(new Error('login failed!'))
                    }
                })
            }
        }
    }
</script>

<style lang="scss" scoped>

</style>