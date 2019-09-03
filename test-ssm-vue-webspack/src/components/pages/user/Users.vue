<template>
  <div id="users">
    <el-form :inline="true" :model="formInline" class="demo-form-inline">

      <el-form-item label="姓名">
        <el-input v-model="formInline.userName" placeholder="姓名"></el-input>
      </el-form-item>                      
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
      </el-form-item>
        
    </el-form>
        <el-button type="success"  @click="showAddUserDialog">添加</el-button>
        <el-button type="warning"  @click="importTableList">导入
          <a href="javascript:;" style="display:none">
            <input ref="import" type="file" @change="importExcel(this)"  accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />
          </a>
    </el-button>
        <el-button type="primary"  @click="outExe">导出</el-button>
    <el-table :data="tableData" style="width: 100%" v-loading="loading2" element-loading-text="拼命加载中" @selection-change="handleSelectionChange">

      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="userName" label="姓名" width="180"></el-table-column>  
      <el-table-column prop="userImg" label="照片名称" width="200"></el-table-column> 
       <el-table-column label="头像" width="180">
                <template scope="scope"  v-if="scope.row.userImg!=null">
                    <img :src="baseImg+scope.row.userImg"  width="150" height="40"  @click="openImg(baseImg+scope.row.userImg)" 
                    style="position: absolute;
                          left:0;
                          top:15px;
                          width:40px;
                          height:40px;
                          border-radius: 50%;"/>
                    <a :href="baseImg+scope.row.userImg" :download="scope.row.userImg">下载</a>
                </template>
                
            </el-table-column>
      <el-table-column  label="操作" width="180">
        <template scope="scope">
          <el-button  type="text" size="small" @click="handleClick(scope.$index)">查看</el-button>
          <el-button type="text" size="small"  @click="showEditDialog(scope.$index)">编辑</el-button>
          <el-button type="text" size="small"  @click="removeData(scope.$index)">删除</el-button>
        </template>
      </el-table-column>
    </el-table> 
      
    <div class="block">
        <div class="r_btn">
          <el-button type="danger" @click="batchRemove">批量删除</el-button>
        </div>
        <div class="r_page">
          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="[10, 20, 30, 40]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total">
          </el-pagination>
        </div>
    </div>
      
    <!-- Form -->
    <el-dialog title="编辑信息" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="姓名" label-width="150">
          <el-input v-model="form.userName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" label-width="150">
          <el-input v-model="form.userPwd" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="update">确 定</el-button>
      </div>
    </el-dialog>

    <!-- Add User -->
    <el-dialog title="用户信息" :visible.sync="addUserDialogVisible">
      <el-form :model="addForm">
        <el-form-item label="姓名" label-width="150">
          <el-input v-model="addForm.userName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" label-width="150">
          <el-input v-model="addForm.userPwd" auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addUserDialogVisible= false">取 消</el-button>
        <el-button type="primary" @click="addUser">添 加</el-button>
      </div>
    </el-dialog>


  </div>
</template>
<style type="text/css" scoped>
  .block {
    margin-top: 20px;
    width: 50%;
  }
  .block .r_btn {
    float: left;
    width: 90px;
  }
  .block .r_page {
     float: right;
  }
