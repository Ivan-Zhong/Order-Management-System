<template>
    <div>
        <Sidebar></Sidebar>
        <br>
        <h1>{{name}}，欢迎您！</h1> 
        



    </div>
</template>

<script>
import Sidebar from '../components/sidebar'
import axios from "axios";
    export default {
        name:"home",
        data(){
            return {
                name: "",
            }
        },
        created: function () {
            axios.get("/api/person/read/own")
            .then((response) => {
                if(response.data.message == "success")
                {
                        this.name = response.data.data.name;
                }
            })
        },
        methods:{
        },
        components:{
            Sidebar
        }
    }

</script>

<style lang="scss" scoped>
</style> 