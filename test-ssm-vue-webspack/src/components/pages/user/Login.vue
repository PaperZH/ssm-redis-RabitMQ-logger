<template>
    <div class="login-wrap">
        <div align="center"><img src="../../../assets/logo.png"/></div> 
        <div class="ms-login">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="0px" class="demo-ruleForm">
            <div class="ms-title">后台管理系统</div>
                <el-form-item prop="username">
                    <el-input v-model="ruleForm.username" placeholder="username or email"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password" placeholder="password" v-model="ruleForm.password" @keyup.enter.native="submitForm('ruleForm')"></el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
                    <el-button type="primary" @click="register()">注册</el-button>
                </div>
            </el-form>
        </div>
    </div>
</template>

<script>
    import {uploadFile, login} from '../../../service/UserService.js'

    export default {
        data: function(){
            return {
                ruleForm: {
                    username: '',
                    password: ''
                },
                rules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            submitForm(formName) {
                const self = this;
                
                self.$refs[formName].validate((valid) => {
                    if (valid) {
                        var qs = require('qs');
                        var params = qs.stringify({
                            username: self.ruleForm.username,
                            password: self.ruleForm.password
                        });
                       
                        login(params).then(result => {                  
                            if (result.status) {
                               
                                 sessionStorage.setItem('login_username',self.ruleForm.username);
                                 sessionStorage.setItem('user_img',result.data.user.userImg)
                                 sessionStorage.setItem('token',result.data.token);
                                 sessionStorage.setItem('meuns',qs.stringify(result.data.meuns));
                                 sessionStorage.setItem('routers',result.data.routers);

                                  alert(result.data.token)
                                self.$router.push('/home');
                            } else {
                                self.$message.error(response.data.message);
                            }
                        }).catch(function (error) {
                            console.log(error);
                        });
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            register(){
                this.$router.push('/register')
            }
        }
    }
</script>

<style scoped>
    
    .ms-title{
        margin: -30px auto 40px auto;
        text-align: center;
        font-size:30px;
        color: #505458;
    }
    .ms-login{
        position: absolute;
        left:50%;
        top:50%;
        width:300px;
        height:160px;
        margin:-150px 0 0 -190px;
        padding:40px;
        border-radius: 5px;
        background: #fff;
    }
    .login-btn{
        text-align: center;
    }
    
</style>