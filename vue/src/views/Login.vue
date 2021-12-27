<template>
  <div>
    <div style="width: 50%; float: left">
      <img src="../../public/van.png" width="600" height="600" style="text-align: center; margin: 80px">
    </div>

    <el-card style="width: 600px;text-align: right;margin: 40px; overflow: hidden;height: 90vh;background-color: aliceblue">
    <div style="width:100%; height: 100vh; color: #409EFF;overflow: hidden;text-align: center">
      <div style="width: 400px;margin: 120px auto;">
        <div style="color: black;font-size: 30px;text-align: left;padding-bottom: 30px;font-weight: bolder">登录</div>
        <el-form
                ref="ruleForm"
                :model="ruleForm"
                size="normal"
                :rules="rules"
        >
          <el-form-item label="" prop="username">
            <el-input
                    v-model="ruleForm.username"
                    type="text"
                    placeholder="请输入账号"
            ></el-input>
          </el-form-item>
          <el-form-item label="" prop="password">
            <el-input
                    v-model="ruleForm.password"
                    type="password"
                    placeholder="请输入密码"
                    autocomplete="off"
                    show-password
            ></el-input>
          </el-form-item>
          <el-form-item>
            <div style="display: flex">
              <el-input prefix-icon="el-icon-key" v-model="ruleForm.validCode" style="width: 50%" placeholder="请输入验证码"></el-input>
              <ValidCode @input="createValidCode"></ValidCode>
            </div>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" style="width: 100%" @click="login">登 录</el-button>
          </el-form-item>
          <el-form-item>
            <el-button @click="$router.push('/register')" type="text" style="width:100%">注册</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
    </el-card>
  </div>
</template>

<script>
import {ElMessage} from "element-plus";
import request from "@/utils/request";
import ValidCode from "@/components/ValidCode";

export default {
  mounted() {
    sessionStorage.removeItem("user")
  },
  components:{
    ValidCode
  },
  name: "Login",
  data(){
    return{
      ruleForm:{},
      rules: {
        password:[{trigger:'blur',required:true, message:'请输入密码'}],
        username: [{trigger: 'blur' , required:true, message:'请输入账号'}],
      },
      validCode:''
    }
  },
  methods: {
    resetForm(){
      this.ruleForm = {}
    },
    createValidCode(data){
      this.validCode = data;
    },
    login(){
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          if(!this.ruleForm.validCode){
            ElMessage({type:"error", message:"请填写验证码"})
            return
          }
          if(this.ruleForm.validCode.toLowerCase() !== this.validCode.toLowerCase()){
            ElMessage({type:"error", message:"验证码错误"})
            return
          }
          request.post("user/login",this.ruleForm).then(res => {
            if(res.code === '0'){
              ElMessage({
                type:"success",
                message:"登陆成功"
              })
              sessionStorage.setItem("user", JSON.stringify(res.data))  // 缓存用户信息
              this.$router.push("/") //登陆成功后跳转
            }
            else{
              ElMessage({
                type:"error",
                message:res.msg
              })
            }
          });
        } else {
          console.log('error submit!!')
          return false
        }
      })



    }
  }
}
</script>

<style scoped>

</style>