</style>
<script type="text/javascript">
   import {uploadFile,getUserList,downloadFile,deleteUser, loadData,addUser,updateUser} from '../../../service/UserService.js'

  export default {
    data() {
      return {
        baseImg:'http://localhost:8082/img/',
        tableData: [],
        formInline: {
          userName: '',
          userId:''
        },
        currentPage:1,  
        total:0,
        pageSize:10,
       
        form: {
            userName: '',
            userImg:'',
            userId:'',
            userPwd:''
        },
        addForm: {
            userName: '',
            userImg:'',
            userId:'',
            userPwd:''
        },
        loading2: false,
        dialogFormVisible:false,
        addUserDialogVisible:false,
        selectedOptions: [],
        multipleSelection: []
      }
    },
    mounted() {
      // this.$store.dispatch('showHeader');
      this.loadData();
    },
    methods: {
      //加载数据
      loadData() {
        this.loading2 = true;
        var params = {
          page: this.currentPage, 
          pageSize: this.pageSize, 
          userName: this.formInline.userName, 
        };
        getUserList(params).then(function(result){
          this.tableData = result.data.list;
          this.total = result.data.total;
          this.loading2 = false;
          console.log(this.tableData)
        }.bind(this)).catch(function (error) {
            this.loading2 = false;
            console.log(error);
        }.bind(this));

      },
      //查询
      onSubmit() {
        this.loadData();
      },
      //改变分页大小
      handleSizeChange(val) {
        this.pageSize = val;
      },
      //跳转页数
      handleCurrentChange(val) {
        this.currentPage = val;
        this.loadData();
      },
      //打开编辑窗口
      showEditDialog(row){

        var data = this.tableData[row];
        console.log("editDialog------>>>>>>>>"+data.userId)
        this.form.userId = data.userId;
        this.form.userName = data.userName;
        this.form.userImg = data.userImg;
        this.form.userPwd = data.userPwd;
        console.log("editDialog------->>>>>>>"+this.form.userName)
        
        this.dialogFormVisible = true;
      },

      // 打开添加用户窗口
      showAddUserDialog(){
        this.addUserDialogVisible = true;
      },

    addUser(){
      var _this = this;
      var qs = require('qs');
      var addData = qs.stringify({
        userName:this.addForm.userName,
        userPwd:this.addForm.userPwd
      })
      addUser(addData).then(function(result){
        if(result.status){
          _this.$message({
            type:"success",
            message:"添加成功"
          })
          _this.addUserDialogVisible = false;
           _this.loadData();
        }else{
          _this.$message({
            type:"warning",
            message:"添加失败"
          })
          _this.addUserDialogVisible = true;
        }
      })
      
    },

    handleClick(row){
      var data = this.tableData[row];
        this.$alert('用户名：'+data.userName+'<br>'+
                    '用户ID：'+data.userId+'<br>'+
                    '密码：'+data.userPwd+'<br>'+
                    '图片地址：'+data.userImg+'<br>', '用户信息', {
         dangerouslyUseHTMLString: true
       });
    },
      
      //更新
      update(){

        var _this = this;
        if (this.form.name == "") {
          this.$message.error('姓名不能为空');
          return;
        }
         var qs = require('qs')
         var addData = qs.stringify({
          userName:this.addForm.userName,
          userPwd:this.addForm.userPwd
         })
         updateUser(addData).then(function(resut){
           if(result.status){
             _this.$message({
               type:"success",
               message:"更改成功"
             })
              _this.loadData();
           }else{
             _this.$message({
               type:"warning",
               message:"更改失败"
             })
           }
         })
        this.$message({
               message: '修改成功',
               type: 'success'
        });
        this.dialogFormVisible = false;
       
      },
      //打开图片
      openImg(param){
         window.open(param);
      }
      ,
      //删除
      removeData(row) {
        var data = this.tableData[row];
        var _this = this;
         var qs = require('qs');
          var deleteData = qs.stringify({
            userId:data.userId
          })
        this.$confirm('确定要删除"'+data.userName+'"?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
    
          console.log('delete------------>>>>>>>'+deleteData)
          deleteUser(deleteData).then(function(result){
            loadData();
            if(result.statues=true){
              _this.$message({
              type: 'success',
              message: '删除成功!'
             });
             _this.loadData();
            }else{
              _this.$message({
              type: 'danger',
              message: '删除失败!'
             });
            }
          });
        })
      },
      //导入表格
      importTableList(){
       this.$refs.import.click();
      },

      //导入表格函数
      importExcel(obj){
                 let _this = this;
                let inputDOM = this.$refs.inputer;
                // 通过DOM取文件数据
                this.file = event.currentTarget.files[0];
                var rABS = false; //是否将文件读取为二进制字符串
                var f = this.file;
                var reader = new FileReader();
                //if (!FileReader.prototype.readAsBinaryString) {
                FileReader.prototype.readAsBinaryString = function(f) {
                    var binary = "";
                    var rABS = false; //是否将文件读取为二进制字符串
                    var pt = this;
                    var wb; //读取完成的数据
                    var outdata;
                    var reader = new FileReader();
                    reader.onload = function(e) {
                        var bytes = new Uint8Array(reader.result);
                        var length = bytes.byteLength;
                        for(var i = 0; i < length; i++) {
                            binary += String.fromCharCode(bytes[i]);
                        }
                        var XLSX = require('xlsx');
                        if(rABS) {
                            wb = XLSX.read(btoa(fixdata(binary)), { //手动转化
                                type: 'base64'
                            });
                        } else {
                            wb = XLSX.read(binary, {
                                type: 'binary'
                            });
                        }
                        outdata = XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]]);//outdata就是你想要的东西
                       
                        let arr = []
                        outdata.map(v => {
                            let obj = {}
                            obj.userName = v.userName
                            obj.userImg = v.userImg
                            arr.push(obj)
                        })
                        _this.tableData = arr;
                        console.log(_this.tableData)
                        let para = {
                            //withList: JSON.stringify(this.da)
                            withList: arr
                        }
                        _this.$message({
                            message: '请耐心等待导入成功',
                            type: 'success'
                        });
                        // withImport(para).then(res => {
                        //     window.location.reload()
                        // })
                        
                    }
                    reader.readAsArrayBuffer(f);
                }
                if(rABS) {
                    reader.readAsArrayBuffer(f);
                } else {
                    reader.readAsBinaryString(f);
                }
      },

      //导出表格
      outExe(){
        this.$confirm('此操作将都出excel文件，是否继续?','提示',{
          confirmButtonText:'确定',
          cancelButtonText:'取消',
          type:'warning'
        }).then(()=>{
          this.export2Excel();
        }).catch(()=>{
          console.log('操作异常');
        })
      },
      
      export2Excel(){
        require.ensure([],()=>{
          const{export_json_to_excel} = require('../../../vendor/Export2Excel');
          const tHeader = ['userId','userImg','userName','userPwd'];
          const filterVal = ['userId','userImg','userName','userPwd'];
          const list = this.tableData;
          const data = this.formatJson(filterVal,list);
          export_json_to_excel(tHeader,data,this.tableData.userName+'excel')
        })
      },

      formatJson(filterVal, jsonData){
        return jsonData.map(v=>filterVal.map(j=>v[j]));
      },

      
      toggleSelection(rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row);
          });
        } else {
          this.$refs.multipleTable.clearSelection();
        }
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      batchRemove() {
        this.multipleSelection.forEach(row => {
          var qs = require('qs');
          var deleteData = qs.stringify({
            userId:row.userId
          })
          deleteUser(deleteData);
           console.log(row.userId);
        });
       loadData();
      }
    }
  }
</script>