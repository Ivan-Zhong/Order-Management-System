<template>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="120px">
      <el-form-item label="原密码" >
        <el-input v-model="form.oldpw" type="password"/>
      </el-form-item>
      <el-form-item label="新密码" >
        <el-input v-model="form.newpw" type="password"/>
      </el-form-item>
      <el-form-item label="确认新密码" >
        <el-input v-model="form.validnewpw" type="password"/>
      </el-form-item>
      <!-- <el-form-item label="Activity zone">
        <el-select v-model="form.region" placeholder="please select your zone">
          <el-option label="Zone one" value="shanghai" />
          <el-option label="Zone two" value="beijing" />
        </el-select>
      </el-form-item>
      <el-form-item label="Activity time">
        <el-col :span="11">
          <el-date-picker v-model="form.date1" type="date" placeholder="Pick a date" style="width: 100%;" />
        </el-col>
        <el-col :span="2" class="line">-</el-col>
        <el-col :span="11">
          <el-time-picker v-model="form.date2" type="fixed-time" placeholder="Pick a time" style="width: 100%;" />
        </el-col>
      </el-form-item>
      <el-form-item label="Instant delivery">
        <el-switch v-model="form.delivery" />
      </el-form-item>
      <el-form-item label="Activity type">
        <el-checkbox-group v-model="form.type">
          <el-checkbox label="Online activities" name="type" />
          <el-checkbox label="Promotion activities" name="type" />
          <el-checkbox label="Offline activities" name="type" />
          <el-checkbox label="Simple brand exposure" name="type" />
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="Resources">
        <el-radio-group v-model="form.resource">
          <el-radio label="Sponsor" />
          <el-radio label="Venue" />
        </el-radio-group>
      </el-form-item>
      <el-form-item label="Activity form">
        <el-input v-model="form.desc" type="textarea" />
      </el-form-item> -->
      <el-form-item>
        <el-button type="primary" @click="onSubmit">确认修改</el-button>
        <el-button @click="onCancel('form')">清空</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        oldpw: '',
        newpw:'',
        validnewpw: '',
      }
    }
  },
  methods: {
    onSubmit() {
      this.$message({
        message: 'submit!',
        type: 'success'
      })
    },
    onCancel(formname) {
      this.$message({
        message: 'cancel!',
        type: 'warning'
      }),
      this.$refs[formname].resetFields()
    }
  }
}
</script>

<style scoped>
.line{
  text-align: center;
}
</style>

