<template>
  <el-col :span="15" :push="3">
    <el-card v-if="emailTypeM==1">
      <div slot="header" class="email_type1_header">
        绑定邮箱
      </div>
      <div>
        <el-row class="email_type1_main">
          <el-col :span="15" :push="4">
            <div>
              <div>
                <el-input
                    autofocus
                    type='text'
                    placeholder="请输邮箱"
                    v-model="email"
                    clearable
                    prefix-icon="el-icon-mobile-phone"
                    class="email_type1_main_input"
                >
                </el-input>
              </div>
              <div style="margin-bottom: 10px">
                <el-input
                    autofocus
                    type='text'
                    placeholder="请输入验证码"
                    v-model="emailCode"
                    maxlength="4"
                    show-word-limit
                    clearable
                    prefix-icon="el-icon-mobile-phone"
                    class="email_type1_main_code"
                >
                </el-input>
                <el-button type="primary" @click="getEmailCode(1)" :disabled="!emailBtnStatus"
                           style="width: 120px">
                  {{emailBtnStatus ?'获取验证码':`重新获取(${emailCountDownTime})`}}
                </el-button>
              </div>
              <div style="">
                <el-button type="primary" class="email_type1_main_btn"
                           @click="verificationEmailCode()" :loading="verification_btn">
                  {{verification_btn_content}}
                </el-button>
              </div>
              <div>
                <el-button type="primary" class="email_type1_main_cancel"
                           @click="cancelModifyEmail">
                  取消
                </el-button>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </el-card>
    <el-card v-if="emailTypeM==2">
      <div slot="header" class="email_type1_header">
        修改邮箱
      </div>
      <div>
        <el-steps :active="activeEmail" simple :align-center="true">
          <el-step title="选择验证方式" icon="el-icon-edit"></el-step>
          <el-step title="验证码" icon="el-icon-mobile-phone"></el-step>
          <el-step title="验证新邮箱" icon="el-icon-mobile-phone"></el-step>
        </el-steps>
        <el-row class="email_type2_main">
          <el-col :span="15" :push="3">
            <div v-if="activeEmail==1">
              <h3 style="margin-right: 32%">请选择校验方式</h3>
              <div class="email_type2_main_div">
                <template>
                  <el-radio v-model="verificationType" label="1">
                    <svg-icon name="phone" style="margin-bottom: -8%"/>
                    手机
                  </el-radio>
                  <el-radio v-model="verificationType" label="2">
                    <svg-icon name="email" style="margin-bottom: -8%"/>
                    邮箱
                  </el-radio>
                </template>
              </div>
              <div style="margin: 3% 0 3% 0;">
                <el-button type="primary" class="ver_btn"
                           @click="getCode(1)" :loading="sendCode_btn">
                  {{sendCode_btn_content}}
                </el-button>
              </div>
              <div>
                <el-button type="primary" class="ver_btn"
                           @click="cancelModifyEmail">
                  取消
                </el-button>
              </div>
            </div>
            <div v-if="activeEmail==2">
              <div>
                <el-input
                    autofocus
                    type='text'
                    placeholder="请输入验证码"
                    v-model="code"
                    maxlength="4"
                    show-word-limit
                    clearable
                    prefix-icon="el-icon-mobile-phone"
                    class="email_type2_main_code"
                >
                </el-input>
                <el-button type="primary" @click="getCode(1)" :disabled="!mdEmailBtnStatus"
                           style="width: 120px">
                  {{mdEmailBtnStatus?'获取验证码':`重新获取(${mdEmailCountDownTime})`}}
                </el-button>
              </div>
              <div>
                <el-button type="primary" class="email_type2_main_btn"
                           @click="verificationMdCode" :loading="verification_btn">
                  {{verification_btn_content}}
                </el-button>
              </div>
              <div>
                <el-button type="primary" class="email_type2_main_cancel"
                           @click="cancelModifyEmail">
                  取消
                </el-button>
              </div>
            </div>
            <div v-if="activeEmail==3">
              <div>
                <el-input
                    autofocus
                    type='text'
                    placeholder="请输入新邮箱"
                    v-model="email"
                    clearable
                    prefix-icon="el-icon-mobile-phone"
                    class="email_type2_main_newEmail"
                >
                </el-input>
              </div>
              <div>
                <el-input
                    autofocus
                    type='text'
                    placeholder="请输入验证码"
                    v-model="emailCode"
                    maxlength="4"
                    show-word-limit
                    clearable
                    prefix-icon="el-icon-mobile-phone"
                    class="email_type2_main_codeNew"
                >
                </el-input>
                <el-button type="primary" @click="getEmailCode(2)"
                           :disabled="!mdNewEmailBtnStatus"
                           style="width: 120px">
                  {{mdNewEmailBtnStatus ?'获取验证码':`重新获取(${mdNewEmailCountDownTime})`}}
                </el-button>
              </div>
              <div>
                <el-button type="primary" class="email_type1_main_btn"
                           @click="verificationEmailCode" :loading="verification_btn">
                  {{verification_btn_content}}
                </el-button>
              </div>
              <div>
                <el-button type="primary" class="email_type1_main_cancel"
                           @click="cancelModifyEmail">
                  取消
                </el-button>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </el-card>
    <el-card v-if="emailTypeM==3">
      <div slot="header" class="email_type1_header">
        解绑邮箱
      </div>
      <div>
        <el-row class="email_type2_main">
          <el-col :span="15" :push="4">
            <div>
              <h3 style="margin-right: 38%">请选择校验方式</h3>
              <div class="email_type3_main_div">
                <template>
                  <el-radio v-model="verificationType" label="1">
                    <svg-icon name="phone" style="margin-bottom: -8%"/>
                    手机
                  </el-radio>
                  <el-radio v-model="verificationType" label="2">
                    <svg-icon name="email" style="margin-bottom: -8%"/>
                    邮箱
                  </el-radio>
                </template>
              </div>
              <div>
                <el-input
                    autofocus
                    type='text'
                    placeholder="请输入验证码"
                    v-model="code"
                    maxlength="4"
                    show-word-limit
                    clearable
                    prefix-icon="el-icon-mobile-phone"
                    class="email_type2_main_codeNew"
                >
                </el-input>
                <el-button type="primary" @click="getCode(2)"
                           :disabled="!untieEmailBtnStatus"
                           style="width: 120px">
                  {{untieEmailBtnStatus ?'获取验证码':`重新获取(${untieEmailCountDownTime})`}}
                </el-button>
              </div>
              <div>
                <el-button type="primary" class="email_type2_main_newEmail"
                           @click="verificationUntieEmail()" :loading="verification_btn">
                  {{verification_btn_content}}
                </el-button>
              </div>
              <div>
                <el-button type="primary" class="email_type3_main_cancel"
                           @click="cancelModifyEmail">
                  取消
                </el-button>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
    </el-card>
  </el-col>
