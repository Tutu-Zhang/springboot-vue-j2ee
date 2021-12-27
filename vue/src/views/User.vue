<template>
  <div style="padding: 10px" v-show="user.usertype === 'admin'">
    <!--    功能区-->
    <div style="margin: 10px 10px">
      <el-button @click="add" type="primary" style="margin-right: 20px;margin-top: 0px;margin-bottom: 0px">新增</el-button>
    </div>
    <!--    搜索区-->
    <div style="margin: 10px 0;">
      <el-input v-model="search" placeholder="请输入" style="width: 20%" clearable/>
      <el-button type="primary" style="margin-left: 10px" @click="load">查询</el-button>
    </div>
    <el-table :data="tableData" border stripe style="width: 100%">
      <el-table-column prop="id" label="ID" width="200" sortable/>
      <el-table-column prop="username" label="用户名" width="200"/>
      <el-table-column prop="nickName" label="昵称"/>
      <el-table-column prop="age" label="年龄"/>
      <el-table-column prop="sex" label="性别"/>
      <el-table-column prop="address" label="地址"/>
      <el-table-column prop="number" label="联系方式"/>

      <el-table-column fixed="right" label="操作" width="200">
        <template #default="scope">
          <el-button @click="handleEdit(scope.row)" type="info">编辑</el-button>
          <el-popconfirm title="确定删除？" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button type="danger" >删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin: 10px">
      <el-pagination
          v-model:currentPage="currentPage4"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      >
      </el-pagination>


      <el-dialog v-model="dialogVisible" title="新增用户" width="30%">
        <el-form label-width="120px" v-bind:model="form">
          <el-form-item label="用户名">
            <el-input v-model="form.username " style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="昵称">
            <el-input v-model="form.nickName " style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="年龄">
            <el-input v-model="form.age " style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio v-model="form.sex" label="男">男</el-radio>
            <el-radio v-model="form.sex" label="女">女</el-radio>
            <el-radio v-model="form.sex" label="秀吉">秀吉</el-radio>
          </el-form-item>
          <el-form-item label="联系方式">
            <el-input v-model="form.number " style="width: 80%"></el-input>
          </el-form-item>
          <el-form-item label="地址">
            <el-input type="textarea" v-model="form.address " style="width: 80%"></el-input>
          </el-form-item>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save(form)"
        >确定</el-button
        >
      </span>
        </template>
      </el-dialog>
    </div>
  </div>

</template>

<script>

import request from "@/utils/request";
import { ElMessage } from 'element-plus'
export default {
  name: 'User',
  components: {},
  data() {
    return {
      user:{},
      form:{},
      dialogVisible:false,
      search: '',
      currentPage4:1,
      pageSize:10,
      total:10,
      tableData: [
      ]
    }
  },
  created() {
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)
    this.load();
  }
  ,
  methods: {
    load(){
      request.get("/user",{
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
    add(){
      this.dialogVisible = true;
      this.form = {};
    },
    save(){
      if(this.form.id){
            request.put("/user",this.form).then(res => {
          console.log(res);
          if(res.code === '0'){
              ElMessage({
                type:"success",
                message:"修改成功"
              })
            this.load()//刷新数据
            this.dialogVisible=false;
          }
          else{
            ElMessage({
              type:"error",
              message:res.msg
            })
          }
        });

      }
      else{
        request.post("/user",this.form).then(res => {
          console.log(res);
          if(res.code === '0'){
            ElMessage({
              type:"success",
              message:"新增成功"
            })
            this.load()//刷新数据
            this.dialogVisible=false;
          }
          else{
            ElMessage({
              type:"error",
              message:res.msg
            })
          }
        })

      }
      },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogVisible= true;
    },
    handleSizeChange(pageSize){ //改变页面个数触发
      this.pageSize = pageSize
      this.load()
    },
    handleDelete(id){
      request.delete("/user/" + id).then(res =>{
        if(res.code === '0'){
          ElMessage({
            type:"success",
            message:"删除成功"
          })
        }
        else{
          ElMessage({
            type:"error",
            message:res.msg
          })
        }
        this.load()
      })
    }
    ,
    handleCurrentChange(pageNum){ //改变页码触发
      this.currentPage4= pageNum
    this.load()
    },

    isAdmin(){
      if(JSON.parse(sessionStorage.getItem("user")).usertype == "admin")
        return true;
      else
        return false;
    }
  }
}
</script>
