<template>
  <div class="register">
    <el-header height="70px">
      <Header/>
    </el-header>
    <el-main>
      <el-row type="flex" justify="" center>
        <el-col :span="12" :push="1">
          <div><img src="../../assets/images/login-png.png"></div>
        </el-col>
        <el-col :span="12">
          <div class="login">
            <el-card shadow="hover">
              <el-form :status-icon="true" :model="registerForm" :rules="rules" ref="registerForm">
                <el-form-item>
                  <h1 class="header-title">
                    <router-link to="/">SmallProfit</router-link>
                  </h1>
                </el-form-item>
                <el-form-item prop="phone">
                  <svg-icon name="login" class="icon"/>
                  <el-input placeholder="请输入手机号" v-model="registerForm.phone" clearable
                            class="username"/>
                </el-form-item>
                <el-form-item prop="password">
                  <svg-icon name="password" class="icon"/>
                  <el-input placeholder="请输入您要修改的密码" v-model="registerForm.password" show-password
                            class="username"
                            autocomplete="off" @keyup.enter.native="login"/>
                </el-form-item>
                <el-form-item prop="verify">
                  <svg-icon name="verification_code" class="icon"/>
                  <el-input placeholder="请输入手机验证码" v-model="registerForm.verify" clearable
                            class="username"
                            style="width: 170px;margin-right: 5px"
                            @keyup.enter.native="register('registerForm')"/>
                  <el-button type="primary" @click="getPhoneCode()"  :disabled="!BtnStatus" class="code_btn">
                    {{BtnStatus?'获取验证码':`重新获取(${countDownTime})`}}
                  </el-button>
                </el-form-item>
                <el-form-item>
                  <el-button class="login-btn" @click="resetPassword('registerForm')">重置密码</el-button>
                </el-form-item>
              </el-form>
              <router-link to="/login" style="margin: 7% 52% 0 0">已有账号,去登录</router-link>
              <router-link to="/login">去注册</router-link>
            </el-card>
          </div>
        </el-col>
      </el-row>
    </el-main>
    <el-footer>
      <Footer/>
    </el-footer>
  </div>
</template>

<script>
  const Header = ()=>import("../../components/pages/Header");
  const Footer = ()=>import("../../components/pages/Footer");
  import *as userApi from '../../api/page/user'
  import encryption from "../../util/encryption";

  export default {
    components: {Header, Footer},
    data() {
      let checkPhone = (rule, value, callback) => {
        const phoneReg = /^1[3|4|5|6|7|8][0-9]{9}$/
        if (!value) {
          return callback(new Error('电话号码不能为空'))
        }
        setTimeout(() => {
          if (!Number.isInteger(+value)) {
            callback(new Error('请输入数字值'))
          } else {
            if (phoneReg.test(value)) {
              callback()
            } else {
              callback(new Error('手机号码格式不正确'))
            }
          }
        }, 100)
      };
      return {
        verificationCode: "",
        show: true,
        count: '',
        BtnStatus: true,
        countDownTime: 60,
        registerForm: {
          phone: '',
          password: '',
          verify: ''
        },
        rules: {
          phone: [
            {required: true, validator: checkPhone, trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入要修改的密码', trigger: 'blur'},
            {min: 6, max: 18, message: '长度在 6到 18个字符', trigger: 'blur'},
          ],
          verify: [
            {required: true, message: '请输入手机验证码', trigger: 'blur'},
            {min: 4, max: 4, message: '手机验证码格式错误', trigger: 'blur'}
          ]
        }
      };
    },
    methods: {
      //重置密码
      resetPassword(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let registerFormParameter = {
              phone: '',
              password: '',
              verify: ''
            };
            registerFormParameter.password = encryption.encrypt(this.registerForm.password);
            registerFormParameter.phone = this.registerForm.phone;
            registerFormParameter.verify = this.registerForm.verify;
            userApi.resetPassword(registerFormParameter)
            .then(res => {
              if (res.success) {
                this.$message({
                  message: "重置密码成功",
                  type: "success"
                });
                this.$router.push({
                  path: "/login" //跳转的路径
                });
              } else {
                this.$message.error("重置密码失败,请稍后重试");
              }
            })
          }
        });
      },
      getPhoneCode() {
        if ((/^1[34578]\d{9}$/.test(this.registerForm.phone))) {
          userApi.get_Phone_Code_FP(
           this.registerForm.phone).then(res => {
            if (res.success) {
              this.$message({
                message: "短信发送成功",
                type: "success"
              });
            } else {
              if (res.code == 99999) {
                this.$message.error(res.message);
              } else {
                this.$message.error("手机号已经被注册或手机号不存在");
              }
            }
          })
          this.GetCode();
        } else {
          this.$message({
            message: "请输入正确的手机号",
            type: "error"
          });
        }
      },
      GetCode () {
        let endMsRes = (new Date()).getTime() + 60000;
        //Setitem 为封装 localStoryge 方法
        sessionStorage.setItem('myEndTime', JSON.stringify(endMsRes));
        this.codeCountDown(endMsRes)
      },
      codeCountDown ( endMsRes) {
        this.BtnStatus = false;
        this.countDownTime= Math.ceil((endMsRes - (new Date()).getTime()) / 1000)
        let time = setTimeout(() => {
          this.countDownTime--
          if (this.countDownTime< 1) {
            this.BtnStatus = true
            this.countDownTime= 60
            clearTimeout(time)
            sessionStorage.removeItem('myEndTime')

          } else {
            this.codeCountDown(endMsRes)
          }
        }, 1000)
      }
    },
    created () {
      let myEndTime= sessionStorage.getItem('myEndTime')
      if(myEndTime && this.codeCountDown(myEndTime)){
        this.codeCountDown(myEndTime)
      }
    }

  };
</script>

<style scoped>
  .icon {
    width: 32px;
    height: 32px;
    vertical-align: -0.5em;
    fill: currentColor;
    overflow: hidden;
    margin-right: 7px;
  }

  .header-title {
    font-size: 20px;
    font-weight: 700;
    margin: 0 0 20px 30px;
  }

  .login {
    max-width: 470px;
    margin: 0 auto 5px;
    position: relative;
    padding-left: 15px;
    padding-right: 15px;
    text-align: center;
  }

  .username {
    margin: 10px 0;
    width: 300px;
  }

  .login-btn {
    margin: 25px 0 0 40px;
    color: #fff;
    border: none;
    background-color: #42b983;
    width: 300px;
  }

  .login-btn:hover {
    background: #ECF5FF;
    color: #409EFF;
  }

  .code_btn {
    display: inline-block;
    width: 130px;
  }

  a {
    color: #42b983;
  }

  a:hover {
    color: #409EFF;
  }
</style>