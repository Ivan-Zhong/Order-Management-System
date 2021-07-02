<template>
    <div>
        <Sidebar></Sidebar>
        <div style="margin-left:200px;">
        <br>
        <h1>客户管理</h1>
        <!-- <button id="create">创建新客户</button> -->
        <!-- <button v-if="showcre"><router-link :to="{path: '/createclient'}">创建新客户</router-link></button> -->
        <el-button type="primary" @click="createClient()" v-if="showcre">创建新客户</el-button>
        <br>
        <br>
        <!-- <table  style="margin:0px auto;">
            <tr>
                <th>编号</th>
                <th>客户名</th> 
                <th>联系人</th>
                <th>联系方式</th>
                <th>地址</th>
                <th v-if="showmod">操作</th>
            </tr>
            <tr v-for="(client,index) in clients" :key="client.id">
                <td>{{ index+1 }}</td>    
                <td>{{ client.name }}</td>
                <td>{{ client.contactname }}</td>
                <td>{{ client.tel }}</td>
                <td>{{ client.address }}</td>
                <td>
                    <button v-if="showmod"><router-link :to="{path: '/clientinfo', query: {id: client.id }}">编辑</router-link></button>
                    <button @click="deleteClient(client.id)" v-if="showdel">删除</button>
                </td>
            </tr>

        </table> -->

<el-container>

  <el-table
    :data="clients"
    stripe
    height="605"
    style="width: 100%">
    <el-table-column
      type="index"
      :index="indexMethod">
    </el-table-column>
    <el-table-column
      prop="name"
      label="客户名">
    </el-table-column>
    <el-table-column
      prop="contactname"
      label="联系人">
    </el-table-column>
        <el-table-column
      prop="tel"
      label="电话">
    </el-table-column>
        <el-table-column
      prop="address"
      label="地址">
    </el-table-column>
    <el-table-column label="操作"  v-if="showmod">
        <template #default="scope">
        <el-button
          size="mini"
          icon="el-icon-edit"
          type="primary"
          @click="modifyClient(scope.row.id)" v-if="showmod">编辑</el-button>
        <el-button
          size="mini"
          icon="el-icon-delete"
          type="danger" 
         @click="deleteClient(scope.row.id)" v-if="showdel">删除</el-button>
        </template>
    </el-table-column>
  </el-table>

</el-container>

 </div>
    </div>
</template>

<script>


import Sidebar from '../components/sidebar'
import axios from "axios"
    export default {
        name:"client",
        data(){
            return {
                clients:[],
                id:0,
                name:"",
                contactname:"",
                tel:"",
                address:"",
                identity:"",
                showdel:false,
                showcre:false,
                showmod:false,
            }
        },
        created: function () {
            axios.get("/api/client/read/all")
            .then((response) => {
                if(response.data.message == "success")
                {
                    this.clients = response.data.data;
                }
            })
            axios.get("/api/person/read/own")
            .then((response) => {
                if(response.data.message == "success")
                {
                        this.identity = response.data.data.identity;
                        if(this.identity=="root")
                        {
                            this.showcre=true;
                            this.showmod=true;
                            this.showdel=true;
                        }
                        if(this.identity=="handler")
                        {
                            this.showcre=true;
                            this.showmod=true;
                        }
                            
                }
            })
        },

        methods:{
            // createClient(){
            //     let fd = new FormData();
            //     fd.append("name", this.name);
            //     fd.append("contactname", this.contactname);
            //     fd.append("tel", this.tel);
            //     fd.append("address", this.address);
            //     axios.post("/api/client/add", fd)
            //     .then((response) => {
            //         if(response.data.message == "success")
            //         {
            //             this.$router.push("/staff");
            //         }
            //         else
            //         {
            //             alert("添加失败，客户名重复！");
            //         }
            //     })
            // },
            createClient()
            {
                this.$router.push({
                    path:'/createclient',
                });
            },

            modifyClient(ids)
            {
                this.id=ids;
                this.$router.push({
                    path:'/clientinfo',
                    query: {id: this.id }
                });
            },
            // editClient(){
            //     let fd = new FormData();
            //     fd.append("name", this.name);
            //     fd.append("contactname", this.contactname);
            //     fd.append("tel", this.tel);
            //     fd.append("address", this.address);
            //     axios.post("/api/client/update/{id}", fd)
            //     .then((response) => {
            //         if(response.data.message == "success")
            //         {
            //             this.$router.push("/staff");
            //         }
            //         else
            //         {
            //             alert("修改失败，客户名重复！");
            //         }
            //     })
            // },

            // checkClient(){
            //     axios.get("/api/client/read/one/{id}")
            //     .then((response) => {
            //         if(response.data.message == "success")
            //         {
            //             this.clients = response.data.data;
            //         }
            //     })
            // },

            deleteClient(ids){
                this.id=ids;

        this.$confirm('确定要删除该客户吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
                axios.get(`/api/client/delete/${this.id}`)
                .then((response) => {
                    if(response.data.message == "success")
                    {
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        // alert("删除成功！");
                        axios.get("/api/client/read/all")
                        .then((response) => {
                          if(response.data.message == "success")
                          {
                             this.clients = response.data.data;
                         }
                        })
                        this.$router.replace("/client");
                    }
                })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });

            },
        },
        components:{
            Sidebar
        }
    }
    

    
</script>

<style>

</style> 