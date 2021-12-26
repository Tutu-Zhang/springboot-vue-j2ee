
  <template>
    <div style="padding: 10px"  v-show="user.usertype === 'admin' ">
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
        <el-table-column prop="id" label="物流单号" width="50" sortable/>
        <el-table-column prop="sender" label="发件人"/>
        <el-table-column prop="senderId" label="发件人id"/>
        <el-table-column prop="senderAddress" label="发件人地址"/>
        <el-table-column prop="senderNum" label="发件人联系方式"/>
        <el-table-column prop="receiver" label="收件人"/>
        <el-table-column prop="receiverId" label="收件人id"/>
        <el-table-column prop="receiverAddress" label="收件人地址"/>
        <el-table-column prop="receiverNum" label="收件人联系方式"/>
        <el-table-column prop="cargoInfo" label="货物信息"/>
        <el-table-column prop="cargoWeight" label="货物重量"/>
        <el-table-column prop="cargoSituation" label="货物状态"/>
        <el-table-column prop="cargoPosition" label="货物位置"/>
        <el-table-column prop="vehicleInfo" label="运输车辆"/>

        <el-table-column fixed="right" label="操作" width="300">
          <template #default="scope">
            <el-button @click="handleChoose(scope.row)" type="primary">选择运车</el-button>
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

        <el-dialog v-model="dialogVisible" title="新增物流单" width="30%">
          <el-form label-width="120px" v-bind:model="form">

            <el-form-item label="发件人">
              <el-input v-model="form.sender " style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="发件人id">
              <el-input v-model="form.senderId " style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="发件人地址">
              <el-input v-model="form.senderAddress " style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="发件人联系方式">
              <el-input v-model="form.senderNum " style="width: 80%"></el-input>
            </el-form-item>

            <el-form-item label="收件人">
              <el-input v-model="form.receiver " style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="收件人id">
              <el-input v-model="form.receiverId " style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="收件人地址">
              <el-input v-model="form.receiverAddress " style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="收件人联系方式">
              <el-input v-model="form.receiverNum " style="width: 80%"></el-input>
            </el-form-item>

            <el-form-item label="货物信息">
              <el-input v-model="form.cargoInfo " style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="货物重量">
              <el-input v-model="form.cargoWeight " style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="货物状态">
              <el-input v-model="form.cargoSituation " style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="货物位置">
              <el-input v-model="form.cargoPosition" style="width: 80%"></el-input>
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


        <el-dialog v-model="dialogVisible1" title="车辆分配" width="40%" top="20vh">
          <el-table :data="vehicleData" style="width: 90%">
            <el-table-column prop="id" label="车辆ID" width="200"/>
            <el-table-column prop="vehicleInfo" label="车辆信息" width="200"/>

            <el-table-column fixed="right" label="操作" width="200">
              <template #default="scope">
                <el-button @click="confirmVehicle(scope.row)" type="primary" v-show="confirmAvailable()">分配</el-button>
                <el-button @click="cancelVehicle()" type="danger" v-show="cancelAvailable(scope.row)">取消分配</el-button>
              </template>
            </el-table-column>
          </el-table>

        </el-dialog>
      </div>
    </div>
  </template>


<script>
import request from "@/utils/request";
import {ElMessage} from "element-plus";

export default {
  name: "Transport",
  components: {},
  data() {
    return {
      user:{},
      form:{},
      VTForm:{},
      dialogVisible:false,
      dialogVisible1:false,
      search: '',
      currentPage4:1,
      pageSize:10,
      total:10,
      tableData: [
      ],
      vehicleData:[]
    }
  },
  created() {
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)
    this.load();
  },
  methods: {
    vehicleSave(){
        request.post("/vehicleTransport",this.VTForm).then(res => {
          console.log(res);
          if(res.code === '0'){
            ElMessage({
              type:"success",
              message:"车辆分配成功"
            })
            this.load()//刷新数据
            this.dialogVisible1=false;
          }
          else{
            ElMessage({
              type:"error",
              message:res.msg
            })
          }
        })
      },
    confirmVehicle(row){
      this.form.vehicleId = row.id;
      this.form.vehicleInfo = row.vehicleInfo;
      this.save();
      this.VTForm.vehicleId = row.id;
      this.VTForm.transportId = this.form.id;
      this.vehicleSave();
    },
    cancelVehicle(id){
      this.VTForm.vehicleId = this.form.vehicleId;
      this.VTForm.transportId = this.form.id;
      request.delete("/vehicleTransport/" + this.form.id).then(res =>{
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
      this.form.vehicleId = 0;
      this.form.vehicleInfo ="";
      this.save();
    },
    confirmAvailable(row){
      if(this.form.vehicleId == 0){
        return true;
      }
      else
        return false;
    },
    cancelAvailable(row){
      if(this.form.vehicleId == row.id){
        return true;
      }
      else
        return false;
    },
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

      request.get("/vehicle",{params:{
            pageNum:1,
            pageSize:100
      }}
      ).then(res=>{
        console.log(res);
        this.vehicleData = res.data.records;
      })
    },
    add(){
      this.dialogVisible = true;
      this.form = {};
    },
    save(){
      if(this.form.id){
        request.put("/transport",this.form).then(res => {
          console.log(res);
          if(res.code === '0'){
            ElMessage({
              type:"success",
              message:"修改成功"
            })
          }
          else{
            ElMessage({
              type:"error",
              message:res.msg
            })
          }
        });
        this.load()//刷新数据
        this.dialogVisible=false;
      }
      else{
        request.post("/transport",this.form).then(res => {
          console.log(res);
          if(res.code === '0'){
            ElMessage({
              type:"success",
              message:"新增成功"
            })
          }
          else{
            ElMessage({
              type:"error",
              message:res.msg
            })
          }
        })
        this.load()//刷新数据
        this.dialogVisible=false;
      }
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogVisible= true;
    },
    handleChoose(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogVisible1= true;
    },
    handleSizeChange(pageSize){ //改变页面个数触发
      this.pageSize = pageSize
      this.load()
    },
    handleDelete(id){
      request.delete("/transport/" + id).then(res =>{
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
  }
}
</script>

<style scoped>

</style>