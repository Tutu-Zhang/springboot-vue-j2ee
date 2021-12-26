
  <template>
    <div style="padding: 10px">
      <!--    功能区-->
      <!--    搜索区-->
      <div style="margin: 10px 0;">
        <el-input v-model="search" placeholder="请输入" style="width: 20%" clearable/>
        <el-button type="primary" style="margin-left: 10px" @click="load">查询</el-button>
      </div>
      <el-table :data="tableData" border stripe style="width: 100%">
        <el-table-column prop="id" label="物流单号" width="200" sortable/>
        <el-table-column prop="sender" label="发件人" width="200"/>
        <el-table-column prop="senderAddress" label="发件人地址"/>
        <el-table-column prop="senderNum" label="发件人联系方式"/>
        <el-table-column prop="cargoSituation" label="货物状态"/>

        <el-table-column fixed="right" label="操作" width="200">
          <template #default="scope">
            <el-button @click="handleEdit(scope.row)" type="info">详细</el-button>

            <el-popconfirm title="确认收货？" @confirm="handleReceive(scope.row)">
              <template #reference>
                <el-button type="warning" :disabled="receivable(scope.row.cargoSituation)">收货</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>

      <el-dialog v-model="dialogVisible" title="货物运输状态" width="30%">
        <el-descriptions title="">
          <el-descriptions-item label="详细信息">{{form.cargoInfo}}</el-descriptions-item>
          <el-descriptions-item label="货物重量">{{form.cargoWeight}}</el-descriptions-item>
          <el-descriptions-item label="当前位置">{{form.cargoPosition}}</el-descriptions-item>
        </el-descriptions>
      </el-dialog>

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
      dialogVisible:false,
      search: '',
      receive:'',
      currentPage4:1,
      pageSize:10,
      total:10,
      tableData: [
      ]
    }
  },
  created() {
    this.refreshUser();
    this.load();
  },
  methods: {
    refreshUser(){
      let userJson = sessionStorage.getItem("user");
      if(!userJson){
        return
      }
      let userId = JSON.parse(userJson).id;
      //从后台取出更新后的最新用户信息
      request.get("/user/" + userId).then(res => {
        this.user = res.data;
      })
    },
    receivable(situation){
      if(situation == "已发货"){
        return false;
      }
      else
        return true;
    },

    load(){
      request.get("/transport/receipt/" + JSON.parse(sessionStorage.getItem("user")).receiver
      ).then(res=>{
        console.log(res);
        this.tableData = res.data.records;
        this.total = res.data.total;
      })
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row));
      this.dialogVisible= true;
    },
    handleSizeChange(pageSize){ //改变页面个数触发
      this.pageSize = pageSize
      this.load()
    },
    handleReceive(row){
      request.put("/transport/receive", row).then(res =>{
        if(res.code === '0'){
          ElMessage({
            type:"success",
            message:"收货成功"
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