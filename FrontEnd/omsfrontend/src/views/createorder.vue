<template>
  <div>
    <Sidebar></Sidebar>
    <br />
    <h1>创建新订单</h1>
    <br />
    <form>
      客户名：
      <!-- <select v-model="clientname" @change="selectClass($event)">
                <option :value="client.name" v-for="client in clients" :key="client.id" >{{client.name}}</option>
            </select> -->
      <el-select
        v-model="clientname"
        placeholder="请选择客户"
        @change="selectClass($event)"
      >
        <el-option
          v-for="client in clients"
          :key="client.id"
          :value="client.name"
          :label="client.name"
        />
      </el-select>
      <br />
      <br />
      <br />
      订单描述<br /><br />
      <el-input
        type="textarea"
        :rows="4"
        style="width: 30%"
        placeholder="请输入订单描述"
        v-model="description"
      >
      </el-input>
      <br />
      <br />
      <el-button type="primary" @click="createorder()">提交</el-button>
    </form>
  </div>
</template>

<script>
import Sidebar from "../components/sidebar";
import axios from "axios";
export default {
  name: "createorder",
  data() {
    return {
      clients: [],
      clientname: "",
      description: "",
    };
  },
  created: function () {
    axios.get("/api/client/read/all").then((response) => {
      if (response.data.message == "success") {
        this.clients = response.data.data;
      }
    });
  },

  methods: {
    createorder() {
      let fd = new FormData();
      fd.append("description", this.description);
      fd.append("clientname", this.clientname);
      axios.post("/api/order/create/add", fd).then((response) => {
        if (response.data.message == "success") {
          this.$router.push("/order");
          this.$message({
            type: "success",
            message: "添加成功!",
          });
        }
      });
    },

    selectClass(event) {
      this.clientname = event.target.value;
    },
  },
  components: {
    Sidebar,
  },
};
</script>

<style>
</style> 