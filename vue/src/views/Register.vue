<template>
  <div>
    <div style="width: 50%; float: left">
      <img src="../../public/phone.png"  width="600" height="600" style="text-align: center; margin: 80px">
    </div>

    <el-card style="width: 600px;text-align: right;margin: 40px; overflow: hidden;height: 90vh;background-color: aliceblue">
    <div style="width: 100%; height: 100vh;overflow: hidden">
      <div style="width: 400px;margin: 120px auto">
        <div style="color: black;font-size: 30px;text-align: left;padding-bottom: 30px;font-weight: bolder">注册</div>
        <el-form
                ref="ruleForm"
                :model="ruleForm"
                size="normal"
                :rules="rules"
        >
          <el-form-item label="" prop="username">
            <el-input v-model="ruleForm.username" type="text" autocomplete="off" placeholder="请输入账号"></el-input>
          </el-form-item>
          <el-form-item label="" prop="password">
            <el-input v-model="ruleForm.password" type="password" show-password autocomplete="off" placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item label="" prop="conFirm">
            <el-input v-model="ruleForm.conFirm" type="password" autocomplete="off" placeholder="请确认密码"></el-input>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" style="width: 100%" @click="register">注册</el-button>
          </el-form-item>
          <el-form-item>
            <el-button @click="$router.push('/login')" type="text" style="width: 100%">已有账号?去登陆</el-button>
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

export default {
  name: "Register",
  data(){
    return{
        ruleForm: {
        },
      rules: {
          password:[{validator:validatePass, trigger:'blur',required:true, message:'请输入密码'}],
        conFirm: [{ validator: validatePass2, trigger: 'blur' , required:true, message:'请再次输入密码'}],
      },
    }

    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('Please input the password'))
      } else {
        if (this.ruleForm.conFirm !== '') {
          this.$refs.ruleForm.validateField('conFirm')
        }
        callback()
      }
    }

    const validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('Please input the password again'))
      } else if (value !== this.ruleForm.password) {
        callback(new Error("Two inputs don't match!"))
      } else {
        callback()
      }
    }
  },
  methods: {
    resetForm(){
      this.ruleForm = {}
    },
    register(){
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          if(!this.ruleForm.usertype){
            this.ruleForm.usertype = "user"
          }

          request.post("user/register",this.ruleForm).then(res => {
            if(res.code === '0'){
              ElMessage({
                type:"success",
                message:"注册成功"
              })
              this.$router.push("/login") //登陆成功后跳转
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
  },

}
</script>


<style scoped>

</style>