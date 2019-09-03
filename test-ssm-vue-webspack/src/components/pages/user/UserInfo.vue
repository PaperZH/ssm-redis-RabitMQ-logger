  <template>
  <!-- :action="uploadUrl" -->

    <el-upload
      class="avatar-uploader"
      :show-file-list="false"
      :on-success="handleAvatarSuccess"
      :before-upload="beforeAvatarUpload">
      <div slot="tip" class="el-upload__tip">请选择要上传的图片，只能上传jpg/png文件，且不超过2MB</div>
      <img v-if="imageUrl" :src="imageUrl" class="avatar">
    
      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
  </el-upload>
  </template>
  <style>
    .avatar-uploader .el-upload {
      border: 1px dashed #d9d9d9;
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
      border-color: #20a0ff;
    }
    .avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 178px;
      height: 178px;
      line-height: 178px;
      text-align: center;
    }
    .avatar {
      width: 178px;
      height: 178px;
      display: block;
    }
  </style>
  <script type="text/javascript">
    import {uploadFile} from '../../../service/UserService.js'
    export default {
      data() {
        return {
        ImageUrl: ''
        };
      },
      computed:{
        uploadUrl() {
          return uploadFile;
        }
      },
      mounted() {
       // this.$store.dispatch('hideHeader');
      },
      methods: {
        handleAvatarSuccess(res, file) {
          console.log(res);
          this.imageUrl = URL.createObjectURL(file.raw);
          
        },
        beforeAvatarUpload(file) {
          const isJPG = file.type === 'image/jpeg';
          const isLt2M = file.size / 1024 / 1024 < 2;

          if (!isJPG) {
            this.$message.error('上传头像图片只能是 JPG 格式!');
            return false;
          }
          if (!isLt2M) {
            this.$message.error('上传头像图片大小不能超过 2MB!');
            return false;
          }
          let fd = new FormData();      
          var _this = this;                                                                                                                                                                                                                                                                                                                                        
          fd.append('file', file)
          fd.append('userName', sessionStorage.getItem('login_username'));
         uploadFile(fd).then(function(data){
           alert("上传返回信息"+data)
          if(data.status){
            _this.$message({
            type:'success',
            message:'上传成功'          
          })}else{
            _this.$message({
            type:'warning',
            message:'上传失败'  
          })
          }       
         });
         
        return false;
        }
      }
    }
  </script>