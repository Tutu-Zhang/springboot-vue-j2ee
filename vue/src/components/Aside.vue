<template>
  <div style="background-color: #8c939d;">
    <el-menu
        style="width: 200px; min-height: calc(100vh - 50px);background-color: aliceblue;"
        :default-active="path"
        router
        class="el-menu-vertical-demo"
    >
       <el-menu-item index="/user" v-show="user.usertype === 'admin' ">
          <el-icon><user/></el-icon>
          <span>用户管理</span>
      </el-menu-item>

      <el-menu-item index="/vehicle" v-show="user.usertype === 'admin' ">
        <el-icon><van /></el-icon>
        <span>快递运输车辆管理</span>
      </el-menu-item>

      <el-menu-item index="/employee" v-show="user.usertype === 'admin' ">
        <el-icon><avatar/></el-icon>
        <span>员工管理</span>
      </el-menu-item>

      <el-menu-item index="/transport" v-show="user.usertype === 'admin' ">
        <el-icon><set-up /></el-icon>
        <span>订单管理</span>
      </el-menu-item>

      <el-menu-item index="/receipt">
        <el-icon><shopping-bag /></el-icon>
        <span>我的收货订单</span>
      </el-menu-item>

      <el-menu-item index="/shipment">
        <el-icon><sell /></el-icon>
        <span>我的发货订单</span>
      </el-menu-item>

      <el-menu-item index="/deliver">
        <el-icon><plus/></el-icon>
        <span>提交新订单</span>
      </el-menu-item>



    </el-menu>
  </div>
</template>

<script>
import {Router as router} from "vue-router";
import { Apple, Edit, SemiSelect,Plus,Sell,ShoppingBag,SetUp,Avatar,Location,Van,User } from '@element-plus/icons'

import request from "@/utils/request";

export default {
  name: "Aside",
  components:{Edit,SemiSelect,Plus,Sell,ShoppingBag,SetUp,Avatar,Location,Van,User},
  created() {
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)

    request.get("/user/" + this.user.id).then(res =>{
      if(res.code === '0'){
        this.user = res.data
      }
    })

  },
  data(){
    return{
      user:{},
      path: this.$route.path //设置默认高亮的菜单
    }
  },
  methods:{
    load(){
      request.get("/transport",{
        params:{
          pageNum:this.currentPage4,
          pageSize:this.pageSize,
          search:this.search
        }
      }).then(res=>{
        console.log(res);
        this.tableData = res.data.records;
        this.total = res.data.total;
      })
    },
  }
}
</script>

<style scoped>

</style>