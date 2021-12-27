<template>
  <div>
    <el-card style="width: 40%; margin: 10px">
      <el-form ref="form" :model="form" label-width="130px"
               :rules="rules">
        <el-form-item style="text-align: center" label-width="0">
        </el-form-item>
        <el-form-item label="发件人" >
          <el-input v-model="form.sender " style="width: 90%" disabled></el-input>
        </el-form-item>
        <el-form-item label="发件人地址" prop="senderAddress">
          <el-input v-model="form.senderAddress " style="width: 90%"></el-input>
        </el-form-item>
        <el-form-item label="发件人联系方式" prop="senderNum">
          <el-input v-model="form.senderNum " style="width: 90%"></el-input>
        </el-form-item>

        <el-form-item label="收件人" prop="receiver">
          <el-input v-model="form.receiver " style="width: 90%"></el-input>
        </el-form-item>
        <el-form-item label="收件人地址" prop="receiverAddress">
          <el-input v-model="form.receiverAddress " style="width: 90%"></el-input>
        </el-form-item>
        <el-form-item label="收件人联系方式" prop="receiverNum">
          <el-input v-model="form.receiverNum " style="width: 90%"></el-input>
        </el-form-item>

        <el-form-item label="货物信息" prop="cargoInfo">
          <el-input v-model="form.cargoInfo " style="width: 90%"></el-input>
        </el-form-item>
        <el-form-item label="货物重量" prop="cargoWeight">
          <el-input v-model="form.cargoWeight " style="width: 90%"></el-input>
        </el-form-item>
      </el-form>
      <div style="text-align: center">
        <el-button type="primary" @click="save">提交</el-button>
      </div>
    </el-card>

  </div>
</template>

<script>
import request from "@/utils/request";
import { ElMessage } from 'element-plus'
export default {
  name: "Deliver",
  data() {
    return {
      form: {},
      rules: {
        senderAddress:[{trigger:'blur',required:true, message:'请输入发件人地址'}],
        senderNum:[{trigger:'blur',required:true, message:'请输入发件人联系方式'}],
        receiver: [{trigger: 'blur' , required:true, message:'请输入收件人'}],
        receiverAddress: [{trigger: 'blur' , required:true, message:'请输入收件人地址'}],
        receiverNum: [{trigger: 'blur' , required:true, message:'请输入收件人联系方式'}],
        cargoWeight: [{trigger: 'blur' , required:true, message:'请输入货物重量'}],
      },
    }
  },
  created() {
    let str = sessionStorage.getItem("user") || "{}"
    this.form.sender = JSON.parse(str).nickName
    this.form.senderNum = JSON.parse(str).number
    this.form.senderId = JSON.parse(str).id
    this.form.senderAddress = JSON.parse(str).address
  },
  methods: {
    save() {
      this.$refs['form'].validate((valid) => {
        if(valid){
        request.post("/transport", this.form).then(res => {
          console.log(res)
          if (res.code === '0') {
            ElMessage(("success", "提交订单成功"))
          } else {
            ElMessage("error", res.msg)
          }
        })
      }


    })


    }
  }
}
</script>

<style>

</style>
