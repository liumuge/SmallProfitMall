<template>
  <div class="header-wraper">
    <header class="blog-header">
      <h1 class="header-title">
        <router-link to="/">SmallProfit</router-link>
      </h1>
      <nav class="header-nav">
        <ul style="margin-right: -15rem">
          <li v-if="this.$route.path!='/login' && this.username==null">
            <router-link to="/login">您好,请登录</router-link>
          </li>
          <li v-if="this.username!=null">
            <el-dropdown trigger="click">
              <el-badge :is-dot="unreadQuantity!==0" class="header_sign">
                <span class="el-dropdown-link">
                  <img :src="avatar" style="width: 35px; border-radius: 10%;margin-top: 6%">
                        您好,{{username}}
                  <i class="el-icon-arrow-down el-icon--right"></i>
                </span>
              </el-badge>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>
                  <router-link to="/personalCenter">
                    <svg-icon name="personalCenter_h" class="icon"></svg-icon>
                    个人中心
                  </router-link>
                </el-dropdown-item>
                <el-dropdown-item divided>
                  <router-link to="/allOrders">
                    <svg-icon name="orderCenter" class="icon"></svg-icon>
                    订单中心
                  </router-link>
                </el-dropdown-item>
                <el-dropdown-item>
                  <router-link to="/favorite">
                    <svg-icon name="favorite_h" class="icon"></svg-icon>
                    收藏
                  </router-link>
                </el-dropdown-item>
                <el-dropdown-item>
                  <router-link to="/messageCenter">
                    <el-badge :value="unreadQuantity===0?'':unreadQuantity" :max="99">
                      <svg-icon name="message" class="icon"></svg-icon>
                      消息中心
                    </el-badge>
                  </router-link>
                </el-dropdown-item>
                <el-dropdown-item>
                  <router-link @click.native="exit" to="/login">
                    <svg-icon name="exit" class="icon"></svg-icon>
                    退出
                  </router-link>
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </li>
        </ul>
      </nav>
    </header>
  </div>
</template>
<script>
  import {mapActions} from "vuex";

  export default {
    name: "Header",
    data() {
      return {
        username: null,
        avatar: 'http://img.isdfmk.xyz/iduyadfgjdekldjhf.png',
      };
    },
    computed: {
      //vuex
      websocketStatus() {
        return this.$store.state.websocketStatus;
      },
      unreadQuantity:{
        get(){
          return this.$store.state.unreadQuantity;
        },
        set(){

        }
      },
    },
    methods: {
      ...mapActions([
        "modifyWebsocketStatus",
        "modifyUnreadQuantity"
      ]),
      //退出
      exit() {
        sessionStorage.clear();
        this.$router.push("/login");
        this.socketApi.close();
      },
      //新消息消息
      newMessage(msg){
        this.modifyUnreadQuantity(msg.queryResultString.unreadQuantity);
        sessionStorage.setItem("unreadQuantity",JSON.stringify(msg.queryResultString.unreadQuantity));
      }
    },
    created() {
      if (sessionStorage.getItem("uId") && !this.websocketStatus){
          this.socketApi.initWebSocket();
          this.modifyWebsocketStatus(this.socketApi.getWebsocketStatus());
      }
      this.avatar = sessionStorage.getItem("avatar");
      this.username = sessionStorage.getItem("username");
      if (sessionStorage.getItem("unreadQuantity")){
        this.modifyUnreadQuantity(JSON.parse(sessionStorage.getItem("unreadQuantity")));
      }
      //接收新消息
      this.socketApi.depositMethod(80000, this.newMessage);
    },
  };
</script>
<style scoped>
  .header-wraper {
    position: fixed;
    top: 0;
    display: flex;
    width: 100%;
    height: 50px;
    line-height: 50px;
    border-bottom: 1px solid #eee;
    z-index: 999;
    background-color: #fff;
  }

  .blog-header {
    display: flex;
    width: 960px;
    margin: 0 auto;
    justify-content: space-between;
    padding: 0 15px;
  }

  .header-title {
    font-size: 20px;
    font-weight: 700;
  }

  .header-nav {
    display: -webkit-box;
    margin-right: 13rem;
  }

  .blog-header .header-nav ul {
    list-style: none;
  }

  .blog-header .header-nav li {
    display: inline-block;
  }

  .blog-header .header-nav li a {
    padding: 0 15px;
  }

  .icon {
    width: 16px;
    height: 16px;
  }
  .header_sign /deep/ .el-badge__content.is-dot{
    height: 13px;
    width: 13px;
    padding: 0;
    border-radius: 50%;
    margin-top: 10%;
  }
</style>
