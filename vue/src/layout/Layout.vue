<template>
  <!--    头部-->
  <Header v-bind:user="user"></Header>
  <!--    主体-->
  <div style="display: flex">
    <!--      侧边栏-->
    <Aside></Aside>
    <!--      内容区-->
    <router-view style="flex: 1" @userInfo="refreshUser"/>
  </div>
</template>

<script>
import Header from "../components/Header";
import Aside from "../components/Aside";
import request from "@/utils/request";

export default {
  components:{
    Header,
    Aside
  },
  name: "Layout",
  data(){
    return{
      user:{}
    }
  },
  created() {
    this.refreshUser()
  },
  methods:{
    refreshUser(){
      let userJson = sessionStorage.getItem("user");
      if(!userJson){
        return
      }
      let userId = JSON.parse(userJson).id;
       //从后台取出更新后的最新用户信息
      request.get("/user/" + userId).then(res => {
        this.user = res.data
      })
    }
  }

}
</script>

<style scoped>

</style>