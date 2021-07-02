<template>
  <div>
    <Sidebar></Sidebar>
    <br />
    <h1>订单处理</h1>
    <br /><br />
    <div v-if="measurer">
      <form>
        长度：<el-input
          v-model="length"
          placeholder="length"
          style="width: 20%"
        ></el-input>
        <br />
        <br />
        宽度：<el-input
          v-model="width"
          placeholder="width"
          style="width: 20%"
        ></el-input>
        <br />
        <br />
        高度：<el-input
          v-model="height"
          placeholder="height"
          style="width: 20%"
        ></el-input>
        <br />
        <br />
        数量：<el-input
          v-model="number"
          placeholder="number"
          style="width: 20%"
        ></el-input>
        <br />
        <br />
        <el-button type="primary" @click="editOrderMeasure()">提交</el-button>
      </form>
    </div>

    <div v-if="designer">
      <form action="" enctype="multipart/form-data" id="upload">
        材质：<el-input
          v-model="material"
          placeholder="material"
          style="width: 20%"
        ></el-input>
        <br />
        <br />
        <br />

        样板图片：
        <br />
        <br />
        <!-- <el-upload
  class="upload-demo"
  ref="upload"
  :before-upload="beforeUpload"
  action
  :limit="1"
  :on-change="fileChange"
  :auto-upload="false"
  :file-list="fileList">
  <el-button type="primary">点击上传</el-button>
  <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
</el-upload> -->
        <input  type="file" ref="fileId" @change="getFile" />
        <!-- <button @click="this.$refs.fileId.click()">Pick File</button> -->
        <br />


        <br />
        <br />
        <br />
        <el-button type="primary" @click="editOrderDesign()">提交</el-button>
      </form>
    </div>

    <div v-if="pricer">
      <form>
        报价：<el-input
          v-model="price"
          placeholder="price"
          style="width: 20%"
        ></el-input>
        <br />
        <br />
        <el-button
          type="primary"
          @click="editOrderPrice()"
          >提交</el-button
        >
      </form>
    </div>
  </div>
</template>

<script>
import Sidebar from "../components/sidebar";
import axios from "axios";
export default {
  name: "orderprocess",
  data() {
    return {
      status: "",
      handler: false,
      measurer: false,
      designer: false,
      pricer: false,
      length: 0,
      width: 0,
      height: 0,
      number: 0,
      material: "",
      price: 0,
      id: 0,
      xlsxFile: "",
      fileList: [],
      board_list: [],
      //图片变量待写
    };
  },
  created: function () {
    this.id = this.$route.query.id;
    axios.get(`/api/order/getStatus/${this.id}`).then((response) => {
      if (response.data.message == "success") {
        this.status = response.data.data;
        console.log(this.status);

        //root页面待添加，应开放所有信息编辑权限
        if (this.status == "created") {
          this.measurer = true;
        }
        if (this.status == "measured") {
          this.designer = true;
        }
        if (this.status == "designed") {
          this.pricer = true;
        }
      }
    });
  },

  methods: {
    selectClass(event) {
      this.clientname = event.target.value;
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
              message: "提交成功!",
            });
          }
        })
        .catch(() => {
          this.$message({
            type: "error",
            message: "输入的数据格式错误，请重试！",
          });
        });
    },

    //  async fileChange(file) {
    //       console.log("fileChange", file);
    //       let formData = new FormData();
    //     //    注意需要上传的是file.raw这个真实文件
    //       formData.append("file", file.raw);
    //     //    设置上传数据类型
    //       let configs = {
    //         headers: { "Content-Type": "multipart/form-data" }
    //       };
    //       this.board_list = await axios.post(`/api/order/design/update/${this.id}`, formData, configs);
    //       this.$refs["upload"].clearFiles();
    //       console.log(this.board_list);
    //     },

    getFile() {
      //获取file内容
      let files = this.$refs.fileId.files[0];
      this.xlsxFile = files;
    },

    editOrderDesign() {
              if (this.xlsxFile == "") {
        this.$message.error('请先添加文件');
        return;
      }
      let fd = new FormData();

      fd.append("material", this.material);
      //     this.form.files = this.fileList[0].url;
      //   console.log(this.fileList[0].url);

      //     fd.append("image",this.form.files);
fd.append("image", this.xlsxFile);
      // //上传图片待写
      axios.post(`/api/order/design/update/${this.id}`, fd).then((response) => {
        if (response.data.message == "success") {
          this.$router.push("/order");
          this.$message({
            type: "success",
            message: "提交成功!",
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
            message: "提交成功!",
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

<style scoped>

  </style>
