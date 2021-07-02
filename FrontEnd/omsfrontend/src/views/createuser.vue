<template>
    <div>
        <Sidebar></Sidebar>
        <br>
        <h1>创建新用户</h1>
        <form>
            姓名：<el-input v-model="name" placeholder="name" style="width:20%"></el-input>
            <br>
            <br>
            <!-- 电话：<input type="text" placeholder="tel" v-model="tel" /> -->
            电话：<el-input v-model="tel" placeholder="tel" style="width:20%"></el-input>
            <br>
            <br>
            电子邮箱：<el-input v-model="email" placeholder="email" style="width:20%"></el-input>
            <br>
            <br>
            密码：<el-input v-model="password" placeholder="password" style="width:20%" show-password></el-input>
            <br>
            <br>
            身份：
        <el-select v-model="identity" placeholder="请选择用户身份" @change="selectClass($event)">
          <el-option label="公司管理员" value="root" />
          <el-option label="接单人员" value="handler" />
          <el-option label="测量人员" value="measurer" />
          <el-option label="设计人员" value="designer" />
          <el-option label="报价人员" value="pricer" />
          <el-option label="工厂人员" value="factory" />
          <el-option label="安装人员" value="installer" />
          <el-option label="结案人员" value="finisher" />
          <el-option label="客户人员" value="clienter" />
        </el-select>
            <!-- <select v-model="identity" @change="selectClass($event)">
                <option value="root">公司管理员</option>
                <option value="handler">接单人员</option>
                <option value="measurer">测量人员</option>
                <option value="designer">设计人员</option>
                <option value="pricer">报价人员</option>
                <option value="factory">工厂人员</option>
                <option value="installer">安装人员</option>
                <option value="finisher">结案人员</option>
                <option value="clienter">客户人员</option>
            </select> -->
            <br>
            <br>
            <el-button type="primary" @click="createUser()">提交</el-button>
            <!-- <button>submit</button> -->
        </form>



</div>


</template>

<script>


import Sidebar from '../components/sidebar'
import axios from "axios"
    export default {
        name:"createuser",
        data(){
            return {
                id:0,
                name:"",
                tel:"",
                email:"",
                password:"",
                identity:"",
            }
        },
        // created: function () {
        //         console.log("Hello");
        //         this.id=this.$route.query.id;
        //         axios.get(`/api/person/read/others/${this.id}`)
        //         .then((response) => {
        //             if(response.data.message == "success")
        //             {
        //                 this.name = response.data.data.name;
        //                 this.tel = response.data.data.tel;
        //                 this.email = response.data.data.email;
        //                 this.password = response.data.data.password;
        //                 this.identity = response.data.data.identity;
        //                 this.status = 'modify'
        //             }
        //         })
        // },

        methods:{
            createUser(){
                let fd = new FormData();
                fd.append("name", this.name);
                fd.append("tel", this.tel);
                fd.append("email", this.email);
                fd.append("password", this.password);
                fd.append("identity", this.identity);
                axios.post("/api/person/add", fd)
                .then((response) => {
                    if(response.data.message == "success")
                    {
                        this.$router.push("/staff");
                        this.$message({
                            type: 'success',
                            message: '添加成功!'
                        });
                    }
                    else
                    {
                        this.$message({
                            type: 'error',
                            message: '添加失败，用户名重复！'
                        });
                    }
                })
            },

            selectClass(event){
             this.identity = event.target.value; 
             },

            // editUser(){
            //     let fd = new FormData();
            //     fd.append("name", this.name);
            //     fd.append("tel", this.tel);
            //     fd.append("email", this.email);
            //     fd.append("password", this.password);
            //     fd.append("identity", this.identity);
            //     axios.post(`/api/person/update/others/${this.id}`, fd)
            //     .then((response) => {
            //         if(response.data.message == "success")
            //         {
            //             this.$router.push("/staff");
            //             alert("添加成功！");
            //         }
            //         else
            //         {
            //             alert("修改失败，用户名重复！");
            //         }
            //     })
            // },

        },
        components:{
            Sidebar
        }
    }
    

    
</script>

<style>

</style> 