</template>

<script>
  import *as userApi from '../../api/page/user'  //*as别名
  import encryption from "../../util/encryption";
  export default {
    inject: ["reload"],
    name: "emailModify",
    data() {
      return {
        //校验方式
        verificationType: "2",
        //获取邮箱验证码的到计时
        emailBtnStatus: true,
        emailCountDownTime: 60,
        emailTimer: 0,
        //修改邮时验证码的倒计时
        mdEmailBtnStatus: true,
        mdEmailCountDownTime: 60,
        mdEmailTimer: 0,
        //验证新邮箱的倒计时
        mdNewEmailBtnStatus: true,
        mdNewEmailCountDownTime: 60,
        mdNewEmailTimer: 0,
        //解绑邮箱的倒计时
        untieEmailBtnStatus: true,
        untieEmailCountDownTime: 60,
        untieEmailTimer: 0,
        //邮箱
        email: '',
        //绑定时邮箱验证码
        emailCode: '',
        //验证按钮
        verification_btn: false,
        verification_btn_content: '验证',
        //发送验证码按钮
        sendCode_btn: false,
        sendCode_btn_content: "获取验证码",
        //邮箱验步数
        activeEmail: 1,
        emailTypeM: this.emailType,
        //修改邮箱时的验证码
        code: "",
      }
    },
    props: {
      //邮箱变更类型
      emailType: {
        type: Number,
        required: false,
        default: 1
      }
    },
    methods: {
      //取消
      cancelModifyEmail() {
        sessionStorage.setItem('email_active', "1");
        sessionStorage.setItem("emailSign", JSON.stringify(false));
        this.reload();
      },
      //绑定邮箱时校验邮箱验证码
      verificationEmailCode() {
        const code = /^[0-9]*$/
        const mailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/
        if (!this.email) {
          this.$message({
            message: "请输入邮箱",
            type: "warning"
          });
        } else if (!mailReg.test(this.email)) {
          this.$message({
            message: "请输入正确的邮箱格式",
            type: "warning"
          });
        } else if (!code.test(this.emailCode) || this.emailCode == '' || this.emailCode.length
            < 4) {
          this.$message({
            message: "请输入正确格式的验证码",
            type: "warning"
          });
        } else {
          this.verification_btn_content = '验证中..',
              this.verification_btn = true;
          let formData = new FormData()
          formData.append('userId', sessionStorage.getItem("uId"))
          formData.append('email', encryption.encrypt(this.email));
          formData.append('verification', this.emailCode);
          userApi.validationEmail(formData).then(res => {
            if (res.success) {
              this.$message({
                message: "邮箱验证成功!",
                type: "success",
              })
              this.verification_btn_content = '验证',
                  this.verification_btn = false;
              this.cancelModifyEmail();
            } else {
              this.$message({
                message: "邮箱绑定失败!",
                type: "error",
              })
              this.verification_btn_content = '验证',
                  this.verification_btn = false;
            }
          })

        }

      },
      //获取邮箱验证码
      getEmailCode(standard) {
        const mailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/
        if (!this.email) {
          this.$message({
            message: "请输入邮箱",
            type: "warning"
          });
        } else if (!mailReg.test(this.email)) {
          this.$message({
            message: "请输入正确的邮箱格式",
            type: "warning"
          });
        } else {
          if (standard == 1) {
            this.GetCodeEmail();
          } else {
            this.GetCodeMdNewEmail();
          }
          let formData = new FormData();
          formData.append('uId', sessionStorage.getItem("uId"));
          formData.append('email', encryption.encrypt(this.email));
          userApi.getEmailCode(formData).then(res => {
            if (res.success) {
              this.$message({
                message: "邮箱验证码已发送",
                type: "success",
              })
            } else {
              this.$message({
                message: "邮箱验证码发送失败,请检查邮箱",
                type: "error",
              })
            }
          })
        }

      },
      //修改邮箱时获取验证码
      getCode(standard) {
        this.sendCode_btn = true;
        this.sendCode_btn_content = "发送中";
        let formData = new FormData()
        formData.append('userId', sessionStorage.getItem("uId"))
        formData.append('verificationType', this.verificationType);
        userApi.getCode(formData).then(res => {
          if (res.success) {
            this.$message({
              message: "验证码发送成功",
              type: "success",
            })
            this.sendCode_btn = false;
            this.sendCode_btn_content = "获取验证码";
            if (standard == 1) {
              this.GetCodeMdEmail();
              this.activeEmail = 2;
              sessionStorage.setItem("UpdateMailingCode", res.queryResult.list[0]);
              sessionStorage.setItem('email_active', JSON.stringify(this.activeEmail));
            } else {
              //获取解绑邮箱的验证码
              this.GetCodeUntieEmail();
              sessionStorage.setItem("UpdateMailingCode", res.queryResult.list[0]);
            }
          } else {
            this.$message({
              message: "验证码发送失败,请稍后重试",
              type: "error",
            })
            this.sendCode_btn = false;
            this.sendCode_btn_content = "获取验证码";
          }
        })
      },
      //验证修改邮箱时的验证码
      verificationMdCode() {
        const codeMail = /^[0-9]*$/
        if (this.code == '' || this.code.length < 4 || !codeMail.test(this.code)) {
          this.$message({
            message: "请输入正确格式的验证码",
            type: "warning"
          });
        } else {
          this.verification_btn_content = '验证中..',
              this.verification_btn = true;
          let formData = new FormData()
          formData.append('userId', sessionStorage.getItem("uId"))
          formData.append('account', sessionStorage.getItem("UpdateMailingCode"));
          formData.append('verificationType', this.verificationType);
          formData.append('verification', this.code);
          userApi.verificationModifyEmailCode(formData).then(res => {
            if (res.success) {
              this.$message({
                message: "验证成功",
                type: "success",
              })
              this.verification_btn_content = '验证',
                  this.verification_btn = false;
              this.activeEmail = 3;
              sessionStorage.setItem('email_active', JSON.stringify(this.activeEmail));
            } else {
              this.$message({
                message: "验证失败,请重试",
                type: "error",
              })
              this.verification_btn_content = '验证',
                  this.verification_btn = false;
            }
          })
        }
      },
      //验证解绑邮箱
      verificationUntieEmail() {
        const codeMail = /^[0-9]*$/
        if (!codeMail.test(this.code) || this.code == '' || this.code.length < 4) {
          this.$message({
            message: "请输入正确格式的验证码",
            type: "warning"
          });
        } else {
          this.verification_btn_content = '验证中..',
              this.verification_btn = true;
          let formData = new FormData();
          formData.append('validationFunctions', "3");
          formData.append('userId', sessionStorage.getItem("uId"))
          formData.append('account', sessionStorage.getItem("UpdateMailingCode"));
          formData.append('verification', this.code);
          formData.append('verificationType', this.verificationType);
          userApi.verificationModifyEmailCode(formData).then(res => {
            if (res.success) {
              this.$message({
                message: "邮箱解绑成功!",
                type: "success",
              })
              this.verification_btn_content = '验证',
                  this.verification_btn = false;
              this.cancelModifyEmail();
            } else {
              this.$message({
                message: "验证失败,请重试",
                type: "error",
              })
              this.verification_btn_content = '验证',
                  this.verification_btn = false;
            }
          }).catch(error => {
            this.verification_btn_content = '验证',
                this.verification_btn = false;
          })
        }

      },
      //绑定邮箱的到计时
      GetCodeEmail() {
        let endMsResNew = new Date().getTime() + 60000;
        //Setitem 为封装 localStoryge 方法
        sessionStorage.setItem('md_myEndTimeEmail', JSON.stringify(endMsResNew));
        this.codeCountDownEmail(endMsResNew)
      },
      codeCountDownEmail(endMsResNew) {
        this.emailBtnStatus = false;
        this.emailCountDownTime = Math.ceil((endMsResNew - new Date().getTime()) / 1000);
        this.emailTimer = setTimeout(() => {
          this.emailCountDownTime--
          if (this.emailCountDownTime < 1) {
            this.emailBtnStatus = true
            this.emailCountDownTime = 60
            clearTimeout(this.emailTimer)
            sessionStorage.removeItem('md_myEndTimeEmail')
          } else {
            this.codeCountDownEmail(endMsResNew)
          }
        }, 1000)
      },
      //验证旧邮箱的到计时
      GetCodeMdEmail() {
        let endMsResNew = new Date().getTime() + 60000;
        //Setitem 为封装 localStoryge 方法
        sessionStorage.setItem('md_myEndTimeMdEmail', JSON.stringify(endMsResNew));
        this.codeCountDownMdEmail(endMsResNew)
      },
      codeCountDownMdEmail(endMsResNew) {
        this.mdEmailBtnStatus = false;
        this.mdEmailCountDownTime = Math.ceil((endMsResNew - new Date().getTime()) / 1000);
        this.mdEmailTimer = setTimeout(() => {
          this.mdEmailCountDownTime--
          if (this.mdEmailCountDownTime < 1) {
            this.mdEmailBtnStatus = true
            this.mdEmailCountDownTime = 60
            clearTimeout(this.mdEmailTimer)
            sessionStorage.removeItem('md_myEndTimeMdEmail')
          } else {
            this.codeCountDownMdEmail(endMsResNew)
          }
        }, 1000)
      },
      //验证新邮箱的到计时
      GetCodeMdNewEmail() {
        let endMsResNew = new Date().getTime() + 60000;
        //Setitem 为封装 localStoryge 方法
        sessionStorage.setItem('md_myEndTimeMdNewEmail', JSON.stringify(endMsResNew));
        this.codeCountDownMdNewEmail(endMsResNew)
      },
      codeCountDownMdNewEmail(endMsResNew) {
        this.mdNewEmailBtnStatus = false;
        this.mdNewEmailCountDownTime = Math.ceil((endMsResNew - new Date().getTime()) / 1000);
        this.mdNewEmailTimer = setTimeout(() => {
          this.mdNewEmailCountDownTime--
          if (this.mdNewEmailCountDownTime < 1) {
            this.mdNewEmailBtnStatus = true
            this.mdNewEmailCountDownTime = 60
            clearTimeout(this.mdNewEmailTimer)
            sessionStorage.removeItem('md_myEndTimeMdNewEmail')
          } else {
            this.codeCountDownMdNewEmail(endMsResNew)
          }
        }, 1000)
      },
      //验证解绑邮箱的到计时
      GetCodeUntieEmail() {
        let endMsResNew = new Date().getTime() + 60000;
        //Setitem 为封装 localStoryge 方法
        sessionStorage.setItem('md_myEndTimeUntieEmail', JSON.stringify(endMsResNew));
        this.codeCountDownUntieEmail(endMsResNew)
      },
      codeCountDownUntieEmail(endMsResNew) {
        this.untieEmailBtnStatus = false;
        this.untieEmailCountDownTime = Math.ceil((endMsResNew - new Date().getTime()) / 1000);
        this.untieEmailTimer = setTimeout(() => {
          this.untieEmailCountDownTime--
          if (this.untieEmailCountDownTime < 1) {
            this.untieEmailBtnStatus = true
            this.untieEmailCountDownTime = 60
            clearTimeout(this.untieEmailTimer)
            sessionStorage.removeItem('md_myEndTimeUntieEmail')
          } else {
            this.codeCountDownUntieEmail(endMsResNew)
          }
        }, 1000)
      },

    },
    created() {
      if (sessionStorage.getItem('email_active') == "1") {
        this.activeEmail = 1;
      }
      if (sessionStorage.getItem('email_active') == "2") {
        this.activeEmail = 2;
      }
      if (sessionStorage.getItem('email_active') == "3") {
        this.activeEmail = 3;
      }
      this.emailTypeM = sessionStorage.getItem('emailType');

      let myEndTimeEmail = sessionStorage.getItem('md_myEndTimeEmail');
      if (myEndTimeEmail && this.codeCountDownEmail(myEndTimeEmail)) {
        this.codeCountDownEmail(myEndTimeEmail)
      }
      let myEndTimeMdEmail = sessionStorage.getItem('md_myEndTimeMdEmail');
      if (myEndTimeMdEmail && this.codeCountDownMdEmail(myEndTimeMdEmail)) {
        this.codeCountDownMdEmail(myEndTimeMdEmail)
      }
      let myEndTimeMdNewEmail = sessionStorage.getItem('md_myEndTimeMdNewEmail');
      if (myEndTimeMdNewEmail && this.codeCountDownMdNewEmail(myEndTimeMdNewEmail)) {
        this.codeCountDownMdNewEmail(myEndTimeMdNewEmail)
      }
      let myEndTimeUntieEmail = sessionStorage.getItem('md_myEndTimeUntieEmail');
      if (myEndTimeUntieEmail && this.codeCountDownUntieEmail(myEndTimeUntieEmail)) {
        this.codeCountDownUntieEmail(myEndTimeUntieEmail)
      }
    },
    beforeDestroy() {
      sessionStorage.setItem("email_active", '1');
    }
  }
