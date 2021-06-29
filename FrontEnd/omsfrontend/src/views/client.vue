<template>
    <div>
        <Sidebar></Sidebar>
        <button id="create">创建新客户</button>
        <!-- <button @click="createClient">创建新客户</button> -->
        <table style="position: absolute;left: 50%;transform: translate(-50%,-50%);">
            <tr>
                <th>编号</th>
                <th>客户名</th> 
                <th>联系人</th>
                <th>联系方式</th>
                <th>地址</th>
                <th>操作</th>
            </tr>
            <tr v-for="client in clients" :key="client.id">
                <td>{{ client.id }}</td>    
                <td>{{ client.name }}</td>
                <td>{{ client.contactname }}</td>
                <td>{{ client.tel }}</td>
                <td>{{ client.address }}</td>
                <td>
                    <button @click="editClient">编辑</button>
                    <button @click="deleteClient">删除</button>
                </td>
            </tr>

        </table>


<!-- 模态框 -->
<div id="myModal" class="modal">

  <!-- Modal content -->
  <div class="modal-content">
    <div class="modal-header">
      <span class="close">x</span>
      <h2>模态框头部</h2>
    </div>
    <div class="modal-body">
      <p>模态框上的内容……</p>
      <p>模态框上的内容……</p>
    </div>
    <div class="modal-footer">
      <h3>模态框底部</h3>
    </div>
  </div>

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
                name:"",
                contactname:"",
                tel:"",
                address:"",
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

            checkClient(){
                axios.get("/api/client/read/one/{id}")
                .then((response) => {
                    if(response.data.message == "success")
                    {
                        this.clients = response.data.data;
                    }
                })
            },

            deleteClient(){
                axios.get("/api/client/delete/{id}")
                .then((response) => {
                    if(response.data.message == "success")
                    {
                        this.$router.push("/staff");
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

.modal {
    display: none; /* Hidden by default */
    position: fixed; /* Stay in place */
    z-index: 1; /* Sit on top */
    padding-top: 100px; /* Location of the box */
    left: 0;
    top: 0;
    width: 100%; /* Full width */
    height: 100%; /* Full height */
    overflow: auto; /* Enable scroll if needed */
    background-color: rgb(0,0,0); /* Fallback color */
    background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
    position: relative;
    background-color: #fefefe;
    margin: auto;
    padding: 0;
    border: 1px solid #888;
    width: 80%;
    box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19);
    -webkit-animation-name: animatetop;
    -webkit-animation-duration: 0.4s;
    animation-name: animatetop;
    animation-duration: 0.4s
}

/* Add Animation */
@-webkit-keyframes animatetop {
    from {top:-300px; opacity:0} 
    to {top:0; opacity:1}
}

@keyframes animatetop {
    from {top:-300px; opacity:0}
    to {top:0; opacity:1}
}

/* The Close Button */
.close {
    color: white;
    float: right;
    font-size: 28px;
    font-weight: bold;
}

.close:hover,
.close:focus {
    color: #000;
    text-decoration: none;
    cursor: pointer;
}

.modal-header {
    padding: 2px 16px;
    background-color: #5cb85c;
    color: white;
}

.modal-body {padding: 2px 16px;}

.modal-footer {
    padding: 2px 16px;
    background-color: #5cb85c;
    color: white;
}






</style> 