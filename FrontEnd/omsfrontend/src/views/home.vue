<template>
    <div>
        <Sidebar></Sidebar>
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
            // modifyMyInfo(){
            //     let fd = new FormData();
            //     fd.append("name", this.name);
            //     fd.append("tel", this.tel);
            //     fd.append("email", this.email);
            //     fd.append("password", this.password);
            //     axios.post("/api/person/update/own", fd)
            //     .then((response) => {
            //         if(response.data.message == "success")
            //         {
            //             this.$router.push("/myinfo");
            //             alert("修改成功！")
            //         }
            //         else
            //         {
            //             alert("修改失败，用户名重复！")
            //         }
            //     })
            // }
        },
        components:{
            Sidebar
        }
    }

</script>

<style lang="scss" scoped>
</style> 