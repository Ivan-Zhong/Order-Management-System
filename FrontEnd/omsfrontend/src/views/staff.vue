<template>
    <div>
        <Sidebar></Sidebar>
        
        <div style="margin-left:200px;">
        <br>
        <h1>人员管理</h1>
        <!-- <button @click="getPersons">get persons</button> -->
        <!-- <button><router-link :to="{path: '/createuser'}">创建新用户</router-link></button> -->
        <el-button type="primary" @click="createUser()">创建新用户</el-button>
        <br>
        <br>
        <!-- <table style="margin:0px auto;">
            <tr>
                <th>编号</th> 
                <th>姓名</th> 
                <th>电话</th>
                <th>邮箱</th>
                <th>密码</th>
                <th>身份</th>
                <th>操作</th>
            </tr>
            <tr v-for="(person,index) in persons" :key="person.id">
                <td>{{ index+1 }}</td>
                <td>{{ person.name }}</td>
                <td>{{ person.tel }}</td>
                <td>{{ person.email }}</td>
                <td>{{ person.password }}</td>
                <td>{{ person.identity }}</td>
                <td>
                    <button><router-link :to="{path: '/userinfo', query: {id: person.id }}">编辑</router-link></button>
                    <button @click="deleteUser(person.id)">删除</button>
                </td>                
            </tr>

        </table> -->

<el-container>

  <el-table
    :data="persons"
    stripe
    height="605"
    style="width: 100%">
    <el-table-column
      type="index"
      :index="indexMethod">
    </el-table-column>
    <el-table-column
      prop="name"
      label="姓名">
    </el-table-column>
    <el-table-column
      prop="tel"
      label="电话">
    </el-table-column>
        <el-table-column
      prop="email"
      label="邮箱">
    </el-table-column>
        <el-table-column
      prop="password"
      label="密码">
    </el-table-column>
        <el-table-column
      prop="identity"
      label="身份">
      　　　<template #default="scope">
<el-tag type="success" v-if="scope.row.identity=== 'root'">公司管理员</el-tag>
<el-tag type="success" v-if="scope.row.identity=== 'handler'">接单人员</el-tag>
<el-tag type="success" v-if="scope.row.identity=== 'measurer'">测量人员</el-tag>
<el-tag type="success" v-if="scope.row.identity=== 'designer'">设计人员</el-tag>
<el-tag type="success" v-if="scope.row.identity=== 'pricer'">报价人员</el-tag>
<el-tag type="success" v-if="scope.row.identity=== 'factory'">工厂人员</el-tag>
<el-tag type="success" v-if="scope.row.identity=== 'installer'">安装人员</el-tag>
<el-tag type="success" v-if="scope.row.identity=== 'finisher'">结案人员</el-tag>
<el-tag type="success" v-if="scope.row.identity=== 'clienter'">客户人员</el-tag>
　　　　　　</template>
</el-table-column>
    <el-table-column label="操作">
        <template #default="scope">
        <el-button
          size="mini"
          type="primary"
          @click="modifyUser(scope.row.id)">编辑</el-button>
        <el-button
          size="mini"
          type="danger" 
         @click="deleteUser(scope.row.id)">删除</el-button>
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
        name:"staff",
        data(){
            return {
                persons:[],
                id:0,
                name:"",
                tel:"",
                email:"",
                password:"",
                identity:"",
            }
        },
        created: function () {
            axios.get("/api/person/read/all")
            .then((response) => {
                if(response.data.message == "success")
                {
                    this.persons = response.data.data;
                }
            })
        },

        methods:{
            deleteUser(ids){
                this.id=ids;
        this.$confirm('确定要删除该用户吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
                axios.get(`/api/person/delete/${this.id}`)
                .then((response) => {
                    if(response.data.message == "success")
                    {
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        axios.get("/api/person/read/all")
                        .then((response) => {
                        if(response.data.message == "success")
                        {
                            this.persons = response.data.data;
                        }
                        })
                        this.$router.replace("/staff");
                    }
                })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
            },

            // deleteUser(ids){
            //     this.id=ids;
            //     axios.get(`/api/person/delete/${this.id}`)
            //     .then((response) => {
            //         if(response.data.message == "success")
            //         {
            //             this.$message({
            //                 type: 'success',
            //                 message: '删除成功!'
            //             });
            //             axios.get("/api/person/read/all")
            //             .then((response) => {
            //             if(response.data.message == "success")
            //             {
            //                 this.persons = response.data.data;
            //             }
            //             })
            //             this.$router.replace("/staff");
            //         }
            //     })
            // },

            createUser()
            {
                this.$router.push({
                    path:'/createuser',
                });
            },

            modifyUser(ids)
            {
                this.id=ids;
                this.$router.push({
                    path:'/userinfo',
                    query: {id: this.id }
                });
            }

        },
        components:{
            Sidebar
        }
    }
</script>

<style lang="scss" scoped>
</style> 