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
      <el-table-column prop="id" label="车辆ID" width="200" sortable/>
      <el-table-column prop="driverId" label="司机ID" width="200"/>
      <el-table-column prop="vehicleInfo" label="车辆信息"/>


      <el-table-column fixed="right" label="操作" width="400">
        <template #default="scope">
          <el-button @click="handleEdit(scope.row)" type="info">编辑车辆信息</el-button>
          <el-button @click="allocateDriver(scope.row)" type="primary">分配司机</el-button>
          <el-button @click="seeOrder(scope.row.id)" type="warning">查看订单</el-button>
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

      <el-dialog v-model="dialogVisible" title="新增车辆" width="30%">
        <el-form label-width="120px" v-bind:model="form">
          <el-form-item label="车辆信息">
            <el-input v-model="form.vehicleInfo " style="width: 80%"></el-input>
          </el-form-item>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save(form)">确定</el-button>
      </span>
        </template>
      </el-dialog>

      <el-dialog v-model="dialogVisible2" title="编辑车辆信息" width="30%">
        <el-form label-width="120px" v-bind:model="form">
          <el-form-item label="车辆信息">
            <el-input v-model="form.vehicleInfo " style="width: 80%"></el-input>
          </el-form-item>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible2 = false">取消</el-button>
        <el-button type="primary" @click="save(form)">确定</el-button>
      </span>
        </template>
      </el-dialog>

      <el-dialog v-model="dialogVisible1" title="司机分配" width="40%">
            <el-table :data="driverData" border style="width: 100%">
              <el-table-column prop="id" label="司机ID" width="200"/>
              <el-table-column prop="employeeName" label="司机名字" width="200"/>
              <el-table-column prop="situation" label="司机状态"/>


              <el-table-column fixed="right" label="操作" width="200">
                <template #default="scope">
                  <el-button @click="confirmDriver(scope.row)" type="primary" v-show="confirmAvailable(scope.row)">选定</el-button>
                  <el-button @click="cancelDriver(scope.row)" type="danger" v-show="cancelAvailable(scope.row)">取消</el-button>
                </template>
              </el-table-column>
            </el-table>

      </el-dialog>

      <el-dialog v-model="dialogVisible3" title="订单查看" width="30%">
        <el-table :data="transportData" border style="width: 100%">
          <el-table-column prop="transportId" label="订单号"/>
        </el-table>

      </el-dialog>
    </div>
  </div>

</template>

<script>

import request from "@/utils/request";
import { ElMessage } from 'element-plus'
import Employee from "@/views/Employee";
export default {
  name: 'Vehicle',
  components: {},
  data() {
    return {
      form:{},
      user:{},
      driverForm:{},
      dialogVisible:false,
      dialogVisible1:false,
      dialogVisible2:false,
      dialogVisible3:false,
      search: '',
      currentPage4:1,
      pageSize:10,
      total:10,
      tableData: [
      ],
      driverData:[],
      transportData:[]
    }
  },
  created() {
    this.load();
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)
  },

  methods: {
    load(){
      request.get("/vehicle",{
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

      request.get("/employee/driver","司机"
      ).then(res=>{
        console.log(res);
        this.driverData = res.data.records;
      })
    },
    add(){
      this.dialogVisible = true;
      this.form = {};
    },
    driverSave(){
      request.put("/employee",this.driverForm).then(res => {
        console.log(res);
        if(res.code === '0'){
          ElMessage({
            type:"success",
            message:"司机修改成功"
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
    },
    save(){
      if(this.form.id){
            request.put("/vehicle",this.form).then(res => {
          console.log(res);
          if(res.code === '0'){
              ElMessage({
                type:"success",
                message:"车辆信息修改成功"
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
        request.post("/vehicle",this.form).then(res => {
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
      this.dialogVisible2= true;
    },
    allocateDriver(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogVisible1= true;
    },
    seeOrder(id){
      request.get("/vehicleTransport/" + id
      ).then(res=>{
        console.log(res);
        this.transportData = res.data.records;
      })
      this.dialogVisible3 = true;
    },
    confirmDriver(row){
      this.form.driverId = row.id;
      this.save();
      this.driverForm = row;
      this.driverForm.situation = "已分配";
      this.driverSave();
    },
    cancelDriver(row){
      this.form.driverId = 0;
      this.save();
      this.driverForm = row;
      this.driverForm.situation = "空闲";
      this.driverSave();
    },
    confirmAvailable(row){
      if(row.situation == "空闲" && this.form.driverId == 0){
        return true;
      }
      else
        return false;
    },
    cancelAvailable(row){
      if(row.situation == "已分配"&& this.form.driverId == row.id){
        return true;
      }
      else
        return false;
    },
    handleSizeChange(pageSize){ //改变页面个数触发
      this.pageSize = pageSize
      this.load()
    },
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
