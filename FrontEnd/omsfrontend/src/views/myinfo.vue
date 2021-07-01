<template>
  <div>
    <Sidebar></Sidebar>
    <br />
    <h1>个人信息</h1>
    <!-- <form @submit.prevent="modifyMyInfo"> -->
    <form>
      姓名：<el-input
        v-model="name"
        placeholder="name"
        style="width: 20%"
      ></el-input>
      <br />
      <br />
      电话：<el-input
        v-model="tel"
        placeholder="tel"
        style="width: 20%"
      ></el-input>
      <br />
      <br />
      电子邮箱：<el-input
        v-model="email"
        placeholder="email"
        style="width: 20%"
      ></el-input>
      <br />
      <br />
      密码：<el-input
        v-model="password"
        placeholder="password"
        style="width: 20%"
        show-password
      ></el-input>
      <br />
      <br />
      身份：&nbsp;&nbsp;
      <el-tag v-if="identity === 'root'">公司管理员</el-tag>
      <el-tag v-if="identity === 'handler'">接单人员</el-tag>
      <el-tag v-if="identity === 'measurer'">测量人员</el-tag>
      <el-tag v-if="identity === 'designer'">设计人员</el-tag>
      <el-tag v-if="identity === 'pricer'">报价人员</el-tag>
      <el-tag v-if="identity === 'factory'">工厂人员</el-tag>
      <el-tag v-if="identity === 'installer'">安装人员</el-tag>
      <el-tag v-if="identity === 'finisher'">结案人员</el-tag>
      <el-tag v-if="identity === 'clienter'">客户人员</el-tag>
      <br />
      <br />
      <el-button type="primary" @click="modifyMyInfo()">提交修改</el-button>
      <!-- <input type="button" @click="modifyMyInfo()" value="提交更改"> -->
      <br />
      <br />
      <!-- <button>提交更改</button>&nbsp;&nbsp;&nbsp; -->
    </form>

    <el-button type="danger" @click="logout()">注销</el-button>
  </div>
</template>

<script>
import Sidebar from "../components/sidebar";
import axios from "axios";
export default {
  name: "myinfo",
  data() {
    return {
      name: "",
      tel: "",
      email: "",
      password: "",
      identity: "",
    };
  },
  created: function () {
    axios.get("/api/person/read/own").then((response) => {
      if (response.data.message == "success") {
        this.name = response.data.data.name;
        this.tel = response.data.data.tel;
        this.email = response.data.data.email;
        this.password = response.data.data.password;
        this.identity = response.data.data.identity;
      }
    });
  },
  methods: {
    logout() {
      this.$confirm("确定要退出登录吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          axios.get("/api/person/logout").then((response) => {
            if (response.data.message == "success") {
              this.$router.push("/");
              this.$message({
                type: "success",
                message: "注销成功!",
              });
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消注销",
          });
        });
    },
    modifyMyInfo() {
      let fd = new FormData();
      fd.append("name", this.name);
      fd.append("tel", this.tel);
      fd.append("email", this.email);
      fd.append("password", this.password);
      axios.post("/api/person/update/own", fd).then((response) => {
        if (response.data.message == "success") {
          this.$router.push("/home");
          this.$message({
            type: "success",
            message: "修改成功!",
          });
        } else {
          this.$message({
            type: "error",
            message: "修改失败，用户名重复！",
          });
        }
      });
    },
  },

  components: {
    Sidebar,
  },
};
</script>

<style lang="scss" scoped>
</style> 