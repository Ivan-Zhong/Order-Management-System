<template>
  <div>
    <Sidebar></Sidebar>
    <div style="margin-left: 200px; margin-right: 10px">
      <br />
      <h1>订单管理</h1>
      <el-button type="primary" @click="createOrder()" v-if="showcre"
        >创建新订单</el-button
      >
      <br /><br />
      <!-- <table style="margin: 0px auto">
        <tr>
          <th>编号</th>
          <th>客户</th>
          <th>订单描述</th>
          <th>长度</th>
          <th>宽度</th>
          <th>高度</th>
          <th>数量</th>
          <th>材质</th>
          <th>报价</th>
          <th>样板图</th>
          <th>订单状态</th>
          <th>操作</th>
        </tr>
        <tr v-for="(order, index) in orders" :key="order.id">
          <td>{{ index + 1 }}</td>
          <td>{{ order.clientname }}</td>
           <td>{{ order.title }}</td>
          <td>{{ order.description }}</td>
          <td>{{ order.length }}</td>
          <td>{{ order.width }}</td>
          <td>{{ order.height }}</td>
          <td>{{ order.number }}</td>
          <td>{{ order.material }}</td>
          <td>{{ order.price }}</td>
          <td><a href="#"></a>查看图片</td>
          <td>{{ order.status }}</td>
          <td>
            <button>
              <router-link
                :to="{ path: '/createorder', query: { id: order.id } }"
                v-if="showedit"
                >编辑</router-link
              >
            </button>
            <button @click="deleteOrder(order.id)" v-if="showdel">删除</button>

            <button @click="AcceptOrder(order.id)" v-if="showacc">接受</button>
                    <button @click="RejectOrder(order.id)" v-if="showrej">拒绝</button>
                    <button @click="ProduceOrder(order.id)" v-if="showpro">生产完成</button>
                    <button @click="InstallOrder(order.id)" v-if="showins">安装完成</button>
                    <button @click="FinishOrder(order.id)" v-if="showfin">结束订单</button>
          </td>
        </tr>
      </table> -->

      <el-table
        :data="orders"
        stripe
        height="605"
        style="width: 100%; margin-right: 20px"
      >
        <el-table-column type="expand">
          <template #default="scope">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="订单描述">
                <span>{{ scope.row.description }}</span>
              </el-form-item>
              <br />
              <br />
              <br />
              <el-form-item label="样板图片">
                <span>
                  <img
                    :src="'/images/' +  scope.row.imagename "
                    style="height: 500px"
                  />
                  <br />
                  <el-button type="primary"
                    ><a
                      :href="'/api/order/image/download/' +scope.row.imagename"
                      style="
                        font-weight: normal;
                        color: white;
                        text-decoration: none;
                      "
                      >下载图片</a
                    ></el-button
                  >
                  <br />
                </span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column type="index" :index="indexMethod"></el-table-column>
        <el-table-column label="客户名" prop="clientname"> </el-table-column>
        <el-table-column label="订单名" prop="title"> </el-table-column>
        <el-table-column label="长度" prop="length"
          ><template #default="scope">
            <span v-if="scope.row.length != 0">{{ scope.row.length }}</span>
          </template>
        </el-table-column>
        <el-table-column label="宽度" prop="width"
          ><template #default="scope">
            <span v-if="scope.row.width != 0">{{ scope.row.width }}</span>
          </template>
        </el-table-column>
        <el-table-column label="高度" prop="height">
          <template #default="scope">
            <span v-if="scope.row.height != 0">{{ scope.row.height }}</span>
          </template></el-table-column
        >
        <el-table-column label="数量" prop="number">
          <template #default="scope">
            <span v-if="scope.row.number != 0">{{ scope.row.number }}</span>
          </template></el-table-column
        >
        <el-table-column label="材质" prop="material"> </el-table-column>
        <el-table-column label="定价" prop="price">
          <template #default="scope">
            <span v-if="scope.row.price != 0">{{ scope.row.price }}</span>
          </template></el-table-column
        >
        <el-table-column label="订单状态" prop="status">
          　　　<template #default="scope">
            <el-tag type="success" v-if="scope.row.status === 'created'"
              >已创建</el-tag
            >
            <el-tag type="success" v-if="scope.row.status === 'measured'"
              >已测量</el-tag
            >
            <el-tag type="success" v-if="scope.row.status === 'designed'"
              >设计完毕</el-tag
            >
            <el-tag type="success" v-if="scope.row.status === 'priced'"
              >已定价</el-tag
            >
            <el-tag type="success" v-if="scope.row.status === 'accepted'"
              >客户接受</el-tag
            >
            <el-tag type="warning" v-if="scope.row.status === 'rejected'"
              >客户拒绝</el-tag
            >
            <el-tag type="success" v-if="scope.row.status === 'produced'"
              >生产完毕</el-tag
            >
            <el-tag type="success" v-if="scope.row.status === 'installed'"
              >安装完毕</el-tag
            >
            <el-tag type="success" v-if="scope.row.status === 'finished'"
              >订单结束</el-tag
            >
            　　　　　　</template
          >
        </el-table-column>

        <el-table-column
          fixed="right"
          label="操作"
          style="white-space: pre-wrap"
        >
          <template #default="scope">
            <div>
              <el-button
                size="mini"
                icon="el-icon-edit"
                type="primary"
                @click="modifyOrder(scope.row.id)"
                v-if="
                  (ishandler && scope.row.status === 'created') ||
                  (ismeasurer && scope.row.status === 'measured') ||
                  (isdesigner && scope.row.status === 'designed') ||
                  (ispricer && scope.row.status === 'priced')
                "
                >编辑</el-button
              >
              <el-button
                size="mini"
                icon="el-icon-edit-outline"
                type="warning"
                @click="processOrder(scope.row.id)"
                v-if="
                  (ismeasurer && scope.row.status === 'created') ||
                  (isdesigner && scope.row.status === 'measured') ||
                  (ispricer && scope.row.status === 'designed')
                "
                >处理</el-button
              >

              <el-button
                size="mini"
                icon="el-icon-delete"
                type="danger"
                @click="deleteOrder(scope.row.id)"
                v-if="showdel"
                >删除</el-button
              >

              <div v-if="showacc">
                <el-button
                  size="mini"
                  icon="el-icon-check"
                  type="success"
                  @click="AcceptOrder(scope.row.id)"
                  >接受</el-button
                >
                <br /><br />
                <el-button
                  size="mini"
                  icon="el-icon-close"
                  type="danger"
                  @click="RejectOrder(scope.row.id)"
                  >拒绝</el-button
                >
              </div>
              <el-button
                size="mini"
                icon="el-icon-check"
                type="success"
                @click="ProduceOrder(scope.row.id)"
                v-if="showpro"
                >完成生产</el-button
              >

              <el-button
                size="mini"
                icon="el-icon-check"
                type="success"
                @click="InstallOrder(scope.row.id)"
                v-if="showins"
                >完成安装</el-button
              >

              <el-button
                size="mini"
                icon="el-icon-check"
                type="success"
                @click="FinishOrder(scope.row.id)"
                v-if="showfin"
                >结束订单</el-button
              >
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import Sidebar from "../components/sidebar";
import axios from "axios";
export default {
  name: "order",
  data() {
    return {
      id: 0,
      orders: [],
      identity: "",
      showdel: false,
      showcre: false,
      showedit: false,
      showprocess: false,
      showacc: false,
      showpro: false,
      showins: false,
      showfin: false,
      isroot: false,
      ishandler: false,
      ismeasurer: false,
      isdesigner: false,
      ispricer: false,
      imagename: "",
    };
  },
  created: function () {
    axios.get("/api/person/read/own").then((response) => {
      if (response.data.message == "success") {
        this.identity = response.data.data.identity;
        if (this.identity == "root") {
          axios
            .get("/api/order/root/read/all") //待确认
            .then((response) => {
              if (response.data.message == "success") {
                this.orders = response.data.data;
              }
            });
          this.isroot = true;
          this.showedit = true;
          this.showdel = true;
          this.showcre = true;
          this.showprocess = true;
        }
        if (this.identity == "handler") {
          axios.get("/api/order/create/read/all").then((response) => {
            if (response.data.message == "success") {
              this.orders = response.data.data;
            }
          });
          this.showcre = true;
          this.showedit = true;
          this.ishandler = true;
        }
        if (this.identity == "measurer") {
          axios.get("/api/order/measure/read/all").then((response) => {
            if (response.data.message == "success") {
              this.orders = response.data.data;
            }
          });
          this.showedit = true;
          this.showprocess = true;
          this.ismeasurer = true;
        }
        if (this.identity == "designer") {
          axios.get("/api/order/design/read/all").then((response) => {
            if (response.data.message == "success") {
              this.orders = response.data.data;
            }
          });
          this.showedit = true;
          this.showprocess = true;
          this.isdesigner = true;
        }
        if (this.identity == "pricer") {
          axios.get("/api/order/price/read/all").then((response) => {
            if (response.data.message == "success") {
              this.orders = response.data.data;
            }
          });
          this.showedit = true;
          this.showprocess = true;
          this.ispricer = true;
        }
        if (this.identity == "clienter") {
          axios.get("/api/order/consider/read/all").then((response) => {
            if (response.data.message == "success") {
              this.orders = response.data.data;
            }
          });
          this.showacc = true;
        }
        if (this.identity == "factory") {
          axios.get("/api/order/produce/read/all").then((response) => {
            if (response.data.message == "success") {
              this.orders = response.data.data;
            }
          });
          this.showpro = true;
        }
        if (this.identity == "installer") {
          axios.get("/api/order/install/read/all").then((response) => {
            if (response.data.message == "success") {
              this.orders = response.data.data;
            }
          });
          this.showins = true;
        }
        if (this.identity == "finisher") {
          axios.get("/api/order/finish/read/all").then((response) => {
            if (response.data.message == "success") {
              this.orders = response.data.data;
            }
          });
          this.showfin = true;
        }
      }
    });
  },

  methods: {
    // getImage(ids) {
    //   thi.id = ids;
    //   axios.get(`/api/order/read/one/${this.id}`).then((response) => {
    //     if (response.data.message == "success") {
    //       this.imagename = response.data.data.imagename;
    //     }
    //   });
    // },

        ImageDownload() {
      axios
      .request({
    url:`/api/order/image/download/${this.imagename}`,
    method: 'get',
    responseType:'blob', // 注意这里！！！
  })
        // .get(`/api/order/image/download/${this.imagename}`)
        .then((response) => {
            console.log("hello")
            this.$message({
              type: "success",
              message: "图片下载成功!",
            });
        });
    },

    createOrder() {
      this.$router.push({
        path: "/createorder",
      });
    },

    modifyOrder(ids) {
      this.id = ids;
      this.$router.push({
        path: "/orderinfo",
        query: { id: this.id },
      });
    },

    processOrder(ids) {
      this.id = ids;
      this.$router.push({
        path: "/orderprocess",
        query: { id: this.id },
      });
    },

    deleteOrder(ids) {
      this.id = ids;

      this.$confirm("确定要删除该订单吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          axios.get(`/api/order/delete/${this.id}`).then((response) => {
            if (response.data.message == "success") {
              this.$message({
                type: "success",
                message: "删除成功!",
              });
              axios.get("/api/order/root/read/all").then((response) => {
                if (response.data.message == "success") {
                  this.orders = response.data.data;
                }
              });
              this.$router.replace("/order");
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },

    AcceptOrder(ids) {
      this.id = ids;

      this.$confirm("确定要接受该订单吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          axios
            .get(`/api/order/consider/update/accept/${this.id}`)
            .then((response) => {
              if (response.data.message == "success") {
                this.$message({
                  type: "success",
                  message: "已接受!",
                });
                axios.get("/api/order/consider/read/all").then((response) => {
                  if (response.data.message == "success") {
                    this.orders = response.data.data;
                  }
                });
                this.$router.replace("/order");
              }
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消",
          });
        });
    },

    RejectOrder(ids) {
      this.id = ids;

      this.$confirm("确定要拒绝该订单吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          axios
            .get(`/api/order/consider/update/reject/${this.id}`)
            .then((response) => {
              if (response.data.message == "success") {
                this.$message({
                  type: "success",
                  message: "已拒绝!",
                });
                axios.get("/api/order/consider/read/all").then((response) => {
                  if (response.data.message == "success") {
                    this.orders = response.data.data;
                  }
                });
                this.$router.replace("/order");
              }
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消",
          });
        });
    },

    ProduceOrder(ids) {
      this.id = ids;

      this.$confirm("确认已生产完毕吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          axios.get(`/api/order/produce/update/${this.id}`).then((response) => {
            if (response.data.message == "success") {
              this.$message({
                type: "success",
                message: "已确认生产完毕!",
              });
              axios.get("/api/order/produce/read/all").then((response) => {
                if (response.data.message == "success") {
                  this.orders = response.data.data;
                }
              });
              this.$router.replace("/order");
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消",
          });
        });
    },

    InstallOrder(ids) {
      this.id = ids;

      this.$confirm("确认已安装完毕吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          axios.get(`/api/order/install/update/${this.id}`).then((response) => {
            if (response.data.message == "success") {
              this.$message({
                type: "success",
                message: "已确认安装完毕!",
              });
              axios.get("/api/order/install/read/all").then((response) => {
                if (response.data.message == "success") {
                  this.orders = response.data.data;
                }
              });
              this.$router.replace("/order");
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消",
          });
        });
    },

    FinishOrder(ids) {
      this.id = ids;

      this.$confirm("确认要结束订单吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          axios
            .get(`/api/order/finish/update/${this.id}`) //api待修改
            .then((response) => {
              if (response.data.message == "success") {
                this.$message({
                  type: "success",
                  message: "已结束订单!",
                });
                axios.get("/api/order/finish/read/all").then((response) => {
                  if (response.data.message == "success") {
                    this.orders = response.data.data;
                  }
                });
                this.$router.replace("/order");
              }
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消",
          });
        });
    },
  },
  components: {
    Sidebar,
  },
};
</script>

<style>
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
  font-weight: bold;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>