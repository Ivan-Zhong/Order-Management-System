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

        <div v-if="designer">
        <form @submit.prevent="editOrderDesign">
            材质：<input type="text" placeholder="material" v-model="material" />
            <br>
            <br>

        //上传图片待写

            <button>submit</button>
        </form>
        </div>

        <div v-if="pricer">
        <form @submit.prevent="editOrderPrice">
            报价：<input type="text" placeholder="price" v-model="price" />
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
                designer:false,
                pricer:false,
                clientname:"",
                description:"",
                length:0,
                width:0,
                height:0,
                number:0,
                material:"",
                price:0,
                //图片变量待写
            }
        },
        created: function () {
            axios.get("/api/person/read/own")
            .then((response) => {
                if(response.data.message == "success")
                {
                        this.identity = response.data.data.identity;
                        this.id=this.$route.query.id;
                        //root页面待添加，应开放所有信息编辑权限
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
                            designer=true;
                            axios.get(`/api/order/design/read/one/${this.id}`)
                             .then((response) => {
                               if(response.data.message == "success")
                                {
                                    this.material = response.data.data.material;
                                    //上传图片待写
                                }
                            })
                        }
                        if(this.identity=="pricer")
                        {
                            pricer=true;
                            axios.get(`/api/order/price/read/one/${this.id}`)
                             .then((response) => {
                               if(response.data.message == "success")
                                {
                                    this.price = response.data.data.price;
                                }
                            })
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

            editOrderDesign(){
                let fd = new FormData();
                fd.append("material", this.material);
                 //上传图片待写
                axios.post(`/api/order/design/update/${this.id}`, fd)
                .then((response) => {
                    if(response.data.message == "success")
                    {
                        this.$router.push("/order");
                        alert("修改成功！")
                    }
                })
            },

            editOrderPrice(){
                let fd = new FormData();
                fd.append("price", this.price);
                axios.post(`/api/order/price/update/${this.id}`, fd)
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