<template>
    <div>
        <Sidebar></Sidebar>
        <h1>clientinfo</h1>
        <form @submit.prevent="status==='create'?createClient:editClient">
            客户名<input type="text" placeholder="name" v-model="name" />
            <br>
            <br>
            负责人<input type="text" placeholder="contactname" v-model="contactname" />
            <br>
            <br>
            联系方式<input type="text" placeholder="tel" v-model="tel" />
            <br>
            <br>
            地址<input type="text" placeholder="address" v-model="address" />
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
        name:"clientinfo",
        data(){
            return {
                client:{},
                name:"",
                contactname:"",
                tel:"",
                address:"",
                status: 'create',
            }
        },
        created: function () {
                axios.get("/api/client/read/one/{id}")
                .then((response) => {
                    if(response.data.message == "success")
                    {
                        this.name = response.data.data.name;
                        this.contactname = response.data.data.contactname;
                        this.tel = response.data.data.tel;
                        this.address = response.data.data.address;
                        this.status = 'modify'
                    }
                })
        },

        methods:{
            createClient(){
                let fd = new FormData();
                fd.append("name", this.name);
                fd.append("contactname", this.contactname);
                fd.append("tel", this.tel);
                fd.append("address", this.address);
                axios.post("/api/client/add", fd)
                .then((response) => {
                    if(response.data.message == "success")
                    {
                        this.$router.push("/staff");
                    }
                    else
                    {
                        alert("添加失败，客户名重复！");
                    }
                })
            },

            editClient(){
                let fd = new FormData();
                fd.append("name", this.name);
                fd.append("contactname", this.contactname);
                fd.append("tel", this.tel);
                fd.append("address", this.address);
                axios.post("/api/client/update/{id}", fd)
                .then((response) => {
                    if(response.data.message == "success")
                    {
                        this.$router.push("/staff");
                    }
                    else
                    {
                        alert("修改失败，客户名重复！");
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