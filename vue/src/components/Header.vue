<template>
  <div style="height : 50px; line-height: 50px ;border-bottom: 1px solid #ccc; display: flex;background-color: ghostwhite">
    <div style="width: 200px;padding-left: 30px;font-weight: bold; color: royalblue">懒狗物流管理</div>
    <div style="flex: 1px;"></div>
    <div style="width: 100px;margin-right: 30px">
      <el-dropdown>
        <el-button type="primary">
          {{ user.nickName }}<el-icon class="el-icon--right"><arrow-down /></el-icon>
        </el-button>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="$router.push('/person')">个人信息</el-dropdown-item>
            <el-dropdown-item @click="$router.push('/login')">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import ArrowDown from "@element-plus/icons/lib/ArrowDown";

 export  default {
   data(){
     return{
     }
   },
   name:"Header",
    components:{ArrowDown,},
   props: ['user'],
 }
</script>