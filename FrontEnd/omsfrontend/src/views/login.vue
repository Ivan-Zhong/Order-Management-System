<template>
    <div>
        <h1>login</h1>
        <form @submit.prevent="submitLogin">
            <input type="text" placeholder="name" v-model="name" />
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