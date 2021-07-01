<template>
  <div>
    <Sidebar></Sidebar>
    <br />
    <h1>订单信息</h1>
    <div v-if="handler">
      <form>
        客户名：
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
        <br />``
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
        <el-button type="primary" @click="editOrderCreate()">提交</el-button>
      </form>
    </div>

    <div v-if="measurer">
      <form>
        长度：<el-input v-model="length" placeholder="length" style="width:20%"></el-input>
        <br />
        <br />
        宽度：<el-input v-model="width" placeholder="width" style="width:20%"></el-input>
        <br />
        <br />
        高度：<el-input v-model="height" placeholder="height" style="width:20%"></el-input>
        <br />
        <br />
        数量：<el-input v-model="number" placeholder="number" style="width:20%"></el-input>
        <br />
        <br />
        <el-button type="primary" @click="editOrderMeasure()">提交</el-button>
      </form>
    </div>

    <div v-if="designer">
      <form>
        材质：<el-input v-model="material" placeholder="material" style="width:20%"></el-input>
        <br />
        <br />

        //上传图片待写

        <el-button type="primary" @click="editOrderDesign()">提交</el-button>
      </form>
    </div>

    <div v-if="pricer">
      <form>
        报价：<el-input v-model="price" placeholder="price" style="width:20%"></el-input>
        <br />
        <br />
        <el-button type="primary" @click="editOrderPrice()">提交</el-button>
      </form>
    </div>
  </div>
</template>

<script>
import Sidebar from "../components/sidebar";
import axios from "axios";
export default {
  name: "orderinfo",
  data() {
    return {
      identity: "",
      handler: false,
      measurer: false,
      designer: false,
      pricer: false,
      clientname: "",
      description: "",
      length: 0,
      width: 0,
      height: 0,
      number: 0,
      material: "",
      price: 0,
      //图片变量待写
    };
  },
  created: function () {
    axios.get("/api/person/read/own").then((response) => {
      if (response.data.message == "success") {
        this.identity = response.data.data.identity;
        this.id = this.$route.query.id;
        //root页面待添加，应开放所有信息编辑权限
        if (this.identity == "handler") {
          handler = true;
          axios
            .get(`/api/order/create/read/one/${this.id}`)
            .then((response) => {
              if (response.data.message == "success") {
                this.clientname = response.data.data.clientname;
                this.description = response.data.data.description;
              }
            });
        }
        if (this.identity == "measurer") {
          measurer = true;
          axios
            .get(`/api/order/measure/read/one/${this.id}`)
            .then((response) => {
              if (response.data.message == "success") {
                this.length = response.data.data.length;
                this.width = response.data.data.width;
                this.height = response.data.data.height;
                this.number = response.data.data.number;
              }
            });
        }
        if (this.identity == "designer") {
          designer = true;
          axios
            .get(`/api/order/design/read/one/${this.id}`)
            .then((response) => {
              if (response.data.message == "success") {
                this.material = response.data.data.material;
                //上传图片待写
              }
            });
        }
        if (this.identity == "pricer") {
          pricer = true;
          axios.get(`/api/order/price/read/one/${this.id}`).then((response) => {
            if (response.data.message == "success") {
              this.price = response.data.data.price;
            }
          });
        }
      }
    });
  },

  methods: {
    selectClass(event) {
      this.clientname = event.target.value;
    },

    editOrderCreate() {
      let fd = new FormData();
      fd.append("clientname", this.clientname);
      fd.append("description", this.description);
      axios.post(`/api/order/create/update/${this.id}`, fd).then((response) => {
        if (response.data.message == "success") {
          this.$router.push("/order");
          this.$message({
            type: "success",
            message: "修改成功!",
          });
        }
      });
    },

    editOrderMeasure() {
      let fd = new FormData();
      fd.append("length", this.length);
      fd.append("width", this.width);
      fd.append("height", this.height);
      fd.append("number", this.number);
      axios
        .post(`/api/order/measure/update/${this.id}`, fd)
        .then((response) => {
          if (response.data.message == "success") {
            this.$router.push("/order");
            this.$message({
              type: "success",
              message: "修改成功!",
            });
          }
        });
    },

    editOrderDesign() {
      let fd = new FormData();
      fd.append("material", this.material);
      //上传图片待写
      axios.post(`/api/order/design/update/${this.id}`, fd).then((response) => {
        if (response.data.message == "success") {
          this.$router.push("/order");
          this.$message({
            type: "success",
            message: "修改成功!",
          });
        }
      });
    },

    editOrderPrice() {
      let fd = new FormData();
      fd.append("price", this.price);
      axios.post(`/api/order/price/update/${this.id}`, fd).then((response) => {
        if (response.data.message == "success") {
          this.$router.push("/order");
          this.$message({
            type: "success",
            message: "修改成功!",
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

<style>
</style> 