</script>

<style scoped>
  .email_type3_main_div {
    margin: 5% 28% 8% 0
  }

  .email_type2_main_div {
    margin: 5% 21% 5% 0
  }

  .email_type3_main_cancel {
    width: 60%;
    margin-left: -5%
  }

  .email_type2_main {
    margin: 5% 0 5% 15%
  }

  .email_type2_main_codeNew {
    width: 31%;
    margin: 0 3% 1% -5%;
  }

  .email_type2_main_newEmail {
    width: 60%;
    margin: 2% 0 3% -5%
  }

  .email_type2_main_code {
    width: 36%;
    margin-right: 2%;
    margin-left: -5%
  }

  .email_type2_main_btn {
    width: 65%;
    margin: 3% 0 3% -5%;
  }

  .email_type2_main_cancel {
    width: 65%;
    margin-left: -5%
  }

  .email_type1_main_cancel {
    width: 60%;
    margin-left: -5%
  }

  .email_type1_main_btn {
    width: 60%;
    margin: 3% 0 3% -5%;
  }

  .email_type1_main_code {
    width: 35%;
    margin-right: 3%;
    margin-left: -5%;
  }

  .email_type1_main_input {
    width: 60%;
    margin-right: 5%;
    margin-bottom: 10px;
  }

  .email_type1_header {
    text-align: left;
    font-weight: bold
  }

  .ver_btn {
    width: 35%;
    margin-left: -20%
  }

  .email_type1_main {
    margin-top: 50px;
    margin-bottom: 50px
  }
</style>