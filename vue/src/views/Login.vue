<template>
  <div>
    <div style="width: 50%; float: left">
      <img src="https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg1.juimg.com%2F170323%2F330747-1F32322432787.jpg&refer=http%3A%2F%2Fimg1.juimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642666109&t=21001a790900150676098c3d83af2533" width="800" height="800" >
    </div>

    <div style="width:50%; height: 100vh;background-color: white ;overflow: hidden;float:right">
      <div style="width: 400px;margin: 120px auto">
        <div style="color: black;font-size: 30px;text-align: left;padding-bottom: 30px;font-weight: bolder">登陆</div>
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
            <el-button @click="$router.push('/register')" type="text" style="width: 0%">注册</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
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