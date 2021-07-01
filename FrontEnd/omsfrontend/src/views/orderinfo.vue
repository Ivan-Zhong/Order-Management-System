<template>
    <div>
        <Sidebar></Sidebar>
        <h1>orderinfo</h1>
        <div v-if="handler">
        <form @submit.prevent="editOrderCreate">
            客户名：
            <select v-model="clientname" @change="selectClass($event)">
                <option :value="client.name" v-for="client in clients" :key="client.id" >{{client.name}}</option>
            </select>
            <br>``
            <br>
            订单描述<br><textarea v-model="description"></textarea>
            <br>
            <br>
            <button>submit</button>
        </form>
        </div>

        <div v-if="measurer">
        <form @submit.prevent="editOrderMeasure">
            长度：<input type="text" placeholder="length" v-model="length" />
            <br>
            <br>
            宽度：<input type="text" placeholder="width" v-model="width" />
            <br>
            <br>
            高度：<input type="text" placeholder="height" v-model="height" />
            <br>
            <br>
            数量：<input type="text" placeholder="number" v-model="number" />
            <br>
            <br>
            <button>submit</button>
        </form>
        </div>


</div>


</template>

<script>


import Sidebar from '../components/sidebar'
import axios from "axios"
    export default {
        name:"orderinfo",
        data(){
            return {
                identity:"",
                handler:false,
                measurer:false,
                clientname:"",
                description:"",
                length:"",
                width:"",
                height:"",
                number:"",
            }
        },
        created: function () {
            axios.get("/api/person/read/own")
            .then((response) => {
                if(response.data.message == "success")
                {
                        this.identity = response.data.data.identity;
                        this.id=this.$route.query.id;
                        if(this.identity=="handler")
                        {
                            handler=true;
                            axios.get(`/api/order/create/read/one/${this.id}`)
                             .then((response) => {
                               if(response.data.message == "success")
                                {
                                    this.clientname = response.data.data.clientname;
                                    this.description = response.data.data.description;
                                }
                            })
                        }
                        if(this.identity=="measurer")
                        {
                            measurer=true;
                            axios.get(`/api/order/measure/read/one/${this.id}`)
                             .then((response) => {
                               if(response.data.message == "success")
                                {
                                    this.length = response.data.data.length;
                                    this.width = response.data.data.width;
                                    this.height = response.data.data.height;
                                    this.number = response.data.data.number;
                                }
                            })
                        }
                        if(this.identity=="designer")
                        {

                        }
                        if(this.identity=="pricer")
                        {

                        }
                        if(this.identity=="clienter")
                        {

                        }
                        if(this.identity=="factory")
                        {

                        }
                        if(this.identity=="installer")
                        {

                        }
                        if(this.identity=="finisher")
                        {

                        }
                            
                }
            })

        },

        methods:{

            editOrderCreate(){
                let fd = new FormData();
                fd.append("clientname", this.clientname);
                fd.append("description", this.description);
                axios.post(`/api/order/create/update/${this.id}`, fd)
                .then((response) => {
                    if(response.data.message == "success")
                    {
                        this.$router.push("/order");
                        alert("修改成功！")
                    }
                })
            },

            
            editOrderMeasure(){
                let fd = new FormData();
                fd.append("length", this.length);
                fd.append("width", this.width);
                fd.append("height", this.height);
                fd.append("number", this.number);
                axios.post(`/api/order/measure/update/${this.id}`, fd)
                .then((response) => {
                    if(response.data.message == "success")
                    {
                        this.$router.push("/order");
                        alert("修改成功！")
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